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
 * <complexType name="LookupResponseType">
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
public class LookupResponse extends Response {

    private static final String code_id = "$Id: LookupResponse.java,v 1.4 2006/04/25 21:22:09 kas Exp $";

    // <element name="pso" type="spml:PSOType" minOccurs="0" />
    private PSO m_pso = null;

    public LookupResponse() { ; }

    public LookupResponse(String[] errorMessages,
                          StatusCode status,
                          String requestID,
                          ErrorCode error,
                          PSO pso) {
        super(errorMessages,
              status,
              requestID,
              error);
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
        if (!(o instanceof LookupResponse)) return false;
        if (!super.equals(o)) return false;

        final LookupResponse lookupResponseType = (LookupResponse) o;

        if (m_pso != null ? !m_pso.equals(lookupResponseType.m_pso) : lookupResponseType.m_pso != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_pso != null ? m_pso.hashCode() : 0);
        return result;
    }
}
