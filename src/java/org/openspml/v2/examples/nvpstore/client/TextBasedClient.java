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
/*
 * Author(s): kent.spaulding@sun.com
 * Date: Sep 13, 2006
 * Time: 4:22:17 PM
 */

package org.openspml.v2.examples.nvpstore.client;

import org.openspml.v2.client.Spml2Client;
import org.openspml.v2.examples.nvpstore.psp.NVPObjectStoreExecutor;
import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.msg.spml.AddRequest;
import org.openspml.v2.msg.spml.AddResponse;
import org.openspml.v2.msg.spml.DeleteRequest;
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
import org.openspml.v2.msg.spml.Target;
import org.openspml.v2.profiles.DSMLProfileRegistrar;
import org.openspml.v2.profiles.dsml.DSMLAttr;
import org.openspml.v2.profiles.dsml.DSMLModification;
import org.openspml.v2.profiles.dsml.DSMLProfileException;
import org.openspml.v2.profiles.dsml.DSMLValue;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.Spml2ExceptionWithResponse;
import org.openspml.v2.util.xml.ObjectFactory;
import org.openspml.v2.util.xml.ReflectiveXMLMarshaller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class TextBasedClient {

    public static final String DEFAULT_URL = "http://localhost:8080/nvp/spml2";

    /**
     * This class just allows this to be tested without putting the executor in
     * a servlet.  That's a lot easier.
     */
    private static class LocalSpmlClient extends Spml2Client {

        private NVPObjectStoreExecutor mExecutor = null;

        public LocalSpmlClient(String schemaUrl, String baseDir) throws Spml2Exception {
            super(schemaUrl);
            mExecutor = new NVPObjectStoreExecutor(schemaUrl, baseDir);
        }

        public Response send(Request req) throws Spml2Exception {
            // just call the executor... we are NOT testing the Marshalling with this client
            Response resp;
            try {
                resp = mExecutor.execute(req);
            }
            catch (Spml2ExceptionWithResponse e) {
                System.out.println("There were errors in the response!");
                resp = e.getResponse();
            }
            return resp;
        }
    }

    /**
     * Turn this off if you don't want to see all the XML on the console.
     */
    private static boolean mTrace = true;

    private static String mTargetName = null;
    private static XMLMarshaller mMarshaller;

    private final URI mURI;
    private final Spml2Client mClient;

    private static void printXml(Marshallable m) throws Spml2Exception {
        String xml = mMarshaller.marshall(m);
        System.out.println(xml);
    }

    private Modification newModWithDSMLMod(String name,
                                           String value,
                                           ModificationMode type) throws DSMLProfileException {

        DSMLModification wrappee = new DSMLModification(name, value, type);
        Modification wrapper = new Modification();
        wrapper.addOpenContentElement(wrappee);
        return wrapper;
    }

    private Modification newModWithDSMLMod(String name,
                                           String[] values,
                                           ModificationMode type) throws DSMLProfileException {

        DSMLValue[] dsmlValues = new DSMLValue[values.length];
        for (int k = 0; k < values.length; k++) {
            dsmlValues[k] = new DSMLValue(values[k]);
        }

        DSMLModification wrappee = new DSMLModification(name, dsmlValues, type);
        Modification wrapper = new Modification();
        wrapper.addOpenContentElement(wrappee);
        return wrapper;
    }

    private Response send(Request req)
            throws Spml2Exception {
        if (mTrace) printXml(req);
        Response resp = mClient.send(req);
        if (mTrace) printXml(resp);
        return resp;
    }

    private ModifyResponse modifyPhoneInfo(PSOIdentifier psoId, String[] newValues, String newFirstName)
            throws Spml2Exception {

        Modification[] modifications = {
                newModWithDSMLMod("phoneNumbers", newValues, ModificationMode.REPLACE),
                newModWithDSMLMod("firstName", newFirstName, ModificationMode.REPLACE),
        };

        ModifyRequest mreq = new ModifyRequest("rid0", // String requestId,
                                               ExecutionMode.SYNCHRONOUS, // ExecutionMode executionMode,
                                               psoId, // PSOIdentifier psoID
                                               modifications, // Modification[] modifications
                                               ReturnData.EVERYTHING// ReturnData returnData
        );

        return (ModifyResponse) send(mreq);
    }

    private Response delete(PSOIdentifier psoIdentifier) throws Spml2Exception {
        DeleteRequest del = new DeleteRequest(null,
                                              ExecutionMode.SYNCHRONOUS,
                                              psoIdentifier,
                                              false);
        return send(del);
    }

    private Response doListTargets() throws Spml2Exception {
        ListTargetsRequest ltr = new ListTargetsRequest("firstOne",
                                                        ExecutionMode.SYNCHRONOUS,
                                                        mURI);
        return send(ltr);
    }

    private AddResponse doAddPhoneInfo(String cid,
                                       String first,
                                       String last,
                                       String[] nums,
                                       String[] types) throws Spml2Exception {

        AddRequest req = new AddRequest();
        req.setExecutionMode(ExecutionMode.SYNCHRONOUS);
        req.setTargetId(mTargetName);

        Extensible data = new Extensible();

        // set the objectclass
        DSMLAttr attr = new DSMLAttr(DSMLAttr.RESERVED_TYPE_ATTR_NAME, "phoneInfo");
        data.addOpenContentElement(attr);

        attr = new DSMLAttr("firstName", first);
        data.addOpenContentElement(attr);

        attr = new DSMLAttr("lastName", last);
        data.addOpenContentElement(attr);

        // set the nums
        attr = new DSMLAttr("phoneNumbers", stringArrayToValues(nums));
        data.addOpenContentElement(attr);

        // types
        attr = new DSMLAttr("phoneNumberTypes", stringArrayToValues(types));
        data.addOpenContentElement(attr);

        // set the cid
        attr = new DSMLAttr("contactId", cid);
        data.addOpenContentElement(attr);

        req.setData(data);

        Response resp = send(req);
        return (AddResponse) resp;
    }

    private AddResponse doAddEmailInfo(String cid,
                                       String[] addresses,
                                       String[] types) throws Spml2Exception {

        AddRequest req = new AddRequest();
        req.setExecutionMode(ExecutionMode.SYNCHRONOUS);
        req.setTargetId(mTargetName);

        Extensible data = new Extensible();

        // set the objectclass
        DSMLAttr attr = new DSMLAttr(DSMLAttr.RESERVED_TYPE_ATTR_NAME, "emailInfo");
        data.addOpenContentElement(attr);

        // set the id
        data.addOpenContentElement(new DSMLAttr("contactId", cid));

        // set the email addresses
        attr = new DSMLAttr("emailAddresses", stringArrayToValues(addresses));
        data.addOpenContentElement(attr);

        attr = new DSMLAttr("emailAddressTypes", stringArrayToValues(types));
        data.addOpenContentElement(attr);

        req.setData(data);

        Response resp = send(req);
        return (AddResponse) resp;
    }

    private DSMLValue[] stringArrayToValues(String[] vals) {
        DSMLValue[] values = new DSMLValue[vals.length];
        for (int k = 0; k < vals.length; k++) {
            values[k] = new DSMLValue(vals[k]);
        }
        return values;
    }

    private Response lookup(PSOIdentifier psoId) throws Spml2Exception {

        LookupRequest lr = new LookupRequest(null,
                                             ExecutionMode.SYNCHRONOUS,
                                             psoId,
                                             ReturnData.EVERYTHING);
        return send(lr);
    }

    private TextBasedClient(Spml2Client client) throws Spml2Exception {
        ObjectFactory.ProfileRegistrar dsmlReg = new DSMLProfileRegistrar();
        ObjectFactory.getInstance().register(dsmlReg);
        mURI = dsmlReg.getProfileURI();
        mClient = client;
        mMarshaller = new ReflectiveXMLMarshaller();
    }

    /**
     * This will run through a series of tests.  You can point it a server and execute the
     * SOAP functionality, or you can tell it to execute locally, running everything in
     * process (which does not test the marshalling.)
     * <p/>
     * If there are 0 args, we use a default URL of DEFAULT_URL and go remote.
     * If there is 1 arg, that arg is the URL.
     * If there are 2 args, the first is a schema file URL (could be on a server) and
     * the second is a filename for the baseDir to use for storing files.
     *
     * @throws Spml2Exception IF there's an error, we throw one of these.
     */
    public void execute() throws Spml2Exception {

        Response resp = doListTargets();

        if (resp instanceof ListTargetsResponse) {
            ListTargetsResponse ltresp = (ListTargetsResponse) resp;
            Target[] targets = ltresp.getTargets();
            if (targets != null && targets.length > 0)
                mTargetName = targets[0].getTargetID();
        }

        // Let's add contactId c001, phone email and phone info.
        String cid = "c001";

        String[] values = new String[]{"555-555-0001", "555-555-0002"};
        String[] types = new String[]{"mobile", "home"};
        AddResponse ar = doAddPhoneInfo(cid, "Kathy", "Dee", values, types);

        PSO pso = ar.getPso();

        List psoIds = new ArrayList();
        psoIds.add(pso.getPsoID());

        values = new String[]{"kd@example.com", "kd@openspml.org"};
        types = new String[]{"work", "other"};
        ar = doAddEmailInfo(cid, values, types);

        pso = ar.getPso();
        psoIds.add(pso.getPsoID());

        // let's do a couple of lookups.
        // can we fetch then by the psoIds?
        for (int k = 0; k < psoIds.size(); k++) {
            PSOIdentifier psoIdentifier = (PSOIdentifier) psoIds.get(k);
            lookup(psoIdentifier);
        }

        // modify the phone number.
        String[] newValues = new String[]{"555-555-0003", "555-555-0002"};
        PSOIdentifier phoneInfoPsoId = (PSOIdentifier) psoIds.get(0);
        modifyPhoneInfo(phoneInfoPsoId,
                        newValues,
                        "Cathy");

        lookup(phoneInfoPsoId);

        // delete the email info (psoId[1])
        PSOIdentifier emailId = (PSOIdentifier) psoIds.get(1);
        delete(emailId);

        System.out.println("Look for the email id - we should not find it.");
        try {
            lookup(emailId);
        }
        catch (Spml2ExceptionWithResponse e) {
            LookupResponse res = (LookupResponse) e.getResponse();
            System.out.println("Got the response, error should be noSuchIdentifier: " + res.getError());
        }

        // let's add another email
        values = new String[]{"kd2@example.com", "kd2@openspml.org"};
        types = new String[]{"work", "other"};
        ar = doAddEmailInfo(cid, values, types);

        // did we get it?
        lookup(ar.getPso().getPsoID());

        // finito
    }

    private static void print_usage() {
        System.out.println("This program will run a series of tests against an example server.  It can");
        System.out.println("be run either against a server (just give a servlet URL) or run locally.");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  If there are 0 args, we use a default URL of " + DEFAULT_URL + " and go remote.");
        System.out.println("  If there is 1 arg, that arg is the URL and we go remote.");
        System.out.println("  If there are 2 args, the first is a schema file URL (could be on a server) and");
        System.out.println("    the second is a filename for the baseDir to use for storing csv files.");
    }

    /**
     * Get the ball rolling.
     *
     * @param args see the execute method...
     */
    public static void main(String[] args) {

        try {
            Spml2Client client = null;
            boolean remote = false;
            if (args.length != 0) {
                if (args.length == 1) {
                    if (args[0].equals("--help")) {
                        print_usage();
                        return;
                    }
                    client = new Spml2Client(args[0]);
                    remote = true;
                }
                else if (args.length == 2) {
                    client = new LocalSpmlClient(args[0], args[1]);
                }
            }
            else {
                client = new Spml2Client(DEFAULT_URL);
                remote = true;
            }

            if (remote)
                mTrace = false;// for remote, we'll rely on the trace in the server.

            TextBasedClient test = new TextBasedClient(client);
            test.execute();
        }
        catch (Spml2ExceptionWithResponse e) {
            try {
                System.out.println(e.getResponse().toXML(new ReflectiveXMLMarshaller()));
            }
            catch (Spml2Exception e1) {
                e1.printStackTrace(System.err);
            }
        }
        catch (Spml2Exception e) {
            System.out.println("Error: " + e.getMessage());
            Throwable c = e.getCause();
            if (c != null) {
                System.out.println("   Cause: " + c.getMessage());
            }
            print_usage();
        }
        catch (Throwable t) {
            print_usage();
        }
    }

}
