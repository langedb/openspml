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
 * <complexType name="CapabilitiesListType">
 * <complexContent>
 * <extension base="spml:ExtensibleType">
 * <sequence>
 * <element name="capability" type="spml:CapabilityType" minOccurs="0" maxOccurs="unbounded"/>
 * </sequence>
 * </extension>
 * </complexContent>
 * </complexType>
 * <p/>
 * This should probably implement java.util.List
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 8, 2006
 */
public class CapabilitiesList extends Extensible {

    private static final String code_id = "$Id: CapabilitiesList.java,v 1.5 2006/04/25 21:22:09 kas Exp $";

    // <element name="capability" type="spml:CapabilityType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_capability = new ArrayListWithType(Capability.class);

    public CapabilitiesList() { ; }

    public CapabilitiesList(Capability[] capabilities) {
        if (capabilities != null) {
            m_capability.addAll(Arrays.asList(capabilities));
        }
    }

    public Capability[] getCapabilities() {
        return (Capability[]) m_capability.toArray(new Capability[m_capability.size()]);
    }

    public void clearCapabilities() {
        m_capability.clear();
    }

    public void addCapability(Capability capability) {
        assert (capability != null);
        m_capability.add(capability);
    }

    public boolean removeCapability(Capability capability) {
        assert (capability != null);
        return m_capability.remove(capability);
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CapabilitiesList)) return false;
        if (!super.equals(o)) return false;

        final CapabilitiesList capabilitiesList = (CapabilitiesList) o;

        if (!m_capability.equals(capabilitiesList.m_capability)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_capability.hashCode();
        return result;
    }
}
