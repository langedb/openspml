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

import org.openspml.v2.msg.spml.ErrorCode;
import org.openspml.v2.msg.spml.Request;
import org.openspml.v2.msg.spml.Response;
import org.openspml.v2.msg.spml.StatusCode;
import org.openspml.v2.provider.SPMLExecutor;
import org.openspml.v2.provider.SPMLMarshaller;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.ReflectionUtil;
import org.openspml.v2.util.xml.XmlUtil;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This is a demonstration class for handling SOAP.
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Jan 20, 2006
 */
public class SPMLViaSoapDispatcher implements RPCDispatcher {

    private static final String code_id = "$Id: SPMLViaSoapDispatcher.java,v 1.5 2006/09/25 18:50:07 kas Exp $";

    private static final String INIT_PARAM_MARSHALLERS = "SpmlViaSoap.spmlMarshallers";
    private static final boolean REQUIRE_MARSHALLERS_PARAM = true;

    private static final String INIT_PARAM_EXECUTORS = "SpmlViaSoap.spmlExecutors";
    private static final boolean REQUIRE_EXECUTORS_PARAM = true;

    private SPMLMarshaller[] _marshallers;
    private SPMLExecutor[] _executors;

    public SPMLViaSoapDispatcher() {
    }

    /**
     * We want to run over the config and instantiate our marshallers
     * and excecutors.
     *
     * @param config
     * @throws Spml2TransportException
     */
    public void init(Map config) throws Spml2TransportException {

        // The router is included because it provides a useful
        // method or two.
        _marshallers = initMarshallers(config);
        _executors = initExecutors(config);

    }

    /**
     * This does the bulk of the work.  Uses the marshallers to find a
     * worthy request object; then finds an executor for the request.
     * Returns the result after executing it.
     * <p/>
     * Note that you might use different executors for sync and async.
     * <p/>
     *
     * @param inMessage
     * @return xml represeting the response.
     * @throws Spml2TransportException
     */
    public String dispatchRequest(String inMessage) throws Spml2TransportException {

        try {
            String unwrappedMessage = unwrapRequest(inMessage);

            Request request = null;
            SPMLMarshaller marshaller = null;
            for (int m = 0; request == null && m < _marshallers.length; m++) {
                marshaller = _marshallers[m];
                request = marshaller.unmarshallRequest(unwrappedMessage);
            }

            if (request == null) {
                return null;
            }

            // now we can perform the operation.
            Response response = null;
            for (int o = 0; response == null && o < _executors.length; o++) {
                SPMLExecutor executor = _executors[o];
                response = executor.execute(request);
            }

            if (response == null) {
                response = new Response();
                response.setError(ErrorCode.UNSUPPORTED_OPERATION);
                response.setStatus(StatusCode.FAILURE);
                response.addErrorMessage(request.getClass().getName());
            }

            String result = marshaller.marshallResponse(response);
            if (result == null) {
                // SMOKE:  Shouldn't this result in a SOAPFault?
                throw new Spml2TransportException("The marshaller, " + marshaller.getUniqueName() +
                                                  ", could not marshall:\n" + response);
            }

            return wrapResponse(result);
        }
        catch (Spml2Exception e) {
            // SMOKE:  we may need to catch these and do a SOAPFault... of perhaps
            // http errors suffice.
            throw new Spml2TransportException(e);
        }
    }

    public String getContentType() {
        return "text/xml; charset=UTF-8";
    }

