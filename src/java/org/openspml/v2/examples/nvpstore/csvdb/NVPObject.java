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
package org.openspml.v2.examples.nvpstore.csvdb;

import org.openspml.v2.examples.nvpstore.csvdb.def.NVPDef;
import org.openspml.v2.examples.nvpstore.csvdb.def.NVPObjectDef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NVPObject {

    protected NVPObjectDef mDef;
    protected String mTypeAttrName;
    protected String mUidAttrName;

    protected Map mMap = new HashMap();

    /**
     * @param def          Has the definition of this data set, like 'class'
     * @param typeAttrName One of our attributes holds a type.
     * @param uidAttrName  One of our attributes holds an id.
     * @param map          a Map of data values to add
     */
    protected NVPObject(NVPObjectDef def,
                        String typeAttrName,
                        String uidAttrName,
                        String uid,
                        Map map) {
        mDef = def;
        mTypeAttrName = typeAttrName;
        mUidAttrName = uidAttrName;

        if (map != null) {
            mMap.putAll(map);
        }
        setType(def.getClassName());
        setUid(uid);
        validate();
    }

    protected NVPObject(NVPObjectDef def,
                        String typeAttrName,
                        String uidAttrName,
                        String uid) {
        this(def, typeAttrName, uidAttrName, uid, null);
    }

    protected void setType(String value) {
        mMap.put(mTypeAttrName, value);
    }

    protected String getClassName() {
        return mDef.getClassName();
    }

    public List validate() {

        List errors = new ArrayList();

        if (null == mTypeAttrName) {
            errors.add("The object doesn't name a type NVP.");
        }
        else if (null == getClassName()) {
            errors.add("The object doesn't specify a type; it must have a value for the '" + mTypeAttrName + "' NVP.");
        }
        else {
            Object objectclass = mMap.get(mTypeAttrName);
            if (!(objectclass instanceof String)) {
                errors.add("The object specifies a type, but it's not a String.");
            }
        }

        if (null == mUidAttrName) {
            errors.add("The object doesn't name a uid NVP.");
        }
        else if (null == getUid()) {
            errors.add("The object doesn't have a value for the uid, it must have value for the '" + mUidAttrName + "' NVP.");
        }
        else {
            Object temp = mMap.get(mUidAttrName);
            if (!(temp instanceof String)) {
                errors.add("The object specifies a Uid, but it's not a String.");
            }
        }

        NVPObjectDef def = mDef;
        if (def == null) {
            Object objectclass = mMap.get(mTypeAttrName);
            errors.add("The object specifies a type of," + objectclass + ", but it's not a defined type.");
        }
        else {
            NVPDef[] nvpDefs = def.getNVPDefs();
            for (int k = 0; k < nvpDefs.length; k++) {
                NVPDef nvpDef = nvpDefs[k];
                String name = nvpDef.getName();
                Object value = mMap.get(name);
                if (value == null) {
                    errors.add("The object does not contain a required attribute, " + name + ".");
                }
                else {
                    if (nvpDef.isMultiValued()) {
                        if (!(value instanceof List)) {
                            errors.add(
                                    "The value for attribute, " + name + ", is required to be multi-valued. (a List)");
                        }
                    }
                    else {
                        if (!(value instanceof String)) {
                            errors.add(
                                    "The value for attribute, " + name + ", is NOT multi-valued, but contains a List.");
                        }
                    }
                }
            }
        }

        return (errors.isEmpty()) ? null : errors;
    }

    // be careful with this!
    public void setUid(String newId) {
        mMap.put(mUidAttrName, newId);
    }

    protected void checkReservedNames(String name) throws NVPObjectStoreException {
        if (mTypeAttrName.equals(name)) {
            throw new NVPObjectStoreException("You cannot change the type.");
        }
        if (mUidAttrName.equals(name) && getUid() != null) {
            throw new NVPObjectStoreException("Rename is not supported.  Don't reset the uid.");
        }
    }

    protected void putObject(String name, Object value) throws NVPObjectStoreException {
        checkReservedNames(name);
        checkKnownNames(name);
        mMap.put(name, value);
    }

    private void checkKnownNames(String name) throws NVPObjectStoreException {
        if (!mDef.isDefinedNVP(name)) {
            throw new NVPObjectStoreException("There is no NVP named '" + name + ".'");
        }
    }

    public void put(String name, String value) throws NVPObjectStoreException {
        putObject(name, value);
    }

    public void put(String name, List value) throws NVPObjectStoreException {
        putObject(name, value);
    }

    public String getUid() {
        return (String) mMap.get(mUidAttrName);
    }

    public String getId() {
        return (String) mMap.get(mDef.getNameOfIdNVP());
    }

    public String getText(String name) {
        return (String) mMap.get(name);
    }

    public List getMultiText(String name) {
        return (List) mMap.get(name);
    }

    public Map asMap() {
        // should we remove the id and type?
        return Collections.unmodifiableMap(mMap);
    }

    public void remove(String name) throws NVPObjectStoreException {
        // like don't change the type or the uid.
        checkReservedNames(name);
        mMap.remove(name);
    }

    public String toString() {
        return mMap.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NVPObject nvpObject = (NVPObject) o;

        if (mDef != null ? !mDef.equals(nvpObject.mDef) : nvpObject.mDef != null) return false;
        if (mMap != null ? !mMap.equals(nvpObject.mMap) : nvpObject.mMap != null) return false;
        if (mTypeAttrName != null ? !mTypeAttrName.equals(nvpObject.mTypeAttrName) : nvpObject.mTypeAttrName != null)
            return false;
        if (mUidAttrName != null ? !mUidAttrName.equals(nvpObject.mUidAttrName) : nvpObject.mUidAttrName != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (mDef != null ? mDef.hashCode() : 0);
        result = 31 * result + (mTypeAttrName != null ? mTypeAttrName.hashCode() : 0);
        result = 31 * result + (mUidAttrName != null ? mUidAttrName.hashCode() : 0);
        result = 31 * result + (mMap != null ? mMap.hashCode() : 0);
        return result;
    }
}
