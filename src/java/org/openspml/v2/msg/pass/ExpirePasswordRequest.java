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
package org.openspml.v2.msg.pass;

import org.openspml.v2.msg.spml.ExecutionMode;
import org.openspml.v2.msg.spml.PSOIdentifier;

/**
 * <complexType name="ExpirePasswordRequestType">
 * <complexContent>
 * <extension base="spml:RequestType">
 * <sequence>
 * <element name="psoID" type="spml:PSOIdentifierType" />
 * </sequence>
 * <attribute name="remainingLogins" type="int" use="optional" default="1" />
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 13, 2006
 */
public class ExpirePasswordRequest extends BasicPasswordRequest {

    private static final String code_id = "$Id: ExpirePasswordRequest.java,v 1.3 2006/04/25 21:22:09 kas Exp $";

    // <attribute name="remainingLogins" type="int" use="optional" default="1" />
    private Integer m_remainingLogins = null; // optional

    public ExpirePasswordRequest() { ; }

    public ExpirePasswordRequest(String requestId,
                                 ExecutionMode executionMode,
                                 PSOIdentifier psoID,
                                 Integer remainingLogins) {
        super(requestId, executionMode, psoID);
        m_remainingLogins = remainingLogins;
    }

    public int getRemainingLogins() {
        return m_remainingLogins != null ? m_remainingLogins.intValue() : 1;
    }

    public void setRemainingLogins(int remainingLogins) {
        m_remainingLogins = new Integer(remainingLogins);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpirePasswordRequest)) return false;
        if (!super.equals(o)) return false;

        final ExpirePasswordRequest expirePasswordRequest = (ExpirePasswordRequest) o;

        if (m_remainingLogins != null ? !m_remainingLogins.equals(expirePasswordRequest.m_remainingLogins) : expirePasswordRequest.m_remainingLogins != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_remainingLogins != null ? m_remainingLogins.hashCode() : 0);
        return result;
    }
}