    /**
     * Initialize the marshallers names in the config
     *
     * @param config
     * @return  the marhsallers that we instantiated from the config contents.
     * @throws Spml2TransportException
     */
    protected SPMLMarshaller[] initMarshallers(Map config) throws Spml2TransportException {

        String marshallers = (String) config.get(INIT_PARAM_MARSHALLERS);
        if (marshallers == null) {
            if (REQUIRE_MARSHALLERS_PARAM) {
                throw new Spml2TransportException("Missing init-param: " + INIT_PARAM_MARSHALLERS);
            }
            return new SPMLMarshaller[0];
        }

        try {
            List list = new ArrayList();
            String[] classNames = marshallers.split(",");
            for (int k = 0; k < classNames.length; k++) {
                Object o = ReflectionUtil.instantiate(classNames[k]);
                if (o instanceof SPMLMarshaller) {
                    SPMLMarshaller m = (SPMLMarshaller) o;
                    list.add(m);
                    // allow it to initialize
                    String prefix = INIT_PARAM_MARSHALLERS + "." + m.getUniqueName();
                    m.init(getInitArgsAsMap(prefix, config));
                }
                else if (o != null) {
                    System.err.print(getClass() + ": " +
                                     classNames[k] +
                                     " does not implement " + SPMLMarshaller.class.getName());
                }
            }

            if (REQUIRE_MARSHALLERS_PARAM && list.size() == 0) {
                throw new Spml2TransportException("The init-param, " + INIT_PARAM_MARSHALLERS +
                                                  ", did not define any valid SPMLMarshaller classes.");
            }

            return (SPMLMarshaller[]) list.toArray(new SPMLMarshaller[list.size()]);
        }
        catch (Spml2Exception e) {
            throw new Spml2TransportException(e);
        }
    }

    /**
     * Set up the executors list from the config.
     *
     * @param config
     * @return the executors we instantiated from the config contents
     * @throws Spml2TransportException
     */
    protected SPMLExecutor[] initExecutors(Map config) throws Spml2TransportException {

        try {
            String executors = (String) config.get(INIT_PARAM_EXECUTORS);
            if (executors == null) {
                if (REQUIRE_EXECUTORS_PARAM) {
                    throw new ServletException("Missing init-param: " + INIT_PARAM_EXECUTORS);
                }
                return new SPMLExecutor[0];
            }

            List list = new ArrayList();
            String[] classNames = executors.split(",");
            for (int k = 0; k < classNames.length; k++) {
                Object o = ReflectionUtil.instantiate(classNames[k]);
                if (o instanceof SPMLExecutor) {
                    SPMLExecutor op = (SPMLExecutor) o;
                    list.add(op);
                    String prefix = INIT_PARAM_EXECUTORS + "." + op.getUniqueName();
                    op.init(getInitArgsAsMap(prefix, config));
                 }
                else if (o != null) {
                    System.err.print(getClass() + ": " +
                                     classNames[k] +
                                     " does not implement " + SPMLExecutor.class.getName());
                }
            }

            if (REQUIRE_EXECUTORS_PARAM && list.size() == 0) {
                throw new Spml2TransportException("The init-param, " + INIT_PARAM_EXECUTORS +
                                                  ", did not define any valid SPMLExecutor classes.");
            }

            return (SPMLExecutor[]) list.toArray(new SPMLExecutor[list.size()]);

        }
        catch (Exception e) {
            throw new Spml2TransportException(e);
        }
    }

    /**
     * @param prefix
     * @param config
     * @return A Map of all the keys that started with the prefix
     */
    private Map getInitArgsAsMap(String prefix, Map config) {
        // walk the config and get all properties that start
        // with prefix
        Map result = new HashMap();
        Iterator iter = config.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            if (name.startsWith(prefix)) {
                String key = name.substring(prefix.length() + 1);
                result.put(key, config.get(name));
            }
        }
        return result;
    }

    /**
     * Pull out the Soap body
     *
     * @param message
     * @return  message xml without the SOAP elements... just the body.
     */
    protected String unwrapRequest(String message) throws Spml2Exception {
        return XmlUtil.getSoapBodyContents(message);
    }

    /**
     * Add a soap envelope.
     *
     * @param result
     * @return The result, wrapped in a SOAP Envelope.
     */
    protected String wrapResponse(String result) {
        StringBuffer b = new StringBuffer();
        b.append("<?xml version='1.0' encoding='UTF-8'?>\n");
        b.append("<SOAP-ENV:Envelope\n");
        b.append("  xmlns:SOAP-ENV='http://schemas.xmlsoap.org/soap/envelope/'>\n");
        b.append("<SOAP-ENV:Body>\n");

        b.append(result);

        b.append("</SOAP-ENV:Body>\n");
        b.append("</SOAP-ENV:Envelope>\n");

        return b.toString();
    }
}
