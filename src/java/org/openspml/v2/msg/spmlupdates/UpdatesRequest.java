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
package org.openspml.v2.msg.spmlupdates;

import org.openspml.v2.msg.spml.ExecutionMode;
import org.openspml.v2.msg.spmlsearch.Query;
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Arrays;
import java.util.List;

/**
 * <complexType name="UpdatesRequestType">
 * <complexContent>
 * <extension base="spml:RequestType">
 * <sequence>
 * <element ref="spmlsearch:query" minOccurs="0"/>
 * <element name="updatedByCapability" type="xsd:string" minOccurs="0" maxOccurs="unbounded"/>
 * </sequence>
 * <attribute name="updatedSince" type="xsd:dateTime" use="optional"/>
 * <attribute name="token" type="xsd:string" use="optional"/>
 * <attribute name="maxSelect" type="xsd:int" use="optional"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class UpdatesRequest extends BasicRequest {

    private static final String code_id = "$Id: UpdatesRequest.java,v 1.4 2006/04/25 21:22:09 kas Exp $";

    //* <element ref="spmlsearch:query" minOccurs="0"/>
    private ListWithType m_query = new ArrayListWithType(Query.class);

    //* <element name="updatedByCapability" type="xsd:string" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_updatedByCapability = new ArrayListWithType(String.class);

    //* <attribute name="updatedSince" type="xsd:dateTime" use="optional"/>
    private String m_updatedSince = null; // TODO: dateTime

    //* <attribute name="token" type="xsd:string" use="optional"/>
    private String m_token = null;

    //* <attribute name="maxSelect" type="xsd:int" use="optional"/>
    private Integer m_maxSelect = null;

    public UpdatesRequest() { ; }

    public UpdatesRequest(String requestId,
                          ExecutionMode executionMode,
                          Query[] query,
                          String[] updatedByCapability,
                          String updatedSince,
                          String token,
                          Integer maxSelect) {
        super(requestId, executionMode);

        if (query != null) {
            m_query.addAll(Arrays.asList(query));
        }

        if (updatedByCapability != null) {
            m_updatedByCapability.addAll(Arrays.asList(updatedByCapability));
        }

        m_updatedSince = updatedSince;
        m_token = token;
        m_maxSelect = maxSelect;
    }

    public Query[] getQueries() {
        return (Query[]) m_query.toArray(new Query[m_query.size()]);
    }

    public void addQuery(Query query) {
        if (query != null) {
            m_query.add(query);
        }
    }

    public boolean removeQuery(Query query) {
        return m_query.remove(query);
    }

    public void clearQueries() {
        m_query.clear();
    }

    public String[] getUpdatedByCapabilities() {
        return (String[]) m_updatedByCapability.toArray(new String[m_updatedByCapability.size()]);
    }

    public void addUpdatedByCapability(List updatedByCapability) {
        if (updatedByCapability != null)
            m_updatedByCapability.add(updatedByCapability);
    }

    public boolean removeUpdatedByCapability(List updatedByCapability) {
        return m_updatedByCapability.remove(updatedByCapability);
    }

    public void clearUpdatedByCapabilities() {
        m_updatedByCapability.clear();
    }

    // TODO: dateTime
    public String getUpdatedSince() {
        return m_updatedSince;
    }

    public void setUpdatedSince(String updatedSince) {
        m_updatedSince = updatedSince;
    }

    public String getToken() {
        return m_token;
    }

    public void setToken(String token) {
        m_token = token;
    }

    public int getMaxSelect() {
        if (m_maxSelect == null) return 0;
        return m_maxSelect.intValue();
    }

    public void setMaxSelect(int maxSelect) {
        m_maxSelect = new Integer(maxSelect);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdatesRequest)) return false;
        if (!super.equals(o)) return false;

        final UpdatesRequest updatesRequest = (UpdatesRequest) o;

        if (m_maxSelect != null ? !m_maxSelect.equals(updatesRequest.m_maxSelect) : updatesRequest.m_maxSelect != null) return false;
        if (!m_query.equals(updatesRequest.m_query)) return false;
        if (m_token != null ? !m_token.equals(updatesRequest.m_token) : updatesRequest.m_token != null) return false;
        if (!m_updatedByCapability.equals(updatesRequest.m_updatedByCapability)) return false;
        if (m_updatedSince != null ? !m_updatedSince.equals(updatesRequest.m_updatedSince) : updatesRequest.m_updatedSince != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_query.hashCode();
        result = 29 * result + m_updatedByCapability.hashCode();
        result = 29 * result + (m_updatedSince != null ? m_updatedSince.hashCode() : 0);
        result = 29 * result + (m_token != null ? m_token.hashCode() : 0);
        result = 29 * result + (m_maxSelect != null ? m_maxSelect.hashCode() : 0);
        return result;
    }
}


