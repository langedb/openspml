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
 * Represents the &lt;not&gt; element in a filter.
 *
 * NOTE: Since the profile does not allow for Not at the top-level (just
 * attributes and filter elements are allowed) we are implementing Not with
 * that in mind.  This makes this unsuitable for use in a general DSML 2.0
 * implementation, but that's okay.
 *
 *  <pre> &lt;xsd:element name="not" type="Filter"/&gt; </pre>
 */
public class Not extends FilterItem {

    private static final String code_id = "$Id: Not.java,v 1.7 2006/06/29 22:31:46 kas Exp $";

    private BasicFilter mFilterDelegate = new BasicFilter();

    protected Not() {
        super();
    }

    protected void toXML(XmlBuffer buffer) throws DSMLProfileException {
        String xml = mFilterDelegate.toXML("not", buffer.getIndent(), false);
        buffer.addAnyElement(xml, true);
    }

    public Not(FilterItem item) {
        mFilterDelegate.setItem(item);
    }

    public void setItem(FilterItem item) {
        mFilterDelegate.setItem(item);
    }

    public FilterItem getItem() {
        return mFilterDelegate.getItem();
    }

    public void parseXml(DSMLUnmarshaller um, Object e)
            throws DSMLProfileException {
        um.visitNot(this, e);
    }

    public boolean matches(Map attrs) throws DSMLProfileException {
        return !mFilterDelegate.getItem().matches(attrs);
    }

    public void accept(FilterItemVisitor visitor) throws DSMLProfileException {
        visitor.visitNot(this);
    }
}
