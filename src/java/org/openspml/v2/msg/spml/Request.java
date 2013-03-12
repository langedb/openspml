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

// <complexType name="RequestType">
// 	<complexContent>
// 		<extension base="spml:ExtensibleType">
// 			<attribute name="requestID" type="xsd:ID" use="optional"/>
// 			<attribute name="executionMode" type="spml:ExecutionModeType" use="optional"/>
// 		</extension>
// 	</complexContent>
// </complexType>

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Jan 26, 2006
 */
public abstract class Request extends ExtensibleMarshallable {

    public static final String code_id = "$Id: Request.java,v 1.2 2006/04/21 23:09:02 kas Exp $";

    // <attribute name="requestID" type="xsd:ID" use="optional"/>
    private String m_requestID = null;

    // <attribute name="executionMode" type="spml:ExecutionModeType" use="optional"/>
    private ExecutionMode m_executionMode = null;

    protected Request() {};
    
    protected Request(String requestId,
                      ExecutionMode executionMode) {
        m_requestID = requestId;
        m_executionMode = executionMode;
    }

    public String getRequestID() {
        return m_requestID;
    }

    public void setRequestID(String requestID) {
        m_requestID = requestID;
    }

    public ExecutionMode getExecutionMode() {
        return m_executionMode;
    }

    public void setExecutionMode(ExecutionMode executionMode) {
        m_executionMode = executionMode;
    }

    public String toXML(XMLMarshaller m) throws Spml2Exception {
        return m.marshall(this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        if (!super.equals(o)) return false;

        final Request request = (Request) o;

        if (m_executionMode != null ? !m_executionMode.equals(request.m_executionMode) : request.m_executionMode != null) return false;
        if (m_requestID != null ? !m_requestID.equals(request.m_requestID) : request.m_requestID != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_requestID != null ? m_requestID.hashCode() : 0);
        result = 29 * result + (m_executionMode != null ? m_executionMode.hashCode() : 0);
        return result;
    }
}
