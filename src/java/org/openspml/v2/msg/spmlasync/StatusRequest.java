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
package org.openspml.v2.msg.spmlasync;

import org.openspml.v2.msg.spml.ExecutionMode;


/**
 * <complexType name="StatusRequestType">
 * <complexContent>
 * <extension base="spml:RequestType">
 * <attribute name="returnResults" type="xsd:boolean" use="optional"  default="false" />
 * <attribute name="asyncRequestID" type="xsd:string" use="optional"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 15, 2006
 */
public class StatusRequest extends BasicRequest {

    private static final String code_id = "$Id: StatusRequest.java,v 1.4 2006/04/25 21:22:09 kas Exp $";

    public StatusRequest() { ; }

    // <attribute name="returnResults" type="xsd:boolean" use="optional"  default="false" />
    private Boolean m_returnResults = null; // optional - false

    public StatusRequest(String requestId,
                         ExecutionMode executionMode,
                         String asyncRequestID,
                         boolean returnResults) {
        super(requestId, executionMode, asyncRequestID, false);
        m_returnResults = new Boolean(returnResults);
    }

    public boolean getReturnResults() {
        if (m_returnResults == null) return false;
        return m_returnResults.booleanValue();
    }

    public void setReturnResults(boolean returnResults) {
        m_returnResults = new Boolean(returnResults);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatusRequest)) return false;
        if (!super.equals(o)) return false;

        final StatusRequest statusRequest = (StatusRequest) o;

        if (m_returnResults != null ? !m_returnResults.equals(statusRequest.m_returnResults) : statusRequest.m_returnResults != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_returnResults != null ? m_returnResults.hashCode() : 0);
        return result;
    }
}

