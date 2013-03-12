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

package org.openspml.v2.msg.spmlbulk;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.openspml.v2.msg.MarshallerTestCase;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.msg.spmlsearch.Query;

/**
 */
public class TestBulkMarshalling extends MarshallerTestCase {

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public TestBulkMarshalling(String name) {
        super(name);
    }

    /**
     * Creates the TestSuite for the directories in the package.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TestBulkMarshalling.class);
        return suite;
    }

    public void testBulkDeleteRequest() {
        try {
            Extensible data = getOpenContentTestData(0, 1);
            Query query = getQuery();
            BulkDeleteRequest o = new BulkDeleteRequest("fakeRequestId",
                                                        null, /* execution mode */
                                                        query,
                                                        null /* boolean recursive */
                                                        );
            o.setOpenContent(data.getOpenContentAttrs(),
                             data.getOpenContentElements());
            
            marshallUnmarshall(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    public void testBulkDeleteResponse() {
        try {
            System.out.println("not implemented");

        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testBulkModifyRequest() {
        try {
            System.out.println("not implemented");

        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testBulkModifyResponse() {
        try {
            System.out.println("not implemented");

        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
