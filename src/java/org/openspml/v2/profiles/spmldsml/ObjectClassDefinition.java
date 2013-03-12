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

import org.openspml.v2.msg.PrefixAndNamespaceTuple;

//<xsd:complexType name="ObjectClassDefinitionType">
//    <complexContent>
//       <extension base="spml:ExtensibleType">
//           <xsd:sequence>
//               <xsd:element name="memberAttributes" type="spmldsml:AttributeDefinitionReferencesType" minOccurs="0" maxOccurs="1"/>
//               <xsd:element name="superiorClasses" type="spmldsml:ObjectClassDefinitionReferencesType" minOccurs="0" maxOccurs="1"/>
//           </xsd:sequence>
//           <xsd:attribute name="name" type="xsd:string" use="required"/>
//           <xsd:attribute name="description" type="xsd:string" use="optional"/>
//       </extension>
//   </complexContent>
//</xsd:complexType>

public class ObjectClassDefinition extends ExtensibleElement {

    private static final String code_id = "$Id: ObjectClassDefinition.java,v 1.3 2006/08/30 18:02:59 kas Exp $";

    // <xsd:element name="memberAttributes" type="spmldsml:AttributeDefinitionReferencesType" minOccurs="0" maxOccurs="1"/>
    private AttributeDefinitionReferences m_memberAttributes;

    // <xsd:element name="superiorClasses" type="spmldsml:ObjectClassDefinitionReferencesType" minOccurs="0" maxOccurs="1"/>
    private ObjectClassDefinitionReferences m_superiorClasses;

    // <xsd:attribute name="name" type="xsd:string" use="required"/>
    private String m_name; // required

    // <xsd:attribute name="description" type="xsd:string" use="optional"/>
    private String m_description;

    public ObjectClassDefinition() { ; }

    public ObjectClassDefinition(String name,
                                 AttributeDefinitionReferences memberAttributes,
                                 ObjectClassDefinitionReferences superiorClasses,
                                 String description) {
        assert name != null;
        m_name = name;
        m_memberAttributes = memberAttributes;
        m_superiorClasses = superiorClasses;
        m_description = description;
    }

    public ObjectClassDefinition(String name,
                                 AttributeDefinitionReferences memberAttributes,
                                 ObjectClassDefinitionReferences superiorClasses) {
        this(name, memberAttributes, superiorClasses, null);
    }

    public ObjectClassDefinition(String name,
                                 AttributeDefinitionReferences memberAttributes) {
        this(name, memberAttributes, null, null);
    }

    public ObjectClassDefinition(String name) {
        this(name, null, null, null);
    }

    public AttributeDefinitionReferences getMemberAttributes() {
        return m_memberAttributes;
    }

    public void setMemberAttributes(AttributeDefinitionReferences memberAttributes) {
        m_memberAttributes = memberAttributes;
    }

    public ObjectClassDefinitionReferences getSuperiorClasses() {
        return m_superiorClasses;
    }

    public void setSuperiorClasses(ObjectClassDefinitionReferences superiorClasses) {
        m_superiorClasses = superiorClasses;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        assert name != null;
        m_name = name;
    }

    public String getDescription() {
        return m_description;
    }

    public void setDescription(String description) {
        m_description = description;
    }

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        return DSMLMarshallableCreator.staticGetNamespacesInfo();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectClassDefinition)) return false;
        if (!super.equals(o)) return false;

        final ObjectClassDefinition objectClassDefinition = (ObjectClassDefinition) o;

        if (m_description != null ? !m_description.equals(objectClassDefinition.m_description) : objectClassDefinition.m_description != null) return false;
        if (m_memberAttributes != null ? !m_memberAttributes.equals(objectClassDefinition.m_memberAttributes) : objectClassDefinition.m_memberAttributes != null) return false;
        if (m_name != null ? !m_name.equals(objectClassDefinition.m_name) : objectClassDefinition.m_name != null) return false;
        if (m_superiorClasses != null ? !m_superiorClasses.equals(objectClassDefinition.m_superiorClasses) : objectClassDefinition.m_superiorClasses != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_memberAttributes != null ? m_memberAttributes.hashCode() : 0);
        result = 29 * result + (m_superiorClasses != null ? m_superiorClasses.hashCode() : 0);
        result = 29 * result + (m_name != null ? m_name.hashCode() : 0);
        result = 29 * result + (m_description != null ? m_description.hashCode() : 0);
        return result;
    }
}
