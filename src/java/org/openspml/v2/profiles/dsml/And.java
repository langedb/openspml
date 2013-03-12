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

import java.util.Map;

/**
 * From the DSML spec...
 *
 * <pre> &lt;xsd:element name="and" type="FilterSet"/&gt; </pre>
 */
public class And extends FilterSet {

    private static final String code_id = "$Id: And.java,v 1.6 2006/06/29 22:31:46 kas Exp $";

    protected void toXML(XmlBuffer buffer) throws DSMLProfileException {
        super.toXML("and", buffer);
    }

    public And(FilterItem[] items) {
        super(items);
    }

    public And(FilterItem item) {
        super(item);
    }

    public And() {
        super();
    }

    public boolean matches(Map attrs) throws DSMLProfileException {
        FilterItem items[] = getItems();
        boolean result = true;
        for (int k = 0; result == true && k < items.length; k++) {
            FilterItem item = items[k];
            result &= item.matches(attrs);
        }
        return result;
    }

    public void accept(FilterItemVisitor v) throws DSMLProfileException {
        v.visitAnd(this);
    }
}
