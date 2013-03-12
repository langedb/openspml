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
package org.openspml.v2.examples.nvpstore.psp;

import org.openspml.v2.examples.nvpstore.csvdb.NVPObject;
import org.openspml.v2.examples.nvpstore.csvdb.NVPObjectStore;
import org.openspml.v2.examples.nvpstore.csvdb.NVPObjectStoreException;
import org.openspml.v2.examples.nvpstore.csvdb.def.NVPDef;
import org.openspml.v2.examples.nvpstore.csvdb.def.NVPObjectDef;
import org.openspml.v2.examples.nvpstore.csvdb.def.NVPObjectDefReference;
import org.openspml.v2.examples.nvpstore.csvdb.def.NVPObjectStoreDef;
import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.msg.spml.AddRequest;
import org.openspml.v2.msg.spml.AddResponse;
import org.openspml.v2.msg.spml.DeleteRequest;
import org.openspml.v2.msg.spml.DeleteResponse;
import org.openspml.v2.msg.spml.ErrorCode;
import org.openspml.v2.msg.spml.ExecutionMode;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.msg.spml.ListTargetsRequest;
import org.openspml.v2.msg.spml.ListTargetsResponse;
import org.openspml.v2.msg.spml.LookupRequest;
import org.openspml.v2.msg.spml.LookupResponse;
import org.openspml.v2.msg.spml.Modification;
import org.openspml.v2.msg.spml.ModificationMode;
import org.openspml.v2.msg.spml.ModifyRequest;
import org.openspml.v2.msg.spml.ModifyResponse;
import org.openspml.v2.msg.spml.PSO;
import org.openspml.v2.msg.spml.PSOIdentifier;
import org.openspml.v2.msg.spml.Request;
import org.openspml.v2.msg.spml.Response;
import org.openspml.v2.msg.spml.ReturnData;
import org.openspml.v2.msg.spml.Schema;
import org.openspml.v2.msg.spml.SchemaEntityRef;
import org.openspml.v2.msg.spml.StatusCode;
import org.openspml.v2.msg.spml.Target;
import org.openspml.v2.profiles.DSMLProfileRegistrar;
import org.openspml.v2.profiles.dsml.DSMLAttr;
import org.openspml.v2.profiles.dsml.DSMLModification;
import org.openspml.v2.profiles.dsml.DSMLProfileException;
import org.openspml.v2.profiles.dsml.DSMLValue;
import org.openspml.v2.profiles.spmldsml.AttributeDefinition;
import org.openspml.v2.profiles.spmldsml.AttributeDefinitionReference;
import org.openspml.v2.profiles.spmldsml.AttributeDefinitionReferences;
import org.openspml.v2.profiles.spmldsml.DSMLSchema;
import org.openspml.v2.profiles.spmldsml.ObjectClassDefinition;
import org.openspml.v2.profiles.spmldsml.ObjectClassDefinitionReference;
import org.openspml.v2.profiles.spmldsml.ObjectClassDefinitionReferences;
import org.openspml.v2.provider.SPMLExecutor;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.xml.ObjectFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NVPObjectStoreExecutor implements SPMLExecutor {

    public static final String NVP_SCHEMA_FILE_URL = "schemaFileURL";
    public static final String NVP_BASEDIR_TO_SAVE_FILES = "baseDir";

    private static NVPObjectStore mOS = null;

    private String mSchemaFileURL = "http://localhost:8080/nvp/nvpstore-schema.xml";
    private String mBaseDir = ".";

    private Target[] mTargets = null;

    public NVPObjectStoreExecutor() { }

    // for testing
    public NVPObjectStoreExecutor(String schemaFileURL, String baseDir)
            throws Spml2Exception {
        Map params = new HashMap();
        params.put(NVP_SCHEMA_FILE_URL, schemaFileURL);
        params.put(NVP_BASEDIR_TO_SAVE_FILES, baseDir);
        init(params);
    }

    // careful, we're making some assumptions, like ONE schema
    protected boolean isMultiValued(String name, String targetId) {

        Target t = null;

        if (targetId == null && mTargets.length == 1) {
            t = mTargets[0];
        }
        else {
            for (int k = 0; k < mTargets.length; k++) {
                Target target = mTargets[k];
                if (target.getTargetID().equals(targetId)) {
                    t = target;
                    break;
                }
            }
        }

        if (t == null)
            return false;

        Schema[] s = t.getSchemas();
        List dsmlSchemas = s[0].getOpenContentElements(DSMLSchema.class);
        DSMLSchema schema = (DSMLSchema) dsmlSchemas.get(0);

        AttributeDefinition[] ads = schema.getAttributeDefinitions();
        for (int k = 0; k < ads.length; k++) {
            AttributeDefinition ad = ads[k];
            if (ad.getName().equals(name)) {
                return ad.isMultivalued();
            }
        }

        return false;
    }

    /**
     * The Map of params come from, for example, web.xml.  In the servlet
     * config these would be init-params in a web.xml with names like:
     * <p/>
     * <pre>
     * SpmlViaSoap.spmlExecutors.nvpos.&lt;attrname&gt;
     * </pre>
     * The attrname is the 'key' of the param in the Map.  These are the names we expect:
     * <ul>
     * <li>"schemaFile" - the location of the file that defines the schema for this example</li>
     * <li>"baseDir" - this names the directory in which you want to save files for the NVPObjectStore
     * </ul>
     * Simple enough.
     * <p/>
     *
     * @param params The map of the parameters, which are named as above.
     * @throws Spml2Exception
     */
    public void init(Map params) throws Spml2Exception {
        synchronized (NVPObjectStoreExecutor.class) {
            if (mOS == null) {

                // first, make sure we all know we're using the DSML profile
                ObjectFactory.getInstance().register(new DSMLProfileRegistrar());

                // Now, let's load up the schema for our object store.
                {
                    String fileUrl = (String) params.get(NVP_SCHEMA_FILE_URL);
                    if (fileUrl != null) {
                        mSchemaFileURL = fileUrl;
                    }
                }

                BufferedReader in = null;
                StringBuffer xml = new StringBuffer();
                try {
                    URL url = new URL(mSchemaFileURL);
                    in = new BufferedReader(new InputStreamReader(url.openStream()));

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        xml.append(inputLine);
                        xml.append("\n");
                    }
                }
                catch (IOException e) {
                    throw new Spml2Exception("Bad URL? " + mSchemaFileURL, e);
                }
                finally {
                    if (in != null) {
                        try {
                            in.close();
                        }
                        catch (IOException e) {
                            // ignore this.
                        }
                    }
                }

                // create it from the xml schemaFile name and baseDir
                {
                    String baseDirString = (String) params.get(NVP_BASEDIR_TO_SAVE_FILES);
                    if (baseDirString != null)
                        mBaseDir = baseDirString;
                }

                File bdf = new File(mBaseDir);

                System.out.println("xml = \n" + xml);
                try {
                    mOS = new NVPObjectStore(bdf, xml.toString());
                    // TODO: for now, we support a single target.
                    mTargets = new Target[]{convertSchema(mOS.getObjectStoreDef())};
                }
                catch (NVPObjectStoreException e) {
                    throw new Spml2Exception(e.getMessage(), e);
                }
            }
        }
    }

    public String getUniqueName() {
        return "nvpose";
    }

    public Response execute(Request request) throws Spml2Exception {

        // find the method for the request and execute it.
        Response resp = null;
        try {
            Class argType = request.getClass();
            final String methodName = "execute";

            Method method =
                    NVPObjectStoreExecutor.class.getDeclaredMethod(methodName,
                                                                   new Class[]{argType});
            if (method != null) {

                Object responseObj;
                if (request.getExecutionMode() == ExecutionMode.SYNCHRONOUS) {
                    responseObj = method.invoke(this, new Object[]{request});
                    resp = (Response) responseObj;
                    resp.setRequestID(getOrCreateRequestId(request));
                }
                else {
                    return fail(new Response(),
                                ErrorCode.UNSUPPORTED_EXECUTION_MODE,
                                "This example does not support asynchronous requests.");
                }
            }
        }
        catch (NoSuchMethodException e) {
            return fail(resp, ErrorCode.UNSUPPORTED_OPERATION);
        }
        catch (IllegalAccessException e) {
            return fail(resp, ErrorCode.UNSUPPORTED_OPERATION);
        }
        catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t instanceof NVPObjectStoreException) {
                return fail(new Response(), ErrorCode.CUSTOM_ERROR, t.getMessage());
            }
            throw new Spml2Exception(t);// let the dispatcher handle this one.
        }

        return resp;
    }

    private String getOrCreateRequestId(Request request) {
        String rid = request.getRequestID();
        if (rid == null) {
            rid = "rid" + System.currentTimeMillis() + System.identityHashCode(request);
        }
        request.setRequestID(rid);
        return rid;
    }

    private Response fail(Response resp, ErrorCode err) {
        return fail(resp, err, (String[]) null);
    }

    private Response fail(Response resp, ErrorCode err, String message) {
        return fail(resp, err, new String[]{message});
    }

    private Response fail(Response resp, ErrorCode err, List messages) {
        String[] array = (String[]) messages.toArray(new String[messages.size()]);
        return fail(resp, err, array);
    }

    private Response fail(Response resp, ErrorCode err, String[] messages) {
        resp.setStatus(StatusCode.FAILURE);
        resp.setError(err);
        if (messages != null) {
            for (int k = 0; k < messages.length; k++) {
                String message = messages[k];
                resp.addErrorMessage(message);
            }
        }
        return resp;
    }

    /**
     * @param inObjectStoreDef This is the definition of our provider.
     * @return A Target.
     * @throws Spml2Exception if we see trouble, we bail with one of these.
     */
    private Target convertSchema(NVPObjectStoreDef inObjectStoreDef) throws Spml2Exception {

        DSMLSchema outSchema = new DSMLSchema();

        Set seen = new HashSet();
        NVPObjectDef[] defs = inObjectStoreDef.getNVPObjectDefs();
        for (int j = 0; j < defs.length; j++) {
            NVPObjectDef def = defs[j];
            ObjectClassDefinition ocDef = convertNVPObjectDef(def);
            outSchema.addObjectClassDefinition(ocDef);
            NVPDef[] nvpDefs = def.getNVPDefs();
            for (int k = 0; k < nvpDefs.length; k++) {
                NVPDef nvpDef = nvpDefs[k];
                if (!seen.contains(nvpDef)) {
                    outSchema.addAttributeDefinition(convertNVPDef(nvpDef));
                    seen.add(nvpDef);
                }
            }
        }

        Schema schema = new Schema();
        schema.addOpenContentElement(outSchema);

        String tid = inObjectStoreDef.getStoreName();
        Target t = new Target(new Schema[]{schema},
                              null,
                              tid,
                              (new DSMLProfileRegistrar()).getProfileURI());

        // now support all the schema entities
        for (int k = 0; k < defs.length; k++) {
            NVPObjectDef def = defs[k];
            SchemaEntityRef ref = new SchemaEntityRef(tid, def.getClassName());
            schema.addSupportedSchemaEntity(ref);
        }

        return t;
    }

    private AttributeDefinition convertNVPDef(NVPDef nvpDef) {
        return new AttributeDefinition(nvpDef.getName(),
                                       null, null,
                                       nvpDef.isMultiValued());
    }

    private ObjectClassDefinition convertNVPObjectDef(NVPObjectDef def) {

        AttributeDefinitionReferences attrRefs = new AttributeDefinitionReferences();
        NVPDef[] defs = def.getNVPDefs();
        for (int k = 0; k < defs.length; k++) {
            NVPDef nvpDef = defs[k];
            AttributeDefinitionReference ref = new AttributeDefinitionReference();
            ref.setName(nvpDef.getName());
            attrRefs.addAttributeDefinitionReference(ref);
        }

        ObjectClassDefinitionReferences supRefs = new ObjectClassDefinitionReferences();
        NVPObjectDefReference[] refs = def.getParents();
        for (int k = 0; k < refs.length; k++) {
            NVPObjectDefReference ref = refs[k];
            ObjectClassDefinitionReference ocRef = new ObjectClassDefinitionReference(ref.getNameOfNVPObjectDef());
            supRefs.addObjectClassDefinitionReference(ocRef);
        }

        if (supRefs.getObjectClassDefinitionReferences().length == 0) {
            supRefs = null;
        }

        return new ObjectClassDefinition(def.getClassName(), attrRefs, supRefs, null);
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    //  This is where the methods that handle the requests take over.
    //
    ///////////////////////////////////////////////////////////////////////////
    protected Response execute(ListTargetsRequest request) throws Spml2Exception {

        ListTargetsResponse response = new ListTargetsResponse(null,
                                                               StatusCode.SUCCESS,
                                                               getOrCreateRequestId(request),
                                                               null,
                                                               null);

        if (!request.isValid()) {
            return fail(response, ErrorCode.MALFORMED_REQUEST);
        }

        // This is not likely to catch much because we haven't
        // implemented much in terms of isValid on the requests.
        if (!request.isValid()) {
            return fail(response, ErrorCode.MALFORMED_REQUEST);
        }

        // We can do additional checking here... like balk on an ASYNCHRONOUS listTargets
        //   Once we allow ASYNC, this will then getByUid caught here.
        if (request.getExecutionMode() == ExecutionMode.ASYNCHRONOUS) {
            return fail(response, ErrorCode.UNSUPPORTED_EXECUTION_MODE);
        }

        // handle the profile - if they ask for something other than DSML complain
        // normally, we might have to wait until we see what targets we have and
        // then see if we have any matching the profile that was requested.  profile
        // can be null in this case and be okay, because we just have one target.
        URI uri = request.getProfile();
        if (uri != null && !(new DSMLProfileRegistrar()).getProfileURI().equals(uri)) {
            return fail(response, ErrorCode.UNSUPPORTED_PROFILE);
        }

        // just in case - although init should have covered this.
        if (mTargets == null) {
            mTargets = new Target[]{convertSchema(mOS.getObjectStoreDef())};
        }

        return new ListTargetsResponse(null, StatusCode.SUCCESS, null, null, mTargets);
    }

    private boolean validTarget(String targetID) {

        if (targetID == null) {
            return (mTargets.length == 1);
        }

        for (int k = 0; k < mTargets.length; k++) {
            Target target = mTargets[k];
            if (targetID.equals(target.getTargetID())) {
                return true;
            }
        }
        return false;
    }

    // add
    protected Response execute(AddRequest add) throws Spml2Exception {

        AddResponse response = new AddResponse(null,
                                               StatusCode.SUCCESS,
                                               getOrCreateRequestId(add),
                                               null, null);

        if (!add.isValid()) {
            return fail(response, ErrorCode.MALFORMED_REQUEST);
        }

        // it is a valid target?
        if (!validTarget(add.getTargetId())) {
            return fail(response, ErrorCode.NO_SUCH_IDENTIFIER);
        }

        // we need to find the "objectclass" from the add so we
        // can find the def for the object.
        Extensible data = add.getData();

        List attrs = data.getOpenContentElements(DSMLAttr.class);
        String oc = getObjectClassFromAttrs(attrs);

        if (oc == null) {
            return fail(response,
                        ErrorCode.MALFORMED_REQUEST,
                        "There is no objectclass defined in the request.");
        }

        NVPObject no;
        try {
            no = createNVPObjectFromAttrs(oc, attrs);

            // was there an id in the attributes?
            PSOIdentifier psoID = add.getPsoID();

            // if we have one of these; we MUST use it.
            if (psoID != null) {
                no.setUid(psoID.getID());
            }
            else {
                psoID = new PSOIdentifier(no.getUid(), null, mOS.getObjectStoreDef().getStoreName());
            }

            if (!mOS.add(no)) {
                return fail(response,
                            ErrorCode.ALREADY_EXISTS,
                            "An object with the id, " + psoID.getID() + ", already exists.");
            }

            mOS.commit();

            PSO pso = createPSOFromNVPObject(psoID, add.getReturnData(), no);
            response.setPso(pso);
            return response;
        }
        catch (NVPObjectStoreException e) {
            return fail(response, ErrorCode.CUSTOM_ERROR, e.getMessage());
        }
    }

    private NVPObject createNVPObjectFromAttrs(String oc, List attrs) throws NVPObjectStoreException {
        NVPObject no;
        no = mOS.createNVPObject(oc);

        for (int k = 0; k < attrs.size(); k++) {
            DSMLAttr dsmlAttr = (DSMLAttr) attrs.get(k);
            String name = dsmlAttr.getName();
            if (!name.equals(DSMLAttr.RESERVED_TYPE_ATTR_NAME)) {
                DSMLValue[] values = dsmlAttr.getValues();
                if (values.length == 1) {
                    no.put(name, values[0].getValue());
                }
                else {
                    List list = new ArrayList();
                    for (int j = 0; j < values.length; j++) {
                        DSMLValue value = values[j];
                        list.add(value.toString());
                    }
                    no.put(name, list);
                }
            }
        }
        return no;
    }

    private String getObjectClassFromAttrs(List attrs) {
        String oc = null;
        for (int k = 0; k < attrs.size(); k++) {
            DSMLAttr dsmlAttr = (DSMLAttr) attrs.get(k);
            if (dsmlAttr.getName().equals(DSMLAttr.RESERVED_TYPE_ATTR_NAME)) {
                oc = dsmlAttr.getValues()[0].getValue();
                break;
            }
        }
        return oc;
    }

    // lookup
    protected Response execute(LookupRequest lookup) throws Spml2Exception {

        LookupResponse response = new LookupResponse(null,
                                                     StatusCode.SUCCESS,
                                                     getOrCreateRequestId(lookup),
                                                     null, null);

        PSOIdentifier psoID = lookup.getPsoID();
        if (!lookup.isValid() || psoID == null) {
            return fail(response, ErrorCode.MALFORMED_REQUEST);
        }

        // it is a valid target or id?
        if (!validTarget(psoID.getTargetID())) {
            return fail(response, ErrorCode.NO_SUCH_IDENTIFIER);
        }

        NVPObject no;
        try {
            // put the objectclass in the id?  NO - the store needs to give ids; not the provider.
            // but then how do we link them?
            no = mOS.get(psoID.getID());

            if (no == null) {
                return fail(response,
                            ErrorCode.NO_SUCH_IDENTIFIER,
                            "There's no object with the id of " + psoID.getID());
            }

            ReturnData rd = lookup.getReturnData();
            PSO pso = createPSOFromNVPObject(psoID, rd, no);
            response.setPso(pso);
            return response;
        }
        catch (NVPObjectStoreException e) {
            return fail(response, ErrorCode.CUSTOM_ERROR, e.getMessage());
        }
    }

    private PSO createPSOFromNVPObject(PSOIdentifier psoID, ReturnData rd, NVPObject no) throws
            DSMLProfileException {

        PSO pso = new PSO();
        pso.setPsoID(psoID);

        // What shall we send back?

        // We're catching DATA and EVERYTHING in one test here.
        //   and I'm reading from the NVPObject cause it may have applied
        //   changes during the write, like say a 'modified' field...
        //   of course, that's just academic.
        if (rd != ReturnData.IDENTIFIER) {
            // put the object into the results.
            Map attrMap = no.asMap();
            Iterator iter = attrMap.entrySet().iterator();

            // replace whatever this one is with the name "objectclass"
            String typeAttrName = mOS.getObjectStoreDef().getNameOfTypeNVP();

            while (iter.hasNext()) {
                Map.Entry e = (Map.Entry) iter.next();
                Object key = e.getKey();
                Object val = e.getValue();

                // we should walk the types and do the right thing?
                DSMLValue[] values;
                if (val instanceof String) {
                    values = new DSMLValue[]{new DSMLValue(val.toString())};
                }
                else if (val instanceof List) {
                    List list = (List) val;
                    values = new DSMLValue[list.size()];
                    for (int k = 0; k < list.size(); k++) {
                        String s = list.get(k).toString();
                        values[k] = new DSMLValue(s);
                    }
                }
                else {
                    values = null;
                }

                if (values != null && values.length != 0) {
                    String keyStr = key.toString();
                    if (keyStr.equals(typeAttrName)) {
                        keyStr = DSMLAttr.RESERVED_TYPE_ATTR_NAME;
                    }
                    pso.addOpenContentElement(new DSMLAttr(keyStr, values));
                }
            }
        }

        return pso;
    }

    // modify
    protected Response execute(ModifyRequest modify) throws Spml2Exception {

        ModifyResponse response = new ModifyResponse(null,
                                                     StatusCode.SUCCESS,
                                                     getOrCreateRequestId(modify),
                                                     null, null);

        PSOIdentifier psoID = modify.getPsoID();

        if (!modify.isValid() || psoID == null) {
            return fail(response, ErrorCode.MALFORMED_REQUEST);
        }

        // it is a valid target?
        String targetId = psoID.getTargetID();
        if (!validTarget(targetId)) {
            return fail(response, ErrorCode.NO_SUCH_IDENTIFIER);
        }

        try {
            // find the object
            NVPObject object = mOS.get(psoID.getID());
            if (object == null) {
                return fail(response,
                            ErrorCode.NO_SUCH_IDENTIFIER,
                            "There is no object with the id " + psoID + ".");
            }

            Modification[] mods = modify.getModifications();

            for (int i = 0; i < mods.length; i++) {
                Modification mod = mods[i];

                OpenContentElement[] modoces = mod.getOpenContentElements();
                if (modoces != null && modoces.length > 0) {
                    OpenContentElement modoce = modoces[0];
                    if (modoce instanceof DSMLModification) {
                        DSMLModification realModification = (DSMLModification) modoce;
                        ModificationMode mode = realModification.getOperation();
                        String name = realModification.getName();
                        if (mode == null
                            || mode == ModificationMode.ADD
                            || mode == ModificationMode.REPLACE) {
                            DSMLValue[] values = realModification.getValues();
                            List newValues = new ArrayList();
                            for (int j = 0; j < values.length; j++) {
                                DSMLValue value = values[j];
                                String vString = value.getValue();
                                newValues.add(vString);
                            }

                            // we need to know if this is supposed to be
                            // an MVA or not... and the Target schema will tell
                            // us.
                            if (!isMultiValued(name, targetId)) {
                                object.put(name, newValues.get(0).toString());
                            }
                            else {
                                object.put(name, newValues);
                            }
                        }
                        else if (mode == ModificationMode.DELETE) {
                            object.remove(name);
                        }
                    }
                }

                List errors = object.validate();
                if (errors != null) {
                    return fail(response, ErrorCode.MALFORMED_REQUEST, errors);
                }
            }

            if (!mOS.modify(object)) {
                return fail(response,
                            ErrorCode.INVALID_IDENTIFIER,
                            "No such id; id = " + psoID.getID() + ".");
            }

            mOS.commit();

            return response;
        }
        catch (NVPObjectStoreException e) {
            return fail(response, ErrorCode.CUSTOM_ERROR, e.getMessage());
        }
    }

    // delete
    protected Response execute(DeleteRequest delete) throws Spml2Exception {

        DeleteResponse response = new DeleteResponse(null,
                                                     StatusCode.SUCCESS,
                                                     getOrCreateRequestId(delete),
                                                     null);

        PSOIdentifier psoID = delete.getPsoID();

        if (!delete.isValid() || psoID == null) {
            return fail(response, ErrorCode.MALFORMED_REQUEST);
        }

        // it is a valid target?
        if (!validTarget(psoID.getTargetID())) {
            return fail(response, ErrorCode.NO_SUCH_IDENTIFIER);
        }

        try {
            boolean res = mOS.delete(psoID.getID());
            if (res) {
                mOS.commit();
            }
            else {
                return fail(response,
                            ErrorCode.INVALID_IDENTIFIER,
                            "No such id; id = " + psoID.getID() + ".");
            }
            return response;
        }
        catch (NVPObjectStoreException e) {
            return fail(response, ErrorCode.CUSTOM_ERROR, e.getMessage());
        }
    }
}
