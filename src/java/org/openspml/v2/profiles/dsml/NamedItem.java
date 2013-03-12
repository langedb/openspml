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

import java.net.URI;
import java.net.URISyntaxException;

/**
 * This is base class to capture the various objects that have names of Attributes.
 */
abstract class NamedItem {

    private static final String code_id = "$Id: NamedItem.java,v 1.6 2006/06/21 22:41:37 kas Exp $";

    /**
     * <pre> &lt;xsd:attribute name="name" type="AttributeDescriptionValue" use="optional"/&gt; </pre>
     */
    private AttributeDescriptionValue mName = new AttributeDescriptionValue();

    protected NamedItem() { ; }

    protected NamedItem(String name) throws DSMLProfileException {
        setName(name);
    }

    public String getName() {
        return mName.toString();
    }

    public void setName(String name) throws DSMLProfileException {
        mName.setName(name);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NamedItem)) return false;

        final NamedItem namedItem = (NamedItem) o;

        if (mName != null ? !mName.equals(namedItem.mName) : namedItem.mName != null) return false;

        return true;
    }

    public int hashCode() {
        return (mName != null ? mName.hashCode() : 0);
    }


    protected void addSubclassAttributes(XmlBuffer buffer)
            throws DSMLProfileException {
        ;
    }

    protected void addSubclassElements(XmlBuffer buffer)
            throws DSMLProfileException {
        ;
    }

    protected String toXML(String s)
            throws DSMLProfileException {
        return toXML(s, 0, true);
    }

    protected String toXML(String s, int indent)
            throws DSMLProfileException {
        return toXML(s, indent, true);
    }

    private void toXML(String s, XmlBuffer buffer, boolean setNS)
            throws DSMLProfileException {

        if (setNS) {
            try {
                buffer.setNamespace(new URI("urn:oasis:names:tc:DSML:2:0:core"));
            }
            catch (URISyntaxException e) {
                // safety; should never happen
                throw new DSMLProfileException("Unable to set namespace", e);
            }
        }

        buffer.setPrefix("dsml");

        buffer.addOpenStartTag(s);
        buffer.addAttribute("name", mName);
        addSubclassAttributes(buffer);
        buffer.closeStartTag();

        buffer.incIndent();
        addSubclassElements(buffer);
        buffer.decIndent();
        buffer.addEndTag(s);

    }

    protected String toXML(String s, int indent, boolean setNS)
            throws DSMLProfileException {

            XmlBuffer buffer = new XmlBuffer();

            buffer.setIndent(indent);
            toXML(s, buffer, setNS);

            return buffer.toString();
    }

}
