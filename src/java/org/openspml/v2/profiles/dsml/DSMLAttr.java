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
 * Copyright 2006 Tripod Technology Group, Inc.
 * All Rights Reserved.
 * Use is subject to license terms.
 */
/*
 * Copyright 2006 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */
package org.openspml.v2.profiles.dsml;

import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.util.xml.XmlBuffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * From the DSML spec...
 * <p/>
 * <pre>
 * &lt;complexType name="Attr"&gt;
 * &lt;sequence&gt;
 * &lt;element name="value" type="Value" minOccurs="0" maxOccurs="unbounded"/&gt;
 * &lt;/sequence&gt;
 * &lt;attribute name="name" type="AttributeDescriptionValue" use="required"/&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 * @author Blaine Busler and Kent Spaulding
 */
public class DSMLAttr extends NamedItem implements DSMLUnmarshaller.Parseable, OpenContentElement {

    /**
     * The profile specifies that an attr with the name is reserved and
     * provides the name of the objectclass for a given set of attributes.
     */
    public static final String RESERVED_TYPE_ATTR_NAME = "objectclass";

    // <element name="value" type="DsmlValue" minOccurs="0" maxOccurs="unbounded"/>
    private List mValues = new ArrayList();

    // This is just to make the DSMLUnmarshaller a little cleaner
    protected DSMLAttr() { ; }

    public DSMLAttr(String name, String value) throws DSMLProfileException {
        setName(name);
        if (value != null)
            mValues.add(new DSMLValue(value));
    }

    public DSMLAttr(String name, DSMLValue[] values) throws DSMLProfileException {
        setName(name);
        if (values != null)
            mValues.addAll(Arrays.asList(values));
    }

    public DSMLValue[] getValues() {
        return (DSMLValue[]) mValues.toArray(new DSMLValue[mValues.size()]);
    }

    public void addValue(DSMLValue value) {
        if (value != null)
            mValues.add(value);
    }

    public void addValues(DSMLValue[] values) {
        for (int k = 0; k < values.length; k++) {
            DSMLValue value = values[k];
            addValue(value);
        }
    }

    public void setValues(DSMLValue[] values) {
        clearValues();
        addValues(values);
    }

    public void clearValues() {
        mValues.clear();
    }

    public int numValues() {
        return mValues.size();
    }

    protected void addSubclassElements(XmlBuffer buffer)
            throws DSMLProfileException {

        super.addSubclassElements(buffer);
        DSMLValue[] mVals = getValues();
        for (int k = 0; k < mVals.length; k++)
            mVals[k].toXML(buffer);
    }

    public String toXML(int indent) throws DSMLProfileException {
        return super.toXML("attr", indent);
    }

    public String toXML() throws DSMLProfileException {
        return toXML(0);
    }

    public void parseXml(DSMLUnmarshaller m, Object xmlObj)
            throws DSMLProfileException {
        m.visitDSMLAttr(this, xmlObj);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DSMLAttr)) return false;
        if (!super.equals(o)) return false;

        final DSMLAttr attr = (DSMLAttr) o;

        if (mValues != null ? !mValues.equals(attr.mValues) : attr.mValues != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (mValues != null ? mValues.hashCode() : 0);
        return result;
    }
}
