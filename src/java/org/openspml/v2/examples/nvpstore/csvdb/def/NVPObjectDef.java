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
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Iterator;

/**
 * An NVP Group is a named set of NVPs.  The name is bascially a "type".
 * An NVP is a name-value-pair (aka Attribute).  Value can be multiple, or not.
 */
public class NVPObjectDef implements MarshallableElement {

    private static final String code_id = "$Id: NVPObjectDef.java,v 1.4 2006/10/17 18:58:20 kas Exp $";

    private String m_classname;// what 'type' does this map to?  set when we parse
    private String m_nameOfIdNVP;

    // what is the set of NVPs that we need to be an object.
    private ListWithType m_nvpdef = new ArrayListWithType(NVPDef.class);

    // These are the "superclasses" - can be null
    private ListWithType m_superclass = new ArrayListWithType(NVPObjectDefReference.class);

    protected NVPObjectDef() { }

    public String getClassName() {
        return m_classname;
    }

    public String getNameOfIdNVP() {
        return m_nameOfIdNVP;
    }

    public NVPDef[] getNVPDefs() {
        return (NVPDef[]) m_nvpdef.toArray(new NVPDef[m_nvpdef.size()]);
    }

    public NVPObjectDefReference[] getParents() {
        return (NVPObjectDefReference[]) m_superclass.toArray(new NVPObjectDefReference[m_superclass.size()]);
    }

    public boolean hasParents() {
        return m_superclass != null && !m_superclass.isEmpty();
    }

    public boolean isDefinedNVP(String name) {
        for (Iterator iterator = m_nvpdef.iterator(); iterator.hasNext();) {
            NVPDef nvpDef = (NVPDef) iterator.next();
            if (nvpDef.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String getElementName() {
        return "objectdef";
    }

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        return NVPStoreDefMarshallableCreator.staticGetNamespacesInfo();
    }

    public boolean isValid() {

        if (m_classname != null && !m_nvpdef.isEmpty() && m_nameOfIdNVP != null) {

            // do we have an nvpDef with the name?
            for (int i = 0; i < m_nvpdef.size(); i++) {
                NVPDef def = (NVPDef) m_nvpdef.get(i);
                if (def.getName().equals(m_nameOfIdNVP)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NVPObjectDef that = (NVPObjectDef) o;

        if (m_classname != null ? !m_classname.equals(that.m_classname) : that.m_classname != null) return false;
        if (m_nameOfIdNVP != null ? !m_nameOfIdNVP.equals(that.m_nameOfIdNVP) : that.m_nameOfIdNVP != null)
            return false;
        if (m_nvpdef != null ? !m_nvpdef.equals(that.m_nvpdef) : that.m_nvpdef != null) return false;
        if (m_superclass != null ? !m_superclass.equals(that.m_superclass) : that.m_superclass != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (m_classname != null ? m_classname.hashCode() : 0);
        result = 31 * result + (m_nameOfIdNVP != null ? m_nameOfIdNVP.hashCode() : 0);
        result = 31 * result + (m_nvpdef != null ? m_nvpdef.hashCode() : 0);
        result = 31 * result + (m_superclass != null ? m_superclass.hashCode() : 0);
        return result;
    }
}