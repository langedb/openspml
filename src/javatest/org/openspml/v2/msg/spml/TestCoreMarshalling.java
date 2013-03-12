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
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.openspml.v2.msg.MarshallerTestCase;
import org.openspml.v2.msg.OCEtoMarshallableAdapter;
import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.msg.spmlref.ReferenceDefinition;
import org.openspml.v2.profiles.DSMLProfileRegistrar;
import org.openspml.v2.profiles.spmldsml.DSMLSchema;
import org.openspml.v2.util.xml.ObjectFactory;
import org.openspml.v2.util.xml.OperationalNameValuePair;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 9, 2006
 */
public class TestCoreMarshalling extends MarshallerTestCase {

    private static final String code_id = "$Id: TestCoreMarshalling.java,v 1.13 2006/08/30 18:02:59 kas Exp $";

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public TestCoreMarshalling(String name) {
        super(name);
    }

    /**
     * Creates the TestSuite for the directories in the package.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TestCoreMarshalling.class);
        return suite;
    }

    public void testMarshallSelection() {
        try {

            Extensible data = getOpenContentTestData(2, 2);
            Selection sel = new Selection(getNamespacePrefixMappings(),
                                          "this/is/the/path",
                                          "bogus:uri");

            sel.setOpenContent(data.getOpenContentAttrs(),
                               data.getOpenContentElements());
            marshallUnmarshall(sel);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testMarshallSelectionWithOperationalAttr() {
        try {

            OpenContentElement oa = new OperationalNameValuePair("sessionId",
                                                                 "DFDFKFJKDFEDKFJDKFJCKSDDsdsDJDKCFKER");

            marshallUnmarshall((OperationalNameValuePair) oa);

            Selection sel = new Selection(getNamespacePrefixMappings(),
                                          "this/is/the/path",
                                          "bogus:uri");

            sel.setOpenContent(null,
                               new OpenContentElement[] { oa } );

            marshallUnmarshall(sel);

            System.out.println("   TESTING THE FIND Operational Attr");
            oa = sel.findOperationalNVPByName("sessionId");
            assertNotNull(oa);

            System.out.println("oa.toXML() = \n" + oa.toXML());

            assertFalse(oa instanceof OCEtoMarshallableAdapter);

        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testMarshallAddRequest() {
        try {
            Extensible data = getOpenContentTestData();
            PSOIdentifier psoId = new PSOIdentifier("psoId", null, "psoTarget");
            PSOIdentifier containerId = new PSOIdentifier("psoContainerId", null, "psoTarget");

            CapabilityData[] cda = new CapabilityData[]{
                new CapabilityData(true, makeURI("foo:1")),
                new CapabilityData(true, makeURI("foo:2")),
                new CapabilityData(true, makeURI("foo:3")),
                new CapabilityData(true, makeURI("foo:4")),
                new CapabilityData(true, makeURI("foo:5")),
            };

            AddRequest ar = new AddRequest("requestId",
                                           ExecutionMode.SYNCHRONOUS,
                                           psoId,
                                           containerId,
                                           data,
                                           cda,
                                           "targetId",
                                           ReturnData.DATA);

            ar.addOpenContentAttr("arFoo", "arFooValue");
            ar.addOpenContentElement(new OpenContent("arOne"));
            ar.addOpenContentElement(new OpenContent("arTwo"));

            marshallUnmarshall(ar);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testMarshallAddResponse() {
        try {

            Extensible data = getOpenContentTestData();

            PSO pso = createPSO();

            String[] errorMessages = {
                "error one",
                "error two",
                "error three",
                "error four",
            };

            AddResponse ar = new AddResponse(errorMessages,
                                             StatusCode.SUCCESS,
                                             "requestID",
                                             ErrorCode.CUSTOM_ERROR,
                                             pso);

            ar.setOpenContent(data.getOpenContentAttrs(),
                              data.getOpenContentElements());

            marshallUnmarshall(ar);

            ar = new AddResponse();
            marshallUnmarshall(ar);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testModifyRequest() {
        try {
            System.out.println("TestCoreMarshalling.testModifyRequest not implemented");
            ModifyRequest mr = null;
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testModifyResponse() {
        try {
            System.out.println("TestCoreMarshalling.testModifyResponse not implemented");
            ModifyResponse o = null;

        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testDeleteRequest() {
        try {
            PSOIdentifier psoId = new PSOIdentifier("dummy_psoId", null, "dummy_psoTargetID");
            DeleteRequest o = new DeleteRequest("dummy_requestID", ExecutionMode.SYNCHRONOUS, psoId, false);
            marshallUnmarshall(o);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testDeleteResponse() {
        try {

            Extensible data = getOpenContentTestData();
            String[] errorMessages = {
                "error one",
                "error two",
                "error three",
                "error four",
            };

            DeleteResponse o = new DeleteResponse(errorMessages,
                                                  StatusCode.SUCCESS,
                                                  "requestID",
                                                  ErrorCode.CUSTOM_ERROR);

            o.setOpenContent(data.getOpenContentAttrs(),
                             data.getOpenContentElements());

            marshallUnmarshall(o);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testLookupRequest() {
        try {
            System.out.println("TestCoreMarshalling.testLookupRequest not implemented.");
            LookupRequest o = null;
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testLookupResponse() {
        try {
            System.out.println("TestCoreMarshalling.testLookupResponse not implemented.");
            LookupResponse o = null;
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testListTargetsRequestNoOC() {

        try {
            ListTargetsRequest o = new ListTargetsRequest("requestId",
                                                          ExecutionMode.SYNCHRONOUS,
                                                          makeURI("profile"));

            marshallUnmarshall(o);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testListTargetsRequest() {

        try {
            Extensible data = getOpenContentTestData();
            ListTargetsRequest o = new ListTargetsRequest("requestId",
                                                          ExecutionMode.SYNCHRONOUS,
                                                          makeURI("profile"));

            o.setOpenContent(data.getOpenContentAttrs(),
                             data.getOpenContentElements());

            marshallUnmarshall(o);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    protected Target[] getTargets() {
        Target[] targets = {
            new Target(getSchemaTypes(),
                       getCapabilitiesList(),
                       "dummy_targetID",
                       makeURI("uri:profile:dummy"))
        };
        return targets;
    }

    private Schema[] getSchemaTypes() {
        return new Schema[]{
            new Schema(getSchemaEntityRefs(), makeURI("uri:dummy:entity:ref")),
        };
    }

    private SchemaEntityRef[] getSchemaEntityRefs() {
        return null;
    }

    private CapabilitiesList getCapabilitiesList() {
        return null;
    }

    public void testListTargetsResponse() {
        try {
            ListTargetsResponse o = new ListTargetsResponse(getErrorMessages(3),
                                                            StatusCode.PENDING,
                                                            "dummy_RequestID",
                                                            null,
                                                            getTargets());
            marshallUnmarshall(o);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private static final String LTRESPONSE_XML =
            "<listTargetsResponse status=\"success\" xmlns=\"urn:oasis:names:tc:SPML:2:0\" xmlns:spmldsml=\"urn:oasis:names:tc:SPML:2:0:DSML\">\n" +
            "    <target targetID=\"Sun-Interop-DSML\">\n" +
            "        <schema>\n" +
            "            <spmldsml:schema xmlns:spmldsml=\"urn:oasis:names:tc:SPML:2:0:DSML\">\n" +
            "                <spmldsml:attributeDefinition name=\"cn\" description=\"Common Name\"/>\n" +
            "                <spmldsml:attributeDefinition name=\"uid\" description=\"User ID\"/>\n" +
            "                <spmldsml:attributeDefinition name=\"email\" description=\"Email Address\"/>\n" +
            "                <spmldsml:attributeDefinition name=\"description\" description=\"Description\"/>\n" +
            "                <spmldsml:objectClassDefinition name=\"account\">\n" +
            "                    <spmldsml:memberAttributes>\n" +
            "                        <spmldsml:attributeDefinitionReference name=\"cn\" required=\"true\"/>\n" +
            "                        <spmldsml:attributeDefinitionReference name=\"uid\"/>\n" +
            "                        <spmldsml:attributeDefinitionReference name=\"email\"/>\n" +
            "                    </spmldsml:memberAttributes>\n" +
            "                </spmldsml:objectClassDefinition>\n" +
            "                <spmldsml:objectClassDefinition name=\"role\">\n" +
            "                    <spmldsml:memberAttributes>\n" +
            "                        <spmldsml:attributeDefinitionReference name=\"cn\" required=\"true\"/>\n" +
            "                        <spmldsml:attributeDefinitionReference name=\"description\"/>\n" +
            "                    </spmldsml:memberAttributes>\n" +
            "                </spmldsml:objectClassDefinition>\n" +
            "            </spmldsml:schema>\n" +
            "            <supportedSchemaEntity entityName=\"account\"/>\n" +
            "            <supportedSchemaEntity entityName=\"role\"/>\n" +
            "        </schema>\n" +
            "        <capabilities>\n" +
            "            <capability namespaceURI=\"urn:oasis:names:tc:SPML:2:0:password\"/>\n" +
            "            <capability namespaceURI=\"urn:oasis:names:tc:SPML:2:0:reference\">\n" +
            "                <appliesTo entityName=\"account\"/>\n" +
            "                <spmlref:referenceDefinition typeOfReference=\"roles\" xmlns:spmlref=\"urn:oasis:names:tc:SPML:2:0:reference\">\n" +
            "                    <spmlref:schemaEntity entityName=\"account\"/>\n" +
            "                    <spmlref:canReferTo entityName=\"role\" targetID=\"Sun-Interop-DSML\"/>\n" +
            "                </spmlref:referenceDefinition>\n" +
            "            </capability>\n" +
            "        </capabilities>\n" +
            "    </target>\n" +
            "</listTargetsResponse>";

    public void testListTargetResponseFromXml() {
        try {

            ObjectFactory.getInstance().register(new DSMLProfileRegistrar());

            System.out.println(LTRESPONSE_XML);
            ListTargetsResponse ltr =
                    (ListTargetsResponse) mUnmarshaller.unmarshall(LTRESPONSE_XML);

            // Is the OpenContent what we expect?
            Target[] targets = ltr.getTargets();
            assertTrue(targets.length == 1);
            Target target = targets[0];
            CapabilitiesList cl = target.getCapabilities();
            assertNotNull(cl);
            Capability[] cs = cl.getCapabilities();
            assertTrue(cs.length == 2);

            // Did we get the wrappers?
            OpenContentElement[] oces = target.getSchemas()[0].getOpenContentElements();
            assertTrue(oces.length == 1);
            assertTrue(oces[0] instanceof OCEtoMarshallableAdapter);
            OCEtoMarshallableAdapter schemaAdapter = (OCEtoMarshallableAdapter) oces[0];
            assertTrue(schemaAdapter.getMarshallable() instanceof DSMLSchema);
            DSMLSchema schema = (DSMLSchema) schemaAdapter.getMarshallable();
            assertTrue(schema.getAttributeDefinitions().length == 4);
            assertTrue(schema.getObjectClassDefinitions().length == 2);
            assertTrue(schema.getOpenContentElements().length == 0);

            Capability ref = cs[1];
            assertTrue(ref.getNamespaceURI().toString().equals("urn:oasis:names:tc:SPML:2:0:reference"));
            oces = ref.getOpenContentElements();

            assertTrue(oces.length == 1);
            assertTrue(oces[0].getClass().equals(OCEtoMarshallableAdapter.class));

            OCEtoMarshallableAdapter adapter = (OCEtoMarshallableAdapter) oces[0];
            assertTrue(adapter.getMarshallable() instanceof ReferenceDefinition);

            String xml = ltr.toXML(mMarshaller);
            System.out.println(xml);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private static final String DSML_SCHEMA_XML =
            "<spmldsml:schema xmlns:spmldsml=\"urn:oasis:names:tc:SPML:2:0:DSML\">\n" +
            "    <spmldsml:attributeDefinition name=\"cn\" description=\"Common Name\"/>\n" +
            "    <spmldsml:attributeDefinition name=\"uid\" description=\"User ID\"/>\n" +
            "    <spmldsml:attributeDefinition name=\"email\" description=\"Email Address\"/>\n" +
            "    <spmldsml:attributeDefinition name=\"description\" description=\"Description\"/>\n" +
            "    <spmldsml:objectClassDefinition name=\"account\">\n" +
            "        <spmldsml:memberAttributes>\n" +
            "            <spmldsml:attributeDefinitionReference name=\"cn\" required=\"true\"/>\n" +
            "            <spmldsml:attributeDefinitionReference name=\"uid\"/>\n" +
            "            <spmldsml:attributeDefinitionReference name=\"email\"/>\n" +
            "        </spmldsml:memberAttributes>\n" +
            "    </spmldsml:objectClassDefinition>\n" +
            "    <spmldsml:objectClassDefinition name=\"role\">\n" +
            "        <spmldsml:memberAttributes>\n" +
            "            <spmldsml:attributeDefinitionReference name=\"cn\" required=\"true\"/>\n" +
            "            <spmldsml:attributeDefinitionReference name=\"description\"/>\n" +
            "        </spmldsml:memberAttributes>\n" +
            "    </spmldsml:objectClassDefinition>\n" +
            "</spmldsml:schema>\n";

    public void testDSMLSchemaFromXml() {
        try {

            System.out.println(DSML_SCHEMA_XML);
            DSMLSchema s = (DSMLSchema) mUnmarshaller.unmarshall(DSML_SCHEMA_XML);

            assertTrue(s.getAttributeDefinitions().length == 4);
            assertTrue(s.getObjectClassDefinitions().length == 2);
            assertTrue(s.getOpenContentElements().length == 0);

            String xml = s.toXML(mMarshaller);
            System.out.println(xml);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private static final String NS_DSML_SCHEMA_XML =
            "<ns1:schema xmlns:ns1=\"urn:oasis:names:tc:SPML:2:0:DSML\">\n" +
            "    <ns1:attributeDefinition name=\"cn\" description=\"Common Name\"/>\n" +
            "    <ns1:attributeDefinition name=\"uid\" description=\"User ID\"/>\n" +
            "    <ns1:attributeDefinition name=\"email\" description=\"Email Address\"/>\n" +
            "    <ns1:attributeDefinition name=\"description\" description=\"Description\"/>\n" +
            "    <ns1:objectClassDefinition name=\"account\">\n" +
            "        <ns1:memberAttributes>\n" +
            "            <ns1:attributeDefinitionReference name=\"cn\" required=\"true\"/>\n" +
            "            <ns1:attributeDefinitionReference name=\"uid\"/>\n" +
            "            <ns1:attributeDefinitionReference name=\"email\"/>\n" +
            "        </ns1:memberAttributes>\n" +
            "    </ns1:objectClassDefinition>\n" +
            "    <ns1:objectClassDefinition name=\"role\">\n" +
            "        <ns1:memberAttributes>\n" +
            "            <ns1:attributeDefinitionReference name=\"cn\" required=\"true\"/>\n" +
            "            <ns1:atrributeDefinitionReference name=\"description\"/>\n" +
            "        </ns1:memberAttributes>\n" +
            "    </ns1:objectClassDefinition>\n" +
            "</ns1:schema>\n";

    public void testDSMLSchemaFromXmlNsFix() {
        try {

            System.out.println(NS_DSML_SCHEMA_XML);
            DSMLSchema s = (DSMLSchema) mUnmarshaller.unmarshall(DSML_SCHEMA_XML);

            assertTrue(s.getAttributeDefinitions().length == 4);

            String xml = s.toXML(mMarshaller);
            System.out.println(xml);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
