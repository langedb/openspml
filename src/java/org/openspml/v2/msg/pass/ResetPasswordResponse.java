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
 * <complexType name="ResetPasswordResponseType">
 * <complexContent>
 * <extension base="spml:ResponseType">
 * <sequence>
 * <element name="pass" type="string" minOccurs="0" />
 * </sequence>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 13, 2006
 */
public class ResetPasswordResponse extends BasicPasswordResponse {

    private static final String code_id = "$Id: ResetPasswordResponse.java,v 1.4 2006/04/25 21:22:09 kas Exp $";

    //* <element name="pass" type="string" minOccurs="0" />
    // collection or array to be an element
    private String[] m_password = new String[1]; // optional

    public ResetPasswordResponse() { ; }

    // full
    public ResetPasswordResponse(String[] errorMessages,
                                 StatusCode status,
                                 String requestId,
                                 ErrorCode errorCode,
                                 String password) {
        super(errorMessages, status, requestId, errorCode);
        m_password[0] = password;
    }

    public String getPassword() {
        return m_password[0];
    }

    public void setPassword(String password) {
        m_password[0] = password;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResetPasswordResponse)) return false;
        if (!super.equals(o)) return false;

        final ResetPasswordResponse resetPasswordResponse = (ResetPasswordResponse) o;

        if (m_password[0] != null ? !m_password[0].equals(resetPasswordResponse.m_password[0]) : resetPasswordResponse.m_password[0] != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_password[0] != null ? m_password[0].hashCode() : 0);
        return result;
    }
}
