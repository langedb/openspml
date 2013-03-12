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


/**
 * <complexType name="ModifyResponseType">
 * <complexContent>
 * <extension base="spml:ResponseType">
 * <sequence>
 * <element name="pso" type="spml:PSOType" minOccurs="0" />
 * </sequence>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 7, 2006
 */
public class ModifyResponse extends Response {

    private static final String code_id = "$Id: ModifyResponse.java,v 1.4 2006/04/25 21:22:09 kas Exp $";

    // <element name="pso" type="spml:PSOType" minOccurs="0" />
    private PSO m_pso = null;

    public ModifyResponse() { ; }

    public ModifyResponse(String[] errorMessages,
                          StatusCode statusCode,
                          String requestID,
                          ErrorCode error,
                          PSO pso) {
        super(errorMessages, statusCode, requestID, error);
        m_pso = pso;
    }

    public PSO getPso() {
        return m_pso;
    }

    public void setPso(PSO pso) {
        m_pso = pso;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModifyResponse)) return false;
        if (!super.equals(o)) return false;

        final ModifyResponse modifyResponseType = (ModifyResponse) o;

        if (m_pso != null ? !m_pso.equals(modifyResponseType.m_pso) : modifyResponseType.m_pso != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_pso != null ? m_pso.hashCode() : 0);
        return result;
    }
}
