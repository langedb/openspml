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

import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Arrays;

//<xsd:complexType name="AttributeDefinitionReferencesType">
//     <complexContent>
//        <extension base="spml:ExtensibleType">
//            <xsd:sequence>
//                <xsd:element name="attributeDefinitionReference" type="spmldsml:AttributeDefinitionReferenceType" minOccurs="0" maxOccurs="unbounded"/>
//            </xsd:sequence>
//        </extension>
//    </complexContent>
//</xsd:complexType>

public class AttributeDefinitionReferences extends ExtensibleElement {

    private static final String code_id = "$Id: AttributeDefinitionReferences.java,v 1.3 2006/08/30 18:02:59 kas Exp $";

    // <xsd:element name="attributeDefinitionReference" type="spmldsml:AttributeDefinitionReferenceType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_attributeDefinitionReference = new ArrayListWithType(AttributeDefinitionReference.class);

    public AttributeDefinitionReferences() { ; }

    public AttributeDefinitionReferences(AttributeDefinitionReference[] references) {
        if (references != null) {
            m_attributeDefinitionReference.addAll(Arrays.asList(references));
        }
    }

    public AttributeDefinitionReference[] getAttributeDefinitionReferences() {
        return (AttributeDefinitionReference[])
                m_attributeDefinitionReference.toArray(
                        new AttributeDefinitionReference[m_attributeDefinitionReference.size()]);
    }

    public void addAttributeDefinitionReference(AttributeDefinitionReference reference) {
        assert (reference != null);
        m_attributeDefinitionReference.add(reference);
    }

    public boolean removeAttributeDefinitionReference(AttributeDefinitionReference reference) {
        assert (reference != null);
        return m_attributeDefinitionReference.remove(reference);
    }

    public void clearAttributeDefinitionReferences() {
        m_attributeDefinitionReference.clear();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeDefinitionReferences)) return false;
        if (!super.equals(o)) return false;

        final AttributeDefinitionReferences attributeDefinitionReferences = (AttributeDefinitionReferences) o;

        if (m_attributeDefinitionReference != null ? !m_attributeDefinitionReference.equals(
                attributeDefinitionReferences.m_attributeDefinitionReference) : attributeDefinitionReferences.m_attributeDefinitionReference != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_attributeDefinitionReference != null ? m_attributeDefinitionReference.hashCode() : 0);
        return result;
    }
}
