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
package org.openspml.v2.msg;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.openspml.v2.msg.spml.Extensible;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * This test case starts in the current working directory and
 * searches for all .class files underneath the dir.  It loads
 * each class and verifies that if the class extends Extensible.class
 * it contains a protected default constructor.
 *
 * This is required by the reflective marshallers in this toolkit.
 */
public class SearchForConstructorsTest extends TestCase {

    private static final String code_id = "$Id: SearchForConstructorsTest.java,v 1.5 2006/08/30 18:02:59 kas Exp $";

    private File mBaseDir = new File(".");

    public void testAllClasses() {
        try {

            // walk all the class files and see that they have
            // a ctor that takes no args, iff the class is Extensible.
            System.out.println("SearchForConstructorsTest.testAllClasses");

            File dir = mBaseDir;
            List allClasses = new ArrayList();
            getAllClassFiles(dir, allClasses);

            assertTrue(allClasses.size() != 0);

            List nein = new ArrayList();
            int tested = 0;
            for (int k = 0; k < allClasses.size(); k++) {
                File file = (File) allClasses.get(k);

                String classname = file.getAbsolutePath();
                classname = classname.substring(classname.indexOf("./") + 2);
                classname = classname.replaceAll("\\/", ".");
                classname = classname.replaceAll("\\.class", "");

                Class cls = Class.forName(classname);
                if (Extensible.class.isAssignableFrom(cls)) {
                    System.out.println("Checking classname = " + classname);
                    try {
                        tested++;
                        Constructor ctor = cls.getDeclaredConstructor(null);
                        if (!Modifier.isAbstract(cls.getModifiers())) {
                            if (Modifier.isProtected(ctor.getModifiers())) {
                                System.err.println("\tWarning: protected no-arg ctor on concrete class " + cls.getName());
                            }
                            if (cls.getName().indexOf("Basic") >= 0) {
                                System.err.println("\tWarning: class " + cls.getName() + " should probably be abstract.");
                            }
                        }
                    }
                    catch (NoSuchMethodException e) {
                        nein.add(cls.getName());
                    }
                }
            }

            if (!nein.isEmpty()) {
                System.out.println("\n\nERROR: " + nein.size() + " classes need protected default CTORS: ");
                for (int k = 0; k < nein.size(); k++) {
                    String s = (String) nein.get(k);
                    System.out.println("    " + s);
                }
                fail("\n\nFix the classes that don't have the right ctors...");
            }

            assertTrue(tested > 0);
        }
        catch (Throwable t) {
            System.out.println("Failure from exception: " + t.getMessage());
            t.printStackTrace();
            fail(t.getMessage());
        }

    }

    private void getAllClassFiles(File startDir, List list) {
        File[] files = startDir.listFiles();
        for (int k = 0; k < files.length; k++) {
            File file = files[k];
            if (file.isDirectory()) {
                getAllClassFiles(file, list);
            }
            else {
                if (file.getName().endsWith(".class")) {
                    list.add(file);
                }
            }
        }
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(SearchForConstructorsTest.class);
        return suite;
    }

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

}
