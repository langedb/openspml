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
/*
 * kudos to SOAPClient4XG
 */
package org.openspml.v2.client;

import org.openspml.v2.transport.RPCRouterMonitor;
import org.openspml.v2.util.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;


public class SimpleSOAPClient {

    private static final String code_id = "$Id: SimpleSOAPClient.java,v 1.2 2006/08/30 18:02:59 kas Exp $";

    private String _username;
    private String _password;

    private String _proxyHost;
    private int _proxyPort;

    private String _header = null;
    private String _bodyAttributes = null;

    private RPCRouterMonitor _monitor = null;

    public void setHeader(String header) {
        _header = header;
    }

    public void setBodyAttributes(String attrs) {
        _bodyAttributes = attrs;
    }

    public void setMonitor(RPCRouterMonitor monitor) {
        _monitor = monitor;
    }

    public String sendAndReceive(final URL url,
                                 final String action,
                                 final String xml) throws IOException {

        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) connection;

        if (_username != null && _password != null) {
            Authenticator.setDefault(new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(_username,
                                                      _password.toCharArray());
                }
            });
        }

        if (_proxyHost != null) {
            String host = System.getProperty("http.proxyHost");
            if (host == null) {
                System.setProperty("http.proxyHost", _proxyHost);
                System.setProperty("http.proxyPort", _proxyPort + "");
            }
            else {
                System.out.println("WARNING: Did not override system's http.proxyHost settings.");
            }
        }

        String soapXml = addSOAPEnvelopeIfMissing(xml);

        byte[] xmlBytes = soapXml.getBytes();

        // Set the appropriate HTTP parameters.
        httpConn.setRequestProperty("Content-Length",
                                    String.valueOf(xmlBytes.length));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
        if (action != null) {
            httpConn.setRequestProperty("SOAPAction", action);
        }
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);

        if (_monitor != null)
            _monitor.send(soapXml);

        OutputStream out = httpConn.getOutputStream();
        out.write(xmlBytes);
        out.close();

        InputStreamReader isr =
                new InputStreamReader(httpConn.getInputStream());
        BufferedReader in = new BufferedReader(isr);

        String inputLine;
        StringBuffer result = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
            result.append(inputLine);

        in.close();

        String toReturn =  result.length() == 0 ? null : result.toString();

        if (_monitor != null)
            _monitor.receive(toReturn);

        return toReturn;
    }

    private String addSOAPEnvelopeIfMissing(String xml) {

        if (xml.indexOf("http://schemas.xmlsoap.org/soap/envelope/") > 0) {
            return xml;

        }

        StringBuffer buffer = new StringBuffer();
        if (_header == null) {
            String[] header = {"<?xml version='1.0' encoding='UTF-8'?>",
                               "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">",
                               "<SOAP-ENV:Header/>"};

            for (int k = 0; k < header.length; k++) {
                buffer.append(header[k]);
            }
        }
        else {
            buffer.append(_header);
        }

        if (_bodyAttributes == null) {
            buffer.append("<SOAP-ENV:Body>");
        }
        else {
            buffer.append("<SOAP-ENV:Body ");
            buffer.append(_bodyAttributes);
            buffer.append(">");
        }

        buffer.append(xml);

        String[] footer = {"</SOAP-ENV:Body>",
                           "</SOAP-ENV:Envelope>"};

        for (int k = 0; k < footer.length; k++) {
            buffer.append(footer[k]);
        }

        return buffer.toString();
    }

    public SimpleSOAPClient(String username,
                            String password,
                            String proxyHost,
                            int proxyPort) {

        this._username = username;
        this._password = password;

        this._proxyHost = proxyHost;
        this._proxyPort = proxyPort;

    }

    public SimpleSOAPClient(String username,
                            String password,
                            String proxyHost) {

        this(username, password, proxyHost, 8080);
    }

    public SimpleSOAPClient(String username, String password) {
        this(username, password, null);
    }

    public SimpleSOAPClient() {
        this(null, null, null);
    }

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.err.println("Usage:  java SimpleSOAPClient " +
                               "http://soapURL soapEnvelopeFile.xml" +
                               "[action=<SOAPAction>] <username> <password>");
            System.err.println("SOAPAction is optional.");
            System.exit(1);
        }

        String SOAPUrl = args[0];
        String xmlFile2Send = args[1];

        String SOAPAction = "";
        String username = null;
        String password = null;

        for (int k = 2; k < args.length; k++) {
            if (args[k].startsWith("action=")) {
                SOAPAction = args[k].substring("action=".length());
            }
            else if (username == null) {
                username = args[k];
            }
            else if (password == null) {
                password = args[k];
            }
        }

        URL url = new URL(SOAPUrl);
        String xml = FileUtil.readFile(xmlFile2Send);

        SimpleSOAPClient ssc = new SimpleSOAPClient(username, password);
        String result = ssc.sendAndReceive(url,
                                           SOAPAction,
                                           xml);

        // Read the response and write it to standard out.
        System.out.println("Result is:\n" + result);
    }

}
