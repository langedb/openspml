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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {

    private static final String code_id = "$Id: FileUtil.java,v 1.3 2006/08/31 22:49:47 kas Exp $";

    public static byte[] readFileBytes(File file)
            throws IOException {

        byte[] bytes = null;
        FileInputStream fis = new FileInputStream(file);
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
            byte[] buf = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = fis.read(buf)) != -1) {
                bout.write(buf, 0, bytesRead);
            }
            bytes = bout.toByteArray();
        }
        finally {
            try {
                fis.close();
            }
            catch (IOException ex) {
                // ignore these
            }
        }
        return bytes;
    }

    public static String readFile(File file) throws IOException {
        byte[] bytes = readFileBytes(file);
        return new String(bytes);
    }

    public static String readFile(String filename) throws IOException {
        return readFile(new File(filename));
    }

    /**
     * Store the contents of a String in a file.
     */
    public static void writeFile(String filename, String contents)
            throws IOException {

        File path = new File(filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(contents.getBytes());
        }
        finally {
            try {
                if (fos != null) fos.close();
            }
            catch (java.io.IOException e) {
                // ignore
            }
        }
    }

    public interface FileLineProcessor {
        /** process the line that was read from a file. */
        public void process(String line);
    };

    public static void readFileAndProcessLines(File name, FileLineProcessor processor)
            throws FileNotFoundException, IOException {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(name));

            String line = reader.readLine();
            while (line != null) {
                processor.process(line);
                line = reader.readLine();
            }
        }
        finally {
            if (reader != null) reader.close();
        }
    }
}
