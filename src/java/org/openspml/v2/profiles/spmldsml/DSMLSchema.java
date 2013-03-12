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

import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;
import org.openspml.v2.util.xml.ReflectiveXMLMarshaller;

import java.util.Arrays;

//<xsd:complexType name="SchemaType">
//    <complexContent>
//       <extension base="spml:ExtensibleType">
//           <xsd:sequence>
//               <xsd:element name="objectClassDefinition" type="spmldsml:ObjectClassDefinitionType" minOccurs="0" maxOccurs="unbounded"/>
//               <xsd:element name="attributeDefinition" type="spmldsml:AttributeDefinitionType" minOccurs="0" maxOccurs="unbounded"/>
//           </xsd:sequence>
//       </extension>
//   </complexContent>
//</xsd:complexType>

public class DSMLSchema extends ExtensibleElement implements Marshallable, OpenContentElement {

    private static final String code_id = "$Id: DSMLSchema.java,v 1.5 2006/10/04 01:06:12 kas Exp $";

    // <xsd:element name="objectClassDefinition" type="spmldsml:ObjectClassDefinitionType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_objectClassDefinition = new ArrayListWithType(ObjectClassDefinition.class);

    // <xsd:element name="attributeDefinition" type="spmldsml:AttributeDefinitionType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_attributeDefinition = new ArrayListWithType(AttributeDefinition.class);

    public DSMLSchema() { ; }

    public DSMLSchema(ObjectClassDefinition[] objectClassDefs,
                  AttributeDefinition[] attributeDefs) {
        if (objectClassDefs != null) {
            m_objectClassDefinition.addAll(Arrays.asList(objectClassDefs));
        }
        if (attributeDefs != null) {
            m_attributeDefinition.addAll(Arrays.asList(attributeDefs));
        }
    }

    public String getElementName() {
        return "schema";
    }

    public ObjectClassDefinition[] getObjectClassDefinitions() {
        return (ObjectClassDefinition[])
                m_objectClassDefinition.toArray(new ObjectClassDefinition[m_objectClassDefinition.size()]);
    }

    public void addObjectClassDefinition(ObjectClassDefinition reference) {
        assert (reference != null);
        m_objectClassDefinition.add(reference);
    }

    public boolean removeObjectClassDefinition(ObjectClassDefinition reference) {
        assert (reference != null);
        return m_objectClassDefinition.remove(reference);
    }

    public void clearObjectClassDefinitions() {
        m_objectClassDefinition.clear();
    }

    public AttributeDefinition[] getAttributeDefinitions() {
        return (AttributeDefinition[])
                m_attributeDefinition.toArray(new AttributeDefinition[m_attributeDefinition.size()]);
    }

    public void addAttributeDefinition(AttributeDefinition reference) {
        assert (reference != null);
        m_attributeDefinition.add(reference);
    }

    public boolean removeAttributeDefinition(AttributeDefinition reference) {
        assert (reference != null);
        return m_attributeDefinition.remove(reference);
    }

    public void clearAttributeDefinitions() {
        m_attributeDefinition.clear();
    }

    public String toXML(XMLMarshaller m) throws Spml2Exception {
        return m.marshall(this);
    }

    /**
     * Most of the checks can be handled with setters/getters and assertions.
     *
     * @return   true if valid, false otherwise.
     */
    public boolean isValid() {
        return true;
    }

    public String toXML(int indent) throws Spml2Exception {
        XMLMarshaller m = new ReflectiveXMLMarshaller();
        m.setIndent(indent);
        return m.marshall(this);
    }

    public String toXML() throws Spml2Exception {
        return toXML(0);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DSMLSchema)) return false;
        if (!super.equals(o)) return false;

        final DSMLSchema schema = (DSMLSchema) o;

        if (m_attributeDefinition != null ? !m_attributeDefinition.equals(schema.m_attributeDefinition) : schema.m_attributeDefinition != null) return false;
        if (m_objectClassDefinition != null ? !m_objectClassDefinition.equals(schema.m_objectClassDefinition) : schema.m_objectClassDefinition != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_objectClassDefinition != null ? m_objectClassDefinition.hashCode() : 0);
        result = 29 * result + (m_attributeDefinition != null ? m_attributeDefinition.hashCode() : 0);
        return result;
    }

}
