package org.openspml.v2.util;

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

/**
 * This is just a place to put methods that are convenient, and Reflection related.
 *
 * @author kent.spaulding@sun.com
 */
public class ReflectionUtil {

    /**
     * Create an instance of a class given a class name.  Map all exceptions
     * to Spml2Exception.
     */
    public static Object instantiate(String classname) throws Spml2Exception {

        Object obj;
        try {
            Class c = Class.forName(classname);
            obj = c.newInstance();
        }
        catch (Throwable t) {
            //t.printStackTrace(System.err);
            throw new Spml2Exception(t);
        }
        return obj;
    }

}
