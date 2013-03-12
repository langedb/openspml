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
package org.openspml.v2.msg.spmlsuspend;

import org.openspml.v2.msg.spml.ExecutionMode;
import org.openspml.v2.msg.spml.PSOIdentifier;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 15, 2006
 */
abstract class BasicDatedRequest extends BasicRequest {

    private static final String code_id = "$Id: BasicDatedRequest.java,v 1.3 2006/04/21 23:09:02 kas Exp $";

    // <attribute name="effectiveDate" type="dateTime" use="optional"/>
    private String m_effectiveDate = null; /*TODO type="dateTime"*/

    protected BasicDatedRequest() { ; }

    protected BasicDatedRequest(String requestId,
                                ExecutionMode executionMode, PSOIdentifier psoID) {
        super(requestId, executionMode, psoID);
    }

    public String getEffectiveDate() {
        return m_effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        m_effectiveDate = effectiveDate;
    }

    // TODO add some util methods so the users don't have to
    // format the date string themselves.

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicDatedRequest)) return false;
        if (!super.equals(o)) return false;

        final BasicDatedRequest basicDatedRequest = (BasicDatedRequest) o;

        if (m_effectiveDate != null ? !m_effectiveDate.equals(basicDatedRequest.m_effectiveDate) : basicDatedRequest.m_effectiveDate != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_effectiveDate != null ? m_effectiveDate.hashCode() : 0);
        return result;
    }
}
