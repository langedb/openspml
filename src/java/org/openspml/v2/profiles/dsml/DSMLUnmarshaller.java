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
import org.openspml.v2.msg.spml.ModificationMode;
import org.openspml.v2.util.xml.ObjectFactory;
import org.openspml.v2.util.xml.XmlElement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Capture all the "public" knowledge of parsers, etc...
 * Integrate the classes in the system.
 * <p/>
 * NOTE: If we wanted to compartmentalize parser support, we could
 * use yet another dispatch (instead of instanceof) in the visit
 * methods to various XmlRep unmarshallers.
 */
public class DSMLUnmarshaller implements ObjectFactory.OCEUnmarshaller {

    private static final String code_id = "$Id: DSMLUnmarshaller.java,v 1.9 2006/08/11 23:05:48 kas Exp $";

    protected static final String DSML_CORE_URI = "urn:oasis:names:tc:DSML:2:0:core";

    /**
     * Make it clear that you are top-level by implementing this interface and OpenContentElement
     */
    static protected interface Parseable {
        public void parseXml(DSMLUnmarshaller um, Object e) throws DSMLProfileException;
    };

    private FilterItem createFilterItem(String localName) {

        FilterItem result = null;
        if (localName != null) {
            if (localName.equals("and")) {
                result = new And();
            }
            else if (localName.equals("or")) {
                result = new Or();
            }
            else if (localName.equals("not")) {
                result = new Not();
            }
            else if (localName.equals("equalityMatch")) {
                result = new EqualityMatch();
            }
            else if (localName.equals("substrings")) {
                result = new Substrings();
            }
            else if (localName.equals("greaterOrEqual")) {
                result = new GreaterOrEqual();
            }
            else if (localName.equals("lessOrEqual")) {
                result = new LessOrEqual();
            }
            else if (localName.equals("present")) {
                result = new Present();
            }
            else if (localName.equals("approxMatch")) {
                result = createApproxMatch();
            }
            else if (localName.equals("extensibleMatch")) {
                result = createExtensibleMatch();
            }
        }

        return result;
    }

    private void throwUnknownXmlRep(Parseable p, Object obj)
            throws DSMLProfileException {
        throw new DSMLProfileException("Could not parse type " + p.getClass().getName() +
                                       ", with representation " + obj.toString() + ", of class " +
                                       obj.getClass().getName() + ".");
    }

    private DSMLValue[] getValuesFromXml(XmlElement e) {

        // children can only be <value>.
        List values = new ArrayList();

        for (XmlElement child = e.getChildElement(); child != null;
             child = child.next()) {
            values.add(new DSMLValue(child.getContent()));
        }

        return (DSMLValue[]) values.toArray(new DSMLValue[values.size()]);
    }

    /**
     * Extend this class and override this to replace the
     * implementation of the ExtensibleMatch (which has an
     * unimplemented matches() method.)
     *
     * @return a new empty ExtensibleMatch
     */
    protected ExtensibleMatch createExtensibleMatch() {
        return new ExtensibleMatch();
    }

    /**
     * Extend this class and override this to replace the
     * implementation of the ApproxMatch (which has an
     * unimplemented matches() method.)
     *
     * @return a new empty ApproxMatch
     */
    protected ApproxMatch createApproxMatch() {
        return new ApproxMatch();
    }

    protected DSMLAttr visitDSMLAttr(DSMLAttr item, Object xmlObj) throws DSMLProfileException {

        xmlObj = conditionallyWrapElement(xmlObj);
        if (xmlObj instanceof XmlElement) {
            XmlElement e = (XmlElement) xmlObj;

            String name = e.getAttribute("name");

            if (name == null) {
                throw new DSMLProfileException("No 'name' attribute in the object (Attribute|Modification)!");
            }

            DSMLValue[] values = getValuesFromXml(e);

            item.setName(name);
            item.setValues(values);
        }
        else {
            throwUnknownXmlRep(item, xmlObj);
        }
        return item;
    }

    protected DSMLModification visitDSMLModification(DSMLModification item, Object xmlObj)
            throws DSMLProfileException {

        xmlObj = conditionallyWrapElement(xmlObj);
        if (xmlObj instanceof XmlElement) {
            XmlElement e = (XmlElement) xmlObj;

            String name = e.getAttribute("name");
            String operation = e.getAttribute("operation");

            DSMLValue[] values = getValuesFromXml(e);

            item.setName(name);
            item.setValues(values);
            item.setOperation(ModificationMode.getConstant(operation));
        }
        else {
            throwUnknownXmlRep(item, xmlObj);
        }
        return item;
    }

