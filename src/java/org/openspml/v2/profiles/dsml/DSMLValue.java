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

import org.openspml.v2.util.xml.XmlBuffer;

/**
 * <code>
 * <xsd:simpleType name="Value">
 *    <xsd:union memberTypes="xsd:string xsd:base64Binary xsd:anyURI"/>
 * </xsd:simpleType>
 * </code>
 *
 * @author Blaine Busler and Kent Spaulding
 */
public class DSMLValue {

    // <xsd:union memberTypes="xsd:string xsd:base64Binary xsd:anyURI"/>
    private String mValue = null;

    public DSMLValue(String value) {
        this.mValue = value;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }

    protected void toXML(String name, XmlBuffer buffer) {
        buffer.addStartTag(name, false);
        buffer.addContent(mValue);
        buffer.addEndTag(name, false);
    }

    protected void toXML(XmlBuffer buffer) {
        toXML("value", buffer);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DSMLValue)) return false;

        final DSMLValue value = (DSMLValue) o;

        if (mValue != null ? !mValue.equals(value.mValue) : value.mValue != null) return false;

        return true;
    }

    public int hashCode() {
        return (mValue != null ? mValue.hashCode() : 0);
    }

    public String toString() {
        return mValue;
    }
}
