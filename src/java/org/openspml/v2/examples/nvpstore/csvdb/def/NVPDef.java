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
package org.openspml.v2.examples.nvpstore.csvdb.def;

import org.openspml.v2.msg.MarshallableElement;
import org.openspml.v2.msg.PrefixAndNamespaceTuple;

public class NVPDef implements MarshallableElement {

    private static final String code_id = "$Id: NVPDef.java,v 1.3 2006/10/06 17:47:57 kas Exp $";

    private String m_name = null;
    private NVPDataType m_type = null;
    private Boolean m_required;

    protected NVPDef() {;}

    public NVPDef(String name, NVPDataType type, boolean req) {
        m_name = name;
        m_type = type;
        m_required = Boolean.valueOf(req);
    }

    public NVPDef(String name) {
        this(name, NVPDataType.TEXT, false);
    }

    public String getName() {
        return m_name;
    }

    public boolean isMultiValued() {
        return (m_type == NVPDataType.MULTIBINARY || m_type == NVPDataType.MULTITEXT);
    }

    public NVPDataType getType() {
        return m_type;
    }

    public boolean isRequired() {
        if (m_required == null) {
            return false;
        }
        return m_required.booleanValue();
    }

    public void setRequired(boolean req) {
        m_required = Boolean.valueOf(req);
    }

    public String getElementName() {
        return "nvp";
    }

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        return NVPStoreDefMarshallableCreator.staticGetNamespacesInfo();
    }

    public boolean isValid() {
        return m_name != null && m_type != null;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NVPDef nvpDef = (NVPDef) o;

        if (m_name != null ? !m_name.equals(nvpDef.m_name) : nvpDef.m_name != null) return false;
        if (m_required != null ? !m_required.equals(nvpDef.m_required) : nvpDef.m_required != null) return false;
        if (m_type != null ? !m_type.equals(nvpDef.m_type) : nvpDef.m_type != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (m_name != null ? m_name.hashCode() : 0);
        result = 31 * result + (m_type != null ? m_type.hashCode() : 0);
        result = 31 * result + (m_required != null ? m_required.hashCode() : 0);
        return result;
    }
}
