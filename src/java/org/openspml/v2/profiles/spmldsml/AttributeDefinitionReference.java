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
package org.openspml.v2.profiles.spmldsml;

import java.net.URI;

//<xsd:complexType name="AttributeDefinitionReferenceType">
//    <complexContent>
//       <extension base="spml:ExtensibleType">
//           <xsd:attribute name="schema" type="anyURI" use="optional" />
//           <xsd:attribute name="required" type="xsd:boolean" use="optional" default="false"/>
//           <xsd:attribute name="name" type="xsd:string" use="required"/>
//       </extension>
//   </complexContent>
//</xsd:complexType>

public class AttributeDefinitionReference extends ExtensibleElement {

    private static final String code_id = "$Id: AttributeDefinitionReference.java,v 1.2 2006/05/15 23:31:00 kas Exp $";

    // <xsd:attribute name="schema" type="anyURI" use="optional" />
    private URI m_schema;

    // <xsd:attribute name="required" type="xsd:boolean" use="optional" default="false"/>
    private Boolean m_required;

    // <xsd:attribute name="name" type="xsd:string" use="required"/>
    private String m_name;

    protected AttributeDefinitionReference(String name, URI schema, Boolean required) {
        assert name != null;
        m_schema = schema;
        m_required = required;
        m_name = name;
    }

    public AttributeDefinitionReference() { ; }

    public AttributeDefinitionReference(String name, URI schema, boolean required) {
        this(name, schema, new Boolean(required));
    }

    public AttributeDefinitionReference(String name, URI schema) {
        this(name, schema, null);
    }

    public AttributeDefinitionReference(String name) {
        this(name, null, null);
    }

    public URI getSchema() {
        return m_schema;
    }

    public void setSchema(URI schema) {
        m_schema = schema;
    }

    public Boolean getRequired() {
        return m_required;
    }

    public void setRequired(Boolean required) {
        m_required = required;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        assert name != null;
        m_name = name;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeDefinitionReference)) return false;
        if (!super.equals(o)) return false;

        final AttributeDefinitionReference attributeDefinitionReference = (AttributeDefinitionReference) o;

        if (!m_name.equals(attributeDefinitionReference.m_name)) return false;
        if (m_required != null ? !m_required.equals(attributeDefinitionReference.m_required) : attributeDefinitionReference.m_required != null) return false;
        if (m_schema != null ? !m_schema.equals(attributeDefinitionReference.m_schema) : attributeDefinitionReference.m_schema != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_schema != null ? m_schema.hashCode() : 0);
        result = 29 * result + (m_required != null ? m_required.hashCode() : 0);
        result = 29 * result + m_name.hashCode();
        return result;
    }
}
