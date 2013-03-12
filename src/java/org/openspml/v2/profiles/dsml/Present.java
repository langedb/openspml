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
 * A FilterItem that is a present element.
 *   This is where Java and the xsd disagree - the type in the
 *   xsd is about layout and not behavior.  We don't want Present
 *   object to be useful for variables declared as type AttributeDescription.
 * <p/>
 * <pre>
 * &lt;xsd:element name="present" type="AttributeDescription"/&gt;
 * </pre>
 */
public class Present extends NamedFilterItem {

    private static final String code_id = "$Id: Present.java,v 1.7 2006/06/29 22:31:46 kas Exp $";

    protected void toXML(XmlBuffer buffer) throws DSMLProfileException {
        buffer.addOpenStartTag("present");
        buffer.addAttribute("name", getName());
        buffer.closeEmptyElement();
    }

    public Present() {
    }

    public Present(String name) throws DSMLProfileException {
        super(name);
    }

    public boolean matches(Map attrs) throws DSMLProfileException {
        return attrs.containsKey(getName());
    }

    public void accept(FilterItemVisitor visitor) throws DSMLProfileException {
        visitor.visitPresent(this);
    }
}
