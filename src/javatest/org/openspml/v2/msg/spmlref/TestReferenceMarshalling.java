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
 *
 * Use is subject to license terms.
 */
package org.openspml.v2.msg.spmlref;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.openspml.v2.msg.MarshallerTestCase;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.msg.spml.PSOIdentifier;
import org.openspml.v2.msg.spml.SchemaEntityRef;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 9, 2006
 */
public class TestReferenceMarshalling extends MarshallerTestCase {

    private static final String code_id = "$Id: TestReferenceMarshalling.java,v 1.4 2006/06/08 18:34:22 kas Exp $";

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public TestReferenceMarshalling(String name) {
        super(name);
    }

    /**
     * Creates the TestSuite for the directories in the package.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TestReferenceMarshalling.class);
        return suite;
    }

    protected Extensible getReferenceData() {
        return null;
    }

    public void testReference() {
        try {
            PSOIdentifier containerId = new PSOIdentifier("container1", null, "targetID");
            PSOIdentifier psoId = new PSOIdentifier("id", containerId, "targetId");
            Extensible referenceData = getReferenceData();
            Reference o = new Reference(psoId, referenceData, "typeOfReference");

            String res = o.toXML(mMarshaller);
            System.out.println("res=\n\n" + res);

            System.out.println("unmarshall\n");
            Reference o2 = (Reference) mUnmarshaller.unmarshall(res);

            System.out.println("remarshall:");
            res = o2.toXML(mMarshaller);
            System.out.println("o2 =\n\n" + res);

        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testHasReference() {

        try {
            PSOIdentifier containerId = new PSOIdentifier("container1", null, "targetID");
            PSOIdentifier psoId = new PSOIdentifier("id", containerId, "targetId");
            Extensible referenceData = getReferenceData();
            HasReference o = new HasReference(psoId, referenceData, "typeOfReference");

            String res = o.toXML(mMarshaller);
            System.out.println("res=\n\n" + res);

            System.out.println("remarshall:");

            HasReference o2 = (HasReference) mUnmarshaller.unmarshall(res);

            res = o2.toXML(mMarshaller);
            System.out.println("res=\n\n" + res);

        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testReferenceDefinition() {

        try {
            SchemaEntityRef schemaEnt = new SchemaEntityRef("targetID", "entityName", true);
            SchemaEntityRef[] canReferTo = new SchemaEntityRef[]{
                new SchemaEntityRef("targetID", "referred1", false),
                new SchemaEntityRef("targetID", "referred2", false),
                new SchemaEntityRef("targetID", "referred3", false),
            };

            SchemaEntityRef[] referenceDataTypes = new SchemaEntityRef[]{
                new SchemaEntityRef("targetID", "dataType1", false),
                new SchemaEntityRef("targetID", "dataType2", false),
                new SchemaEntityRef("targetID", "dataType3", false),
            };

            ReferenceDefinition o = new ReferenceDefinition(schemaEnt,
                                                            canReferTo,
                                                            referenceDataTypes,
                                                            "typeOfReference");

            String res = o.toXML(mMarshaller);
            System.out.println("res=\n\n" + res);

            System.out.println("remarshall:");

            ReferenceDefinition o2 = (ReferenceDefinition) mUnmarshaller.unmarshall(res);

            res = o2.toXML(mMarshaller);
            System.out.println("res=\n\n" + res);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private static final String REFERENCEDEF_XML_1 =
            "<spmlref:referenceDefinition xmlns='urn:oasis:names:tc:SPML:2:0' xmlns:spmlref='urn:oasis:names:tc:SPML:2:0:reference' typeOfReference='roles'>\n" +
            "  <spmlref:schemaEntity entityName=\"account\"/>\n" +
            "  <spmlref:canReferTo targetID='Sun-Interop-DSML' entityName='role' isContainer='false'/>\n" +
            "</spmlref:referenceDefinition>\n";

    public void testReferenceDefintionFromXml_1() {

        try {
            ReferenceDefinition rd = (ReferenceDefinition)
                    mUnmarshaller.unmarshall(REFERENCEDEF_XML_1);
            marshallUnmarshall(rd);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private static final String REFERENCEDEF_XML_2 =
            "<spmlref:referenceDefinition typeOfReference=\"roles\" xmlns:spmlref=\"urn:oasis:names:tc:SPML:2:0:reference\">\n" +
            "  <spmlref:schemaEntity entityName=\"account\"/>\n" +
            "  <spmlref:canReferTo entityName=\"role\" targetID=\"Sun-Interop-DSML\"/>\n" +
            "</spmlref:referenceDefinition>\n";

    public void testReferenceDefintionFromXml_2() {

        try {
            ReferenceDefinition rd = (ReferenceDefinition)
                    mUnmarshaller.unmarshall(REFERENCEDEF_XML_2);
            marshallUnmarshall(rd);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
