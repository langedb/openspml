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
 * Copyright 2006 Tripod Technology Group, Inc.
 * All Rights Reserved.
 * Use is subject to license terms.
 */
package org.openspml.v2.profiles.dsml;

import junit.framework.TestCase;
import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.msg.XMLUnmarshaller;
import org.openspml.v2.msg.spml.Request;
import org.openspml.v2.profiles.DSMLProfileRegistrar;
import org.openspml.v2.util.xml.ObjectFactory;
import org.openspml.v2.util.xml.ReflectiveDOMXMLUnmarshaller;
import org.openspml.v2.util.xml.ReflectiveXMLMarshaller;

/**
 * @author Blaine Busler
 */
public abstract class RequestsWithDsmlTestBase extends TestCase {
    protected static final String INDENT = "-- ";
    protected static final String INDENT2 = "     -- ";

    XMLMarshaller mMarshaller = null;
    XMLUnmarshaller mUnmarshaller = null;

    public RequestsWithDsmlTestBase(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        mMarshaller = new ReflectiveXMLMarshaller();
        mUnmarshaller = new ReflectiveDOMXMLUnmarshaller();
        ObjectFactory.getInstance().register(new DSMLProfileRegistrar());
    }

    protected void unmarshallMarshall(String xml, Class expectedClass, String header) {
        try {
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Beginning unmarshal/marshal test" + (header == null ? "" : ": " + header));

            // unmarshal:
            System.out.println("\nUnmarshalling request xml:\n" + xml);
            Marshallable req = mUnmarshaller.unmarshall(xml);
            System.out.println("Unmarshalling complete");

            if (!(expectedClass.isAssignableFrom(req.getClass())))
                fail(expectedClass.getName() + " expected but unmarshalled to: " + req.getClass().getName());

            if (!(req instanceof Request))
                fail(Request.class + " expected but unmarshalled to: " + req.getClass().getName());

            // Make sure the unmarshalled request is as expected
            String err = validate((Request) req);
            if (err != null) {
                System.out.println("Test failed: " + err);
                fail(err);
            }

            showContents((Request) req);

            // Marshal:
            System.out.println("\nMarshalling resulting object");
            String xml2 = req.toXML(mMarshaller);
            System.out.println("Marshalled request to:\n" + xml2);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        finally {
            System.out.println("Completed unmarshal/marshal test" + (header == null ? "" : ": " + header) + '\n');
        }

    }

    protected abstract void showContents(Request req);

    protected abstract String validate(Request req);
}
