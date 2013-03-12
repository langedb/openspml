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

import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.net.URI;
import java.util.Arrays;

/**
 * <complexType name="SchemaType">
 * <complexContent>
 * <extension base="spml:ExtensibleType">
 * <sequence>
 * <annotation>
 * </documentation>
 * </annotation>
 * <element name="supportedSchemaEntity" type="spml:SchemaEntityRefType" minOccurs="0" maxOccurs="unbounded"/>
 * </sequence>
 * <attribute name="ref" type="anyURI" use="optional" />
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 7, 2006
 */
public class Schema extends Extensible {

    private static final String code_id = "$Id: Schema.java,v 1.5 2006/06/27 00:47:19 kas Exp $";

    // <element name="supportedSchemaEntity" type="spml:SchemaEntityRefType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_supportedSchemaEntity = new ArrayListWithType(SchemaEntityRef.class);

    // <attribute name="ref" type="anyURI" use="optional" />
    private URI m_ref = null; // optional

    public Schema() { ; }

    public Schema(SchemaEntityRef[] supportedSchemaEntity,
                  URI ref) {
        if (supportedSchemaEntity != null)
            m_supportedSchemaEntity.addAll(Arrays.asList(supportedSchemaEntity));
        m_ref = ref;
    }

    public SchemaEntityRef[] getSupportedSchemaEntities() {
        return (SchemaEntityRef[]) m_supportedSchemaEntity.toArray(new SchemaEntityRef[m_supportedSchemaEntity.size()]);
    }

    public void addSupportedSchemaEntity(SchemaEntityRef se) {
        assert (se != null);
        m_supportedSchemaEntity.add(se);
    }

    public boolean removeSupportedSchemaEntity(SchemaEntityRef se) {
        assert (se != null);
        return m_supportedSchemaEntity.remove(se);
    }

    public void clearSupportedSchemaEntities() {
        m_supportedSchemaEntity.clear();
    }

    public URI getRef() {
        return m_ref;
    }

    public void setRef(URI ref) {
        m_ref = ref;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schema)) return false;
        if (!super.equals(o)) return false;

        final Schema schemaType = (Schema) o;

        if (m_ref != null ? !m_ref.equals(schemaType.m_ref) : schemaType.m_ref != null) return false;
        if (!m_supportedSchemaEntity.equals(schemaType.m_supportedSchemaEntity)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_supportedSchemaEntity.hashCode();
        result = 29 * result + (m_ref != null ? m_ref.hashCode() : 0);
        return result;
    }
}
