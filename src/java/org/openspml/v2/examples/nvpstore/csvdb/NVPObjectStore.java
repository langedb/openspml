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
import org.openspml.v2.examples.nvpstore.csvdb.def.NVPObjectDef;
import org.openspml.v2.examples.nvpstore.csvdb.def.NVPObjectStoreDef;
import org.openspml.v2.examples.nvpstore.csvdb.def.NVPStoreDefMarshallableCreator;
import org.openspml.v2.msg.XMLUnmarshaller;
import org.openspml.v2.util.FileUtil;
import org.openspml.v2.util.xml.ObjectFactory;
import org.openspml.v2.util.xml.ReflectiveDOMXMLUnmarshaller;
import org.openspml.v2.util.xml.UnknownSpml2TypeException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This is a data store for NVPObjects.  It does all of
 * its work in memory until you tell it to commit all
 * changes to the file. (Another option would be to write
 * a running log of lines that represent the on-going state of
 * the store - with each line have an operation
 * prefix like add, modify, delete, etc.. and read this
 * file back on startup, mapping the operations to methods.
 * What are we, prevaylor?)
 * <p/>
 * This class takes an xml string that represents an
 * NVPObjectStoreDef and simulates a storage device that
 * uses the schema.
 * <p/>
 * This will be used as the back-end for our Example
 * Provider.
 */
public class NVPObjectStore {

    private static final String code_id = "$Id: NVPObjectStore.java,v 1.6 2006/10/17 22:48:36 kas Exp $";

    /**
     * This is helper class - we use it to manage the lines
     * within a file that represents the extent of the objects
     * in an NVPObjectDef.
     */
    class NVPObjectCache {

        private File mFile;
        private NVPObjectDef mDef;

        private Map mUidToNVPObject = new LinkedHashMap();
        private Map mIdToUid = new HashMap();

        private CSVFormatter mFormatter = null;

        // We are keeping copies of the object in this cache.
        // Why?  You don't expect to write an object and have
        // changes applied after the write affect the stored
        // object.
        private NVPObject createNVPO(String uid, Map attrs) {
            return new NVPObject(mDef,
                                 mObjectStoreDef.getNameOfTypeNVP(),
                                 mObjectStoreDef.getNameOfUidNVP(),
                                 uid,
                                 attrs);
        }

        private void cacheObject(String uid, Map attrs) {
            NVPObject obj = createNVPO(uid, attrs);
            mUidToNVPObject.put(uid, obj);
            mIdToUid.put(obj.getId(), uid);
        }

        // We don't care what types are in the file because we have
        // the file's definition to drive us.  However, the file is
        // self-describing... we just advance past the header.
        protected void readFile(final String uidNVP) throws IOException {
            if (mFile.exists()) {
                FileUtil.FileLineProcessor processor = new FileUtil.FileLineProcessor() {
                    private boolean sawAttrNames = false;
                    private boolean sawTypes = false;

                    public void process(String line) {
                        // the first two are headers...
                        if (!sawAttrNames) {
                            sawAttrNames = true;
                        }
                        else if (!sawTypes) {
                            sawTypes = true;
                        }
                        else {
                            Map nvpoMap = mFormatter.unformat(line);
                            String uid = (String) nvpoMap.get(uidNVP);
                            cacheObject(uid, nvpoMap);
                        }
                    }
                };

                FileUtil.readFileAndProcessLines(mFile, processor);
            }
        }

        public NVPObjectCache(File baseDir,
                              String uidNVPName,
                              NVPObjectDef def) throws IOException {

            mDef = def;
            mFile = new File(baseDir, def.getClassName() + ".csv");

            // we need a 'synthetic' nvpdef for the uid.  it'll be first in
            // the list.
            NVPDef[] defs = def.getNVPDefs();
            NVPDef[] allDefs = new NVPDef[defs.length + 1];
            allDefs[0] = new NVPDef(uidNVPName, NVPDataType.TEXT, true);
            for (int k = 0; k < defs.length; k++) {
                allDefs[k + 1] = defs[k];
            }
            mFormatter = new CSVFormatter(allDefs);
            readFile(uidNVPName);
        }

        // No, we cannot handle really large files... what did you expect?
        private void writeFile() throws IOException {

            List lines = new ArrayList();
            Iterator iter = mUidToNVPObject.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                NVPObject o = (NVPObject) entry.getValue();
                lines.add(mFormatter.format(o.asMap()));
            }

            BufferedWriter writer = null;
            try {
                mFile.delete();

                writer = new BufferedWriter(new FileWriter(mFile));
                String[] header = mFormatter.getHeader();
                for (int k = 0; k < header.length; k++) {
                    String line = header[k];
                    writer.write(line);
                    writer.newLine();
                }

                for (int k = 0; k < lines.size(); k++) {
                    String line = (String) lines.get(k);
                    writer.write(line);
                    writer.newLine();
                }
            }
            finally {
                if (writer != null) {
                    try {
                        writer.close();
                    }
                    catch (IOException e) {
                    }
                }
            }
        }

