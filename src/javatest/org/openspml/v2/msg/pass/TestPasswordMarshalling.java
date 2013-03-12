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
package org.openspml.v2.msg.pass;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.MarshallerTestCase;
import org.openspml.v2.msg.spml.ErrorCode;
import org.openspml.v2.msg.spml.ExecutionMode;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.msg.spml.PSOIdentifier;
import org.openspml.v2.msg.spml.StatusCode;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 9, 2006
 */
public class TestPasswordMarshalling extends MarshallerTestCase {

    private static final String code_id = "$Id: TestPasswordMarshalling.java,v 1.3 2006/06/08 18:34:22 kas Exp $";

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public TestPasswordMarshalling(String name) {
        super(name);
    }

    /**
     * Creates the TestSuite for the directories in the package.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TestPasswordMarshalling.class);
        return suite;
    }

    public void testSetPasswordRequest() {

        try {
            PSOIdentifier psoId = new PSOIdentifier("psoId", null, "targetId");
            Extensible data = getOpenContentTestData();
            SetPasswordRequest o = new SetPasswordRequest("requestId",
                                                          ExecutionMode.SYNCHRONOUS,
                                                          psoId,
                                                          "pass",
                                                          "currentPwdValue");

            o.setOpenContentAttrs(data.getOpenContentAttrs());
            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testSetPasswordResponseNoOC() {

        try {

            SetPasswordResponse o = new SetPasswordResponse(getErrorMessages(),
                                                            StatusCode.SUCCESS,
                                                            "rid",
                                                            ErrorCode.NO_SUCH_REQUEST);
            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testSetPasswordResponseBothOC() {

        try {
            String[] errMsgs = {
                "g", "h",
            };

            Extensible data = getOpenContentTestData();
            SetPasswordResponse o = new SetPasswordResponse(errMsgs,
                                                            StatusCode.SUCCESS,
                                                            "rid",
                                                            ErrorCode.NO_SUCH_REQUEST);

            o.setOpenContent(data.getOpenContentAttrs(),
                             data.getOpenContentElements());
            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testSetPasswordResponseWithNoOCAttrs() {

        try {
            String[] errMsgs = {
                "g", "h",
            };

            Extensible data = getOpenContentTestData();
            SetPasswordResponse o = new SetPasswordResponse(errMsgs,
                                                            StatusCode.SUCCESS,
                                                            "rid",
                                                            ErrorCode.NO_SUCH_REQUEST);
            o.setOpenContent(null,
                             data.getOpenContentElements());
            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testSetPasswordResponseWithNoOCElements() {

        try {
            String[] errMsgs = {
                "g", "h",
            };

            Extensible data = getOpenContentTestData();
            SetPasswordResponse o = new SetPasswordResponse(errMsgs,
                                                            StatusCode.SUCCESS,
                                                            "rid",
                                                            ErrorCode.NO_SUCH_REQUEST);
            o.setOpenContent(data.getOpenContentAttrs(),
                             null);
            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    protected void validateUnmarshalledObject(Marshallable m2) {
        if (m2 instanceof ExpirePasswordRequest) {
            ExpirePasswordRequest epr = (ExpirePasswordRequest) m2;
            assertTrue(epr.getRemainingLogins() == 2);
        }
    }

    public void testExpirePasswordRequest() {

        try {
            PSOIdentifier psoId = new PSOIdentifier("psoId", null, "targetId");
            Extensible data = getOpenContentTestData();
            ExpirePasswordRequest o = new ExpirePasswordRequest("requestId",
                                                                ExecutionMode.SYNCHRONOUS,
                                                                psoId,
                                                                new Integer(2));
            o.setOpenContent(data.getOpenContentAttrs(),
                             data.getOpenContentElements());
            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testResetPasswordRequest() {

        try {
            PSOIdentifier psoId = new PSOIdentifier("psoId", null, "targetId");
            Extensible data = getOpenContentTestData();
            ResetPasswordRequest o = new ResetPasswordRequest("requestId",
                                                              ExecutionMode.SYNCHRONOUS,
                                                              psoId);
            o.setOpenContent(data.getOpenContentAttrs(),
                             data.getOpenContentElements());
            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testValidatePasswordRequest() {

        try {
            PSOIdentifier psoId = new PSOIdentifier("psoId", null, "targetId");
            Extensible data = getOpenContentTestData();
            ValidatePasswordRequest o = new ValidatePasswordRequest("requestId",
                                                                    ExecutionMode.SYNCHRONOUS,
                                                                    psoId,
                                                                    "currentPwd");
            o.setOpenContent(data.getOpenContentAttrs(),
                             data.getOpenContentElements());
            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private static final String PASSWORD_NS_TEST =
            ""
            + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<pass:validatePasswordRequest xmlns='urn:oasis:names:tc:SPML:2:0' xmlns:pass='urn:oasis:names:tc:SPML:2:0:password' ocAttr1='bar1' ocAttr2='bar2' requestID='requestId' executionMode='synchronous'>\n"
            + "  <openContent name=\"elName1\"/>\n"
            + "  <openContent name=\"elName2\"/>\n"
            + "  <pass:psoID ID='psoId' targetID='targetId'/>\n"
            + "  <pass:password>currentPwd</pass:password>\n"
            + "</pass:validatePasswordRequest>\n";

    public void testPasswordPsoIDNamespace() {
        try {

            ValidatePasswordRequest vpr = (ValidatePasswordRequest) mUnmarshaller.unmarshall(PASSWORD_NS_TEST);
            assertTrue("psoID must not be null.",
                       vpr.getPsoID() != null);

            String xml = vpr.toXML(mMarshaller);
            System.out.println("xml is: \n" + xml);

            assertTrue("The pass: namespace qualifier is NOT on psoID.",
                       xml.indexOf("pass:psoID") >= 0);

        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
