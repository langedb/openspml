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
package org.openspml.v2.msg.spml;

import org.openspml.v2.msg.BasicOpenContentAttr;
import org.openspml.v2.msg.OCEtoMarshallableAdapter;
import org.openspml.v2.msg.OpenContentAttr;
import org.openspml.v2.msg.OpenContentContainer;
import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.msg.PrefixAndNamespaceTuple;
import org.openspml.v2.util.xml.BasicMarshallableElement;
import org.openspml.v2.util.xml.OperationalNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

//<complexType name="ExtensibleType">
//    <sequence>
//        <any namespace="##other" minOccurs="0" maxOccurs="unbounded" processContents="lax"/>
//    </sequence>
//    <anyAttribute namespace="##other" processContents="lax"/>
// </complexType>

/**
 * This is the basis of all the various types in the toolkit.
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 1, 2006
 */
public class Extensible extends BasicMarshallableElement implements OpenContentContainer {

    private static final String code_id = "$Id: Extensible.java,v 1.9 2006/10/17 22:48:36 kas Exp $";

    /**
     * This is public because we need to add these for open content quite often.
     */
    public Extensible() {
        ;
    }

    private Set mOpenContentAttrs = new LinkedHashSet();
    private List mOpenContentElements = new ArrayList();

    /**
     * This is a convenience method that looks for our utilitarian
     * operational attribute object.
     *
     * @param name
     * @return null if not known, OperationalNVP otherwise.
     * @deprecated changing the name of this method.
     */
    public OperationalNameValuePair findOperationalAttrByName(String name) {
        return findOperationalNVPByName(name);
    }

    /**
     * This is a convenience method that looks for our utilitarian
     * operational NVP object.
     * <p/>
     * Makes it clear that these are Elements we are looking for...
     *
     * @param name
     * @return The "element" that is the operationalNVP with the given name or null.
     */
    public OperationalNameValuePair findOperationalNVPByName(String name) {
        for (Iterator i = mOpenContentElements.iterator(); i.hasNext();) {
            OpenContentElement ocEl = (OpenContentElement) i.next();
            if (ocEl instanceof OperationalNameValuePair) {
                if (((OperationalNameValuePair) ocEl).getName().equals(name)) {
                    return (OperationalNameValuePair) ocEl;
                }
            }
        }
        return null;
    }

    /**
     * This will look any OperationalNVP elements and remove those that
     * have a matching name.  It will return a count of how many were removed.
     *
     * @param name The name of the elements to remove.
     * @return The count of elements that matched and were removed.
     */
    public int removeAllOperationalNVPsWithName(String name) {

        int removed = 0;
        List newList = new ArrayList();

        for (Iterator i = mOpenContentElements.iterator(); i.hasNext();) {
            OpenContentElement ocEl = (OpenContentElement) i.next();
            if (ocEl instanceof OperationalNameValuePair) {
                if (((OperationalNameValuePair) ocEl).getName().equals(name)) {
                    removed++;
                }
                else {
                    newList.add(ocEl);
                }
            }
        }

        if (removed != 0) {
            synchronized (this) {
                mOpenContentElements = newList;
            }
        }

        return removed;
    }

    /**
     * Add an open content attr to this object.
     *
     * @param name
     * @param value
     */
    public void addOpenContentAttr(String name, String value) {
        addOpenContentAttr(new BasicOpenContentAttr(name, value));
    }

    /**
     * Return an array of all the attributes (name,value) that are not
     * part of the type as defined in the xsd.
     *
     * @return an array of attrs, might be 0 length.  Will not be null.
     */
    public OpenContentAttr[] getOpenContentAttrs() {
        return (OpenContentAttr[]) mOpenContentAttrs.toArray(new OpenContentAttr[mOpenContentAttrs.size()]);
    }