    protected FilterItem getFilterItem(Object xmlObj)
            throws DSMLProfileException {

        xmlObj = conditionallyWrapElement(xmlObj);
        if (xmlObj instanceof XmlElement) {
            XmlElement e = (XmlElement) xmlObj;
            for (XmlElement child = e.getChildElement();
                 child != null; child = child.next()) {

                FilterItem item = createFilterItem(child.getLocalName());
                if (item != null) {
                    item.parseXml(this, child);
                    return item;
                }
            }
        }

        throw new DSMLProfileException("Parsing error: this must have one FilterItem element.");
    }

    private Object conditionallyWrapElement(Object xmlObj) {
        // we want to accept Element or XmlElement
        if (xmlObj instanceof Element) {
            xmlObj = new XmlElement((Element) xmlObj);
        }
        return xmlObj;
    }

    protected AttributeDescriptions visitAttributeDescriptions(AttributeDescriptions item,
                                                               Object xmlObj)
            throws DSMLProfileException {

        xmlObj = conditionallyWrapElement(xmlObj);
        if (xmlObj instanceof XmlElement) {
            XmlElement e = (XmlElement) xmlObj;

            item.clearAttributeDescriptions();

            for (XmlElement child = e.getChildElement(); child != null; child = child.next()) {
                AttributeDescription desc = new AttributeDescription();
                desc.parseXml(this, child);
                item.addAttributeDescription(desc);
            }
        }
        else {
            throwUnknownXmlRep(item, xmlObj);
        }

        return item;
    }

    protected AttributeDescription visitAttributeDescription(AttributeDescription item,
                                                             Object xmlObj)
            throws DSMLProfileException {

        xmlObj = conditionallyWrapElement(xmlObj);
        if (xmlObj instanceof XmlElement) {
            XmlElement e = (XmlElement) xmlObj;

            String name = e.getAttribute("name");

            if (name == null) {
                throw new DSMLProfileException("XML error: No 'name' attribute in the AttributeDescription!");
            }

            item.setName(name);
        }
        else {
            throwUnknownXmlRep(item, xmlObj);
        }

        return item;
    }

    /**
     * The ObjectFactory calls this to determine if the obj is something
     * we can handle and create.  These are the top-level classes only.
     *
     * @param obj
     * @return an object as OpenContentElement, if we recognize the obj
     * @throws DSMLProfileException
     */
    public OpenContentElement unmarshall(Object obj)
            throws DSMLProfileException {

        if (obj instanceof Node) {
            Node node = (Node) obj;
            if (node.getNodeType() == Node.ELEMENT_NODE &&
                DSML_CORE_URI.equals(node.getNamespaceURI())) {

                Parseable p = null;
                String localName = node.getLocalName();
                if ("attr".equals(localName)) {
                    p = new DSMLAttr();
                }
                else if ("modification".equals(localName)) {
                    p = new DSMLModification();
                }
                else if ("filter".equals(localName)) {
                    p = new Filter();
                }
                else if ("attributes".equals(localName)) {
                    p = new AttributeDescriptions();
                }

                if (p != null) {
                    p.parseXml(this, node);
                    return (OpenContentElement) p;
                }
            }
        }

        return null;
    }

    protected Parseable visitFilterSet(FilterSet item, Object xmlObj)
            throws DSMLProfileException {

        xmlObj = conditionallyWrapElement(xmlObj);
        if (xmlObj instanceof XmlElement) {
            XmlElement e = (XmlElement) xmlObj;

            // iterate over the children and add the items...
            for (XmlElement child = e.getChildElement();
                 child != null; child = child.next()) {

                FilterItem fi = createFilterItem(child.getLocalName());
                fi.parseXml(this, child);
                if (item != null) {
                    item.addItem(fi);
                }
            }
        }
        else {
            throwUnknownXmlRep(item, xmlObj);
        }

        return item;
    }

    protected BasicFilter visitBasicFilter(BasicFilter item, Object xmlObj)
            throws DSMLProfileException {

        FilterItem fi = getFilterItem(xmlObj);
        if (fi != null) {
            item.setItem(fi);
        }
        else {
            throwUnknownXmlRep(item, xmlObj);
        }
        return item;
    }

    protected Not visitNot(Not not, Object xmlObj)
            throws DSMLProfileException {

        FilterItem fi = getFilterItem(xmlObj);
        if (fi != null) {
            not.setItem(fi);
        }
        else {
            throwUnknownXmlRep(not, xmlObj);
        }
        return not;
    }

