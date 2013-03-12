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

import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.PrefixAndNamespaceTuple;
import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.HashSet;
import java.util.Set;

public class NVPObjectStoreDef implements Marshallable {

    private static final String code_id = "$Id: NVPObjectStoreDef.java,v 1.1 2006/10/06 02:23:10 kas Exp $";

    /**
     * All objects (defined by and NVPOBjectDef) will have
     * an NVP with this name, with a value that names an element
     * objectdef in the schema.
     */
    private String m_nameOfTypeNVP = "type";

    /**
     * All objects (defined by and NVPOBjectDef) must have
     * an NVP with this name, with a value that serves as the
     * object id (which is unique for this store)
     */
    private String m_nameOfUidNVP = "uid";

    /**
     * This is the name of the schema/store
     */
    private String m_storeName = null;

    private ListWithType m_objectdef = new ArrayListWithType(NVPObjectDef.class);

    protected NVPObjectStoreDef() {;}

    /**
     * If an object is to be considered valid, it will have
     * an attribute with this name whose value is the name
     * of the ObjectDef that defines the attributes in the
     * object.
     * <p/>
     *
     * @return The name of the TypeAttr. (one of the attrs is defined to hold type info)
     */
    public String getNameOfTypeNVP() {
        return m_nameOfTypeNVP;
    }

    public String getNameOfUidNVP() {
        return m_nameOfUidNVP;
    }

    public NVPObjectDef[] getNVPObjectDefs() {
        return (NVPObjectDef[]) m_objectdef.toArray(new NVPObjectDef[m_objectdef.size()]);
    }

    public NVPObjectDef getNVPObjectDef(String s) {
        for (int k = 0; k < m_objectdef.size(); k++) {
            NVPObjectDef nvpObjectDef = (NVPObjectDef) m_objectdef.get(k);
            if (nvpObjectDef.getClassName().equals(s)) {
                return nvpObjectDef;
            }
        }
        return null;
    }

    public String getStoreName() {
        return m_storeName;
    }

    public String toXML(XMLMarshaller xmlMarshaller) throws Spml2Exception {
        return xmlMarshaller.marshall(this);
    }

    public String getElementName() {
        return "objectstoredef";
    }

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        return NVPStoreDefMarshallableCreator.staticGetNamespacesInfo();
    }

    public boolean isValid() {
        boolean res = (m_storeName != null && !m_objectdef.isEmpty());
        Set set = new HashSet();
        for (int k = 0; res && k < m_objectdef.size(); k++) {
            NVPObjectDef nvpObjectDef = (NVPObjectDef) m_objectdef.get(k);
            res &= nvpObjectDef.isValid();
            String type = nvpObjectDef.getClassName();
            res &= !set.contains(type);
            set.add(type);
        }
        return res;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NVPObjectStoreDef nvpObjectStoreDef = (NVPObjectStoreDef) o;

        if (m_nameOfTypeNVP != null ? !m_nameOfTypeNVP.equals(nvpObjectStoreDef.m_nameOfTypeNVP) : nvpObjectStoreDef.m_nameOfTypeNVP != null)
            return false;
        if (m_nameOfUidNVP != null ? !m_nameOfUidNVP.equals(nvpObjectStoreDef.m_nameOfUidNVP) : nvpObjectStoreDef.m_nameOfUidNVP != null)
            return false;
        if (m_objectdef != null ? !m_objectdef.equals(nvpObjectStoreDef.m_objectdef) : nvpObjectStoreDef.m_objectdef != null)
            return false;
        if (m_storeName != null ? !m_storeName.equals(nvpObjectStoreDef.m_storeName) : nvpObjectStoreDef.m_storeName != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (m_nameOfTypeNVP != null ? m_nameOfTypeNVP.hashCode() : 0);
        result = 31 * result + (m_nameOfUidNVP != null ? m_nameOfUidNVP.hashCode() : 0);
        result = 31 * result + (m_storeName != null ? m_storeName.hashCode() : 0);
        result = 31 * result + (m_objectdef != null ? m_objectdef.hashCode() : 0);
        return result;
    }
}
