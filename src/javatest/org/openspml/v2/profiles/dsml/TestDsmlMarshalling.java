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
package org.openspml.v2.profiles.dsml;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.openspml.v2.msg.MarshallerTestCase;
import org.openspml.v2.msg.spml.AddRequest;
import org.openspml.v2.msg.spml.AddResponse;
import org.openspml.v2.msg.spml.ErrorCode;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.msg.spml.Modification;
import org.openspml.v2.msg.spml.ModificationMode;
import org.openspml.v2.msg.spml.ModifyRequest;
import org.openspml.v2.msg.spml.ModifyResponse;
import org.openspml.v2.msg.spml.PSO;
import org.openspml.v2.msg.spml.StatusCode;
import org.openspml.v2.profiles.DSMLProfileRegistrar;
import org.openspml.v2.util.xml.ObjectFactory;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 9, 2006
 */
public class TestDsmlMarshalling extends MarshallerTestCase {

    private static final String code_id = "$Id: TestDsmlMarshalling.java,v 1.11 2006/08/30 18:02:59 kas Exp $";

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public TestDsmlMarshalling(String name) {
        super(name);
    }

    /**
     * Creates the TestSuite for the directories in the package.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TestDsmlMarshalling.class);
        return suite;
    }

    private static boolean mCreatorsAdded = false;

    public void setUp() throws Exception {
        super.setUp();
        if (!mCreatorsAdded) {
            ObjectFactory.getInstance().register(new DSMLProfileRegistrar());
            mCreatorsAdded = true;
        }
    }

    private static final String ADDREQUEST_WITH_DSML =
            "<spml:addRequest xmlns:spml='urn:oasis:names:tc:SPML:2:0'>\n" +
            "  <spml:containerID  ID='OU=accounting,DC=acme.com' targetID='acme.com'/>\n" +
            "  <spml:data>\n" +
            "    <attr name='CN' xmlns='urn:oasis:names:tc:DSML:2:0:core'>\n" +
            "      <value>John Doe</value>\n" +
            "    </attr>\n" +
            "    <attr name='uid' xmlns='urn:oasis:names:tc:DSML:2:0:core'>\n" +
            "      <value>jdoe</value>\n" +
            "    </attr>\n" +
            "    <attr name='objectclass' xmlns='urn:oasis:names:tc:DSML:2:0:core'>\n" +
            "      <value>user</value>\n" +
            "    </attr>\n" +
            "  </spml:data>\n" +
            "</spml:addRequest>\n";

    public void testMarshallAddRequestWithDsml() {
        try {
            System.out.println("AddRequest is: \n" + ADDREQUEST_WITH_DSML);
            AddRequest ar = (AddRequest) mUnmarshaller.unmarshall(ADDREQUEST_WITH_DSML);
            marshallUnmarshall(ar);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testAddRequestWithDsmlOpenContent() {
        try {
            System.out.println("TestDsmlMarshalling.testAddRequestWithDsmlOpenContent");
            AddRequest ar = new AddRequest();
            Extensible ext = new Extensible();
            ar.setData(ext);
            ext.addOpenContentElement(new DSMLAttr("foo", "foovalue"));
            ext.addOpenContentElement(new DSMLAttr("bar", "barvalue"));

            marshallUnmarshall(ar);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }


    public void testMarshallAddResponseWithDsml() {
        try {

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


            ar.addOpenContentElement(new DSMLAttr("foo", "foovalue"));
            ar.addOpenContentElement(new DSMLAttr("bar", "barvalue"));

            marshallUnmarshall(ar);

        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
        
     // BCB: added this and the modify test method below. This example was copied from the DSML profile spec,
     // but the <data> element tags were missing so I added them here. 
    private static final String MODIFYREQUEST_WITH_DSML =
        "<spml:modifyRequest xmlns:spml=\"urn:oasis:names:tc:SPML:2:0\">\n" +
        "   <spml:psoID xmlns:spml=\"urn:oasis:names:tc:SPML:2:0\" ID=\"CN=John Doe,OU=accounting,DC=acme.com\" targetID=\"acme.com \"/>\n" +
        "   <spml:modification xmlns:spml=\"urn:oasis:names:tc:SPML:2:0\">\n" +
        "     <spml:data xmlns:spml=\"urn:oasis:names:tc:SPML:2:0\">\n" +
        "       <modification name =\"CN\" operation =\"replace\" xmlns=\"urn:oasis:names:tc:DSML:2:0:core\">\n" +
        "          <value>Jane Doe</value>\n" +
        "       </modification>\n" +
        "     </spml:data>\n" +
        "   </spml:modification>\n" +
        "</spml:modifyRequest >\n";

    public void testModifyRequestWithDsml() {
        try {
            System.out.println("ModifyRequest is: \n" + MODIFYREQUEST_WITH_DSML);
            ModifyRequest mr = (ModifyRequest) mUnmarshaller.unmarshall(MODIFYREQUEST_WITH_DSML);
            marshallUnmarshall(mr);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testModifyResponseWithDsml() {
        try {
            System.out.println("TestDsmlMarshalling.testModifyResponseWithDsml not implemented");
            ModifyResponse o = null;
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testModifyRequestWithDsmlOpenContent() {
        try {
            System.out.println("TestDsmlMarshalling.testModifyRequestWithDsmlOpenContent");
            ModifyRequest r = new ModifyRequest();
            Modification mod = new Modification();
            r.addModification(mod);
            Extensible ext = new Extensible();
            mod.setData(ext);

            ext.addOpenContentElement(new DSMLModification("foo", "foovalue", ModificationMode.REPLACE));
            ext.addOpenContentElement(new DSMLModification("bar", "barvalue", ModificationMode.ADD));
            ext.addOpenContentElement(new DSMLModification("baz", "bazvalue", ModificationMode.DELETE));

            marshallUnmarshall(r);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testObjectsInProfile() {
        try {

            DSMLAttr[] attrsArray = new DSMLAttr[] {
                new DSMLAttr("sn", "snValue"),
                new DSMLAttr("mva", new DSMLValue[] { new DSMLValue("mva_one"), new DSMLValue("mva_two") } )
            };

            String xml = null;
            for (int k = 0; k < attrsArray.length; k++) {
                DSMLAttr attr = attrsArray[k];
                xml = attr.toXML(2);
                System.out.println("Attr[" + k + "].toXML() is:\n" + xml);
                assertTrue(xml.indexOf(DSMLUnmarshaller.DSML_CORE_URI) >= 0);
            }

            DSMLModification[] modsArray = new DSMLModification[] {
                new DSMLModification("sn", "snValue", ModificationMode.ADD),
                new DSMLModification("mva",
                                     new DSMLValue[] { new DSMLValue("mva_one"),
                                                       new DSMLValue("mva_two") },
                                     ModificationMode.REPLACE),
            };

            for (int k = 0; k < modsArray.length; k++) {
                DSMLModification modification = modsArray[k];
                xml = modification.toXML(4);
                System.out.println("Mod[" + k + "].toXML() is:\n" + xml);
                assertTrue(xml.indexOf(DSMLUnmarshaller.DSML_CORE_URI) >= 0);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
