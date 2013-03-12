/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at /OPENSPML_V2_TOOLKIT.LICENSE
 * or http://www.openspml.org/v2/licensing.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at /OPENSPML_V2_TOOLKIT.LICENSE.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */
/*
 * Copyright 2006 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */
package org.openspml.v2.transport;

import org.openspml.v2.util.ReflectionUtil;
import org.openspml.v2.util.Spml2Exception;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Component Name: org.openspml.v2.transport.RPCRouterServlet
 * <p/>
 * Author(s): Kent Spaulding
 * <p/>
 * Description:
 * <p/>
 * A message router; that passes on the work to a chain of RPCDispatcher
 * objects.  This is the only thing in this package that knows about
 * Servlets - and perhaps should be moved to a util.servlet package...
 * <p/>
 * To configure:
 * <p/>
 * <servlet>
 * <servlet-name>openspmlRouter</servlet-name>
 * <display-name>RPC Router</display-name>
 * <description>no description</description>
 * <servlet-class>
 * org.openspml.v2.transport.RPCRouterServlet
 * </servlet-class>
 * <init-param>
 * <param-name>dispatchers</param-name>
 * <param-value>com.something.CustomDispatcher</param-value>
 * </init-param>
 * <init-param>
 * <param-name>SpmlViaSoap.spmlMarshallers</param-name>
 * <param-value>csv string of marshaller class names</param-value>
 * </init-param>
 * <init-param>
 * <param-name>spmlMarshaller.somevarname</param-name>
 * <param-value>some value</param-value>
 * </init-param>
 * </servlet>
 * <p/>
 * The normal <servlet-name> for a SOAP router is often "rpcrouter".  Some
 * products use "rpcrouter2" - here we use "openspmlRouter" to avoid conflicts
 * if a site already has another router installed; and is not meant for
 * SPMLv2 requests.
 * <p/>
 * You can name this anything you want, but the corresponding name then
 * needs to be used when specifying the URL in the clients/requestors.
 * <p/>
 * The <init-param> named "dispatchers" contains a list of names of classes
 * that implement the RPCDispatcher interface.  It is represented in the
 * web.xml file as a single string, multiple class names are delimited
 * with a comma.  You only need to set this parameter if you want
 * to register your own custom dispatcher(s).  By default,
 * this router will add the following handler internally:
 * <p/>
 * org.openspml.v2.transport.SPMLViaSoapDispatcher  - for SOAP requests carrying SPML
 * <p/>
 * SPMLViaSoapDispatcher requires that you specify an init parameter
 * named "SpmlViaSoap.spmlMarshallers"  that is the name of classes implementing
 * the SPMLMarshaller interface.
 * <p/>
 * In this example, a test handler is
 * registered, though in practice this is always changed to a
 * vendor-specific handler class.
 * <p/>
 * A typical request formatted by the Apache SOAP client looks like:
 * <p/>
 * <?xml version='1.0' encoding='UTF-8'?>
 * <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance" xmlns:xsd="http://www.w3.org/1999/XMLSchema">
 * <SOAP-ENV:Body>
 * <ns1:xmlForRequest xmlns:ns1="namespace" SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
 * <someElement xsi:type="xsd:string">someString</someElement>
 * </ns1:xmlForRequest>
 * </SOAP-ENV:Body>
 * </SOAP-ENV:Envelope>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Jan 20, 2006
 */
public class RPCRouterServlet extends HttpServlet implements RPCRouter {

    private static final String code_id = "$Id: RPCRouterServlet.java,v 1.2 2006/09/25 18:50:07 kas Exp $";

    // Automatically registered dispatcher classes
    private static final String SPML_VIA_SOAP_DISPATCHER = "org.openspml.v2.transport.SPMLViaSoapDispatcher";

    /**
     * Array of registered RPCDispatchers.
     */
    private RPCDispatcher[] _dispatchers = new RPCDispatcher[0];

    /**
     * Flag to control console trace messages.
     */
    private boolean _trace;

    /**
     * Object to receive send/receive events.
     */
    private RPCRouterMonitor _monitor;

    //////////////////////////////////////////////////////////////////////
    //
    // Initialization
    //
    //////////////////////////////////////////////////////////////////////

    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        String s = config.getInitParameter("trace");
        if ("true".equals(s))
            _trace = true;

        List dispatcherClassnames = null;
        s = config.getInitParameter("dispatchers");
        if (s != null) {
            dispatcherClassnames = Arrays.asList(s.split(","));
        }

        if (dispatcherClassnames == null) {
            dispatcherClassnames = new ArrayList();
            dispatcherClassnames.add(SPML_VIA_SOAP_DISPATCHER);
        }

        String[] dispatcherNames =
                (String[]) dispatcherClassnames.toArray(new String[dispatcherClassnames.size()]);

        List dispatcherList = new ArrayList();
        Map configMap = getConfigMap(config);

