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
package org.openspml.v2.util.xml;

import org.openspml.v2.util.Spml2Exception;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;


public class XmlUtil {

    /**
     * This is not part of DOM - so we provide a helper method; this
     * one assumes xerces.
     */
    public static String serializeNode(Node node) throws Spml2Exception {

        String serialization = null;
        StringWriter writer = null;

        try {
            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            Source input = new DOMSource(node);
            writer = new StringWriter();
            Result output = new StreamResult(writer);

            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            if (node.getNodeType() == Node.DOCUMENT_NODE) {
                DocumentType doctype = ((Document) node).getDoctype();
                String publicId = null;
                String systemId = null;

                if (doctype != null) {
                    publicId = doctype.getPublicId();
                    systemId = doctype.getSystemId();
                }

                if (systemId != null) {
                    transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
                                                  systemId);
                    if (publicId != null) {
                        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,
                                                      publicId);
                    }
                }
            }

            transformer.transform(input, output);
            serialization = writer.toString();
            return serialization;
        }
        catch (TransformerConfigurationException ex) {
            throw new Spml2Exception(ex);
        }
        catch (TransformerException ex) {
            throw new Spml2Exception(ex);
        }
        finally {
            try {
                writer.close();
            }
            catch (java.io.IOException ioe) {
            }
        }
    }

    public static String makeXmlFragmentFromDoc(String xml) {
        String toRemove = "\\<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?\\>\n";
        StringBuffer buffer = new StringBuffer();
        String[] pieces = xml.split(toRemove);
        for (int k = 0; k < pieces.length; k++) {
            buffer.append(pieces[k]);
        }
        return buffer.toString();
    }

    public static Element getFirstChildElement(Node node) {

        // we want the first child element of the given node
        Element firstChildElement = null;
        if (node != null) {
            Node child = node.getFirstChild();
            while (child != null) {
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    firstChildElement = (Element) child;
                    break;
                }
                child = child.getNextSibling();
            }
        }
        return firstChildElement;
    }

    public static String getSoapBodyContents(String message) throws Spml2Exception {

        Document doc = XmlParser.parse(message);

        // Get the first <soap:Body> element in the DOM
        NodeList list = doc.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/",
                                                   "Body");
        Node node = list.item(0);
        node = XmlUtil.getFirstChildElement(node);
        return XmlUtil.serializeNode(node);
    }
}
