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

import java.util.Arrays;

/**
 * <complexType name="PSOType">
 * <complexContent>
 * <extension base="spml:ExtensibleType">
 * <sequence>
 * <element name="psoID" type="spml:PSOIdentifierType" />
 * <element name="data" type="spml:ExtensibleType" minOccurs="0" />
 * <element name="capabilityData" type="spml:CapabilityDataType" minOccurs="0" maxOccurs="unbounded"/>
 * </sequence>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 7, 2006
 */
public class PSO extends Extensible {

    private static final String code_id = "$Id: PSO.java,v 1.3 2006/04/25 21:22:09 kas Exp $";

    // <element name="psoID" type="spml:PSOIdentifierType" />
    private PSOIdentifier m_psoID = null;

    // <element name="data" type="spml:ExtensibleType" minOccurs="0" />
    private Extensible m_data = null;

    // <element name="capabilityData" type="spml:CapabilityDataType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_capabilityData = new ArrayListWithType(CapabilityData.class);

    public PSO() { ; }

    public PSO(PSOIdentifier psoID,
               Extensible data,
               CapabilityData[] capabilityData) {
        assert (psoID != null);
        m_psoID = psoID;

        m_data = data;
        if (capabilityData != null)
            m_capabilityData.addAll(Arrays.asList(capabilityData));

    }

    public PSOIdentifier getPsoID() {
        return m_psoID;
    }

    public void setPsoID(PSOIdentifier psoID) {
        m_psoID = psoID;
        assert (m_psoID != null);
    }

    public Extensible getData() {
        return m_data;
    }

    public void setData(Extensible data) {
        m_data = data;
    }

    public CapabilityData[] getCapabilityData() {
        return (CapabilityData[]) m_capabilityData.toArray(new CapabilityData[m_capabilityData.size()]);
    }

    public void addCapabilityData(CapabilityData capabilityData) {
        assert (capabilityData != null);
        m_capabilityData.add(capabilityData);
    }

    public boolean removeCapabilityData(CapabilityData capabilityData) {
        assert (capabilityData != null);
        return m_capabilityData.remove(capabilityData);
    }

    public void clearCapabilityData() {
        m_capabilityData.clear();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSO)) return false;
        if (!super.equals(o)) return false;

        final PSO psoType = (PSO) o;

        if (m_capabilityData != null ? !m_capabilityData.equals(psoType.m_capabilityData) : psoType.m_capabilityData != null) return false;
        if (m_data != null ? !m_data.equals(psoType.m_data) : psoType.m_data != null) return false;
        if (!m_psoID.equals(psoType.m_psoID)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_psoID.hashCode();
        result = 29 * result + (m_data != null ? m_data.hashCode() : 0);
        result = 29 * result + (m_capabilityData != null ? m_capabilityData.hashCode() : 0);
        return result;
    }
}
