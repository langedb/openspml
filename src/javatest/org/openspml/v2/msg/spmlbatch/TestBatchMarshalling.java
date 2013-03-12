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
package org.openspml.v2.msg.spmlbatch;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.openspml.v2.msg.MarshallerTestCase;
import org.openspml.v2.msg.pass.SetPasswordRequest;
import org.openspml.v2.msg.spml.AddRequest;
import org.openspml.v2.msg.spml.AddResponse;
import org.openspml.v2.msg.spml.CapabilityData;
import org.openspml.v2.msg.spml.DeleteResponse;
import org.openspml.v2.msg.spml.ErrorCode;
import org.openspml.v2.msg.spml.ExecutionMode;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.msg.spml.PSOIdentifier;
import org.openspml.v2.msg.spml.Response;
import org.openspml.v2.msg.spml.ReturnData;
import org.openspml.v2.msg.spml.StatusCode;

/**
 */
public class TestBatchMarshalling extends MarshallerTestCase {

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public TestBatchMarshalling(String name) {
        super(name);
    }

    /**
     * Creates the TestSuite for the directories in the package.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TestBatchMarshalling.class);
        return suite;
    }

    public void testMarshallBatchRequestNoOps() {
        try {
            BatchRequest br = new BatchRequest("rid", ExecutionMode.ASYNCHRONOUS, Processing.PARALLEL, OnError.RESUME);
            marshallUnmarshall(br);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testMarshallBatchRequest() {
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

            BatchRequest br = new BatchRequest("rid", ExecutionMode.ASYNCHRONOUS, Processing.PARALLEL, OnError.RESUME);

            for (int a = 0; a < 3; a++) {
                AddRequest ar = new AddRequest("requestId" + a,
                                               ExecutionMode.SYNCHRONOUS,
                                               psoId,
                                               containerId,
                                               data,
                                               cda,
                                               "targetId",
                                               ReturnData.DATA);

                ar.addOpenContentAttr("arFoo_" + a, "arFooValue_" + a);
                ar.addOpenContentElement(new OpenContent("arOne_" + a));
                ar.addOpenContentElement(new OpenContent("arTwo_" + a));
                br.addRequest(ar);
            }

            SetPasswordRequest spr = new SetPasswordRequest("sprRequestId",
                                                            ExecutionMode.ASYNCHRONOUS,
                                                            psoId,
                                                            "password",
                                                            "currentPassword");
            br.addRequest(spr);

            marshallUnmarshall(br);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testBatchResponse() {
        try {
            BatchResponse o = new BatchResponse(getErrorMessages(5),
                                                StatusCode.SUCCESS,
                                                "dummy_requestId",
                                                ErrorCode.UNSUPPORTED_SELECTION_TYPE);
            Response[] resp = new Response[] {
                new AddResponse(getErrorMessages(),
                                StatusCode.SUCCESS,
                                "dummy ar requestId",
                                ErrorCode.CONTAINER_NOT_EMPTY,
                                createPSO()),
                new DeleteResponse(getErrorMessages(),
                                   StatusCode.SUCCESS,
                                   "dummy ar requestId",
                                   ErrorCode.CONTAINER_NOT_EMPTY),
            };

            for (int k = 0; k < resp.length; k++) {
                o.addResponse(resp[k]);
            }

            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testOnErrorEnum() {
        try {
            System.out.println("not implemented");

        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testProcessingEnum() {
        try {
            System.out.println("not implemented");

        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
