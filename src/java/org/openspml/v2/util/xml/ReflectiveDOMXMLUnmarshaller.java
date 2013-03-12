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

import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.MarshallableElement;
import org.openspml.v2.msg.OCEtoMarshallableAdapter;
import org.openspml.v2.msg.OpenContentContainer;
import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.msg.XMLUnmarshaller;
import org.openspml.v2.util.BasicStringEnumConstant;
import org.openspml.v2.util.EnumConstant;
import org.openspml.v2.util.Spml2Exception;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class, given XML, will turn it into Java classes
 * representing the SPML standard.
 * <p/>
 * This implementation uses DOM to do the parsing.
 * <p/>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Mar 2, 2006
 */
public class ReflectiveDOMXMLUnmarshaller implements XMLUnmarshaller {

    private static final String code_id = "$Id: ReflectiveDOMXMLUnmarshaller.java,v 1.19 2006/08/30 18:02:59 kas Exp $";

    protected Marshallable createAndPopulateMarshallable(String typeName, Element el)
            throws Spml2Exception {

        String nsURI = el.getNamespaceURI();

        // get us an object of the right type
        Marshallable m = ObjectFactory.getInstance().createMarshallable(typeName, nsURI);

        // now all we have to do is the rest - populate the
        // fields of the object.
        populate(m, el);

        return m;
    }

    private class OCEUnmarshallerForMarshallables implements ObjectFactory.OCEUnmarshaller {

        public OpenContentElement unmarshall(Object obj) throws Spml2Exception {
            if (obj instanceof Element) {
                Element el = (Element) obj;
                try {
                    Marshallable m = createAndPopulateMarshallable(el.getTagName(), el);
                    if (m != null) {
                        if (m instanceof OpenContentElement) {
                            return (OpenContentElement) m;
                        }
                        else {
                            return new OCEtoMarshallableAdapter(m);
                        }
                    }
                }
                catch (UnknownSpml2TypeException e) {
                    // we want to give other Unmarshallers a shot.
                }
            }
            return null;
        }
    }

    private static OCEUnmarshallerForMarshallables m4Marshallables;

    private void populate(MarshallableElement ext, Element element) throws Spml2Exception {

        // iterate over the element and populate corresponding fields
        List attrFields = new ArrayList();
        List elementFields = new ArrayList();
        ReflectionUtilities.getAttributeAndElementFields(ext, attrFields, elementFields);

        populateAttributes(ext, attrFields, element);
        populateElements(ext, elementFields, element);
    }

    private void populateAttributes(MarshallableElement ext, List attrFields, Element element)
            throws Spml2Exception {

        try {

            // set all the non openContent attributes in the ext
            Set knownAttrs = new HashSet();
            for (int k = 0; k < attrFields.size(); k++) {
                Field field = (Field) attrFields.get(k);
                String attrName = ReflectionUtilities.getAttributeNameFromField(field);
                knownAttrs.add(attrName);
                Object value = element.getAttribute(attrName);
                if (valueIsEmpty(value)) {
                    continue;
                }
                field.setAccessible(true);
                Class fType = field.getType();
                if (BasicStringEnumConstant.class.isAssignableFrom(fType)) {
                    EnumConstant ec = BasicStringEnumConstant.getConstant(fType, value.toString());
                    if (ec != null) {
                        field.set(ext, ec);
                    }
                }
                else if (fType == String.class) {
                    field.set(ext, value);
                }
                else if (fType == URI.class) {
                    try {
                        field.set(ext, new URI(value.toString()));
                    }
                    catch (URISyntaxException e) {
                        throw new Spml2Exception("Bad URI format for field " + field, e);
                    }
                }
                else if (fType == Boolean.class || fType == boolean.class) {
                    field.set(ext, new Boolean(value.toString()));
                }
                else if (fType == Integer.class || fType == int.class) {
                    field.set(ext, new Integer(value.toString()));
                }
            }

            // now we need the other attributes (openContent)
            NamedNodeMap nnm = element.getAttributes();
            int numAttrs = nnm.getLength();
            for (int k = 0; k < numAttrs; k++) {
                Node attr = nnm.item(k);
                String name = attr.getNodeName();
                if (name.startsWith("xmlns")) {        // TODO: maintain a prefix -> URI map?!?!
                    continue;
                }
                if (knownAttrs.contains(name)) {
                    continue;
                }
                if (ext instanceof OpenContentContainer) {
                    ((OpenContentContainer) ext).addOpenContentAttr(name, attr.getNodeValue());
                }
            }
        }
        catch (IllegalAccessException e) {
            // we set this accessible so this shouldn't happen
            e.printStackTrace();
            throw new Spml2Exception(e);
        }
    }

    private boolean valueIsEmpty(Object value) {
        return value == null || value.toString().length() == 0;
    }

