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

/**
 * From DSML 2.0 - this is the base of assertion classes like
 * EqualityMatch.
 * <p/>
 * <pre>
 * &lt;xsd:complexType name="AttributeDescription"&gt;
 *     &lt;xsd:attribute name="name" type="AttributeDescriptionValue" use="required"/&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 */
public class AttributeDescription extends NamedItem implements DSMLUnmarshaller.Parseable {

    private static final String code_id = "$Id: AttributeDescription.java,v 1.5 2006/06/27 00:47:19 kas Exp $";

    protected AttributeDescription() { ; }

    public AttributeDescription(String name) throws DSMLProfileException {
        setName(name);
    }

    protected void toXML(XmlBuffer buffer) throws DSMLProfileException {
        buffer.addOpenStartTag("attribute");
        buffer.addAttribute("name", getName());
        buffer.closeEmptyElement();
    }

    public void parseXml(DSMLUnmarshaller dsmlUnmarshaller, Object xmlObj)
            throws DSMLProfileException {
        dsmlUnmarshaller.visitAttributeDescription(this, xmlObj);
    }
}