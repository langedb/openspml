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
package org.openspml.v2.msg.spmlref;

import org.openspml.v2.msg.spml.SchemaEntityRef;
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Arrays;

/**
 * <complexType name="ReferenceDefinitionType">
 * <complexContent>
 * <extension base="spml:ExtensibleType">
 * <p/>
 * <sequence>
 * <element name="schemaEntity" type="spml:SchemaEntityRefType"/>
 * <element name="canReferTo" type="spml:SchemaEntityRefType" minOccurs="0" maxOccurs="unbounded"/>
 * <element name="referenceDataType" type="spml:SchemaEntityRefType" minOccurs="0" maxOccurs="unbounded"/>
 * </sequence>
 * <attribute name="typeOfReference" type="string" use="required"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 13, 2006
 */
public class ReferenceDefinition extends ExtensibleMarshallable {

    private static final String code_id = "$Id: ReferenceDefinition.java,v 1.6 2006/08/30 18:02:59 kas Exp $";

    // <element name="schemaEntity" type="spml:SchemaEntityRefType"/>
    private SchemaEntityRef m_schemaEntity = null; // required.

    // <element name="canReferTo" type="spml:SchemaEntityRefType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_canReferTo = new ArrayListWithType(SchemaEntityRef.class);

    // <element name="referenceDataType" type="spml:SchemaEntityRefType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_referenceDataType = new ArrayListWithType(SchemaEntityRef.class);

    // <attribute name="typeOfReference" type="string" use="required"/>
    private String m_typeOfReference = null; // required

    public ReferenceDefinition() { ;  }

    public ReferenceDefinition(SchemaEntityRef schemaEntity,
                               SchemaEntityRef[] canReferTo,
                               SchemaEntityRef[] referenceDataType,
                               String typeOfReference) {
        assert (schemaEntity != null);
        m_schemaEntity = schemaEntity;

        if (canReferTo != null)
            m_canReferTo.addAll(Arrays.asList(canReferTo));

        if (referenceDataType != null)
            m_referenceDataType.addAll(Arrays.asList(referenceDataType));

        assert (typeOfReference != null);
        m_typeOfReference = typeOfReference;
    }

    public SchemaEntityRef getSchemaEntity() {
        return m_schemaEntity;
    }

    public void setSchemaEntity(SchemaEntityRef schemaEntity) {
        assert (schemaEntity != null);
        m_schemaEntity = schemaEntity;
    }

    public SchemaEntityRef[] getCanReferTo() {
        return (SchemaEntityRef[]) m_canReferTo.toArray(new SchemaEntityRef[m_canReferTo.size()]);
    }

    public void addCanReferTo(SchemaEntityRef canReferTo) {
        if (canReferTo != null)
            m_canReferTo.add(canReferTo);
    }

    public boolean removeCanReferTo(SchemaEntityRef canReferTo) {
        if (canReferTo != null)
            return m_canReferTo.remove(canReferTo);
        return false;
    }

    public void clearCanReferTo() {
        m_canReferTo.clear();
    }

    public SchemaEntityRef[] getReferenceDataType() {
        return (SchemaEntityRef[]) m_referenceDataType.toArray(new SchemaEntityRef[m_referenceDataType.size()]);
    }

    public void addReferenceDataType(SchemaEntityRef referenceDataType) {
        if (referenceDataType != null)
            m_referenceDataType.add(referenceDataType);
    }

    public boolean removeReferenceDataType(SchemaEntityRef referenceDataType) {
        if (referenceDataType != null)
            return m_referenceDataType.remove(referenceDataType);
        return false;
    }

    public void clearReferenceDataType() {
        m_referenceDataType.clear();
    }

    public String getTypeOfReference() {
        return m_typeOfReference;
    }

    public void setTypeOfReference(String typeOfReference) {
        assert (typeOfReference != null);
        m_typeOfReference = typeOfReference;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferenceDefinition)) return false;
        if (!super.equals(o)) return false;

        final ReferenceDefinition referenceDefinition = (ReferenceDefinition) o;

        if (!m_canReferTo.equals(referenceDefinition.m_canReferTo)) return false;
        if (!m_referenceDataType.equals(referenceDefinition.m_referenceDataType)) return false;
        if (!m_schemaEntity.equals(referenceDefinition.m_schemaEntity)) return false;
        if (!m_typeOfReference.equals(referenceDefinition.m_typeOfReference)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_schemaEntity.hashCode();
        result = 29 * result + m_canReferTo.hashCode();
        result = 29 * result + m_referenceDataType.hashCode();
        result = 29 * result + m_typeOfReference.hashCode();
        return result;
    }
}
