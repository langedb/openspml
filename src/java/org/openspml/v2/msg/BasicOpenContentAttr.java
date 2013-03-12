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
package org.openspml.v2.msg;

/**
 * SPML 2.0 is an open content model.  The content carried by the various
 * objects in our toolkit, can be XML of any type, and implementors can
 * use whatever extra attributes and elements that they like.
 * <p/>
 * This class, and the OpenContentElement, are meant to represent the notion
 * of open content.
 * <p/>
 * This is a class used to represent XMLAttributes (name, type, and value)
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 1, 2006
 */
public class BasicOpenContentAttr implements OpenContentAttr {

    private static final String code_id = "$Id: BasicOpenContentAttr.java,v 1.3 2006/05/23 18:00:57 kas Exp $";

    private final String mName;
    private final String mValue;

    /**
     * We want to ensure that the value is 'simple' - that
     * is, representable as a String.  It could be a boolean,
     * int, etc; but not a complexType (so not any POJO).
     *
     * @param name
     * @param value
     */
    public BasicOpenContentAttr(String name, String value) {
        assert(name != null);
        assert(value != null);
        this.mName = name;
        this.mValue = value;
    }

    public String getName() {
        return mName;
    }

    public String getValue() {
        return mValue;
    }

    // consider adding helper methods like this.
    public boolean getBooleanValue() {
        if (mValue == null) return false;
        return Boolean.valueOf(mValue).booleanValue();
    }

    // do we want the value in the .equals?
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicOpenContentAttr)) return false;

        final BasicOpenContentAttr basicOpenContentAttr = (BasicOpenContentAttr) o;

        if (mName != null ? !mName.equals(basicOpenContentAttr.mName) : basicOpenContentAttr.mName != null) return false;
        if (mValue != null ? !mValue.equals(basicOpenContentAttr.mValue) : basicOpenContentAttr.mValue != null) return false;

        return true;
    }

    // include the value?
    public int hashCode() {
        int result;
        result = (mName != null ? mName.hashCode() : 0);
        result = 29 * result + (mValue != null ? mValue.hashCode() : 0);
        return result;
    }
}