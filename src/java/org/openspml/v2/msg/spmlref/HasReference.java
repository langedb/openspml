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

import org.openspml.v2.msg.PrefixAndNamespaceTuple;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.msg.spml.PSOIdentifier;
import org.openspml.v2.msg.spml.QueryClause;

/**
 * <complexType name="HasReferenceType">
 * <complexContent>
 * <extension base="spml:QueryClauseType">
 * <sequence>
 * <element name="toPsoID" type="spml:PSOIdentifierType" minOccurs="0"/>
 * <element name="referenceData" type="spml:ExtensibleType" minOccurs="0" />
 * </sequence>
 * <attribute name="typeOfReference" type="string" use="optional"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 13, 2006
 */
public class HasReference extends QueryClause {

    private static final String code_id = "$Id: HasReference.java,v 1.4 2006/05/15 23:31:00 kas Exp $";

    // <element name="toPsoID" type="spml:PSOIdentifierType" minOccurs="0"/>
    private PSOIdentifier m_toPsoID = null; // optional

    // <element name="referenceData" type="spml:ExtensibleType" minOccurs="0" />
    private Extensible m_referenceData = null; // optional

    // <attribute name="typeOfReference" type="string" use="optional"/>
    private String m_typeOfReference = null; // optional

    public HasReference() { ; }

    public HasReference(PSOIdentifier toPsoID,
                        Extensible referenceData,
                        String typeOfReference) {
        m_toPsoID = toPsoID;
        m_referenceData = referenceData;
        m_typeOfReference = typeOfReference;
    }

    public PSOIdentifier getToPsoID() {
        return m_toPsoID;
    }

    public void setToPsoID(PSOIdentifier toPsoID) {
        m_toPsoID = toPsoID;
    }

    public Extensible getReferenceData() {
        return m_referenceData;
    }

    public void setReferenceData(Extensible referenceData) {
        m_referenceData = referenceData;
    }

    public String getTypeOfReference() {
        return m_typeOfReference;
    }

    public void setTypeOfReference(String typeOfReference) {
        m_typeOfReference = typeOfReference;
    }

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        return PrefixAndNamespaceTuple.concatNamespacesInfo(
                super.getNamespacesInfo(),
                ExtensibleMarshallable.staticGetNamespacesInfo());
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HasReference)) return false;
        if (!super.equals(o)) return false;

        final HasReference hasReference = (HasReference) o;

        if (m_referenceData != null ? !m_referenceData.equals(hasReference.m_referenceData) : hasReference.m_referenceData != null) return false;
        if (m_toPsoID != null ? !m_toPsoID.equals(hasReference.m_toPsoID) : hasReference.m_toPsoID != null) return false;
        if (m_typeOfReference != null ? !m_typeOfReference.equals(hasReference.m_typeOfReference) : hasReference.m_typeOfReference != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_toPsoID != null ? m_toPsoID.hashCode() : 0);
        result = 29 * result + (m_referenceData != null ? m_referenceData.hashCode() : 0);
        result = 29 * result + (m_typeOfReference != null ? m_typeOfReference.hashCode() : 0);
        return result;
    }
}
