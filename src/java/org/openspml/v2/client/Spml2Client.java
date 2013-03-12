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
package org.openspml.v2.client;

import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.msg.XMLUnmarshaller;
import org.openspml.v2.msg.spml.Request;
import org.openspml.v2.msg.spml.Response;
import org.openspml.v2.transport.RPCRouterMonitor;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.Spml2ExceptionWithResponse;
import org.openspml.v2.util.xml.ReflectiveDOMXMLUnmarshaller;
import org.openspml.v2.util.xml.ReflectiveXMLMarshaller;
import org.openspml.v2.util.xml.XmlUtil;

import java.net.MalformedURLException;
import java.net.URL;

// A class providing a convenient interface for sending and receiving
// SPML2 messages.  Using the SPML2 object model found in a nearby package.

/**
 * A class providing a mechanism to send and recieve SPML2 requests using the
 * Spml2Request and Spml2Response classes. <p> Once the request is formatted as
 * XML, it uses the <code>SOAPClient</code> class to handle the actual
 * communication.  When the XML response is received, it is parsed into a
 * Spml2Response and returned.
 */
public class Spml2Client {

    public static final String code_id = "$Id: Spml2Client.java,v 1.6 2006/06/13 22:50:49 kas Exp $";

    /**
     * We define one of these so we have a chance of using
     * different Soap Client implementations.
     */
    static public interface SOAPClientAdapter {

        public void setHeader(String s);

        public void setBodyAttributes(String s);

        public void setMonitor(RPCRouterMonitor sm);

        public String send(URL url, String action, String xml)
                throws Spml2Exception;
    }

    static class SimpleSOAPClientAdapter implements SOAPClientAdapter {

        private SimpleSOAPClient _client;

        SimpleSOAPClientAdapter(SimpleSOAPClient client) {
            _client = client;
        }

        public void setHeader(String s) {
            _client.setHeader(s);
        }

        public void setBodyAttributes(String s) {
            _client.setBodyAttributes(s);
        }

        public void setMonitor(RPCRouterMonitor sm) {
            _client.setMonitor(sm);
        }

        public String send(URL url, String action, String xml)
                throws Spml2Exception {
            try {
                return _client.sendAndReceive(url, action, xml);
            }
            catch (Exception e) {
                throw new Spml2Exception(e);
            }
        }
    }

    /**
     * This is an acton that can be sent as part of the soap
     */
    private String _action = null;

    /**
     * Use one of these to manage the basic SOAP communications.
     */
    private SOAPClientAdapter _client;

    /**
     * The URL we post to.
     */
    private URL _url;

    /**
     * Trace flag.
     */
    private boolean _trace;

    private SOAPClientAdapter createSOAPClient(String username,
                                               String pwd) {

        return new SimpleSOAPClientAdapter(new SimpleSOAPClient(username, pwd));
    }

    public Spml2Client(String url) throws Spml2Exception {
        this(url, null, null);
    }

    public Spml2Client(String url, String username, String pwd)
            throws Spml2Exception {
        try {
            setUrl(url);
            _client = createSOAPClient(username, pwd);
        }
        catch (MalformedURLException e) {
            throw new Spml2Exception(e);
        }
    }

    /**
     * Set the URL of a web service responding to SOAP requests.
     */
    private void setUrl(String s) throws MalformedURLException {
        setUrl(new URL(s));
    }

    /**
     * Set the URL of a web service responding to SOAP requests.
     */
    private void setUrl(URL url) {
        _url = url;
    }

    /**
     * Set an optional SOAP Header. The string is expected to contain well
     * formed XML.
     */
    public void setHeader(String s) {
        _client.setHeader(s);
    }

    /**
     * Use this action in the SOAPClient.
     * 
     * @param action
     */
    public void setSOAPAction(String action) {
        _action = action;
    }

    /**
     * Set an optional list of attributes for the SOAP Body. The string is
     * expected to contain a fragment of well formed XML attribute definitions,
     * "wsu:Id='myBody'" It is assumed for now that any namespace references in
     * the attribute names do not need to be formally declared in the
     * soap:Envelope.
     */
    public void setBodyAttributes(String s) {
        _client.setBodyAttributes(s);
    }

    public void setTrace(boolean b) {
        _trace = b;
    }

    /**
     * Install a SOAP message monitor.
     */
    public void setMonitor(RPCRouterMonitor m) {
        _client.setMonitor(m);
    }

    /**
     * Send an XmlObject as the body of a SOAP request. <p> We use an XmlObject
     * because XmlBeans makes it rather difficult to use anything else (to
     * narrow the type to a particular type in the schema.)  You should be
     * passing instances of the 'RequestType' enclosing 'Document' objects, e.g.
     * ListTargetsDocument and AddRequestDocument.  (These do NOT have a common
     * base class, and using RequestType sub-interfaces makes for odd tricks...
     * like seeting a requesttype to a Document...) <p> Not accounting for SOAP
     * faults...
     *
     * @param req An XmlOBject (*Document interface) that is the body of the
     *            request.
     * @return one of the *ResponseDocument types from the xmlbeans bindings.
     * @throws Spml2Exception
     * @throws Spml2ExceptionWithResponse
     */
    public Response send(Request req)
            throws Spml2Exception, Spml2ExceptionWithResponse {


        XMLMarshaller marshaller = new ReflectiveXMLMarshaller();
        String requestXML = req.toXML(marshaller);

        String responseXML = send(requestXML);

        if (_trace) {
            System.out.println(responseXML);
        }

        String payloadXML = XmlUtil.getSoapBodyContents(responseXML);
        XMLUnmarshaller unmarshaller = new ReflectiveDOMXMLUnmarshaller();
        Response response = (Response) unmarshaller.unmarshall(payloadXML);
        throwErrors(response);

        return response;
    }

    /**
     * Take an object and return the entire SOAP response as xml.
     *
     * @param req
     * @return SOAP (xml string)
     * @throws Spml2Exception
     */
    public String sendRequest(Request req)
            throws Spml2Exception {
        String toSend = req.toXML(new ReflectiveXMLMarshaller());
        return send(toSend);
    }

    /**
     * Send xml as a payload and return the entire SOAPResponse. Not accounting
     * for SOAP faults, caller must parse the response.
     *
     * @param xml
     * @return The raw SOAP response. (Envelope and Body still there.)
     * @throws Spml2Exception
     */
    public String send(String xml)
            throws Spml2Exception {

        if (_trace) {
            println("SpmlClient: sending to " + _url.toString());
            println(xml);
        }

        xml = _client.send(_url, _action, xml);

        if (_trace) {
            println("SpmlClient: received");
            println(xml);
        }

        return xml;
    }

    public static void println(String msg) {
        System.out.println(msg);
    }

    public void throwErrors(Response res) throws Spml2ExceptionWithResponse {

        if (res != null) {
            String[] msgs = res.getErrorMessages();
            if (msgs != null && msgs.length != 0) {
                throw new Spml2ExceptionWithResponse(msgs[0],res);
            }
        }
    }
}
