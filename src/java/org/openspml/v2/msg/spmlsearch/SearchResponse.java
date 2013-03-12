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
package org.openspml.v2.msg.spmlsearch;

import org.openspml.v2.msg.spml.ErrorCode;
import org.openspml.v2.msg.spml.PSO;
import org.openspml.v2.msg.spml.StatusCode;
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Arrays;

/**
 * <complexType name="SearchResponseType">
 * <complexContent>
 * <extension base="spml:ResponseType">
 * <sequence>
 * <element name="pso" type="spml:PSOType" minOccurs="0" maxOccurs="unbounded"/>
 * <element name="iterator" type="spmlsearch:ResultsIteratorType" minOccurs="0" />
 * </sequence>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class SearchResponse extends BasicResponse {

    private static final String code_id = "$Id: SearchResponse.java,v 1.3 2006/04/25 21:22:09 kas Exp $";

    // <element name="pso" type="spml:PSOType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_pso = new ArrayListWithType(PSO.class);

    // <element name="iterator" type="spmlsearch:ResultsIteratorType" minOccurs="0" />
    private ResultsIterator m_iterator = null; // optional

    public SearchResponse() { ; }

    public SearchResponse(String[] errorMessages,
                          StatusCode status,
                          String requestId,
                          ErrorCode errorCode,
                          PSO[] pso,
                          ResultsIterator iterator) {
        super(errorMessages, status, requestId, errorCode);
        if (pso != null) {
            m_pso.addAll(Arrays.asList(pso));
        }
        m_iterator = iterator;
    }

    public void addPSO(PSO pso) {
        if (pso != null) {
            m_pso.add(pso);
        }
    }

    public boolean removePSO(PSO pso) {
        if (pso != null) {
            return m_pso.remove(pso);
        }
        return false;
    }

    public void clearPSOs() {
        m_pso.clear();
    }

    public PSO[] getPSOs() {
        return (PSO[]) m_pso.toArray(new PSO[m_pso.size()]);
    }

    public ResultsIterator getIterator() {
        return m_iterator;
    }

    public void setIterator(ResultsIterator iterator) {
        m_iterator = iterator;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchResponse)) return false;
        if (!super.equals(o)) return false;

        final SearchResponse searchResponse = (SearchResponse) o;

        if (m_iterator != null ? !m_iterator.equals(searchResponse.m_iterator) : searchResponse.m_iterator != null) return false;
        if (!m_pso.equals(searchResponse.m_pso)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_pso.hashCode();
        result = 29 * result + (m_iterator != null ? m_iterator.hashCode() : 0);
        return result;
    }
}
