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
package org.openspml.v2.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.msg.XMLUnmarshaller;
import org.openspml.v2.profiles.DSMLProfileRegistrar;
import org.openspml.v2.util.FileUtil;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.xml.ObjectFactory;
import org.openspml.v2.util.xml.ReflectiveDOMXMLUnmarshaller;
import org.openspml.v2.util.xml.ReflectiveXMLMarshaller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * This class will run over a directory of sample
 * files and unmarshall them to Java objects.
 * <p/>
 * It will first look for a file called validity_table.csv
 * that is meant to be a .csv that contains:
 * <p/>
 * filename, isValid
 * <p/>
 * We do not recurse.
 */
public class UnmarshalSampleFilesTest extends TestCase {

    private static final String code_id = "$Id: UnmarshalSampleFilesTest.java,v 1.3 2006/08/31 22:49:47 kas Exp $";

    private static String[] _args = null;

    public UnmarshalSampleFilesTest() {
        super("SPMLv2 UnmarshalSampleFilesTest Test");
    }

    public UnmarshalSampleFilesTest(String s) {
        super(s);
    }

    public static void main(String[] args) {
        _args = args;
        TestRunner.run(suite());
    }

    /**
     * Creates the TestSuite for the directories in the package.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(UnmarshalSampleFilesTest.class);
        return suite;
    }

    private static class XMLFilesOnlyFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            name = name.toLowerCase();
            return name.endsWith(".xml");
        }
    };

    public void testFiles() {

        ObjectFactory.getInstance().register(new DSMLProfileRegistrar());
        
        String dirname = null;

        if (_args != null && _args.length > 0) {
            dirname = _args[0];
        }

        if (dirname == null) {
            dirname = System.getProperty("org.openspml.v2.UnmarshallSampleFilesTest.dir");
        }

        if (dirname == null) {
            System.out.println("WARNING: We need to know a directory to start in.\n" +
                 "Pass in an arg or set the system property:\n" +
                 "'org.openspml.v2.UnmarshallSampleFilesTest.dir' to the dir.");
            System.out.println("\nUsing '.' as the directory.");
            dirname = ".";
        }

        try {
            File dir = new File(dirname);
            assertTrue(dir != null);
            assertTrue(dir.exists());
            assertTrue(dir.isDirectory());

            shouldBeValid(dir, new File(""));
            System.out.println("--------------------\n");

            File[] files = dir.listFiles(new XMLFilesOnlyFilter());

            XMLUnmarshaller unmarshaller = new ReflectiveDOMXMLUnmarshaller();
            XMLMarshaller marshaller = new ReflectiveXMLMarshaller();
            marshaller.setIndent(4);

            for (int k = 0; k < files.length; k++) {
                File file = files[k];
                String xml = FileUtil.readFile(file);
                try {
                    System.out.println("\nUnmarshalling " + file.getAbsolutePath() + " from:");
                    System.out.println("====================");
                    System.out.println(xml);
                    System.out.println("====================");
                    Marshallable m = unmarshaller.unmarshall(xml);
                    System.out.println("Remarshalled as:");
                    System.out.println("    ----------------");
                    System.out.println(m.toXML(marshaller));
                    System.out.println("====================");
                }
                catch (Spml2Exception e) {
                    if (shouldBeValid(dir, new File(file.getName()))) {
                        throw e;
                    }
                    else {
                        System.out.println("File " + file.getName() + " is invalid, as expected.");
                        System.out.println("====================");
                    }
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
            fail(e.getMessage());
        }
    }


    private Set mInvalidFiles = null;

    /**
     * Is the file named in the given directory supposed to be valid?
     *
     *
     * @param directory
     * @param file
     */
    private boolean shouldBeValid(File directory, File file)
            throws FileNotFoundException, IOException {

        if (mInvalidFiles == null) {
            mInvalidFiles = new HashSet();
            File table = new File(directory, "invalid_files.txt");
            if (table.exists()) {
                FileUtil.FileLineProcessor fp = new FileUtil.FileLineProcessor() {
                    public void process(String line) {
                        if (line != null && !line.matches("^#.*")) {
                            mInvalidFiles.add(new File(line));
                        }
                    }
                };
                FileUtil.readFileAndProcessLines(table, fp);
                System.out.println("Invalid files are: " + mInvalidFiles);
            }
        }

        return !mInvalidFiles.contains(file);
    }
}
