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
package org.openspml.v2.util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Many thanks to JavaWorld.
 * http://www.javaworld.com/javaworld/javatips/jw-javatip133.html
 * <p/>
 * All subclasses of this class:
 * <li>Should be immutable, e.g. only final private fields in the instances.</li>
 * <li>Don't need to override hashCode - object refs suffice.</li>
 * <li>Should be careful with static fields that are not of this type.</li>
 * <li>Should be final because fieldnames (public static final) could collide.</li>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 1, 2006
 */
public class EnumConstant implements Serializable {

    private static final String code_id = "$Id: EnumConstant.java,v 1.1 2006/03/15 20:40:00 kas Exp $";

    // The representation of the constant on our serialization stream.
    private transient String _fieldName;

    /**
     * Write the instance's field name to the stream.
     */
    private void writeObject(ObjectOutputStream out)
            throws IOException {

        Class cls = getClass();
        Field[] f = cls.getDeclaredFields();

        try {
            for (int i = 0; i < f.length; i++) {
                int mod = f[i].getModifiers();
                if (Modifier.isStatic(mod) &&
                    Modifier.isFinal(mod) &&
                    Modifier.isPublic(mod)) {

                    // is this object the object in the field?
                    if (this == f[i].get(null)) {
                        String fName = f[i].getName();
                        out.writeObject(fName);
                    }
                }
            }
        }
        catch (IllegalAccessException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    /**
     * read the serialized field name and assign to
     * _fieldName
     */
    private void readObject(ObjectInputStream in)
            throws IOException {
        try {

            _fieldName = (String) in.readObject();
        }
        catch (ClassNotFoundException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    /**
     * Replace the deserialized instance with the
     * local static instance to allow correct
     * usage of == operator
     */
    public Object readResolve()
            throws ObjectStreamException {

        try {

            Class clazz = getClass();
            Field f = clazz.getField(_fieldName);
            return f.get(null);
        }
        catch (Exception ex) {
            throw new InvalidObjectException("Failed to resolve enum constant.");
        }

    }

    /**
     * Return the constants, in the order declared.
     * <p/>
     * Subclasses, e.g. FooEnum with a constant named FOO, should implement:
     * <pre>
     * public static FooEnum[] getConstants() {
     *   List temp = FOO.getEnumConstants();
     *   return (FooEnum[]) temp.toArray(new FooEnum[temp.size()]);
     * }
     * </pre>
     *
     * @return an array of constants, as defined in the class.
     */
    protected static List getEnumConstants(Class cls) {

        Field[] f = cls.getDeclaredFields();

        List temp = new ArrayList(f.length);

        for (int i = 0; i < f.length; i++) {
            int mod = f[i].getModifiers();
            if (Modifier.isStatic(mod) &&
                Modifier.isFinal(mod) &&
                Modifier.isPublic(mod)) {
                try {
                    temp.add(f[i].get(null));
                }
                catch (IllegalAccessException e) {
                    // this shouldn't happen, we checked the access.
                }
            }
        }
        EnumConstant[] e = (EnumConstant[]) temp.toArray(new EnumConstant[temp.size()]);
        return temp;
    }
}