    private void populateNFI(NamedFilterItem nfi, XmlElement e)
            throws DSMLProfileException {

        String name = e.getAttribute("name");

        if (name == null) {
            throw new DSMLProfileException("XML error: No 'name' attribute in the FilterItem!");
        }

        nfi.setName(name);
    }

    protected NamedFilterItem visitNamedFilterItem(NamedFilterItem nfi, Object xmlObj)
            throws DSMLProfileException {
        xmlObj = conditionallyWrapElement(xmlObj);
        if (xmlObj instanceof XmlElement) {
            XmlElement e = (XmlElement) xmlObj;
            populateNFI(nfi, e);
        }
        else {
            throwUnknownXmlRep(nfi, xmlObj);
        }
        return nfi;
    }

    protected AttributeValueAssertion visitAttributeValueAssertion(AttributeValueAssertion ava,
                                                                   Object xmlObj)
            throws DSMLProfileException {

        xmlObj = conditionallyWrapElement(xmlObj);
        if (xmlObj instanceof XmlElement) {
            XmlElement e = (XmlElement) xmlObj;

            populateNFI(ava, e);

            for (XmlElement child = e.getChildElement(); child != null; child = child.next()) {
                if (DSMLUnmarshaller.DSML_CORE_URI.equals(child.getNamespaceURI())) {
                    String ln = child.getLocalName();
                    if ("value".equals(ln)) {
                        if (ava.getValue() == null) {
                            ava.setValue(new DSMLValue(child.getContent()));
                        }
                        else {
                            throw new DSMLProfileException("Parsing error: only one Value element is allowed.");
                        }
                    }
                }
            }
        }
        else {
            throwUnknownXmlRep(ava, xmlObj);
        }
        return ava;
    }

    protected Parseable visitSubstrings(Substrings substrings, Object xmlObj)
            throws DSMLProfileException {

        xmlObj = conditionallyWrapElement(xmlObj);
        if (xmlObj instanceof XmlElement) {
            XmlElement e = (XmlElement) xmlObj;

            populateNFI(substrings, e);

            for (XmlElement child = e.getChildElement();
                 child != null; child = child.next()) {
                if (DSMLUnmarshaller.DSML_CORE_URI.equals(child.getNamespaceURI())) {
                    String ln = child.getLocalName();
                    if ("initial".equals(ln)) {
                        if (substrings.getInitial() == null) {
                            substrings.setInitial(new DSMLValue(child.getContent()));
                        }
                        else {
                            throw new DSMLProfileException("Parsing error: only one initial element is allowed.");
                        }
                    }
                    else if ("any".equals(ln)) {
                        DSMLValue v = new DSMLValue(child.getContent());
                        substrings.addAny(v);
                    }
                    else if ("final".equals(ln)) {
                        if (substrings.getFinal() == null) {
                            substrings.setFinal(new DSMLValue(child.getContent()));
                        }
                        else {
                            throw new DSMLProfileException("Parsing error: only one final element is allowed.");
                        }
                    }
                }
            }
        }
        else {
            throwUnknownXmlRep(substrings, xmlObj);
        }
        return substrings;
    }

    public void visitExtensibleMatch(ExtensibleMatch em, Object xmlObj)
            throws DSMLProfileException {

        xmlObj = conditionallyWrapElement(xmlObj);
        if (xmlObj instanceof XmlElement) {
            XmlElement e = (XmlElement) xmlObj;

            populateNFI(em, e);

            for (XmlElement child = e.getChildElement();
                 child != null; child = child.next()) {
                if (DSMLUnmarshaller.DSML_CORE_URI.equals(child.getNamespaceURI())) {
                    String ln = child.getLocalName();
                    if ("dnAttributes".equals(ln)) {
                        Boolean dnAttrs = new Boolean(child.getContent());
                        em.setDnAttributes(dnAttrs.booleanValue());
                    }
                    else if ("matchingRule".equals(ln)) {
                        em.setMatchingRule(child.getContent());
                    }
                    else if ("value".equals(ln)) {
                        if (em.getValue() == null) {
                            em.setValue(new DSMLValue(child.getContent()));
                        }
                        else {
                            throw new DSMLProfileException("Parsing error: only one Value element is allowed.");
                        }
                    }
                }
            }
        }
        else {
            throwUnknownXmlRep(em, xmlObj);
        }
    }
}


