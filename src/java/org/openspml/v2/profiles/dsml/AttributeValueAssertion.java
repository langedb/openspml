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

import org.openspml.v2.util.xml.XmlBuffer;

import java.util.List;
import java.util.Map;


/**
 * From DSML 2.0 - this is the base of assertion classes like
 * EqualityMatch.
 *
 * <pre>
 * &lt;xsd:complexType name="AttributeValueAssertion"&gt;
 *     &lt;xsd:sequence&gt;
 *         &lt;xsd:element name="value" type="DsmlValue"/&gt;
 *     &lt;/xsd:sequence&gt;
 *     &lt;xsd:attribute name="name" type="AttributeDescriptionValue" use="required"/&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 */
abstract class AttributeValueAssertion extends NamedFilterItem {

    private static final String code_id = "$Id: AttributeValueAssertion.java,v 1.10 2006/08/30 18:02:59 kas Exp $";

    // <element name="value" type="DsmlValue"/>
    private DSMLValue mValue = null;

    protected AttributeValueAssertion() { ; }
    
    protected void addSubclassElements(XmlBuffer buffer) throws DSMLProfileException {
        super.addSubclassElements(buffer);
        if (mValue != null) {
            mValue.toXML(buffer);
        }
    }

    public void parseXml(DSMLUnmarshaller um, Object e) throws DSMLProfileException {
        um.visitAttributeValueAssertion(this, e);
    }

    public AttributeValueAssertion(String name, String value) throws DSMLProfileException {
        setName(name);
        if (value != null)
            mValue = new DSMLValue(value);
    }

    public AttributeValueAssertion(String name, DSMLValue value) throws DSMLProfileException {
        setName(name);
        if (value != null)
            mValue = value;
    }

    public DSMLValue getValue() {
        return mValue;
    }

    public void setValue(DSMLValue value) {
        if (value != null)
            mValue = value;
    }

    protected boolean valueAssertion(String thisValue, String testValue) {
        return false;
    }

    public boolean matches(Map attrs) throws DSMLProfileException {

        List values = (List) attrs.get(getName());
        if (values != null) {
            // does a value in there equal our value?
            String thisValue = getValue().toString();
            for (int k = 0; k < values.size(); k++) {
                String value = (String) values.get(k);
                if (valueAssertion(thisValue, value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeValueAssertion)) return false;
        if (!super.equals(o)) return false;

        final AttributeValueAssertion attributeValueAssertion = (AttributeValueAssertion) o;

        if (mValue != null ? !mValue.equals(attributeValueAssertion.mValue) : attributeValueAssertion.mValue != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (mValue != null ? mValue.hashCode() : 0);
        return result;
    }

}