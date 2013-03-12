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

import java.net.URI;

//<complexType name="CapabilityDataType">
//    <complexContent>
//        <extension base="spml:ExtensibleType">
//            <annotation>
//                <documentation>Contains capability specific elements.</documentation>
//            </annotation>
//            <attribute name="mustUnderstand" type="boolean" use="optional"/>
//            <attribute name="capabilityURI" type="anyURI" />
//        </extension>
//    </complexContent>
//</complexType>

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 2, 2006
 */
public class CapabilityData extends Extensible {

    private static final String code_id = "$Id: CapabilityData.java,v 1.3 2006/04/25 21:22:09 kas Exp $";

    // <attribute name="mustUnderstand" type="boolean" use="optional"/>
    private Boolean m_mustUnderstand = null;

    // <attribute name="capabilityURI" type="anyURI" />
    private URI m_capabilityURI;

    protected CapabilityData(Boolean mustUnderstand,
                             URI capabilityURI) {
        m_mustUnderstand = mustUnderstand;
        assert(capabilityURI != null);
        m_capabilityURI = capabilityURI;
    }

    public CapabilityData() { ; }

    public CapabilityData(boolean mustUnderstand,
                          URI capabilityURI) {
        this(new Boolean(mustUnderstand), capabilityURI);
    }

    public CapabilityData(URI capabilityURI) {
        this(null, capabilityURI);
    }

    public boolean isMustUnderstand() {
        if (m_mustUnderstand == null) return false;
        return m_mustUnderstand.booleanValue();
    }

    public void setMustUnderstand(boolean mustUnderstand) {
        m_mustUnderstand = new Boolean(mustUnderstand);
    }

    public URI getCapabilityURI() {
        return m_capabilityURI;
    }

    public void setCapabilityURI(URI capabilityURI) {
        m_capabilityURI = capabilityURI;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CapabilityData)) return false;
        if (!super.equals(o)) return false;

        final CapabilityData capabilityData = (CapabilityData) o;

        if (m_capabilityURI != null ? !m_capabilityURI.equals(capabilityData.m_capabilityURI) : capabilityData.m_capabilityURI != null) return false;
        if (m_mustUnderstand != null ? !m_mustUnderstand.equals(capabilityData.m_mustUnderstand) : capabilityData.m_mustUnderstand != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_mustUnderstand != null ? m_mustUnderstand.hashCode() : 0);
        result = 29 * result + (m_capabilityURI != null ? m_capabilityURI.hashCode() : 0);
        return result;
    }
}
