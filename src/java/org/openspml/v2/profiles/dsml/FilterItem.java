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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a starter class that denotes a type to restrict
 * setters on this package.
 * <p/>
 * Each of the classes representing the typed elements of this this choice,
 * e.g. "and" - which is a descendant of "FilterSet", will implement this interface.
 * <pre>
 * &lt;xsd:group name="FilterGroup"&gt;
 *     &lt;xsd:sequence&gt;
 *         &lt;xsd:choice&gt;
 *             &lt;xsd:element name="and" type="FilterSet"/&gt;
 *             &lt;xsd:element name="or" type="FilterSet"/&gt;
 *             &lt;xsd:element name="not" type="Filter"/&gt;
 *             &lt;xsd:element name="equalityMatch" type="AttributeValueAssertion"/&gt;
 *             &lt;xsd:element name="substrings" type="SubstringFilter"/&gt;
 *             &lt;xsd:element name="greaterOrEqual" type="AttributeValueAssertion"/&gt;
 *             &lt;xsd:element name="lessOrEqual" type="AttributeValueAssertion"/&gt;
 *             &lt;xsd:element name="present" type="AttributeDescription"/&gt;
 *             &lt;xsd:element name="approxMatch" type="AttributeValueAssertion"/&gt;
 *             &lt;xsd:element name="extensibleMatch" type="MatchingRuleAssertion"/&gt;
 *         &lt;/xsd:choice&gt;
 *     &lt;/xsd:sequence&gt;
 * &lt;/xsd:group&gt;
 * <p/>
 * </pre>
 */

abstract public class FilterItem implements DSMLUnmarshaller.Parseable {

    private static final String code_id = "$Id: FilterItem.java,v 1.7 2006/08/30 18:02:59 kas Exp $";

    protected FilterItem() { }

    abstract protected void toXML(XmlBuffer buffer) throws DSMLProfileException;

    abstract public void parseXml(DSMLUnmarshaller um, Object e)
            throws DSMLProfileException;

    /**
     * We want to just deal with Maps of String to List of Strings.
     * You can override this to deal with other types if you'd like,
     * but the matches() methods are implemented expecting List
     * as the value in the Map.
     *
     * @param attrs
     * @return A Map of attrName->List of valueStrings (often 1)
     */
    protected Map convertAttrsToMap(DSMLAttr[] attrs) {

        Map result = new HashMap();
        if (attrs == null) return result;

        for (int k = 0; k < attrs.length; k++) {
            DSMLAttr attr = attrs[k];
            DSMLValue[] values = attr.getValues();
            if (values != null && values.length > 0) {
                List valuesList = new ArrayList();
                for (int j = 0; j < values.length; j++) {
                    valuesList.add(values[j].getValue());
                }
                result.put(attr.getName(), valuesList);
            }
        }

        return result;
    }

    final public boolean matches(DSMLAttr[] attrs)
            throws DSMLProfileException {
        Map attrsMap = convertAttrsToMap(attrs);
        return matches(attrsMap);
    }

    public boolean matches(Map attrs) throws DSMLProfileException {
        return false;
    }

    abstract public void accept(FilterItemVisitor visitor) throws DSMLProfileException;
}