    /**
     * Convenience method to get the value of the named OpenContentAttr.
     *
     * @param name
     * @return The value of the named attr, or null if there isn't one.
     */
    public String findOpenContentAttrValueByName(String name) {
        for (Iterator i = mOpenContentAttrs.iterator(); i.hasNext();) {
            OpenContentAttr ocA = (OpenContentAttr) i.next();
            if (ocA.getName().equals(name)) {
                return ocA.getValue();
            }
        }
        return null;
    }

    /**
     * Add an open content element to this object.
     *
     * @param obj
     */
    public void addOpenContentElement(OpenContentElement obj) {
        assert(obj != null);
        mOpenContentElements.add(obj);
    }

    /**
     * Returns an array of all the open content elements.
     *
     * @return Never null, but can be zero-length.
     */
    public OpenContentElement[] getOpenContentElements() {
        return (OpenContentElement[]) mOpenContentElements.toArray(new OpenContentElement[mOpenContentElements.size()]);
    }

    /**
     * Return the open content elements that are assignable form the
     * given class.
     *
     * @param cls If the element is assignable for this, return it.
     * @return a List of all OpenContentElements assignable from the given class (of its type)
     */
    public List getOpenContentElements(Class cls) {
        List result = new ArrayList();
        for (int k = 0; k < mOpenContentElements.size(); k++) {
            OpenContentElement oce = (OpenContentElement) mOpenContentElements.get(k);
            if (oce.getClass().isAssignableFrom(cls)) {
                result.add(oce);
            }
            else if (oce instanceof OCEtoMarshallableAdapter) {
                OCEtoMarshallableAdapter adapter = (OCEtoMarshallableAdapter) oce;
                if (adapter.getAdaptedObject().getClass().isAssignableFrom(cls)) {
                    result.add(adapter);
                }
            }
        }
        return result;
    }

    // MarshallableElement interface

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        return new PrefixAndNamespaceTuple[]{
                new PrefixAndNamespaceTuple("spml", "urn:oasis:names:tc:SPML:2:0", true),
        };
    }

    public boolean isValid() {
        return true;
    }

    // and a bunch of convenience methods

    public void addOpenContentAttr(OpenContentAttr attribute) {
        assert(attribute != null);
        mOpenContentAttrs.add(attribute);
    }

    public boolean removeOpenContentAttr(OpenContentAttr attribute) {
        assert(attribute != null);
        return mOpenContentAttrs.remove(attribute);
    }

    /**
     * This will clear the existing open content elements and set it to the values
     * in the given array of elements.
     *
     * @param elements
     */
    public void setOpenContentElements(OpenContentElement[] elements) {
        mOpenContentElements.clear();
        if (elements != null && elements.length != 0)
            mOpenContentElements.addAll(Arrays.asList(elements));
    }

    /**
     * This will clear the existing open content attributes and set it to the values
     * in the given array of attributes.
     *
     * @param attrs
     */
    public void setOpenContentAttrs(OpenContentAttr[] attrs) {
        mOpenContentAttrs.clear();
        if (attrs != null && attrs.length != 0)
            mOpenContentAttrs.addAll(Arrays.asList(attrs));
    }

    /**
     * This will clear the existing open content and set it to the values
     * in the given arrays of attributes and elements.
     *
     * @param attrs
     * @param elements
     */
    public void setOpenContent(OpenContentAttr[] attrs, OpenContentElement[] elements) {
        setOpenContentAttrs(attrs);
        setOpenContentElements(elements);
    }


    public boolean removeOpenContentElement(OpenContentElement obj) {
        assert(obj != null);
        return mOpenContentElements.remove(obj);
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Extensible)) return false;

        final Extensible extensible = (Extensible) o;

        if (mOpenContentAttrs != null ? !mOpenContentAttrs.equals(extensible.mOpenContentAttrs) : extensible.mOpenContentAttrs != null)
            return false;
        if (mOpenContentElements != null ? !mOpenContentElements.equals(extensible.mOpenContentElements) : extensible.mOpenContentElements != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (mOpenContentAttrs != null ? mOpenContentAttrs.hashCode() : 0);
        result = 29 * result + (mOpenContentElements != null ? mOpenContentElements.hashCode() : 0);
        return result;
    }
}
