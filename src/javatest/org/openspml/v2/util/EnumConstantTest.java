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

import org.openspml.v2.msg.spml.ExecutionMode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 1, 2006
 */
public class EnumConstantTest {

    private static final String code_id = "$Id: EnumConstantTest.java,v 1.1 2006/03/15 20:40:00 kas Exp $";

    private static final String FNAME = "const.ser";
    private static final ExecutionMode CONST = ExecutionMode.SYNCHRONOUS;

    public static void main(String args[]) throws Exception {

        if (args.length != 0) {
            if (args[0].equals("-?") ||
                args[0].equals("--help")) {
                System.out.println("Usage: use -write then run again with -read to test.");
                System.out.println("\n-write will create the " + FNAME + " file.");
                System.out.println("-read to tries to read it back.");
                System.out.println("No args will just display the constants in ExecutionModeType.");
                System.exit(1);
            }

            if (args[0].equals("-write")) {
                save(CONST);
            }
            else if (args[0].equals("-read")) {
                ExecutionMode emt = (ExecutionMode) load();
                if (CONST == emt) {
                    System.out.println("Matched using ==");
                }
                else {
                    System.out.println("Failed to match using ==");
                }
            }
            else {
                System.err.println("Invalid parameter");
            }
        }

        ExecutionMode[] consts = ExecutionMode.getConstants();
        for (int k = 0; k < consts.length; k++) {
            ExecutionMode aConst = consts[k];
            System.out.println("Found a const with a value of " + aConst);
        }
    }

    protected static Object load()
            throws IOException, ClassNotFoundException {

        Object o = null;

        FileInputStream fis = new FileInputStream(FNAME);
        ObjectInputStream ois = new ObjectInputStream(fis);
        o = ois.readObject();
        ois.close();
        return o;

    }

    static void save(Object o)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(FNAME);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(o);
        oos.flush();
        oos.close();
    }

}


