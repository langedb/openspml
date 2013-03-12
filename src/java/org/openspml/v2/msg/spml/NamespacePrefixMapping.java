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
 * <complexType name="NamespacePrefixMappingType">
 * <complexContent>
 * <extension base="spml:ExtensibleType">
 * <attribute name="prefix" type="string" use="required"/>
 * <attribute name="namespace" type="string" use="required"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 7, 2006
 */
public class NamespacePrefixMapping extends Extensible {

    private static final String code_id = "$Id: NamespacePrefixMapping.java,v 1.3 2006/04/25 21:22:09 kas Exp $";

    // <attribute name="prefix" type="string" use="required"/>
    private String m_prefix;

    // <attribute name="namespace" type="string" use="required"/>
    private String m_namespace;

    public NamespacePrefixMapping() { ; }

    public NamespacePrefixMapping(String prefix,
                                  String namespace) {
        assert (prefix != null && namespace != null);
        m_prefix = prefix;
        m_namespace = namespace;
    }

    public String getPrefix() {
        return m_prefix;
    }

    public void setPrefix(String prefix) {
        assert (prefix != null);
        m_prefix = prefix;
    }

    public String getNamespace() {
        return m_namespace;
    }

    public void setNamespace(String namespace) {
        assert (namespace != null);
        m_namespace = namespace;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NamespacePrefixMapping)) return false;
        if (!super.equals(o)) return false;

        final NamespacePrefixMapping namespacePrefixMappingType = (NamespacePrefixMapping) o;

        if (!m_namespace.equals(namespacePrefixMappingType.m_namespace)) return false;
        if (!m_prefix.equals(namespacePrefixMappingType.m_prefix)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_prefix.hashCode();
        result = 29 * result + m_namespace.hashCode();
        return result;
    }
}
