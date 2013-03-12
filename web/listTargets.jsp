<!--
*
* CDDL HEADER START
*
* The contents of this file are subject to the terms of the
* Common Development and Distribution License, Version 1.0 only
* (the "License"). You may not use this file except in compliance
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
*
-->
<!--
*
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* Use is subject to license terms.
*
-->
<%@ page import="org.openspml.v2.client.Spml2Client" %>
<%@ page import="org.openspml.v2.msg.spml.ExecutionMode" %>
<%@ page import="org.openspml.v2.msg.spml.ListTargetsRequest" %>
<%@ page import="org.openspml.v2.msg.spml.Response" %>
<%@ page import="org.openspml.v2.profiles.DSMLProfileRegistrar" %>
<%@ page import="org.openspml.v2.util.Spml2ExceptionWithResponse" %>
<%@ page import="org.openspml.v2.util.xml.ObjectFactory" %>
<%@ page import="org.openspml.v2.util.xml.ReflectiveXMLMarshaller" %>

<%
    // Build the url for the Spml2Client constructor
    final String partialUrl = "http://localhost:8080/nvp";
    final String url = partialUrl + "/spml2";

%>

<html>
<head><title>SPML2 List Targets Example</title></head>
<body>
<%
    // tell the local code that we are using DSML
    ObjectFactory.getInstance().register(new DSMLProfileRegistrar());

    // need a client.
    Spml2Client client = new Spml2Client(url, null, null);

    // execute the list targets
    ListTargetsRequest ltr = new ListTargetsRequest("0", ExecutionMode.SYNCHRONOUS, null);

    Response resp = null;
    String xml = null;
    try {
        resp = client.send(ltr);
    }
    catch (Spml2ExceptionWithResponse e) {
        resp = e.getResponse();
    }

    xml = (new ReflectiveXMLMarshaller()).marshall(resp);

    // Create marshaller for toXML methods
    ReflectiveXMLMarshaller marshaller = new ReflectiveXMLMarshaller();
%>

<!--  This section will output the raw XML request and response -->

ListTargets Result:
<p>
    Request =

<p>
    <textarea rows="4" cols="150">
        <%= ltr.toXML(marshaller)%>
    </textarea>

<p>
    Response =

<p><textarea rows="25" cols="150">
    <%= resp.toXML(marshaller)%>
</textarea>

<p>
    Response Details:
    <br/>
    Result = <%= resp.getStatus().toString() %>

<p>
    Error = <%= resp.getError() %>

<p>
    ErrorMessages =
    <%

        String[] errors = resp.getErrorMessages();
        for ( int i = 0; errors != null && i < errors.length; i++ ) {

    %>
    <%= errors[i] %>
    <BR>
    <%

        }

    %>
</body>
</html>
