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

import org.openspml.v2.msg.spml.ErrorCode;
import org.openspml.v2.msg.spml.StatusCode;

/**
 * <complexType name="ValidatePasswordResponseType">
 * <complexContent>
 * <extension base="spml:ResponseType">
 * <attribute name="valid" type="boolean" use="optional"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 13, 2006
 */
public class ValidatePasswordResponse extends BasicPasswordResponse {

    private static final String code_id = "$Id: ValidatePasswordResponse.java,v 1.4 2006/04/25 21:22:09 kas Exp $";

    // <attribute name="valid" type="boolean" use="optional"/>
    private Boolean m_valid = null;

    public ValidatePasswordResponse() { ; }

    public ValidatePasswordResponse(String[] errorMessages,
                                    StatusCode status,
                                    String requestId,
                                    ErrorCode errorCode,
                                    Boolean valid) {
        super(errorMessages, status, requestId, errorCode);
        m_valid = valid;
    }

    public boolean getValid() {
        if (m_valid == null) return false;
        return m_valid.booleanValue();
    }

    public void setValid(boolean valid) {
        m_valid = new Boolean(valid);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidatePasswordResponse)) return false;
        if (!super.equals(o)) return false;

        final ValidatePasswordResponse validatePasswordResponse = (ValidatePasswordResponse) o;

        if (m_valid != null ? !m_valid.equals(validatePasswordResponse.m_valid) : validatePasswordResponse.m_valid != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_valid != null ? m_valid.hashCode() : 0);
        return result;
    }
}