        /**
         * Returns a false if the id already exists.  We do NOT
         * map add to modify.
         *
         * @param uid
         * @param attrs
         * @return
         */
        public boolean add(String uid, Map attrs) {
            if (mUidToNVPObject.containsKey(uid)) return false;
            cacheObject(uid, attrs);
            return true;
        }

        public boolean modify(String uid, Map attrs) {
            if (!mUidToNVPObject.containsKey(uid)) return false;
            cacheObject(uid, attrs);
            return true;
        }

        public boolean delete(String uid) {
            NVPObject o = (NVPObject) mUidToNVPObject.remove(uid);
            if (o != null) {
                String id = o.getId();
                mIdToUid.remove(id);
                return true;
            }
            return false;
        }

        public Map get(String uid) {
            // we need to turn the line into a Map
            NVPObject obj = (NVPObject) mUidToNVPObject.get(uid);
            if (obj == null) return null;
            return obj.asMap();
        }

        // We're not returning a Map here because one call
        // should suffice.
        public NVPObject getById(String id) {
            // the object in the cache are matched
            String uid = (String) mIdToUid.get(id);
            if (uid != null) {
                return createNVPO(uid, get(uid));
            }
            return null;
        }

    }

    /**
     * Stores data for the set of NVPObjectDefs that are
     * defined in a schema.  Each schema is in a directory
     * with its name.  Each NVPObjectDef (which defines a
     * 'class') is represented by a file with that type's
     * name.  The lines in the file represent the instances
     * of that class.
     * <p/>
     * We need the name of a directory as a base.  We
     * want to allow multiple schema defs in the future (say,
     * one per Target) so we use the schemaName in the location
     * <p/>
     * We use the type of an object as a filename under that.  These
     * are .csv files.
     * <p/>
     * The objects are managed in the NVPObjectCache.  It in turn
     * encodes the values therein, see the CSVFormatter.
     */
    class NVPObjectStoreDefManager {

        private static final String code_id = "$Id: NVPObjectStore.java,v 1.6 2006/10/17 22:48:36 kas Exp $";

        private File mBaseDir = new File(".");

        private LinkedHashMap mTypeToObjectStore = new LinkedHashMap();

        protected NVPObjectCache getObjectCache(NVPObject nvpo) {
            String objectclass = nvpo.getClassName();
            return getObjectCache(objectclass);
        }

        protected NVPObjectCache getObjectCache(String objectclass) {
            return (NVPObjectCache) mTypeToObjectStore.get(objectclass);
        }

        /**
         * Provide maps of names?  [ objecttype-> [nvpname <--> mappedname]+ ]+
         *
         * @param dir            Where are the files to be stored?
         * @param objectStoreDef What is the format?
         * @throws IOException thrown if we encounter issues like no such dir.
         */
        public NVPObjectStoreDefManager(File dir, NVPObjectStoreDef objectStoreDef) throws IOException {
            mBaseDir = new File(dir, objectStoreDef.getStoreName());

            NVPObjectDef[] defs = objectStoreDef.getNVPObjectDefs();
            for (int k = 0; k < defs.length; k++) {
                NVPObjectDef def = defs[k];
                mTypeToObjectStore.put(def.getClassName(),
                                       new NVPObjectCache(mBaseDir,
                                                          objectStoreDef.getNameOfUidNVP(),
                                                          def));
            }
        }

        public boolean add(NVPObject object) {
            NVPObjectCache manager = getObjectCache(object);
            String uid = object.getUid();
            if (manager != null)
                return manager.add(uid, object.mMap);
            return false;
        }

        public boolean delete(String uid) {
            String type = getTypeForUid(uid);
            NVPObjectCache manager = getObjectCache(type);
            if (manager != null)
                return manager.delete(uid);
            return false;
        }

        public boolean modify(NVPObject object) {
            NVPObjectCache manager = getObjectCache(object);
            String uid = object.getUid();
            if (manager != null)
                return manager.modify(uid, object.mMap);
            return false;
        }

        public NVPObject get(String uid) {
            Object[] res = getTypeAndObject(uid);
            if (res != null) {
                return (NVPObject) res[1];
            }
            return null;
        }

        private Object[] getTypeAndObject(String uid) {
            String type = getTypeForUid(uid);
            if (type != null) {
                NVPObjectCache cache = getObjectCache(type);
                NVPObject obj = cache.createNVPO(uid, cache.get(uid));
                return new Object[]{type, obj};
            }
            return null;
        }

        public void commit() throws IOException {
            if (!mBaseDir.exists()) mBaseDir.mkdirs();

            Iterator iter = mTypeToObjectStore.values().iterator();
            while (iter.hasNext()) {
                NVPObjectCache s = (NVPObjectCache) iter.next();
                s.writeFile();
            }
        }

        public String getTypeForUid(String uid) {
            for (Iterator iterator = mTypeToObjectStore.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry entry = (Map.Entry) iterator.next();
                NVPObjectCache cache = (NVPObjectCache) entry.getValue();
                if (cache.get(uid) != null) {
                    return (String) entry.getKey();
                }
            }
            return null;
        }
    }

    private NVPObjectStoreDef mObjectStoreDef = null;
    private NVPObjectStoreDefManager mStore = null;

    private void init(File baseDir, String schemaXml) throws NVPObjectStoreException, IOException {

        // we're using the toolkit's parser utilities to read out schema xml.
        ObjectFactory.getInstance().addCreator(new NVPStoreDefMarshallableCreator());

        XMLUnmarshaller u = new ReflectiveDOMXMLUnmarshaller();
        try {
            mObjectStoreDef = (NVPObjectStoreDef) u.unmarshall(schemaXml);
        }
        catch (UnknownSpml2TypeException e) {
            throw new NVPObjectStoreException(e.getMessage());
        }
        mStore = new NVPObjectStoreDefManager(baseDir, mObjectStoreDef);
    }

    public NVPObjectStore(File baseDir, File schemaFile) throws NVPObjectStoreException {
        try {
            String xml = FileUtil.readFile(schemaFile);
            init(baseDir, xml);
        }
        catch (IOException e) {
            throw new NVPObjectStoreException(e);
        }
    }

    public NVPObjectStore(File baseDir, String schemaXml) throws NVPObjectStoreException {
        try {
            init(baseDir, schemaXml);
        }
        catch (IOException e) {
            throw new NVPObjectStoreException(e);
        }
    }

    protected void validateObject(NVPObject obj) throws NVPObjectStoreException {
        List errors = obj.validate();
        if (errors != null) {
            StringBuffer message = new StringBuffer("NVPObject is invalid.\n");
            for (int k = 0; k < errors.size(); k++) {
                String s = (String) errors.get(k);
                message.append(k);
                message.append(": ");
                message.append(s);
                message.append("\n");
            }
            throw new NVPObjectStoreException(message.toString());
        }
    }

    public NVPObjectStoreDef getObjectStoreDef() {
        // let the schema be accessed so we can turn it into a Targets list.
        return mObjectStoreDef;
    }

    public NVPObject createNVPObject(String type) throws NVPObjectStoreException {

        NVPObjectDef def = mObjectStoreDef.getNVPObjectDef(type);
        if (def == null) {
            throw new NVPObjectStoreException("Unknown type; " + type);
        }

        return new NVPObject(def,
                             mObjectStoreDef.getNameOfTypeNVP(),
                             mObjectStoreDef.getNameOfUidNVP(),
                             generateId());
    }

    // we always use the same seed so our id's are always the same progression.
    //  to avoid issues with multiple runs, we put the time in the id too.
    private static Random mRand = new Random(1000);

    private String generateId() {
        int r = 100000 + mRand.nextInt(10000);
        String rand = (r + "").substring(1);
        return "uid:" + rand + ":" + System.currentTimeMillis();
    }

    public boolean add(NVPObject object) throws NVPObjectStoreException {
        validateObject(object);
        return mStore.add(object);
    }

    public boolean delete(String id) throws NVPObjectStoreException {
        return mStore.delete(id);
    }

    public boolean modify(NVPObject object) throws NVPObjectStoreException {
        validateObject(object);
        return mStore.modify(object);
    }

    public NVPObject get(String uid) throws NVPObjectStoreException {
        return mStore.get(uid);
    }

    public String getType(String uid) {
        return mStore.getTypeForUid(uid);
    }

    public NVPObject get(String type, String id) throws NVPObjectStoreException {
        // we want the uid in order to get this, but we don't have it...
        NVPObjectCache cache = mStore.getObjectCache(type);
        if (cache != null) {
            NVPObject obj = cache.getById(id);
            if (obj == null) {
                throw new NVPObjectStoreException("Uknown object, " + id + " of type, " + type + ".");
            }
            return obj;
        }
        throw new NVPObjectStoreException("Unknown type: " + type);

    }

    public void commit() throws NVPObjectStoreException {
        try {
            mStore.commit();
        }
        catch (IOException e) {
            throw new NVPObjectStoreException(e);
        }
    }

    /**
     * Let's do a fairly simple test program.
     *
     * @param args args[0] is the name of the xml file with the defs.
     */
    public static void main(String[] args) {
        try {

            final String TEST_BASE = "./classes";

            String xml = FileUtil.readFile(args[0]);

            int OBJS = 10;
            String PI_TYPE = "phoneInfo";
            String EI_TYPE = "emailInfo";

            List uids = new ArrayList();

            {
                // now create a store
                NVPObjectStore os = new NVPObjectStore(new File(TEST_BASE), xml);

                // create a bunch of objects (like 10)
                for (int k = 0; k < OBJS; k++) {
                    NVPObject pobj = os.createNVPObject(PI_TYPE);
                    String uid = "contactId" + k;
                    popPhoneObj(pobj, uid, "fn" + k, "ln" + k,
                                getRandPhones());
                    os.add(pobj);
                    uids.add(pobj.getUid());

                    NVPObject eobj = os.createNVPObject(EI_TYPE);
                    popEmailObj(eobj, uid, getRandEmails(pobj, k));

                    uids.add(eobj.getUid());
                    os.add(eobj);
                }

                // we'll try a modify
                String firstUid = (String) uids.get(0);
                NVPObject obj = os.get(firstUid);
                obj.put("firstName", "fn1,m");
                os.modify(obj);

                os.commit();
            }

            {
                // Can we getByUid them all back?
                System.out.println("\nREADING THEM BACK BY UID...\n");
                NVPObjectStore os = new NVPObjectStore(new File(TEST_BASE), xml);
                for (int k = 0; k < uids.size(); k++) {
                    String uid = (String) uids.get(k);
                    NVPObject o = os.get(uid);
                    System.out.println("obj =  " + o.toString());
                }
            }

            {
                // and get them by the id too.
                System.out.println("\nREADING THEM BACK BY ID...\n");
                NVPObjectStore os = new NVPObjectStore(new File(TEST_BASE), xml);
                for (int k = 0; k < OBJS; k++) {
                    NVPObject o = os.get(PI_TYPE, "contactId" + k);
                    System.out.println("phone info is " + o.toString());
                    o = os.get(EI_TYPE, "contactId" + k);
                    System.out.println("email info is " + o.toString());
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NVPObjectStoreException e) {
            e.printStackTrace();
        }
    }

    private static final String[] PHONE_KINDS = {
            "Home", "Mobile", "Office", "Other",
    };

    private static String[][] getRandPhones() {
        // use i in the result.
        int howMany = mRand.nextInt(4) + 1;
        String[][] result = new String[2][howMany];
        for (int k = 0; k < howMany; k++) {
            String p = ((9999999 + k) % 899) + 100 + "";
            result[0][k] = "(" + (mRand.nextInt(900) + 100) + ")" + p + "-" + (mRand.nextInt(9000) + 1000);
            result[1][k] = PHONE_KINDS[mRand.nextInt(PHONE_KINDS.length)];
        }
        return result;
    }

    private static final String[] EMAIL_KINDS = {
            "Home", "Office", "Personal", "Alternate",
    };
    private static final String[] EMAIL_DOMAINS = {
            "sun.com", "java.com", "openspml.org", "network.com",
    };

    private static String[][] getRandEmails(NVPObject obj, int i) {
        // use i in the result.
        int howMany = mRand.nextInt(4) + 1;
        String[][] result = new String[2][howMany];
        for (int k = 0; k < howMany; k++) {
            int alg = mRand.nextInt(2);
            if (alg == 0) {
                String e = obj.getText("firstName").substring(0, 1);
                e += obj.getText("lastName").substring(0, 1);
                e += (mRand.nextInt(100000) % 10000) + i;
                result[0][k] = e + "@" + EMAIL_DOMAINS[mRand.nextInt(EMAIL_DOMAINS.length)];
            }
            else {
                String e = obj.getText("firstName") + ".";
                e += obj.getText("lastName") + "@";
                result[0][k] = e + EMAIL_DOMAINS[mRand.nextInt(EMAIL_DOMAINS.length)];
            }
            result[1][k] = EMAIL_KINDS[mRand.nextInt(EMAIL_KINDS.length)];
        }
        return result;
    }

    private static void popPhoneObj(NVPObject obj,
                                    String uid,
                                    String fn,
                                    String ln,
                                    String[][] phones) throws NVPObjectStoreException {
        obj.put("contactId", uid);
        obj.put("firstName", fn);
        obj.put("lastName", ln);
        obj.put("phoneNumbers", Arrays.asList(phones[0]));
        obj.put("phoneNumberTypes", Arrays.asList(phones[1]));
    }

    private static void popEmailObj(NVPObject obj,
                                    String uid,
                                    String[][] emails) throws NVPObjectStoreException {
        obj.put("contactId", uid);
        obj.put("emailAddresses", Arrays.asList(emails[0]));
        obj.put("emailAddressTypes", Arrays.asList(emails[1]));
    }

}