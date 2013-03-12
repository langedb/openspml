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



// <xsd:complexType name="AttributeDefinitionType">
//    <complexContent>
//       <extension base="spml:ExtensibleType">
//           <xsd:attribute name="description" type="xsd:string" use="optional"/>
//           <xsd:attribute name="multivalued" type="xsd:boolean" use="optional" default="false"/>
//           <xsd:attribute name="type" type="xsd:string" use="optional" default="xsd:string"/>
//           <xsd:attribute name="name" type="xsd:string" use="required"/>
//       </extension>
//   </complexContent>
// </xsd:complexType>

public class AttributeDefinition extends ExtensibleElement {

    private static final String code_id = "$Id: AttributeDefinition.java,v 1.2 2006/05/15 23:31:00 kas Exp $";

    // <xsd:attribute name="description" type="xsd:string" use="optional"/>
    private String m_description;

    // <xsd:attribute name="multivalued" type="xsd:boolean" use="optional" default="false"/>
    private Boolean m_multivalued;

    // <xsd:attribute name="type" type="xsd:string" use="optional" default="xsd:string"/>
    private String m_type;

    // <xsd:attribute name="name" type="xsd:string" use="required"/>
    private String m_name; // required

    public AttributeDefinition() { ; }

    protected AttributeDefinition(String name, String type, String description, Boolean multivalued) {
        super();
        assert name != null;
        m_name = name;
        m_description = description;
        m_multivalued = multivalued;
        m_type = type;
    }

    public AttributeDefinition(String name, String type, String description, boolean multivalued) {
        this(name, type, description, new Boolean(multivalued));
    }

    public AttributeDefinition(String name, String type, String description) {
        this(name, type, description, null);
    }

    public AttributeDefinition(String name, String type) {
        this(name, type, null, null);
    }

    public AttributeDefinition(String name) {
        this(name, null, null, null);
    }

    public String getDescription() {
        return m_description;
    }

    public void setDescription(String description) {
        m_description = description;
    }

    public boolean isMultivalued() {
        if (m_multivalued == null)
            return false;
        return m_multivalued.booleanValue();
    }

    public void setMultivalued(boolean multivalued) {
        m_multivalued = new Boolean(multivalued);
    }

    public String getType() {
        return m_type;
    }

    public void setType(String type) {
        m_type = type;
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
        if (!(o instanceof AttributeDefinition)) return false;
        if (!super.equals(o)) return false;

        final AttributeDefinition attributeDefinition = (AttributeDefinition) o;

        if (m_description != null ? !m_description.equals(attributeDefinition.m_description) : attributeDefinition.m_description != null) return false;
        if (m_multivalued != null ? !m_multivalued.equals(attributeDefinition.m_multivalued) : attributeDefinition.m_multivalued != null) return false;
        if (!m_name.equals(attributeDefinition.m_name)) return false;
        if (m_type != null ? !m_type.equals(attributeDefinition.m_type) : attributeDefinition.m_type != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_description != null ? m_description.hashCode() : 0);
        result = 29 * result + (m_multivalued != null ? m_multivalued.hashCode() : 0);
        result = 29 * result + (m_type != null ? m_type.hashCode() : 0);
        result = 29 * result + m_name.hashCode();
        return result;
    }
}
