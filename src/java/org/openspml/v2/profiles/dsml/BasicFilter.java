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

import org.openspml.v2.msg.spml.QueryClause;
import org.openspml.v2.util.xml.XmlBuffer;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * We have one Filter that is a FilterItem, one that is not.  They
 * start inheriting/delegating from here...
 * <p/>
 * <pre>
 * &lt;!-- **** DSML Filter **** --&gt;
 * &lt;xsd:complexType name="Filter"&gt;
 *     &lt;xsd:group ref="FilterGroup"/&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 */
class BasicFilter extends QueryClause implements DSMLUnmarshaller.Parseable {

    private static final String code_id = "$Id: BasicFilter.java,v 1.8 2006/08/30 18:02:59 kas Exp $";

    protected FilterItem mItem = null;

    protected BasicFilter() { ; }

    protected BasicFilter(FilterItem item) {
        mItem = item;
    }

    // I'm betting we'll have a toXML in here... that
    // takes a subclass name like "filter" and "not"
    protected String toXML(String s, int indent, boolean addNSInfo)
            throws DSMLProfileException {


        XmlBuffer buffer = new XmlBuffer();

        if (addNSInfo) {
            try {
                buffer.setNamespace(new URI("urn:oasis:names:tc:DSML:2:0:core"));
            }
            catch (URISyntaxException e) {
                throw new DSMLProfileException(e.getMessage(), e);
            }
        }

        buffer.setPrefix("dsml");
        buffer.setIndent(indent);

        buffer.addStartTag(s);

        buffer.incIndent();

        mItem.toXML(buffer);

        buffer.decIndent();
        buffer.addEndTag(s);
        return buffer.toString();
    }

    public FilterItem getItem() {
        return mItem;
    }

    public void setItem(FilterItem item) {
        mItem = item;
    }

    public void parseXml(DSMLUnmarshaller um, Object e) throws DSMLProfileException {
        um.visitBasicFilter(this, e);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicFilter)) return false;

        final BasicFilter basicFilter = (BasicFilter) o;

        if (mItem != null ? !mItem.equals(basicFilter.mItem) : basicFilter.mItem != null) return false;

        return true;
    }

    public int hashCode() {
        return (mItem != null ? mItem.hashCode() : 0);
    }
}
