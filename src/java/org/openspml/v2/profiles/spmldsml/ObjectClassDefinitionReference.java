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

import java.net.URI;

//<xsd:complexType name="ObjectClassDefinitionReferenceType">
//    <complexContent>
//       <extension base="spml:ExtensibleType">
//           <xsd:attribute name="schemaref" type="anyURI" use="optional" />
//           <xsd:attribute name="name" type="xsd:string" use="required"/>
//       </extension>
//   </complexContent>
//</xsd:complexType>

public class ObjectClassDefinitionReference extends ExtensibleElement {

    private static final String code_id = "$Id: ObjectClassDefinitionReference.java,v 1.3 2006/08/30 18:02:59 kas Exp $";

    // <xsd:attribute name="name" type="xsd:string" use="required"/>
    private String m_name;

    // <xsd:attribute name="schemaref" type="anyURI" use="optional" />
    private URI m_schemaref;

    protected ObjectClassDefinitionReference() { ; }

    public ObjectClassDefinitionReference(String name, URI schemaref) {
        assert name != null;
        m_name = name;
        m_schemaref = schemaref;
    }

    public ObjectClassDefinitionReference(String name) {
        this(name, null);
    }

    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        assert name != null;
        m_name = name;
    }

    public URI getSchemaref() {
        return m_schemaref;
    }

    public void setSchemaref(URI schemaref) {
        m_schemaref = schemaref;
    }

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        return DSMLMarshallableCreator.staticGetNamespacesInfo();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectClassDefinitionReference)) return false;

        final ObjectClassDefinitionReference objectClassDefinitionReference = (ObjectClassDefinitionReference) o;

        if (m_name != null ? !m_name.equals(objectClassDefinitionReference.m_name) : objectClassDefinitionReference.m_name != null) return false;
        if (m_schemaref != null ? !m_schemaref.equals(objectClassDefinitionReference.m_schemaref) : objectClassDefinitionReference.m_schemaref != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (m_name != null ? m_name.hashCode() : 0);
        result = 29 * result + (m_schemaref != null ? m_schemaref.hashCode() : 0);
        return result;
    }
}
