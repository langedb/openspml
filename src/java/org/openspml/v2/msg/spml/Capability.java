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
 * <complexType name="CapabilityType">
 * <complexContent>
 * <extension base="spml:ExtensibleType">
 * <sequence>
 * <element name="appliesTo" type="spml:SchemaEntityRefType" minOccurs="0" maxOccurs="unbounded"/>
 * </sequence>
 * <attribute name="namespaceURI" type="anyURI" />
 * <attribute name="location" type="anyURI" use="optional" />
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 8, 2006
 */
public class Capability extends Extensible {

    private static final String code_id = "$Id: Capability.java,v 1.4 2006/04/25 21:22:09 kas Exp $";

    // <element name="appliesTo" type="spml:SchemaEntityRefType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_appliesTo = new ArrayListWithType(SchemaEntityRef.class);

    // <attribute name="namespaceURI" type="anyURI" />
    private URI m_namespaceURI = null; // required

    // <attribute name="location" type="anyURI" use="optional" />
    private URI m_location = null;

    public Capability() { ; }

    public Capability(SchemaEntityRef[] appliesTo,
                      URI namespaceURI,
                      URI location) {
        if (appliesTo != null)
            m_appliesTo.addAll(Arrays.asList(appliesTo));
        assert (namespaceURI != null);
        m_namespaceURI = namespaceURI;
        m_location = location;
    }

    public SchemaEntityRef[] getAppliesTo() {
        return (SchemaEntityRef[]) m_appliesTo.toArray(new SchemaEntityRef[m_appliesTo.size()]);
    }

    public void addAppliesTo(SchemaEntityRef appliesTo) {
        assert (appliesTo != null);
        m_appliesTo.add(appliesTo);
    }

    public boolean removeAppliesTo(SchemaEntityRef appliesTo) {
        assert (appliesTo != null);
        return m_appliesTo.remove(appliesTo);
    }

    public void clearAppliesTo(SchemaEntityRef appliesTo) {
        m_appliesTo.clear();
    }

    public URI getNamespaceURI() {
        return m_namespaceURI;
    }

    public void setNamespaceURI(URI namespaceURI) {
        assert (namespaceURI != null);
        m_namespaceURI = namespaceURI;
    }

    public URI getLocation() {
        return m_location;
    }

    public void setLocation(URI location) {
        m_location = location;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Capability)) return false;
        if (!super.equals(o)) return false;

        final Capability capability = (Capability) o;

        if (m_appliesTo != null ? !m_appliesTo.equals(capability.m_appliesTo) : capability.m_appliesTo != null) return false;
        if (m_location != null ? !m_location.equals(capability.m_location) : capability.m_location != null) return false;
        if (!m_namespaceURI.equals(capability.m_namespaceURI)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_appliesTo != null ? m_appliesTo.hashCode() : 0);
        result = 29 * result + m_namespaceURI.hashCode();
        result = 29 * result + (m_location != null ? m_location.hashCode() : 0);
        return result;
    }
}
