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
// The Original Copyright notice... note that
// Sun acquired Waveset in late 2003.

// Copyright (C) 2003 Waveset Technologies, Inc..
// 6034 West Courtyard Drive, Suite 210, Austin, Texas 78730
// All rights reserved.
// 
package org.openspml.v2.util.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * A wrapper around the standard DOM Element that provides
 * a more convenient set of methods.
 */
public class XmlElement {

    public static final String code_id = "$Id: XmlElement.java,v 1.1 2006/05/18 00:21:44 kas Exp $";
    //////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    //////////////////////////////////////////////////////////////////////

    /**
     * Nested DOM element.
     */
    private Element _el;

    public XmlElement(Element e) {
        assert e != null;
        _el = e;
    }

    public String getTagName() {
        return _el.getTagName();
    }

    /**
     * Return the element tag name without the namespace qualifier.
     */
    public String getLocalName() {
        return _el.getLocalName();
    }

    /**
     * Return the namespace prefix.
     */
    public String getPrefix() {
        return _el.getPrefix();
    }

    /**
     * Given an element with a namespace prefix and a namespace
     * declaration for that prefix, return the namespace URI.
     * Note that this is not as general as the DOM method, the only
     * scope examined is the local element.
     */
    public String getNamespaceURI() {

        return _el.getNamespaceURI();
    }

    /**
     * Remove an identifier prefix from an attribute value.
     */
    public static String stripPrefix(String value) {

        String tail = value;
        if (value != null) {
            int sharp = value.indexOf("#");
            if (sharp >= 0)
                tail = value.substring(sharp + 1);
        }
        return tail;
    }

    /**
     * Return the value of an attribute.
     * <p/>
     * The DOM getAttribute method returns an empty string if
     * the attribute doesn't exist.  Here, we detect this
     * and return null.
     */
    public String getAttribute(String name) {

        String value = null;
        value = _el.getAttribute(name);
        if (value != null && value.length() == 0)
            value = null;
        return value;
    }


    /**
     * Return a boolean attribute value.
     * <p/>
     * The value must be equal to the string "true" or "1" to
     * be considered true.
     */
    public boolean getBooleanAttribute(String name) {

        String value = _el.getAttribute(name);
        return (value.equals("true") || value.equals("1"));
    }

    /**
     * Return the first child element.
     */
    public XmlElement getChildElement() {

        XmlElement found = null;
        for (Node child = _el.getFirstChild();
             child != null && found == null;
             child = child.getNextSibling()) {

            if (child.getNodeType() == Node.ELEMENT_NODE) {
                found = new XmlElement((Element) child);
                break;
            }
        }
        return found;
    }

    /**
     * Return the first child element with the given local name.
     * Used during SPML parsing to skip over the optional
     * SOAP header and get to the Body.
     */
    public XmlElement getChildElement(String localName) {

        XmlElement found = null;

        for (XmlElement e = getChildElement(); e != null && found == null;
             e = e.getNextElement()) {
            if (localName.equals(e.getLocalName()))
                found = e;
        }

        return found;
    }

    /**
     * Get the next right sibling that is an element.
     */
    public XmlElement getNextElement() {

        XmlElement found = null;
        for (Node next = _el.getNextSibling();
             next != null && found == null;
             next = next.getNextSibling()) {

            if (next.getNodeType() == Node.ELEMENT_NODE) {
                found = new XmlElement((Element) next);
                break;
            }
        }

        return found;
    }

    /**
     * Assimilate the next right sibling within this element wrapper.
     * This makes the element behave more like an iterator which
     * is usually what you want and cuts down on garbage.
     */
    public XmlElement next() {

        XmlElement found = null;
        for (Node next = _el.getNextSibling();
             next != null && found == null;
             next = next.getNextSibling()) {

            if (next.getNodeType() == Node.ELEMENT_NODE) {
                _el = (Element) next;
                found = this;
                break;
            }
        }

        return found;
    }

    /**
     * Return the content of the given element.
     * <p/>
     * We will descend to an arbitrary depth looking for the first
     * non-empty text node.
     * <p/>
     * Note that the parser may break what was originally a single
     * string of pcdata into multiple adjacent text nodes.  Xerces
     * appears to do this when it encounters a '$' in the text, not
     * sure if there is specified behavior, or if its parser specific.
     * <p/>
     * Here, we will congeal adjacent text nodes.
     * <p/>
     * We will NOT ignore text nodes that have only whitespace.
     */
    public String getContent() {

        String content = null;
        // find the first inner text node,
        Text t = findText(_el, false);
        if (t != null) {
            // we have at least some text
            StringBuffer b = new StringBuffer();
            while (t != null) {
                b.append(t.getData());
                Node n = t.getNextSibling();
                t = null;
                if (n != null &&
                    ((n.getNodeType() == Node.TEXT_NODE) ||
                     (n.getNodeType() == Node.CDATA_SECTION_NODE)))
                    t = (Text) n;
            }
            content = b.toString();
        }

        return content;
    }

    /**
     * Locate the first text node at any level below the given node.
     * If the ignoreEmpty flag is true, we will ignore text nodes that
     * contain only whitespace characteres.
     * <p/>
     * Note that if you're trying to extract element content,
     * you probably don't want this since parser's can break up
     * pcdata into multiple adjacent text nodes.  See getContent()
     * for a more useful method.
     */
    private Text findText(Node node, boolean ignoreEmpty) {

        Text found = null;
        if (node != null) {
            if (node.getNodeType() == Node.TEXT_NODE ||
                node.getNodeType() == Node.CDATA_SECTION_NODE) {

                Text t = (Text) node;
                if (!ignoreEmpty)
                    found = t;
                else {
                    // only pay attention if there is something in here
                    // would using trim() be an easier way to do this?
                    String s = t.getData();
                    boolean empty = true;
                    for (int i = 0; i < s.length(); i++) {
                        if (!Character.isWhitespace(s.charAt(i))) {
                            empty = false;
                            break;
                        }
                    }

                    if (!empty)
                        found = t;
                }
            }

            if (found == null) {

                for (Node child = node.getFirstChild();
                     child != null && found == null;
                     child = child.getNextSibling()) {

                    found = findText(child, ignoreEmpty);
                }
            }
        }

        return found;
    }
}
