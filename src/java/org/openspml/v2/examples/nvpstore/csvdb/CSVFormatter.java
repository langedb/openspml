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
package org.openspml.v2.examples.nvpstore.csvdb;

import org.openspml.v2.examples.nvpstore.csvdb.def.NVPDataType;
import org.openspml.v2.examples.nvpstore.csvdb.def.NVPDef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This will format the a string using the information in the
 * NVPObjectDef about NVPs, their cardinality, and type.
 * <p/>
 * The values are encoded if they contain ',' characters...
 * ',' is escaped to a \, pair.  Backslash (because of the comma
 * escaping) is also escaped, so \ becomes \\.
 * <p/>
 * Multiple values are bracketed and their individual values
 * are escaped as above.
 * <p/>
 * We don't support binary, but if you have a Base64 encoder
 * implementation, it's easy to add.
 * </p>
 * Author(s): Kent Spaulding (kent.spaulding@sun.com)
 */
public class CSVFormatter {

    public static final String code_id = "$Id: CSVFormatter.java,v 1.2 2006/10/06 02:23:10 kas Exp $";

    private static final char DELIMITER = ',';
    private static final String DELIMITER_REGEX = "[" + DELIMITER + "]";
    private static final String DELIMITER_REPLACEMENT = "\\\\" + DELIMITER;
    private static final String DELIMITER_REPLACEMENT_REGEX = "\\\\" + DELIMITER;

    /**
     * An internal class to allow us to readily specify formatters
     * for different types supported in the CSV lines.
     */
    private static class CellFormatter {

        private NVPDataType _type = null;

        private CellFormatter(NVPDataType type) {
            _type = type;
        }

        public String toString() {
            return _type.toString();
        }

        public String format(Object arg) {
            if (arg == null)
                return null;
            String result = arg.toString();
            result = result.replaceAll("\\\\", "\\\\\\\\");
            result = result.replaceAll(DELIMITER_REGEX, DELIMITER_REPLACEMENT);
            return result;
        }

        public Object unformat(String arg) {
            if (arg == null) {
                return arg;
            }
            String result = arg.replaceAll("\\\\\\\\", "\\\\");
            result = result.replaceAll(DELIMITER_REPLACEMENT_REGEX, ",");
            return result.equals("") ? null : result;
        }
    }

    private static String encode(byte[] value) {
        return "byte:Binary not supported.";
    }

    private static String encode(String value) {
        return "str:Binary not supported.";
    }

    private static byte[] decode(String base64) {
        return "decode: ninary is not supported.".getBytes();
    }

    /**
     * Format binary values as Base64.
     */
    private static class BinaryFormatter extends CellFormatter {

        private BinaryFormatter() {
            super(NVPDataType.BINARY);
        }

        public String format(Object arg) {
            if (arg instanceof byte[]) {
                return encode((byte[]) arg);
            }
            else if (arg == null) {
                return null;
            }
            return encode(arg.toString());
        }

        public Object unformat(String arg) {
            if (arg == null) return new byte[0];
            return decode(arg);
        }
    }

    /**
     * For MVAs, write a delimited and bracketed list of values.
     */
    private static class MultiFormatter extends CellFormatter {

        private static final String DELIMITER = ",";

        private CellFormatter mDelegate = null;

        private MultiFormatter(NVPDataType type, CellFormatter delegate) {
            super(type);
            mDelegate = delegate;
        }

        public String format(Object args) {

            if (args == null) {
                return null;
            }

            // sometimes a field will be marked multi-* and won't
            // be passed as such.  We do what we can here...
            if (!(args instanceof List)) {
                List list = new ArrayList(1);
                list.add(args);
                args = list;
            }

            List list = (List) args;
            StringBuffer buffer = new StringBuffer();
            int listSize = list.size();
            for (int k = 0; k < listSize; k++) {
                Object obj = list.get(k);
                buffer.append(mDelegate.format(obj));
                if (k < listSize - 1) {
                    buffer.append(DELIMITER);
                }
            }
            return buffer.toString();
        }

        public Object unformat(String arg) {
            String[] values = splitToStrings(arg);
            List result = new ArrayList();
            for (int k = 0; k < values.length; k++) {
                String value = values[k];
                Object tmp = mDelegate.unformat(value);
                if (tmp != null)
                    result.add(tmp);
            }
            return result.isEmpty() ? null : result;
        }
    }

    private static class BracketedMultiFormatter extends MultiFormatter {

        private static final String MULTI_PREFIX = "[";
        private static final String MULTI_SUFFIX = "]";

