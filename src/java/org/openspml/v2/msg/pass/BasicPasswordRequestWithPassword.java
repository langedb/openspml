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
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 13, 2006
 */
abstract class BasicPasswordRequestWithPassword extends BasicPasswordRequest {

    private static final String code_id = "$Id: BasicPasswordRequestWithPassword.java,v 1.2 2006/04/21 23:09:02 kas Exp $";

    // <element name="pass" type="string" />  - USED IN SUBCLASSES
    // collection or array type makes this be treated as an element
    private String[] m_password = new String[1]; // required

    protected BasicPasswordRequestWithPassword() {
        super();
    }

    protected BasicPasswordRequestWithPassword(String requestId,
                                               ExecutionMode executionMode,
                                               PSOIdentifier psoID,
                                               String password) {
        super(requestId, executionMode, psoID);
        assert (password != null);
        m_password[0] = password;
    }

    public String getPassword() {
        return m_password[0];
    }

    public void setPassword(String password) {
        assert (password != null);
        m_password[0] = password;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicPasswordRequestWithPassword)) return false;
        if (!super.equals(o)) return false;

        final BasicPasswordRequestWithPassword basicPasswordRequestWithPassword = (BasicPasswordRequestWithPassword) o;

        if (m_password[0] != null ? !m_password[0].equals(basicPasswordRequestWithPassword.m_password[0]) : basicPasswordRequestWithPassword.m_password[0] != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_password[0] != null ? m_password[0].hashCode() : 0);
        return result;
    }
}
