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
package org.openspml.v2.msg.spmlsearch;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.openspml.v2.msg.MarshallerTestCase;
import org.openspml.v2.profiles.DSMLProfileRegistrar;
import org.openspml.v2.util.xml.ObjectFactory;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 9, 2006
 */
public class TestSearchMarshalling extends MarshallerTestCase {

    private static final String code_id = "$Id: TestSearchMarshalling.java,v 1.7 2006/08/30 18:02:59 kas Exp $";

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public TestSearchMarshalling(String name) {
        super(name);
    }

    /**
     * Creates the TestSuite for the directories in the package.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TestSearchMarshalling.class);
        return suite;
    }

    // The intent is to allow a sysprop (org.openspml.v2.msg.marshallerTC) harbor
    // the name of a marshaller to use.  For now, we only have one impl anyway.
    protected void setUp() throws Exception {
        super.setUp();
        ObjectFactory.getInstance().register(new DSMLProfileRegistrar());
    }

    public void testQuery() {
        try {
            Query q = getQuery();

            marshallUnmarshall(q);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    /**
     * We'll use this repeatedly with different input XML.
     *
     * @param inputXml
     */
    protected void commonTestSearchRequest(String inputXml) {
        try {
            System.out.println("Input XML:\n" + inputXml);
            SearchRequest sr = (SearchRequest) mUnmarshaller.unmarshall(inputXml);

            int inputLines = inputXml.split("\n").length;
            marshallUnmarshall(sr, inputLines);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }


    static final String XML_SEARCH_REQUEST_1 =
            ""
            + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<spmlsearch:searchRequest xmlns:spml='urn:oasis:names:tc:SPML:2:0' xmlns:spmlsearch='urn:oasis:names:tc:SPML:2:0:search'>\n"
            + "   <spmlsearch:query scope='oneLevel'>\n"
            + "       <spml:basePsoID ID='CN=John Doe,OU=accounting,DC=acme.com' targetID='acme.com' />\n"
            + "       <filter xmlns='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "           <substrings name = 'cn'>\n"
            + "               <initial>John</initial>\n"
            + "           </substrings>\n"
            + "       </filter>\n"
            + "       <attributes xmlns='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "           <attribute name = 'cn' />\n"
            + "           <attribute name = 'email' />\n"
            + "       </attributes>\n"
            + "   </spmlsearch:query>\n"
            + "</spmlsearch:searchRequest>";


    private static final String XML_SEARCH_REQUEST_2 =
            ""
            + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<spmlsearch:searchRequest xmlns:spml='urn:oasis:names:tc:SPML:2:0' xmlns:spmlsearch='urn:oasis:names:tc:SPML:2:0:search'>\n"
            + "   <spmlsearch:query scope='oneLevel'>\n"
            + "       <spml:basePsoID ID='CN=John Doe,OU=accounting,DC=acme.com' targetID='acme.com' />\n"
            + "       <dsml:filter xmlns:dsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "         <dsml:and>\n"
            + "           <dsml:equalityMatch name='ematch'>\n"
            + "             <dsml:value>c29ub3Jhcw==</dsml:value>\n"
            + "           </dsml:equalityMatch>\n"
            + "           <dsml:substrings name='substringsname'>\n"
            + "             <dsml:initial>http://www.any.com/verrantque/temperat</dsml:initial>\n"
            + "             <dsml:any>1_dHVyYmluZQ==</dsml:any>\n"
            + "             <dsml:any>2_dHVyYmluZQ==</dsml:any>\n"
            + "             <dsml:final>cmVnZW1xdWU=</dsml:final>\n"
            + "           </dsml:substrings>\n"
            + "           <dsml:greaterOrEqual name='gte-string'>\n"
            + "             <dsml:value>gte.value</dsml:value>\n"
            + "           </dsml:greaterOrEqual>\n"
            + "           <dsml:lessOrEqual name='lte-string'>\n"
            + "             <dsml:value>lte.string.value</dsml:value>\n"
            + "           </dsml:lessOrEqual>\n"
            + "           <dsml:present name='present-name-string'/>\n"
            + "           <dsml:approxMatch name='approxMatch-name-string'>\n"
            + "             <dsml:value>ZmVyYW50</dsml:value>\n"
            + "           </dsml:approxMatch>\n"
            + "           <dsml:extensibleMatch dnAttributes='false' matchingRule='mr.string' name='name-string'>\n"
            + "             <dsml:value>emValue.string</dsml:value>\n"
            + "           </dsml:extensibleMatch>\n"
            + "           <dsml:or>\n"
            + "             <dsml:equalityMatch name='string'>\n"
            + "               <dsml:value>string</dsml:value>\n"
            + "             </dsml:equalityMatch>\n"
            + "             <dsml:substrings name='string'>\n"
            + "               <dsml:initial>http://www.my.edu/flammato/speluncis</dsml:initial>\n"
            + "               <dsml:any>http://www.my.com/flammas/ac</dsml:any>\n"
            + "               <dsml:final>http://www.test.org/caelumque/speluncis</dsml:final>\n"
            + "             </dsml:substrings>\n"
            + "             <dsml:greaterOrEqual name='string'>\n"
            + "               <dsml:value>http://www.sample.com/praeterea/arce</dsml:value>\n"
            + "             </dsml:greaterOrEqual>\n"
            + "             <dsml:lessOrEqual name='string'>\n"
            + "               <dsml:value>http://www.any.com/certo/quisquam</dsml:value>\n"
            + "             </dsml:lessOrEqual>\n"
            + "             <dsml:present name='string'/>\n"
            + "             <dsml:approxMatch name='string'>\n"
            + "               <dsml:value>string</dsml:value>\n"
            + "             </dsml:approxMatch>\n"
            + "             <dsml:extensibleMatch dnAttributes='false' matchingRule='string' name='string'>\n"
            + "               <dsml:value>string</dsml:value>\n"
            + "             </dsml:extensibleMatch>\n"
            + "           </dsml:or>\n"
            + "           <dsml:not>\n"
            + "             <dsml:equalityMatch name='string'>\n"
            + "               <dsml:value>string</dsml:value>\n"
            + "             </dsml:equalityMatch>\n"
            + "           </dsml:not>\n"
            + "         </dsml:and>\n"
            + "       </dsml:filter>\n"
            + "       <dsml:attributes xmlns:dsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "         <dsml:attribute name='one'/>\n"
            + "         <dsml:attribute name='two'/>\n"
            + "       </dsml:attributes>\n"
            + "   </spmlsearch:query>\n"
            + "</spmlsearch:searchRequest>";

    static final String XML_SEARCH_REQUEST_3 =
            ""
            + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<spmlsearch:searchRequest xmlns:spml='urn:oasis:names:tc:SPML:2:0' xmlns:spmlsearch='urn:oasis:names:tc:SPML:2:0:search'>\n"
            + "   <spmlsearch:query scope = 'spmlsearch:oneLevel'>\n"
            + "       <spml:basePsoID ID='CN=John Doe,OU=accounting,DC=acme.com' targetID='acme.com' />\n"
            + "       <dsml:filter xmlns:dsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "           <dsml:not>\n"
            + "             <dsml:equalityMatch name='string'>\n"
            + "               <dsml:value>string</dsml:value>\n"
            + "             </dsml:equalityMatch>\n"
            + "           </dsml:not>\n"
            + "       </dsml:filter>\n"
            + "       <attributes xmlns='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "           <attribute name = 'cn' />\n"
            + "           <attribute name = 'email' />\n"
            + "       </attributes>\n"
            + "   </spmlsearch:query>\n"
            + "</spmlsearch:searchRequest>";

    private static final String XML_SEARCH_REQUEST_4 =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<searchRequest xmlns='urn:oasis:names:tc:SPML:2:0:search'/>";

    public void testSearchRequest_1() {
        commonTestSearchRequest(XML_SEARCH_REQUEST_1);
    }

    public void testSearchRequest_2() {
        commonTestSearchRequest(XML_SEARCH_REQUEST_2);
    }

    public void testSearchRequest_3() {
        commonTestSearchRequest(XML_SEARCH_REQUEST_3);
    }

    public void testSearchRequest_4() {
        commonTestSearchRequest(XML_SEARCH_REQUEST_4);
    }

    public void testSearchResponse() {
        try {
            System.out.println("not implemented");

        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testIterateRequest() {
        try {
            System.out.println("not implemented");

            // test AND, OR, NOT
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testIterateResponse() {
        try {
            System.out.println("not implemented");

            // test AND, OR, NOT
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testCloseIteratorRequest() {
        try {
            System.out.println("not implemented");

            // test AND, OR, NOT
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testCloseIteratorResponse() {
        try {
            System.out.println("not implemented");

            // test AND, OR, NOT
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
