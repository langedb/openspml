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
import java.util.Arrays;
import java.util.List;

/**
 * This is an abstract class for those FilterItems that
 * contain 0,m FilterItems.
 *
 * <pre>
 * &lt;xsd:complexType name="FilterSet"&gt;
 *      &lt;xsd:sequence&gt;
 *          &lt;xsd:group ref="FilterGroup" minOccurs="0" maxOccurs="unbounded"/&gt;
 *      &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 */
public abstract class FilterSet extends FilterItem {

    private static final String code_id = "$Id: FilterSet.java,v 1.6 2006/07/20 01:22:14 kas Exp $";

    private List mItems = new ArrayList();

    protected void toXML(String s, XmlBuffer buffer) throws DSMLProfileException {
        buffer.addStartTag(s);
        buffer.incIndent();
        for (int k = 0; k < mItems.size(); k++) {
            FilterItem filterItem = (FilterItem) mItems.get(k);
            filterItem.toXML(buffer);
        }
        buffer.decIndent();
        buffer.addEndTag(s);
    }

    public void parseXml(DSMLUnmarshaller um, Object e)
            throws DSMLProfileException {
        um.visitFilterSet(this, e);
    }

    public FilterSet(FilterItem[] items) {
        setItems(items);
    }

    public FilterSet(FilterItem item) {
        mItems.add(item);
    }

    public FilterSet() { ; }

    public FilterItem[] getItems() {
        return (FilterItem[]) mItems.toArray(new FilterItem[mItems.size()]);
    }

    public void setItems(FilterItem[] items) {
        mItems.clear();
        mItems.addAll(Arrays.asList(items));
    }

    public void addItems(FilterItem[] items) {
        mItems.addAll(Arrays.asList(items));
    }

    public void addItem(FilterItem item) {
        mItems.add(item);
    }

    public boolean removeItem(FilterItem item) {
        return mItems.remove(item);
    }

    public void clearItems() {
        mItems.clear();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilterSet)) return false;

        final FilterSet filterSet = (FilterSet) o;

        if (mItems != null ? !mItems.equals(filterSet.mItems) : filterSet.mItems != null) return false;

        return true;
    }

    public int hashCode() {
        return (mItems != null ? mItems.hashCode() : 0);
    }
}
