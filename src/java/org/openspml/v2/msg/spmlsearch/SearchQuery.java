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

import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.msg.PrefixAndNamespaceTuple;
import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.msg.spml.PSOIdentifier;
import org.openspml.v2.msg.spml.QueryClause;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * From the specification...
 *
 * <complexType name="SearchQueryType">
 * <complexContent>
 * <extension base="spml:ExtensibleType">
 * <sequence>
 * <annotation>
 * <documentation>Open content is one or more instances of QueryClauseType (including SelectionType) or LogicalOperator.</documentation>
 * </documentation>
 * </annotation>
 * <element name="basePsoID" type="spml:PSOIdentifierType"  minOccurs="0" />
 * </sequence>
 * <attribute name="targetID" type="string" use="optional"/>
 * <attribute name="scope" type="spmlsearch:ScopeType" use="optional"/>
 * </extension>
 * </complexContent>
 * </complexType>
 * 
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
abstract public class SearchQuery extends Extensible implements Marshallable {

    private static final String code_id = "$Id: SearchQuery.java,v 1.8 2006/08/30 18:02:59 kas Exp $";

    protected SearchQuery() {
    }

    // we have additional content that is not named (considered openContent)
    //   - as a list of QueryClause objects.
    private ListWithType m_queryClauses = new ArrayListWithType(QueryClause.class);

    // <element name="basePsoID" type="spml:PSOIdentifierType"  minOccurs="0" />
    private PSOIdentifier m_basePsoID = null; // optional

    // <attribute name="targetID" type="string" use="optional"/>
    private String m_targetID = null;

    // <attribute name="scope" type="spmlsearch:ScopeType" use="optional"/>
    private Scope m_scope = null;

    protected SearchQuery(QueryClause[] queryClauses,
                          PSOIdentifier basePsoID,
                          String targetID,
                          Scope scope) {
        assert (queryClauses != null);     // SMOKE spec
        assert (queryClauses.length != 0); // SMOKE spec
        for (int k = 0; k < queryClauses.length; k++) {
            if (queryClauses[k] != null) {
                m_queryClauses.add(queryClauses[k]);
            }
        }
        assert (!m_queryClauses.isEmpty());

        m_basePsoID = basePsoID;
        m_targetID = targetID;
        m_scope = scope;
    }


    public QueryClause[] getQueryClauses() {
        List temp = new ArrayList();
        temp.addAll(m_queryClauses);

        // we also want all the OpenContentElements that
        // are instanceof QueryClause... we should be able to
        // unmarshall them into the right container - but that's
        // difficult so we'll cheat.
        OpenContentElement[] oces = getOpenContentElements();
        for (int k = 0; k < oces.length; k++) {
            OpenContentElement oce = oces[k];
            if (oce instanceof QueryClause) {
                temp.add(oce);
            }
        }

        return (QueryClause[]) temp.toArray(new QueryClause[temp.size()]);
    }

    public void clearQueryClauses() {
        m_queryClauses.clear();
        OpenContentElement[] elements = getOpenContentElements();
        for (int k = 0; k < elements.length; k++) {
            OpenContentElement element = elements[k];
            if (element instanceof QueryClause) {
                removeOpenContentElement(element);
            }
        }
    }

    public void addQueryClause(QueryClause queryClause) {
        if (queryClause == null) return;
        if (queryClause instanceof OpenContentElement) {
            addOpenContentElement((OpenContentElement) queryClause);
        }
        else {
            m_queryClauses.add(queryClause);
        }
    }

    public boolean removeQueryClause(QueryClause queryClause) {
        if (queryClause == null) return false;
        if (queryClause instanceof OpenContentElement) {
            return removeOpenContentElement((OpenContentElement) queryClause);
        }
        else {
            return m_queryClauses.remove(queryClause);
        }
    }

    public PSOIdentifier getBasePsoID() {
        return m_basePsoID;
    }

    public void setBasePsoID(PSOIdentifier basePsoID) {
        m_basePsoID = basePsoID;
    }

    public String getTargetID() {
        return m_targetID;
    }

    public void setTargetID(String targetID) {
        m_targetID = targetID;
    }

    public Scope getScope() {
        return m_scope;
    }

    public void setScope(Scope scope) {
        m_scope = scope;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchQuery)) return false;
        if (!super.equals(o)) return false;

        final SearchQuery searchQuery = (SearchQuery) o;

        if (m_basePsoID != null ? !m_basePsoID.equals(searchQuery.m_basePsoID) : searchQuery.m_basePsoID != null) return false;
        if (!m_queryClauses.equals(searchQuery.m_queryClauses)) return false;
        if (m_scope != null ? !m_scope.equals(searchQuery.m_scope) : searchQuery.m_scope != null) return false;
        if (m_targetID != null ? !m_targetID.equals(searchQuery.m_targetID) : searchQuery.m_targetID != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_queryClauses.hashCode();
        result = 29 * result + (m_basePsoID != null ? m_basePsoID.hashCode() : 0);
        result = 29 * result + (m_targetID != null ? m_targetID.hashCode() : 0);
        result = 29 * result + (m_scope != null ? m_scope.hashCode() : 0);
        return result;
    }

    // Marshallable stuff
    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        Object[] ours = PrefixAndNamespaceTuple.concatNamespacesInfo(
                super.getNamespacesInfo(),
                NamespaceDefinitions.getMarshallableNamespacesInfo());
        Set all = new LinkedHashSet(Arrays.asList(ours));
        Iterator iter = m_queryClauses.iterator();
        while (iter.hasNext()) {
            QueryClause qc = (QueryClause) iter.next();
            all.addAll(Arrays.asList(qc.getNamespacesInfo()));
        }
        return (PrefixAndNamespaceTuple[]) all.toArray(new PrefixAndNamespaceTuple[all.size()]);
    }

    public String toXML(XMLMarshaller m) throws Spml2Exception {
        return m.marshall(this);
    }

    public boolean isValid() {
        return true;
    }
}
