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

/**
 * <complexType name="TargetType">
 * <complexContent>
 * <extension base="spml:ExtensibleType">
 * <sequence>
 * <element name="schema" type="spml:SchemaType" maxOccurs="unbounded"/>
 * <element name="capabilities" type="spml:CapabilitiesListType" minOccurs="0" />
 * </sequence>
 * <attribute name="targetID" type="string" use="optional"/>
 * <attribute name="profile" type="anyURI" use="optional" />
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 8, 2006
 */
public class Target extends Extensible {

    private static final String code_id = "$Id: Target.java,v 1.4 2006/04/26 19:54:55 kas Exp $";

    // <element name="schema" type="spml:SchemaType" maxOccurs="unbounded"/>
    private ListWithType m_schema = new ArrayListWithType(Schema.class);

    // <element name="capabilities" type="spml:CapabilitiesListType" minOccurs="0" />
    private CapabilitiesList m_capabilities = null;

    // <attribute name="targetID" type="string" use="optional"/>
    private String m_targetID = null;

    // <attribute name="profile" type="anyURI" use="optional" />
    private URI m_profile = null;

    public Target() { ; }

    public Target(Schema[] schema,
                  CapabilitiesList capabilities,
                  String targetID,
                  URI profile) {
        assert (schema != null);
        assert (schema.length > 0);
        for (int k = 0; k < schema.length; k++) {
            assert (schema[k] != null);
            m_schema.add(schema[k]);
        }

        m_capabilities = capabilities;
        m_targetID = targetID;
        m_profile = profile;
    }

    public Schema[] getSchemas() {
        return (Schema[]) m_schema.toArray(new Schema[m_schema.size()]);
    }

    public void addSchema(Schema schema) {
        assert (schema != null);
        m_schema.add(schema);
    }

    public boolean removeSchema(Schema schema) {
        assert (schema != null);
        return m_schema.remove(schema);
    }

    public void clearSchemas() {
        m_schema.clear();
    }

    public CapabilitiesList getCapabilities() {
        return m_capabilities;
    }

    public void setCapabilities(CapabilitiesList capabilities) {
        m_capabilities = capabilities;
    }

    public String getTargetID() {
        return m_targetID;
    }

    public void setTargetID(String targetID) {
        m_targetID = targetID;
    }

    public URI getProfile() {
        return m_profile;
    }

    public void setProfile(URI profile) {
        m_profile = profile;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Target)) return false;
        if (!super.equals(o)) return false;

        final Target target = (Target) o;

        if (m_capabilities != null ? !m_capabilities.equals(target.m_capabilities) : target.m_capabilities != null) return false;
        if (m_profile != null ? !m_profile.equals(target.m_profile) : target.m_profile != null) return false;
        if (!m_schema.equals(target.m_schema)) return false;
        if (m_targetID != null ? !m_targetID.equals(target.m_targetID) : target.m_targetID != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_schema.hashCode();
        result = 29 * result + (m_capabilities != null ? m_capabilities.hashCode() : 0);
        result = 29 * result + (m_targetID != null ? m_targetID.hashCode() : 0);
        result = 29 * result + (m_profile != null ? m_profile.hashCode() : 0);
        return result;
    }
}
