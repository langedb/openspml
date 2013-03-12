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
 * <complexType name="ModificationType">
 * <complexContent>
 * <extension base="spml:ExtensibleType">
 * <sequence>
 * <element name="component" type="spml:SelectionType" minOccurs="0" />
 * <element name="data" type="spml:ExtensibleType" minOccurs="0" />
 * <element name="capabilityData" type="spml:CapabilityDataType" minOccurs="0" maxOccurs="unbounded"/>
 * </sequence>
 * <attribute name="modificationMode" type="spml:ModificationModeType" use="optional"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 7, 2006
 */
public class Modification extends Extensible {

    private static final String code_id = "$Id: Modification.java,v 1.6 2006/08/30 18:02:59 kas Exp $";

    // <element name="component" type="spml:SelectionType" minOccurs="0" />
    private Selection m_component = null;

    // <element name="data" type="spml:ExtensibleType" minOccurs="0" />
    private Extensible m_data = null;

    // <element name="capabilityData" type="spml:CapabilityDataType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_capabilityData = new ArrayListWithType(CapabilityData.class);

    // <attribute name="modificationMode" type="spml:ModificationModeType" use="optional"/>
    private ModificationMode m_modificationMode = null;

    public Modification() { ; }

    public Modification(Selection component,
                        Extensible data,
                        CapabilityData[] capabilityData,
                        ModificationMode modificationMode) {
        m_component = component;
        m_data = data;
        if (capabilityData != null) {
            m_capabilityData.addAll(Arrays.asList(capabilityData));
        }
        m_modificationMode = modificationMode;
    }

    public Selection getComponent() {
        return m_component;
    }

    public void setComponent(Selection component) {
        m_component = component;
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

    public ModificationMode getModificationMode() {
        return m_modificationMode;
    }

    public void setModificationMode(ModificationMode modificationMode) {
        m_modificationMode = modificationMode;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Modification)) return false;
        if (!super.equals(o)) return false;

        final Modification modificationType = (Modification) o;

        if (!m_capabilityData.equals(modificationType.m_capabilityData)) return false;
        if (m_component != null ? !m_component.equals(modificationType.m_component) : modificationType.m_component != null) return false;
        if (m_data != null ? !m_data.equals(modificationType.m_data) : modificationType.m_data != null) return false;
        if (m_modificationMode != null ? !m_modificationMode.equals(modificationType.m_modificationMode) : modificationType.m_modificationMode != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_component != null ? m_component.hashCode() : 0);
        result = 29 * result + (m_data != null ? m_data.hashCode() : 0);
        result = 29 * result + m_capabilityData.hashCode();
        result = 29 * result + (m_modificationMode != null ? m_modificationMode.hashCode() : 0);
        return result;
    }
}
