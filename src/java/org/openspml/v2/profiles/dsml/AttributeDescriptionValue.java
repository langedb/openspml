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
package org.openspml.v2.profiles.dsml;



/**
 * This class is really a wrapper around String, but it
 * insists the String meets a regular expression.
 *
 * <pre>
 *  &lt;!-- ***** AttributeDescriptionValue ***** --&gt;
 *  &lt;xsd:simpleType name="AttributeDescriptionValue"&gt;
 *      &lt;xsd:restriction base="xsd:string"&gt;
 *          &lt;xsd:pattern value="((([0-2](\.[0-9]+)+)|([a-zA-Z]+([a-zA-Z0-9]|[-])*))(;([a-zA-Z0-9]|[-])+)*)"/&gt;
 *      &lt;/xsd:restriction&gt;
 *  &lt;/xsd:simpleType&gt;
 * </pre>
 */
class AttributeDescriptionValue {

    private static final String code_id = "$Id: AttributeDescriptionValue.java,v 1.1 2006/06/01 02:47:52 kas Exp $";

    private static final String REGEX = 
        "((([0-2](\\.[0-9]+)+)|([a-zA-Z]+([a-zA-Z0-9]|[-])*))(;([a-zA-Z0-9]|[-])+)*)";
            
    private String mName = null;

    protected AttributeDescriptionValue() {
        ;
    }
    
    protected AttributeDescriptionValue(String data) throws DSMLProfileException {
        setName(data);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) throws DSMLProfileException {
        if (!name.matches(REGEX)) {
            throw new DSMLProfileException("The name, " + name + " is not valid. It must match:\n  " + REGEX);
        }
         mName = name;
    }

    public String toString() {
        return mName;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeDescriptionValue)) return false;

        final AttributeDescriptionValue attributeDescriptionValue = (AttributeDescriptionValue) o;

        if (mName != null ? !mName.equals(attributeDescriptionValue.mName) : attributeDescriptionValue.mName != null) return false;

        return true;
    }

    public int hashCode() {
        return (mName != null ? mName.hashCode() : 0);
    }
}
