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

import org.openspml.v2.msg.MarshallableElement;
import org.openspml.v2.msg.PrefixAndNamespaceTuple;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.util.Spml2Exception;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This is a class that knows how to interrogate the 
 * objects under the msg package to get things like
 * all the attribute Fields, and all the element Fields
 * for an object.
 * 
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Mar 2, 2006
 */
class ReflectionUtilities {

    private static final String code_id = "$Id: ReflectionUtilities.java,v 1.4 2006/08/30 18:02:59 kas Exp $";

    static void getAttributeAndElementFields(MarshallableElement e,
                                             List attributes,
                                             List elements)
    {
        Class cls = e.getClass();
        List allClasses = new ArrayList();
        while (cls != null && MarshallableElement.class.isAssignableFrom(cls)) {
            allClasses.add(cls);
            cls = cls.getSuperclass();
        };
        // order matters
        Collections.reverse(allClasses);

        for (int k = 0; k < allClasses.size(); k++) {
            Class aClass = (Class) allClasses.get(k);
            Field[] fields = aClass.getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];
                String fieldName = field.getName();
                if (!Modifier.isStatic(field.getModifiers()) &&
                    !Modifier.isTransient(field.getModifiers()) &&
                    fieldName.startsWith("m_")) {
                    Class fieldType = field.getType();
                    if (!Extensible.class.isAssignableFrom(fieldType) &&
                        !Collection.class.isAssignableFrom(fieldType) &&
                        !fieldType.isArray()) {
                        attributes.add(field);
                    }
                    else {
                        elements.add(field);
                    }
                }
            }
        }
    }

    // for now, these are the same.
    public static String getAttributeNameFromField(Field f) {
        return getElementNameFromField(f);
    }

    public static String getElementNameFromField(Field field) {
        return field.getName().substring(2);
    }

    public static String getPreferredPrefix(MarshallableElement me) throws Spml2Exception {

        // If there is only one, it's from the default.
        PrefixAndNamespaceTuple[] tuples = me.getNamespacesInfo();

        // if the length is one, and the first one is the default;
        // return null.
        if (tuples.length == 1 && tuples[0].isDefault)
            return null;

        // otherwise, return the first one that is not the default.
        for (int k = 0; k < tuples.length; k++) {
            PrefixAndNamespaceTuple tuple = tuples[k];
            if (tuple.isDefault) {
                continue;
            }
            return tuple.prefix;
        }

        // we really should get here.
        throw new Spml2Exception(
                "The prefix and namespace declarations for the the MarshallableElement class " + me.getClass() + " are incorrect.");
    }

}
