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

import org.openspml.v2.msg.spml.ExecutionMode;
import org.openspml.v2.msg.spml.ReturnData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <complexType name="SearchRequestType">
 * <complexContent>
 * <extension base="spml:RequestType">
 * <sequence>
 * <element name="query" type="spmlsearch:SearchQueryType" minOccurs="0" />
 * <element name="includeDataForCapability" type="xsd:string" minOccurs="0" maxOccurs="unbounded"/>
 * </sequence>
 * <attribute name="returnData" type="spml:ReturnDataType" use="optional" default="everything"/>
 * <attribute name="maxSelect" type="xsd:int" use="optional"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class SearchRequest extends BasicRequest {

    private static final String code_id = "$Id: SearchRequest.java,v 1.5 2006/06/27 00:47:19 kas Exp $";

    // <element name="query" type="spmlsearch:SearchQueryType" minOccurs="0" />
    private Query m_query = null;

    // <element name="includeDataForCapability" type="xsd:string" minOccurs="0" maxOccurs="unbounded"/>
    private List m_includeDataForCapability = new ArrayList();

    // <attribute name="returnData" type="spml:ReturnDataType" use="optional" default="everything"/>
    private ReturnData m_returnData = null;

    // <attribute name="maxSelect" type="xsd:int" use="optional"/>
    private Integer m_maxSelect = null;

    public SearchRequest() { ; }

    public SearchRequest(String requestId,
                         ExecutionMode executionMode,
                         Query query,
                         String[] includeDataForCapability,
                         ReturnData returnData,
                         Integer maxSelect) {
        super(requestId, executionMode);
        m_query = query;
        if (includeDataForCapability != null) {
            m_includeDataForCapability.addAll(Arrays.asList(includeDataForCapability));
        }
        m_returnData = returnData;
        m_maxSelect = maxSelect;
    }

    public SearchRequest(String requestId,
                         ExecutionMode executionMode,
                         Query query,
                         String[] includeDataForCapability,
                         ReturnData returnData,
                         int maxSelect) {
        this(requestId, executionMode, query,
             includeDataForCapability, returnData,
             new Integer(maxSelect));
    }

    public SearchRequest(String requestId,
                         ExecutionMode executionMode,
                         Query query,
                         String[] includeDataForCapability,
                         ReturnData returnData) {
        this(requestId, executionMode, query,
             includeDataForCapability, returnData,
             null);
    }

    public Query getQuery() {
        return m_query;
    }

    public void setQuery(Query query) {
        m_query = query;
    }

    public String[] getIncludeDataForCapability() {
        return (String[]) m_includeDataForCapability.toArray(new String[m_includeDataForCapability.size()]);
    }

    public void clearIncludeDataForCapability() {
        m_includeDataForCapability.clear();
    }

    public void addIncludeDataForCapability(String includeDataForCapability) {
        if (includeDataForCapability == null) return;
        m_includeDataForCapability.add(includeDataForCapability);
    }

    public boolean removeIncludeDataForCapability(String includeDataForCapability) {
        if (includeDataForCapability == null) return false;
        return m_includeDataForCapability.remove(includeDataForCapability);
    }

    public ReturnData getReturnData() {
        return m_returnData;
    }

    public void setReturnData(ReturnData returnData) {
        m_returnData = returnData;
    }

    public int getMaxSelect() {
        return m_maxSelect != null ? m_maxSelect.intValue() : 0;
    }

    public void setMaxSelect(int maxSelect) {
        m_maxSelect = new Integer(maxSelect);
    }

    public void useDefaultMaxSelect() {
        setMaxSelect(0);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchRequest)) return false;
        if (!super.equals(o)) return false;

        final SearchRequest searchRequest = (SearchRequest) o;

        if (!m_includeDataForCapability.equals(searchRequest.m_includeDataForCapability)) return false;
        if (m_maxSelect != null ? !m_maxSelect.equals(searchRequest.m_maxSelect) : searchRequest.m_maxSelect != null) return false;
        if (m_query != null ? !m_query.equals(searchRequest.m_query) : searchRequest.m_query != null) return false;
        if (m_returnData != null ? !m_returnData.equals(searchRequest.m_returnData) : searchRequest.m_returnData != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_query != null ? m_query.hashCode() : 0);
        result = 29 * result + m_includeDataForCapability.hashCode();
        result = 29 * result + (m_returnData != null ? m_returnData.hashCode() : 0);
        result = 29 * result + (m_maxSelect != null ? m_maxSelect.hashCode() : 0);
        return result;
    }
}
