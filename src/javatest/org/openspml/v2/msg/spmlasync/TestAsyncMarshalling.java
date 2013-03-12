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
package org.openspml.v2.msg.spmlasync;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.openspml.v2.msg.MarshallerTestCase;
import org.openspml.v2.msg.spml.ErrorCode;
import org.openspml.v2.msg.spml.ExecutionMode;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.msg.spml.StatusCode;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 27, 2006
 */
public class TestAsyncMarshalling extends MarshallerTestCase {

    private static final String code_id = "$Id: TestAsyncMarshalling.java,v 1.4 2006/04/21 23:09:02 kas Exp $";

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public TestAsyncMarshalling(String name) {
        super(name);
    }

    /**
     * Creates the TestSuite for the directories in the package.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TestAsyncMarshalling.class);
        return suite;
    }

    public void testCancelRequest() {
        try {
            Extensible data = getOpenContentTestData(2, 2);
            CancelRequest o = new CancelRequest("rid",
                                                ExecutionMode.SYNCHRONOUS,
                                                "arid");
            o.setOpenContent(data.getOpenContentAttrs(),
                             data.getOpenContentElements());
            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testCancelResponse() {
        try {
            Extensible data = getOpenContentTestData(2, 2);
            CancelResponse o = new CancelResponse(getErrorMessages(2),
                                                  StatusCode.FAILURE,
                                                  "rid",
                                                  ErrorCode.INVALID_IDENTIFIER,
                                                  "arid");
            o.setOpenContent(data.getOpenContentAttrs(),
                             data.getOpenContentElements());

            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testStatusRequest() {
        try {
            Extensible data = getOpenContentTestData(2, 2);
            StatusRequest o = new StatusRequest("rid",
                                                ExecutionMode.SYNCHRONOUS,
                                                "arid",
                                                false);
            o.setOpenContent(data.getOpenContentAttrs(),
                             data.getOpenContentElements());

            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testStatusResponse() {
        try {
            Extensible data = getOpenContentTestData(2, 2);
            StatusResponse o = new StatusResponse(getErrorMessages(2),
                                                  StatusCode.FAILURE,
                                                  "rid",
                                                  ErrorCode.INVALID_IDENTIFIER,
                                                  "arid");
            o.setOpenContent(data.getOpenContentAttrs(),
                             data.getOpenContentElements());

            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private static final String SR_REQUEST_ID = "requestIDForSR";

    private static String STATUS_REQUEST_XML =
            "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<ns1:statusRequest xmlns='urn:oasis:names:tc:SPML:2:0' xmlns:ns1='urn:oasis:names:tc:SPML:2:0:async' ocAttr1='bar1' ocAttr2='bar2' requestID='" + SR_REQUEST_ID + "' executionMode='synchronous' asyncRequestID='arid' returnResults='true'>\n" +
            "<openContent name='elName1'/>\n" +
            "<openContent name='elName2'/>\n" +
            "</ns1:statusRequest>";

    public void testPrefixFixForNamespacesWithStatusRequest() {

        try {
            StatusRequest sr = (StatusRequest) mUnmarshaller.unmarshall(STATUS_REQUEST_XML);
            assertNotNull(sr);
            assertTrue(sr.getRequestID().equals(SR_REQUEST_ID));
            assertTrue(sr.getReturnResults());
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }
}