        private BracketedMultiFormatter(NVPDataType type, CellFormatter delegate) {
            super(type, delegate);
        }

        public String format(Object args) {

            String guts = super.format(args);
            if (guts == null || guts.equals("")) {
                return null;
            }

            StringBuffer result = new StringBuffer(MULTI_PREFIX);
            result.append(guts);
            result.append(MULTI_SUFFIX);
            return result.toString();
        }

        public Object unformat(String arg) {
            if (arg.startsWith(MULTI_PREFIX)) {
                arg = arg.substring(MULTI_PREFIX.length());
            }
            if (arg.endsWith(MULTI_SUFFIX)) {
                arg = arg.substring(0, arg.length() - MULTI_SUFFIX.length());
            }
            return super.unformat(arg);
        }
    }

    /**
     * This array is used to map types to cellformatters.
     */
    private final static CellFormatter[] CELL_FORMATTERS = {
            new CellFormatter(NVPDataType.TEXT),
            new BinaryFormatter(),
            new BracketedMultiFormatter(NVPDataType.MULTITEXT, new CellFormatter(NVPDataType.TEXT)),
            new BracketedMultiFormatter(NVPDataType.MULTIBINARY, new BinaryFormatter()),
    };

    /**
     * This is the map of 'type' ids to formatters
     */
    private Map mTypeToCellFormatter = new HashMap();

    // These are the names as the appear in the header...
    private List mColumnNames = null;
    private List mColumnTypes = null;

    // we use these to pull values from the incoming record.
    private String[] mMapNames = null;

    /**
     * package access.
     */
    CSVFormatter() { }

    private void init(String[] columnNames,
                      String[] columnTypes,
                      String[] mapNames) {

        /* create our map of formatters */
        for (int k = 0; k < CELL_FORMATTERS.length; k++) {
            mTypeToCellFormatter.put(CELL_FORMATTERS[k].toString(),
                                     CELL_FORMATTERS[k]);
        }

        mColumnNames = Arrays.asList(columnNames);
        mColumnTypes = Arrays.asList(columnTypes);
        mMapNames = mapNames;
        if (mMapNames == null) {
            mMapNames = (String[]) mColumnNames.toArray(new String[mColumnNames.size()]);
        }

        if (mColumnNames.size() != mColumnTypes.size() && mColumnNames.size() != mMapNames.length) {
            throw new IllegalArgumentException();
        }

        Set typeSet = mTypeToCellFormatter.keySet();
        for (int k = 0; k < mColumnTypes.size(); k++) {
            if (!typeSet.contains(mColumnTypes.get(k))) {
                throw new IllegalArgumentException("The type " + mColumnTypes.get(k) + " is not supported.");
            }
        }
    }

    /**
     * Create one of these based on a Group def...
     *
     * @param nvps     These define the set of NVPs (names, cardinality, type)
     *                 that we use to format the output (and unformat).
     * @param mapNames This array, if specified, defines the names of
     *                 the attributes in the incoming map that are to be used.  These
     *                 correspond, 1:1 and onto, to the names in the def.  So if the
     *                 def has NVPs named { singer, drummer, leader } these are the column names
     *                 in the output.  If mapNames is specified, then the format method
     *                 will look into the argument using these.  So, if mapNames is
     *                 { john, ringo, paul } then the incoming (and outgoing map) will
     *                 use these as keys, but the column names in the file will still
     *                 be { singer, drummer, leader } ( so john<=>singer, ringo<=>drummer,
     *                 and paul<=>leader )
     */
    CSVFormatter(NVPDef[] nvps, String[] mapNames) {

        String[] columnNames = new String[nvps.length];
        String[] columnTypes = new String[nvps.length];

        for (int k = 0; k < nvps.length; k++) {
            columnNames[k] = nvps[k].getName();
            columnTypes[k] = nvps[k].getType().toString();
        }
        init(columnNames, columnTypes, mapNames);
    }

    CSVFormatter(NVPDef[] nvps) {
        this(nvps, null);
    }

    public String[] getHeader() {
        MultiFormatter mf = new MultiFormatter(NVPDataType.TEXT, new CellFormatter(NVPDataType.TEXT));
        return new String[]{
                mf.format(mColumnNames),
                mf.format(mColumnTypes),
        };
    }

