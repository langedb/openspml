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

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Mar 8, 2006
 */
public class DSMLTestSuite extends TestSuite {

    private static final String code_id = "$Id: DSMLTestSuite.java,v 1.3 2006/08/30 18:02:59 kas Exp $";

    private static final Class[] SUITE_CLASSES = {
        TestDsmlMarshalling.class,
        MiscDsmlTest.class,
        AddRequestWithDsmlUnmarshallingTest.class,
        ModifyRequestWithDsmlUnmarshallingTest.class,
    };

    /**
     * You might be asking why we are doing this - calling
     * a function when we have an array.  It might be the case
     * that we reflect on a package for classes, rather than
     * use a static array.
     *
     * @return an array of TestSuite classes to add.
     */
    public static Class[] getTestClasses() {
        return SUITE_CLASSES;
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        Class[] classes = getTestClasses();

        for (int k = 0; k < classes.length; k++) {
            suite.addTestSuite(classes[k]);
        }
        return suite;
    }

    public static void main(String[] args) {
        TestRunner.run(suite());
    }
}

