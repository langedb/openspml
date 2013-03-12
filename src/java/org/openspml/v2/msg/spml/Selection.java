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

import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Arrays;

/**
 * <complexType name="SelectionType">
 * <complexContent>
 * <extension base="spml:QueryClauseType">
 * <sequence>
 * <element name="namespacePrefixMap" type="spml:NamespacePrefixMappingType" minOccurs="0" maxOccurs="unbounded"/>
 * </sequence>
 * <attribute name="path" type="string" use="required"/>
 * <attribute name="namespaceURI" type="string" use="required"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 7, 2006
 */
public class Selection extends QueryClause {

    private static final String code_id = "$Id: Selection.java,v 1.4 2006/06/27 17:23:56 kas Exp $";

    // <element name="namespacePrefixMap" type="spml:NamespacePrefixMappingType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_namespacePrefixMap = new ArrayListWithType(NamespacePrefixMapping.class);

    // <attribute name="path" type="string" use="required"/>
    private String m_path = null;

    // <attribute name="namespaceURI" type="string" use="required"/>
    private String m_namespaceURI = null;

    public Selection() { ; }
    
    public Selection(NamespacePrefixMapping[] prefixes,
                     String path,
                     String namespaceURI) {
        if (prefixes != null)
            m_namespacePrefixMap.addAll(Arrays.asList(prefixes));

        assert (path != null);
        assert (namespaceURI != null);
        m_path = path;
        m_namespaceURI = namespaceURI;
    }

    public String getElementName() {
        return "select";
    }

    public NamespacePrefixMapping[] getNamespacePrefixMaps() {
        return (NamespacePrefixMapping[]) m_namespacePrefixMap.toArray(
                new NamespacePrefixMapping[m_namespacePrefixMap.size()]);
    }

    public void addNamespacePrefixMap(NamespacePrefixMapping nspm) {
        assert (nspm != null);
        m_namespacePrefixMap.add(nspm);
    }

    public boolean removeNamespacePrefixMap(NamespacePrefixMapping nspm) {
        assert (nspm != null);
        return m_namespacePrefixMap.remove(nspm);
    }

    public void clearNamespacePrefixMap() {
        m_namespacePrefixMap.clear();
    }

    public String getPath() {
        return m_path;
    }

    public void setPath(String path) {
        assert (path != null);
        m_path = path;
    }

    public String getNamespaceURI() {
        return m_namespaceURI;
    }

    public void setNamespaceURI(String namespaceURI) {
        assert (namespaceURI != null);
        m_namespaceURI = namespaceURI;
    }

    public String toXML(XMLMarshaller m) throws Spml2Exception {
        return m.marshall(this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Selection)) return false;
        if (!super.equals(o)) return false;

        final Selection selectionType = (Selection) o;

        if (!m_namespacePrefixMap.equals(selectionType.m_namespacePrefixMap)) return false;
        if (!m_namespaceURI.equals(selectionType.m_namespaceURI)) return false;
        if (!m_path.equals(selectionType.m_path)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_namespacePrefixMap.hashCode();
        result = 29 * result + m_path.hashCode();
        result = 29 * result + m_namespaceURI.hashCode();
        return result;
    }
}