        try {
            for (int k = 0; k < dispatcherNames.length; k++) {
                String dispatcherName = dispatcherNames[k];

                Object o = ReflectionUtil.instantiate(dispatcherName);
                if (o instanceof RPCDispatcher) {
                    dispatcherList.add(o);
                    // allow it to initialize
                    try {
                        ((RPCDispatcher) o).init(configMap);
                    }
                    catch (Spml2TransportException e) {
                        throw new ServletException(e);
                    }
                }
                else if (o != null) {
                    System.err.print(getClass() + ": " +
                                     dispatcherName +
                                     " does not implement " + RPCDispatcher.class.getName());
                }
            }

            _dispatchers = (RPCDispatcher[]) dispatcherList.toArray(new RPCDispatcher[dispatcherList.size()]);

            s = config.getInitParameter("monitor");
            if (s != null) {
                Object o = ReflectionUtil.instantiate(s);
                if (o instanceof RPCRouterMonitor) {
                    setMonitor((RPCRouterMonitor) o);
                }
                else if (o != null) {
                    System.err.println(getClass() + ": " +
                                       s +
                                       " does not implement " + RPCRouterMonitor.class.getName());
                }
            }
        }
        catch (Spml2Exception e) {
            throw new ServletException(e.getCause());
        }
    }

    /**
     * Convert the initParams to a Map of String->String
     * This allows Dispatchers to be unaware of Servlets.
     *
     * @param config
     * @return a Map built from the config.
     */
    private Map getConfigMap(ServletConfig config) {
        Map result = new HashMap();
        Enumeration e = config.getInitParameterNames();
        while (e.hasMoreElements()) {
            String name = e.nextElement().toString();
            result.put(name, config.getInitParameter(name));
        }
        return result;
    }

    /**
     * Allow the monitor to be set dynamically.
     */
    public void setMonitor(RPCRouterMonitor sm) {
        if (_monitor != null)
            _monitor.removeListener(this);
        _monitor = sm;
        if (_monitor != null) {
            _monitor.addListener(this);
            _monitor.trace("RPCRouterServlet is using this monitor.");
        }
    }

    /**
     * RPCRouter interface.
     */
    public void closed(RPCRouterMonitor m) {
        if (_monitor == m)
            _monitor = null;
    }

    //////////////////////////////////////////////////////////////////////
    //
    // Error Responses
    //
    //////////////////////////////////////////////////////////////////////

    private void sendError(HttpServletResponse response,
                           int code, String message)
            throws IOException {

        String err = "ERROR: " + message;
        if (_monitor != null)
            _monitor.send(err);

        response.setContentType("text/html; charset=UTF-8");
        response.setStatus(code);

        // I18N!!
        PrintWriter out = response.getWriter();
        out.println(err);
        out.flush();
    }

    private void sendError(HttpServletResponse response,
                           String message)
            throws IOException {

        sendError(response, 403, message);
    }

    private void sendResponse(HttpServletResponse response, String msg, String contentType)
            throws IOException {

        if (_monitor != null)
            _monitor.send(msg);

        response.setContentType(contentType);
        PrintWriter out = response.getWriter();
        out.println(msg);
        out.flush();
    }

    private void trace(String message) {
        if (_trace) { // yes, we are probably checking this twice,
            // best to not call if it is not set.
            System.out.print("RPCRouterServlet: ");
            System.out.println(message);
        }
    }

    //////////////////////////////////////////////////////////////////////
    //
    // Get/Post
    //
    //////////////////////////////////////////////////////////////////////

    /**
     * Called by the servlet container in response to a GET.
     * We're not expecting these here.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        sendError(res, getClass() + ": GET is unsupported");
    }

    /**
     * Get the post data as a String.
     */
    public String getPostDataAsString(HttpServletRequest req)
            throws IOException {

        String post = null;

        int length = req.getContentLength();
        if (length > 0) {

            // do this before building the input stream!
            setCharacterEncoding(req);

            byte[] bytes = new byte[length];
            ServletInputStream is = req.getInputStream();
            int read = 0;

            while (read < length) {
                int remaining = length - read;
                read += is.readLine(bytes, read, remaining);
            }

            post = new String(bytes, "UTF-8");
        }

        return post;
    }

    /**
     * Called by the servlet container in response to a POST.
     * We get the post data as a String, and pass it along to
     * a Call object for further processing.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String post = getPostDataAsString(req);

        if (_trace) {
            trace("\n" + post);
        }

        if (_monitor != null)
            _monitor.receive(post);

        try {
            String response = null;
            String contentType = null;

            for (int k = 0; response == null && k < _dispatchers.length; k++) {
                RPCDispatcher dispatcher = _dispatchers[k];
                response = dispatcher.dispatchRequest(post);
                if (response != null) {
                    contentType = dispatcher.getContentType();
                }
            }

            if (response == null) {
                sendError(res, getClass() + ": " +
                               "Unrecognized message.");
            }
            else {
                if (_trace) {
                    trace(" response:\n" + response);
                }
                sendResponse(res, response, contentType);
            }
        }
        catch (Throwable e) {
            // !! if we can get past request parsing, we should
            // always catch errors and turn them into a properly
            // formatted XML response with a fault element
            System.out.println("RPCRouterServlet: exception");
            e.printStackTrace();
            sendError(res, getClass() + ": " +
                           e.toString());
        }
    }

    ///////////////////////////////////////
    //  UTF-8 support
    ///////////////////////////////////////

    /**
     * Method available only in Servlet 2.3
     */
    private Method _setCharacterEncoding;
    private boolean _testedServletContainer;

    /**
     * If we're in a Servlet 2.3 environment, have to set some things
     * to get UTF-8 support.  But the methods aren't supported in
     * the older Servlet version that this is compiled with by default.
     */
    public void setCharacterEncoding(HttpServletRequest req) {

        if (!_testedServletContainer) {
            try {
                Class c = req.getClass();
                _setCharacterEncoding =
                        c.getMethod("setCharacterEncoding", new Class[]{String.class});
            }
            catch (Throwable t) {
            }
            _testedServletContainer = true;
        }

        if (_setCharacterEncoding != null) {
            try {
                // can we reuse the same method?
                _setCharacterEncoding.invoke(req, new String[]{"UTF-8"});
            }
            catch (Throwable t) {
                // must not be Servlet 2.3?
                _setCharacterEncoding = null;
            }
        }
    }
}
