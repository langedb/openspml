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
package org.openspml.v2.msg.spml;

import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.util.Spml2Exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//<complexType name="ResponseType">
//    <complexContent>
//        <extension base="spml:ExtensibleType">
//            <sequence>
//                        <element name="errorMessage" type="xsd:string" minOccurs="0" maxOccurs="unbounded"/>
//            </sequence>
//            <attribute name="status" type="spml:StatusCodeType" use="required"/>
//            <attribute name="requestID" type="xsd:ID" use="optional"/>
//            <attribute name="error" type="spml:ErrorCode" use="optional"/>
//        </extension>
//    </complexContent>
//</complexType>

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Jan 26, 2006
 */
public class Response extends ExtensibleMarshallable {

    public static final String code_id = "$Id: Response.java,v 1.6 2006/06/29 21:01:21 rfrech Exp $";

    //  <element name="errorMessage" type="xsd:string" minOccurs="0" maxOccurs="unbounded"/>
    private List m_errorMessage = new ArrayList();

    // <attribute name="status" type="spml:StatusCodeType" use="required"/>
    private StatusCode m_status = null;

    // <attribute name="requestID" type="xsd:ID" use="optional"/>
    private String m_requestID = null;

    // <attribute name="error" type="spml:ErrorCode" use="optional"/>
    private ErrorCode m_error = null;

    public Response() { ; }

    protected Response(String[] errorMessages,
                       StatusCode status,
                       String requestId,
                       ErrorCode errorCode) {
        if (errorMessages != null)
            m_errorMessage.addAll(Arrays.asList(errorMessages));

        assert (status != null);
        m_status = status;

        m_requestID = requestId;
        m_error = errorCode;
    }

    public Response(StatusCode status) {
        this(null, status, null, null);
    }

    public Response(StatusCode status,
    		        String requestId) {
        this(null, status, requestId, null);
    }

    public String[] getErrorMessages() {
        return (String[]) m_errorMessage.toArray(new String[m_errorMessage.size()]);
    }

    public void addErrorMessage(String errorMessage) {
        assert (errorMessage != null);
        m_errorMessage.add(errorMessage);
    }

    public boolean removeErrorMessage(String errorMessage) {
        assert (errorMessage != null);
        return m_errorMessage.remove(errorMessage);
    }

    public void clearErrorMessages() {
        m_errorMessage.clear();
    }

    public StatusCode getStatus() {
        return m_status;
    }

    public void setStatus(StatusCode status) {
        m_status = status;
        assert (m_status != null);
    }

    public String getRequestID() {
        return m_requestID;
    }

    public void setRequestID(String requestID) {
        m_requestID = requestID;
    }

    public ErrorCode getError() {
        return m_error;
    }

    public void setError(ErrorCode error) {
        m_error = error;
    }

    public String toXML(XMLMarshaller m) throws Spml2Exception {
        return m.marshall(this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response)) return false;
        if (!super.equals(o)) return false;

        final Response response = (Response) o;

        if (m_error != null ? !m_error.equals(response.m_error) : response.m_error != null) return false;
        if (m_errorMessage != null ? !m_errorMessage.equals(response.m_errorMessage) : response.m_errorMessage != null) return false;
        if (m_requestID != null ? !m_requestID.equals(response.m_requestID) : response.m_requestID != null) return false;
        if (m_status != null ? !m_status.equals(response.m_status) : response.m_status != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_errorMessage != null ? m_errorMessage.hashCode() : 0);
        result = 29 * result + (m_status != null ? m_status.hashCode() : 0);
        result = 29 * result + (m_requestID != null ? m_requestID.hashCode() : 0);
        result = 29 * result + (m_error != null ? m_error.hashCode() : 0);
        return result;
    }
}