    private void populateElements(MarshallableElement ext, List elementFields, Element element)
            throws Spml2Exception {

        try {

            Map fieldsToProcess = new HashMap();
            List batchFields = new ArrayList();

            // run over the fields to find element names
            for (int k = 0; k < elementFields.size(); k++) {

                Field field = (Field) elementFields.get(k);
                field.setAccessible(true);

                String name = ReflectionUtilities.getElementNameFromField(field);
                Class fType = field.getType();
                if (ListWithType.class.isAssignableFrom(fType)) {
                    ListWithType list = (ListWithType) field.get(ext);
                    Class listType = list.getType();
                    if (Marshallable.class.isAssignableFrom(listType)) {
                        batchFields.add(field);
                    }
                }
                fieldsToProcess.put(name, field);
            }

            // need to discern fields from openContent.
            Set elementNames = fieldsToProcess.keySet();

            // just walk this level of children.
            Node node = element.getFirstChild();
            while (node != null) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    // we don't want the prefix on the node's "elName".
                    String elName = node.getLocalName();
                    // is this open content or not?
                    if (elementNames.contains(elName)) {
                        Field field = (Field) fieldsToProcess.get(elName);
                        Class fType = field.getType();
                        if (ListWithType.class.isAssignableFrom(fType)) {
                            // it could be a collection of MarshallableElement type objects.
                            Class typeToCreate = getTypeFromField(ext, field);
                            MarshallableElement child = ObjectFactory.getInstance().createMarshallableElement(
                                    typeToCreate);
                            populate(child, (Element) node);
                            ListWithType values = (ListWithType) field.get(ext);
                            values.add(child);
                        }
                        else if (MarshallableElement.class.isAssignableFrom(fType)) {
                            // it could a single object
                            MarshallableElement child = ObjectFactory.getInstance().createMarshallableElement(fType);
                            populate(child, (Element) node);
                            field.set(ext, child);
                        }
                        else if (fType.isArray()) {
                            // it could be an array; WHICH WE LIMIT TO String[1]
                            if (fType.getComponentType() == String.class) {
                                String[] val = (String[]) field.get(ext);
                                if (val == null || val.length != 1) {
                                    throw new Spml2Exception("Unexpected length of String[] field: " + field);
                                }
                                String nodeValue = node.getNodeValue();
                                if (nodeValue == null) {
                                    Node firstKid = node.getFirstChild();
                                    if (firstKid != null)
                                        nodeValue = firstKid.getNodeValue();
                                }
                                val[0] = nodeValue;  // probably should check for null.
                            }
                            else {
                                throw new Spml2Exception("Unexpected field type: " + field);
                            }
                        }
                        else if (Collection.class.isAssignableFrom(fType)) {
                            // it could be a Collection (not with Type, check that first)
                            Collection c = (Collection) field.get(ext);
                            if (c == null) {
                                throw new Spml2Exception(
                                        "Collection field with null value is unexpected, field: " + field);
                            }
                            String nodeValue = node.getNodeValue();
                            if (nodeValue == null) {
                                Node firstKid = node.getFirstChild();
                                if (firstKid != null)
                                    nodeValue = firstKid.getNodeValue();
                            }
                            if (nodeValue != null)
                                c.add(nodeValue);
                        }
                    }
                    else {
                        boolean batched = false;
                        // try the batch fields
                        for (int k = 0; k < batchFields.size(); k++) {
                            Field field = (Field) batchFields.get(k);
                            ListWithType list = (ListWithType) field.get(ext);
                            Marshallable m = null;
                            try {
                                m = createAndPopulateMarshallable(elName, (Element) node);
                                if (!(m instanceof OpenContentElement)) {
                                    list.add(m);
                                    batched = true;
                                }
                            }
                            catch (UnknownSpml2TypeException e) {
                                // we ignore these - we're processing
                                // batched marshallables
                            }
                        }

                        // now we want to handle open content that is not batched
                        if (!batched && ext instanceof OpenContentContainer) {
                            OpenContentElement ocElement =
                                    ObjectFactory.getInstance().unmarshallOpenContentElement(node);
                            if (ocElement != null) {
                                ((OpenContentContainer) ext).addOpenContentElement(ocElement);
                            }
                        }
                    }
                }

                node = node.getNextSibling();
            }
        }
        catch (IllegalAccessException e) {
            // we set this accessible so this shouldn't happen
            e.printStackTrace();
            throw new Spml2Exception(e);
        }
    }

    private Class getTypeFromField(MarshallableElement parent, Field field) throws IllegalAccessException {
        Class fType = field.getType();
        if (ListWithType.class.isAssignableFrom(fType)) {
            ListWithType val = (ListWithType) field.get(parent);
            assert val != null;
            return val.getType();
        }
        return fType;
    }

    // PUBLIC
    public ReflectiveDOMXMLUnmarshaller() {
        synchronized (this.getClass()) {
            if (m4Marshallables == null) {
                m4Marshallables = new OCEUnmarshallerForMarshallables();
                ObjectFactory of = ObjectFactory.getInstance();
                of.addOCEUnmarshaller(m4Marshallables);
            }
        }
    }

    /**
     * @param xml
     * @return an instance of Marshallable representing the xml
     * @throws UnknownSpml2TypeException
     */
    public Marshallable unmarshall(String xml) throws UnknownSpml2TypeException {
        try {
            Document doc = XmlParser.parse(xml);

            // now the hard work starts.
            Element el = doc.getDocumentElement();
            String typeName = el.getTagName();

            return createAndPopulateMarshallable(typeName, el);
        }
        catch (Spml2Exception e) {
            throw new UnknownSpml2TypeException("SPML2 Parsing error, cannot unmarshall.\n  Message=\n" + e.getMessage() + "\n  XML=\n" + xml, e);
        }
    }

}