    /**
     * Change should contain recordPath==>valueToFormat
     *
     * @param map values to write
     * @return the string to write
     */
    public String format(Map map) {

        StringBuffer record = new StringBuffer();
        int numColumns = mMapNames.length;
        for (int k = 0; k < numColumns; k++) {
            Object value = map.get(mMapNames[k]);
            CellFormatter cf =
                    (CellFormatter) mTypeToCellFormatter.get(mColumnTypes.get(k));
            value = cf.format(value);
            if (value == null) {
                value = "";
            }
            record.append(value);
            if (k < numColumns - 1) {
                record.append(DELIMITER);
            }
        }

        return record.toString();
    }

    public Map unformat(String line) {

        line = line.trim();
        Map result = new HashMap();

        String[] values = splitToStrings(line);

        // Now we decode each one
        for (int k = 0; k < values.length; k++) {
            String value = values[k];
            CellFormatter cf =
                    (CellFormatter) mTypeToCellFormatter.get(mColumnTypes.get(k));
            Object unValue = cf.unformat(value);
            if (unValue != null)
                result.put(mMapNames[k], unValue);
        }
        return result;
    }

    private static String[] splitToStrings(String line) {

        List values = new ArrayList();

        char[] chars = line.toCharArray();
        int lastIdx = 0;
        for (int k = 0; k < chars.length; k++) {
            if (chars[k] == DELIMITER) {
                if (k == 0) {
                    values.add("");
                    lastIdx = k + 1;
                }
                else if (chars[k - 1] != '\\') {
                    values.add(line.substring(lastIdx, k));
                    lastIdx = k + 1;
                }
            }
        }
        values.add(line.substring(lastIdx));

        List result = new ArrayList();
        // now we reassemble the multis
        for (int k = 0; k < values.size(); k++) {
            String s = (String) values.get(k);
            if (s.startsWith("[") && !s.endsWith("]")) {
                do {
                    k = k + 1;
                    s = s + DELIMITER + values.get(k);
                } while (!s.endsWith("]"));
            }
            result.add(s);
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    /*

    public static void main(String[] args) {

        NVPDef[] defs = new NVPDef[]{
                new NVPDef("barMulti", NVPDataType.MULTITEXT),
                new NVPDef("foo", NVPDataType.TEXT),
                new NVPDef("bar", NVPDataType.TEXT),
                new NVPDef("fooMulti", NVPDataType.MULTITEXT),
                new NVPDef("id", NVPDataType.TEXT),
        };

        String[] mapNames = {
                "mmBar",
                "mFoo",
                "mBar",
                "mmFoo",
                "uid",
        };

        NVPObjectDef def = new NVPObjectDef("type", defs, "id");
        CSVFormatter test = new CSVFormatter(def, mapNames);

        // okay, try the delimiter thing.
        Map map = new HashMap();
        map.put("uid", "text,text");
        map.put("mFoo", ",,,,\\foo");
        map.put("mBar", "ba,\\,,\\,r");

        String[] m = new String[]{"one,on\\e", "\\two,two"};
        map.put("mmFoo", Arrays.asList(m));
        m = new String[]{"bar0", "bar1"};
        map.put("mmBar", Arrays.asList(m));

        String mapString1 = map.toString();
        System.out.println("input map is " + mapString1 + "\n");

        String[] header = test.getHeader();
        System.out.println("header[0]\\nheader[1]\\nline is:");
        System.out.println(header[0]);
        System.out.println(header[1]);
        String line = test.format(map);
        System.out.println(line);

        // now reverse it.
        Map umap = test.unformat(line);

        String mapString2 = umap.toString();
        System.out.println("\nunformatted output map is " + mapString2);

        System.out.println("\nThe input and output maps are " + ((mapString1.equals(mapString2)) ? "" : " NOT ") + "equal.");

        // nulls?
        System.out.println("\n===========");
        System.out.println("TESTING THE NULLS");

        System.out.println("===========");
        map = new HashMap();
        map.put("uid", "uid");
        line = test.format(map);
        System.out.println("line with mostly nulls is " + line);
        map = test.unformat(line);
        System.out.println("map is " + map);

        System.out.println("===========");
        map.clear();
        line = test.format(map);
        System.out.println("line with ALL nulls is " + line);
        map = test.unformat(line);
        System.out.println("map is " + map);

        System.out.println("===========");
        map.clear();
        map.put("uid", "uid");
        map.put("mmBar", new ArrayList());
        line = test.format(map);
        System.out.println("line with mostly nulls (and empty multi) is " + line);
        map = test.unformat(line);
        System.out.println("map is " + map);

        System.out.println("===========");
        map.put("mmBar", Arrays.asList(new String[] {"one", "two"}));
        line = test.format(map);
        System.out.println("line with mostly nulls (multi is first in line) " + line);
        map = test.unformat(line);
        System.out.println("map is " + map);

    }
    */
}
