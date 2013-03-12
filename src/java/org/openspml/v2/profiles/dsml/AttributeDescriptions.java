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

import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.util.xml.XmlBuffer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a list of attributes...
 * <p/>
 * <pre>
 *  &lt;!-- This is from the Search portion - the profile says these are used to filter attributes --&gt;
 *  &lt;xsd:complexType name="AttributeDescriptions"&gt;
 *      &lt;xsd:sequence minOccurs="0" maxOccurs="unbounded"&gt;
 *          &lt;xsd:element name="attribute" type="AttributeDescription"/&gt;
 *      &lt;/xsd:sequence&gt;
 *  &lt;/xsd:complexType&gt;
 * </pre>
 */
public class AttributeDescriptions implements DSMLUnmarshaller.Parseable, OpenContentElement {

    private static final String code_id = "$Id: AttributeDescriptions.java,v 1.3 2006/06/21 22:41:37 kas Exp $";

    private List mAttributeDescriptions = new ArrayList();

    public AttributeDescriptions() { ; }

    public AttributeDescriptions(AttributeDescription[] descs) throws DSMLProfileException {
        if (descs != null)
            mAttributeDescriptions.addAll(Arrays.asList(descs));
    }

    public AttributeDescription[] getAttributeDescriptions() {
        return (AttributeDescription[]) mAttributeDescriptions.toArray(
                new AttributeDescription[mAttributeDescriptions.size()]);
    }

    public void addAttributeDescription(AttributeDescription desc) {
        if (desc != null)
            mAttributeDescriptions.add(desc);
    }

    public void addAttributeDescriptions(AttributeDescription[] descs) {
        for (int k = 0; k < descs.length; k++) {
            AttributeDescription desc = descs[k];
            addAttributeDescription(desc);
        }
    }

    public void setAttributeDescriptions(AttributeDescription[] descs) {
        clearAttributeDescriptions();
        addAttributeDescriptions(descs);
    }

    public void clearAttributeDescriptions() {
        mAttributeDescriptions.clear();
    }

    public String toXML(int indent) throws DSMLProfileException {
        try {
            XmlBuffer buffer = new XmlBuffer();
            buffer.setNamespace(new URI("urn:oasis:names:tc:DSML:2:0:core"));
            buffer.setPrefix("dsml");
            buffer.setIndent(indent);

            buffer.addStartTag("attributes");

            buffer.incIndent();
            for (int k = 0; k < mAttributeDescriptions.size(); k++) {
                AttributeDescription desc = (AttributeDescription) mAttributeDescriptions.get(k);
                desc.toXML(buffer);
            }
            buffer.decIndent();
            buffer.addEndTag("attributes");
            return buffer.toString();
        }
        catch (URISyntaxException e) {
            throw new DSMLProfileException(e);
        }
    }

    public String toXML() throws DSMLProfileException {
        return toXML(0);
    }

    public void parseXml(DSMLUnmarshaller um, Object xmlObj)
            throws DSMLProfileException {
        um.visitAttributeDescriptions(this, xmlObj);
    }

}
