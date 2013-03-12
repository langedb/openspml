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
 * <complexType name="SetPasswordRequestType">
 * <complexContent>
 * <extension base="spml:RequestType">
 * <sequence>
 * <element name="psoID" type="spml:PSOIdentifierType" />
 * <element name="pass" type="string" />
 * <element name="currentPassword" type="string" minOccurs="0" />
 * </sequence>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 13, 2006
 */
public class SetPasswordRequest extends BasicPasswordRequestWithPassword {

    private static final String code_id = "$Id: SetPasswordRequest.java,v 1.3 2006/04/25 21:22:09 kas Exp $";

    // <element name="currentPassword" type="string" minOccurs="0" />
    // collection or array to be an element
    private String[] m_currentPassword = new String[1];  // collection or array to be an element

    public SetPasswordRequest() { ; }

    public SetPasswordRequest(String requestId,
                              ExecutionMode executionMode,
                              PSOIdentifier psoID,
                              String password,
                              String currentPassword) {
        super(requestId, executionMode, psoID, password);
        m_currentPassword[0] = currentPassword;
    }

    public String getCurrentPassword() {
        return m_currentPassword[0];
    }

    public void setCurrentPassword(String currentPassword) {
        m_currentPassword[0] = currentPassword;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SetPasswordRequest)) return false;
        if (!super.equals(o)) return false;

        final SetPasswordRequest setPasswordRequest = (SetPasswordRequest) o;

        if (m_currentPassword[0] != null ? !m_currentPassword[0].equals(setPasswordRequest.m_currentPassword[0]) : setPasswordRequest.m_currentPassword[0] != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_currentPassword[0] != null ? m_currentPassword[0].hashCode() : 0);
        return result;
    }
}
