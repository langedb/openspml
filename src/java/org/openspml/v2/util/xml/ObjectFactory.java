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
package org.openspml.v2.util.xml;

import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.MarshallableElement;
import org.openspml.v2.msg.OCEtoXMLStringAdapter;
import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.util.Spml2Exception;
import org.w3c.dom.Element;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URI;

/**
 * This is a singleton class that will, given a name of a Marshallable element,
 * return an object of that type.  The object will be empty.
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Mar 2, 2006
 */
public class ObjectFactory {

    private static final String code_id = "$Id: ObjectFactory.java,v 1.16 2006/09/25 18:50:07 kas Exp $";

    private static final ObjectFactory mInstance;

    static {
        synchronized (ObjectFactory.class) {
            try {
                mInstance = new ObjectFactory();
                mInstance.addCreator(new OperationalNameValuePairCreator());
            }
            catch (Spml2Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    private class DefaultCreator implements MarshallableCreator {

        private Map mURIToPackage = new HashMap();
        private Map mMarshallableNameToClass = new HashMap();

        private DefaultCreator() throws Spml2Exception {

            // populate the map of the uri to the package.
            String[][] uriToPackage = {
                {"urn:oasis:names:tc:SPML:2:0", "org.openspml.v2.msg.spml"},
                {"urn:oasis:names:tc:SPML:2:0:async", "org.openspml.v2.msg.spmlasync"},
                {"urn:oasis:names:tc:SPML:2:0:batch", "org.openspml.v2.msg.spmlbatch"},
                {"urn:oasis:names:tc:SPML:2:0:bulk", "org.openspml.v2.msg.spmlbulk"},
                {"urn:oasis:names:tc:SPML:2:0:password", "org.openspml.v2.msg.pass"},
                {"urn:oasis:names:tc:SPML:2:0:reference", "org.openspml.v2.msg.spmlref"},
                {"urn:oasis:names:tc:SPML:2:0:search", "org.openspml.v2.msg.spmlsearch"},
                {"urn:oasis:names:tc:SPML:2:0:suspend", "org.openspml.v2.msg.spmlsuspend"},
                {"urn:oasis:names:tc:SPML:2:0:updates", "org.openspml.v2.msg.spmlupdates"},
            };

            for (int k = 0; k < uriToPackage.length; k++) {
                String key = uriToPackage[k][0];
                String pkgName = uriToPackage[k][1];
                mURIToPackage.put(key, pkgName);
            }

            // we also need to remap some names
            try {
                // populate the map of name to class
                String[][] nameToClassname = {
                    {"select", "org.openspml.v2.msg.spml.Selection"},
                };

                for (int k = 0; k < nameToClassname.length; k++) {
                    String key = nameToClassname[k][0];
                    String classname = nameToClassname[k][1];
                    mMarshallableNameToClass.put(key, Class.forName(classname));
                }
            }
            catch (ClassNotFoundException e) {
                throw new Spml2Exception("Missing classes in the jar?");
            }
        }

        private String firstCharToUpper(String cname) {
            String fChar = cname.substring(0, 1);
            fChar = fChar.toUpperCase();
            return fChar + cname.substring(1);
        }

        private Class findClass(String name, String uri) throws Spml2Exception {
            // This should be per uri, so for each uri we keep a
            // map of name to class - and that name should NOT include the prefix.
            //   But this works for now, for the one class we have a different name for.
            Class cls = (Class) mMarshallableNameToClass.get(name);

            if (cls == null) {
                String classname = "org.openspml.v2.msg.spml";
                String pkgName = (String) mURIToPackage.get(uri);
                if (pkgName != null) {
                    classname = pkgName;
                }

                // see if we can't create one; take the prefix off.... if there.
                int idx = name.indexOf(":");
                if (idx != -1) {
                    name = name.substring(idx + 1);
                }
                classname = classname + "." + firstCharToUpper(name);

                try {
                    cls = Class.forName(classname);
                }
                catch (ClassNotFoundException e) {
                    throw new Spml2Exception(e);
                }
            }
            return cls;
        }

        public Marshallable createMarshallable(String nameAndPrefix, String uri) throws Spml2Exception {
            Class cls = findClass(nameAndPrefix, uri);
            if (cls == null) return null;
            MarshallableElement e = createMarshallableElement(cls);
            if (!(e instanceof Marshallable)) {
                throw new UnknownSpml2TypeException(
                        "That's just a MarshallableElement: " + nameAndPrefix + ", uri = " + uri);
            }
            return (Marshallable) e;
        }
    }

    private final MarshallableCreator mDefaultCreator = new DefaultCreator();

    private final Object mCreatorsLock = new Object();
    private final List mCreators = new ArrayList();

    private final Object mOCEUnmarshallersLock = new Object();
    private final List mOCEUnmarshallers = new ArrayList();

    private ObjectFactory() throws Spml2Exception {
    }

    /**
     * You can register objects to create wrappers for the unmarshalling.
     */
    public static interface MarshallableCreator {

        public Marshallable createMarshallable(String nameAndPrefix, String uri) throws Spml2Exception;
    }

    /**
     * Implement these to adapt the OpenContentElement stuff to the
     * toolkit.  If you write an unmarshaller that does DOM - it's likely
     * the Object in the method is a Node...
     */
    public static interface OCEUnmarshaller {

        /**
         * Unmarshall the obj, or return null if you do not handle
         * objects of this type.
         *
         * @param obj
         * @return make an Object from the xmlRep obj
         * @throws Spml2Exception
         */
        public OpenContentElement unmarshall(Object obj) throws Spml2Exception;
    }

    /**
     * given a name of an element, create a hollow object that we can
     * populate via a parser.
     *
     * @param name
     * @param uri  (the uri of the namespace)
     * @return a hollow Marshallable for the name and uri...
     * @throws UnknownSpml2TypeException
     */
    public Marshallable createMarshallable(String name, String uri)
            throws UnknownSpml2TypeException {

        Marshallable m = null;
        try {
            synchronized (mCreatorsLock) {
                int size = mCreators.size();
                for (int k = 0; k < size; k++) {
                    MarshallableCreator c = (MarshallableCreator) mCreators.get(k);
                    m = c.createMarshallable(name, uri);
                    if (m != null)
                        break;
                }
            }

            if (m == null) {
                m = mDefaultCreator.createMarshallable(name, uri);
            }
        }
        catch (Spml2Exception e) {
            throw new UnknownSpml2TypeException("Unknown or unsupported SPML type, " + name, e);
        }

        if (m == null) {
            // if we still can't create one, fail.
            throw new UnknownSpml2TypeException("Unknown or unsupported SPML type, name=" + name + ", uri=" + uri);
        }

        return m;
    }

    /**
     * XMLUnmarshaller implementations will use this to create objects
     * within the core SPML schema.
     *
     * @param cls
     * @return the hollow instance of the given class.
     * @throws UnknownSpml2TypeException
     */
    public MarshallableElement createMarshallableElement(Class cls)
            throws UnknownSpml2TypeException {
        try {
            // we're doing this so we don't have to make the ctors public
            Constructor ctor = cls.getDeclaredConstructor(null);
            ctor.setAccessible(true);
            return (MarshallableElement) ctor.newInstance(null);
        }
        catch (Exception e) {
            throw new UnknownSpml2TypeException("Error creating SPML object. cls = " + cls, e);
        }
    }

    /**
     * Need to register an unmarshaller for OpenContent - call this.
     *
     * @param oceum
     */
    public void addOCEUnmarshaller(OCEUnmarshaller oceum) {
        synchronized (mOCEUnmarshallersLock) {
            if (!mOCEUnmarshallers.contains(oceum)) {
                mOCEUnmarshallers.add(oceum);
            }
        }
    }

    /**
     * You can unregister them too.
     *
     * @param oceum
     * @return  true if we found it can removed it.
     */
    public boolean removeOCEUnmarshaller(OCEUnmarshaller oceum) {
        synchronized (mOCEUnmarshallersLock) {
            return mOCEUnmarshallers.remove(oceum);
        }
    }

    // We have to be able to tell the ObjectFactory to unmarshall to Strings for
    // those things that it doesn't recognize.
    private static class OCEUnmarshallerForDOMElementAsString
            implements ObjectFactory.OCEUnmarshaller {

        public OpenContentElement unmarshall(Object obj) throws Spml2Exception {
            if (obj instanceof Element) {
                Element el = (Element) obj;
                String xml = XmlUtil.serializeNode(el);
                xml += "\n";
                xml = XmlUtil.makeXmlFragmentFromDoc(xml);
                return new OCEtoXMLStringAdapter(xml);
            }
            return null;
        }
    }

    private OCEUnmarshaller mOCEForStrings = new OCEUnmarshallerForDOMElementAsString();

    /**
     * XMLUnmarshaller implementations need to to handle OpenContentElements.
     *
     * @param obj
     * @return null if we cannot create one, otherwise, an object from the xmlRep
     * @throws Spml2Exception
     */
    public OpenContentElement unmarshallOpenContentElement(Object obj)
            throws Spml2Exception {

        synchronized (mOCEUnmarshallersLock) {
            OpenContentElement oce = null;
            for (int k = 0; k < mOCEUnmarshallers.size(); k++) {
                OCEUnmarshaller u = (OCEUnmarshaller) mOCEUnmarshallers.get(k);
                oce = u.unmarshall(obj);
                if (oce != null) {
                    break;
                }
            }

            // This is a last resort.
            if (oce == null &&
                System.getProperty("org.openspml.v2.adaptXMLStrings") != null) {
               oce = mOCEForStrings.unmarshall(obj);
            }

            return oce;
        }
    }

    /**
     * @param creator
     */
    public void addCreator(MarshallableCreator creator) {
        synchronized (mCreatorsLock) {
            mCreators.add(creator);
        }
    }

    /**
     * @param creator
     * @return true if removed it.
     */
    public boolean removeCreator(MarshallableCreator creator) {
        synchronized (mCreatorsLock) {
            return mCreators.remove(creator);
        }
    }

    public static interface ProfileRegistrar {

        /**
         * We ask that this be unique - namely
         * as we only allow a registration once.
         * This is the field we use to check if the
         * profile has been registered.
         *
         * @return the profile id - must be unique amongst used registars.
         */
        public String getProfileId();

        /**
         * We need these too.
         * @return A String that has the URI of this profile.
         */
        public URI getProfileURI() throws Spml2Exception;

        /**
         * Generally, this will turn around and add
         * MarshallableCreator(s) to the object factory.
         *
         * @param of This singleton ;-)
         */
        public void register(ObjectFactory of);

        /**
         * Remove the creators from the factory.
         * At present, this is not called automatically.
         *
         * @param of This singleton ;-)
         *
         */
        public void unregister(ObjectFactory of);
    }

    private Map mRegistrarNameToRegistrar = new HashMap();

    /**
     * Used to register profiles.  This makes it easier to put all
     * the info about a profile into one class. e.g. if a profile
     * use several packages and creators, one can place the code
     * that adds them to the factory into a single Registrar and pass
     * it along.
     * <p/>
     * <code>
     * // DSMLProfileRegistrar adds a creator for spmldsml and dsml.
     * ObjectFactory.getInstance().register(
     * new DSMLProfileRegistrar());
     * </code>
     *
     * @param pr The registrar to use and track.
     */
    public String register(ProfileRegistrar pr) {
        String pid = null;
        if (pr != null) {
            pid = pr.getProfileId();
            if (pid != null && mRegistrarNameToRegistrar.get(pid) == null) {
                pr.register(this);
                mRegistrarNameToRegistrar.put(pid, pr);
            }
        }
        return pid;
    }

    /**
     * Given the id of the profile, unregister it.
     * @param profileId
     * @return true iff the profileId was known and unregister was called.
     */
    public boolean unregister(String profileId) {
        if (profileId != null) {
            ProfileRegistrar pr =
                    (ProfileRegistrar) mRegistrarNameToRegistrar.get(profileId);
            if (pr != null) {
                pr.unregister(this);
                return true;
            }
        }
        return false;
    }

    /**
     * Shortcut for the unregister(String profileId) method.
     * 
     * @param pr A registrar...
     * @return true if the pr was successfully unregistered.
     */
    public boolean unregister(ProfileRegistrar pr) {
        return unregister(pr.getProfileId());
    }


    /**
     * This is a Singleton.
     *
     * @return the singleton instance of this class.
     */
    static public ObjectFactory getInstance() {
        return mInstance;
    }

}
