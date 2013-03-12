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
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Arrays;

//<xsd:complexType name="ObjectClassDefinitionReferencesType">
//     <complexContent>
//        <extension base="spml:ExtensibleType">
//            <xsd:sequence>
//                <xsd:element name="objectClassDefinitionReference" type="spmldsml:ObjectClassDefinitionReferenceType" minOccurs="0" maxOccurs="unbounded"/>
//            </xsd:sequence>
//        </extension>
//    </complexContent>
//</xsd:complexType>

public class ObjectClassDefinitionReferences extends ExtensibleElement {

    private static final String code_id = "$Id: ObjectClassDefinitionReferences.java,v 1.3 2006/08/30 18:02:59 kas Exp $";

    private ListWithType m_objectClassDefinitionReference = new ArrayListWithType(ObjectClassDefinitionReference.class);

    public ObjectClassDefinitionReferences() { ; }

    public ObjectClassDefinitionReferences(ObjectClassDefinitionReference[] references) {
        if (references != null) {
            m_objectClassDefinitionReference.addAll(Arrays.asList(references));
        }
    }

    public ObjectClassDefinitionReference[] getObjectClassDefinitionReferences() {
        return (ObjectClassDefinitionReference[])
                m_objectClassDefinitionReference.toArray(
                        new ObjectClassDefinitionReference[m_objectClassDefinitionReference.size()]);
    }

    public void addObjectClassDefinitionReference(ObjectClassDefinitionReference reference) {
        assert (reference != null);
        m_objectClassDefinitionReference.add(reference);
    }

    public boolean removeObjectClassDefinitionReference(ObjectClassDefinitionReference reference) {
        assert (reference != null);
        return m_objectClassDefinitionReference.remove(reference);
    }

    public void clearObjectClassDefinitionReferences() {
        m_objectClassDefinitionReference.clear();
    }

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        return DSMLMarshallableCreator.staticGetNamespacesInfo();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectClassDefinitionReferences)) return false;
        if (!super.equals(o)) return false;

        final ObjectClassDefinitionReferences objectClassDefinitionReferences = (ObjectClassDefinitionReferences) o;

        if (m_objectClassDefinitionReference != null ? !m_objectClassDefinitionReference.equals(
                objectClassDefinitionReferences.m_objectClassDefinitionReference) : objectClassDefinitionReferences.m_objectClassDefinitionReference != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_objectClassDefinitionReference != null ? m_objectClassDefinitionReference.hashCode() : 0);
        return result;
    }
}
