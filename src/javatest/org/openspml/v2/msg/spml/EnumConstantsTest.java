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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.openspml.v2.msg.spmlbatch.Processing;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class EnumConstantsTest extends TestCase {

    private static final String code_id = "$Id: EnumConstantsTest.java,v 1.1 2006/03/15 20:40:00 kas Exp $";

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public EnumConstantsTest(String name) {
        super(name);
    }

    /**
     * Creates the TestSuite for the directories in the package.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(EnumConstantsTest.class);
        return suite;
    }

    public void testErrorCode() {
        System.out.println("");
        System.out.println("EnumConstantsTest.testErrorCode");
        ErrorCode[] array = ErrorCode.getConstants();
        for (int k = 0; k < array.length; k++) {
            ErrorCode errorCode = array[k];
            System.out.println("error code " + k + " is: " + errorCode);
        }
    }

    public void testProcessing() {
        System.out.println("");
        System.out.println("EnumConstantsTest.testProcessing");
        Processing[] array = Processing.getConstants();
        for (int k = 0; k < array.length; k++) {
            Processing errorCode = array[k];
            System.out.println("enum value is " + k + " is: " + errorCode);
        }
    }

}
