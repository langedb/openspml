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
import junit.textui.TestRunner;
import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.msg.XMLUnmarshaller;
import org.openspml.v2.msg.spml.ListTargetsResponse;
import org.openspml.v2.msg.spml.QueryClause;
import org.openspml.v2.msg.spmlsearch.Query;
import org.openspml.v2.msg.spmlsearch.SearchRequest;
import org.openspml.v2.profiles.DSMLProfileRegistrar;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.xml.ObjectFactory;
import org.openspml.v2.util.xml.ReflectiveDOMXMLUnmarshaller;
import org.openspml.v2.util.xml.ReflectiveXMLMarshaller;
import org.openspml.v2.util.xml.XmlParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MiscDsmlTest extends TestCase {

    static final String LIST_TARGETS_RESPONSE = "<listTargetsResponse status=\"success\" xmlns=\"urn:oasis:names:tc:SPML:2:0\">\n"
            + "    <target targetID=\"http://www.summittrust.com\">\n"
            + "        <schema>\n"
            + "            <schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"urn:oasis:names:tc:SPML:2:0:DSML\">\n"
            + "                <objectClassDefinition name=\"user\">\n"
            + "                    <memberAttributes>\n"
            + "                        <attributeDefinitionReference name=\"uid\"/>\n"
            + "                        <attributeDefinitionReference name=\"mail\"/>\n"
            + "                        <attributeDefinitionReference name=\"description\"/>\n"
            + "                    </memberAttributes>\n"
            + "                </objectClassDefinition>\n"
            + "                <attributeDefinition name=\"uid\"/>\n"
            + "                <attributeDefinition name=\"mail\"/>\n"
            + "                <attributeDefinition name=\"description\"/>\n"
            + "            </schema>\n"
            + "        </schema>\n"
            + "        <capabilities>\n"
            + "            <capability namespaceURI=\"urn:oasis:names:tc:SPML:2:0:password\">\n"
            + "                <appliesTo entityName=\"user\"/>\n"
            + "            </capability>\n"
            + "            <capability namespaceURI=\"urn:oasis:names:tc:SPML:2:0:suspend\">\n"
            + "                <appliesTo entityName=\"user\"/>\n"
            + "            </capability>\n"
            + "            <capability namespaceURI=\"urn:oasis:names:tc:SPML:2:0:search\"/>\n"
            + "        </capabilities>\n"
            + "    </target>\n"
            + "</listTargetsResponse>\n";

    public static void main(String[] args) {
        TestRunner.run(MiscDsmlTest.class);
    }

    XMLMarshaller mMarshaller = null;
    XMLUnmarshaller mUnmarshaller = null;

    public MiscDsmlTest(String name) {
        super(name);
    }

    private static boolean _testMarshallerOkay = false;

    private class TestDSMLUnmarshaller extends DSMLUnmarshaller {
        public TestDSMLUnmarshaller() {
            System.out.println("Using the test dsml unmarshaller...");
            _testMarshallerOkay = true;
        }
    }

    private class TestDSMLProfileRegistrar extends DSMLProfileRegistrar {
        protected ObjectFactory.OCEUnmarshaller createDSMLUnmarshaller() {
            return new TestDSMLUnmarshaller();
        }
    }

    protected void setUp() throws Exception {
        mMarshaller = new ReflectiveXMLMarshaller();
        mUnmarshaller = new ReflectiveDOMXMLUnmarshaller();
        ObjectFactory instance = ObjectFactory.getInstance();
        instance.register(new TestDSMLProfileRegistrar());
        assertTrue(_testMarshallerOkay == true);
    }

    protected void tearDown() throws Exception {
        // this doesn't seem to work - hmmm.
        //ObjectFactory.getInstance().unregister(DSMLProfileRegistrar.PROFILE_ID);
    }

    protected final void marshallUnmarshall(Marshallable m, int lines)
            throws Spml2Exception {

        String res = m.toXML(mMarshaller);

        res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + res;
        System.out.println("res=\n" + res);

        Marshallable m2 = (Marshallable) mUnmarshaller.unmarshall(res);
        assertNotNull(m2);

        String res2 = m2.toXML(mMarshaller);
        res2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + res2;
        System.out.println("remarshalled = \n" + res2);

        if (!res.equals(res2)) {
            System.out.println("WARNING: The result of the remarshall is not equivalent " +
                               "to the original xml: calling validation routine.");
        }

        if (lines != 0) {
            String[] res2Lines = res2.split("\n");
            if (lines != res2Lines.length) {
                fail("There are missing lines... input has " + lines + " and output was " + res2Lines.length);
            }
        }
    }

    public void testListTargetsResponse() {
        try {
            String xml = LIST_TARGETS_RESPONSE;

            System.out.println("----------------------------------------------------------------------");

            // unmarshal:
            System.out.println("\nUnmarshalling request xml:\n" + xml);
            Marshallable resp = mUnmarshaller.unmarshall(xml);
            System.out.println("Unmarshalling complete");
            System.out.println("-- Resulting class: " + resp.getClass().getName());

            ListTargetsResponse ltr = (ListTargetsResponse) resp;
            xml = ltr.toXML(mMarshaller);

            System.out.println("Marshalled xml = \n\n" + xml);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testAttrAlone() {
        try {
            System.out.println("----------------------------------------------------------------------");
            DSMLAttr attr = new DSMLAttr("testattr", "testvalue");

            String xml = attr.toXML();
            System.out.println("xml = \n" + xml);

            DSMLValue[] values = new DSMLValue[] {
                new DSMLValue("one"),
                new DSMLValue("two"),
                new DSMLValue("three"),
            };
            attr.setValues(values);

            xml = attr.toXML();
            System.out.println("xml with 3 values = \n" + xml);

            xml = "<dsml:attr xmlns:dsml='urn:oasis:names:tc:DSML:2:0:core' name='attr'>\n"
                    + "    <dsml:value>value</dsml:value>\n"
                    + "    <dsml:value>value2</dsml:value>\n"
                    + "</dsml:attr>";

            // testing this a tad tricky...
            Document doc = XmlParser.parse(xml);

            // now the hard work starts.
            Element el = doc.getDocumentElement();

            OpenContentElement oce = 
                    ObjectFactory.getInstance().unmarshallOpenContentElement(el);

            assertTrue(oce instanceof DSMLAttr);

            System.out.println("parsed xml to \n" + oce.toXML());
        }
        catch (Exception e) {
            fail(e.getMessage());
        }

    }

    //
    // do this with some XML that we parse and then run the filters....
    //
    // we could use a test file like:
    //
    //   #testfile
    //   attrname=value0
    //   attrname=value1 (scan for all values with the same name
    //   filter=
    //   <> DSML Filter XML on many lines...
    //   match=true|false
    //
    protected Filter getFilterFromSearchRequest(String inputXml) {
        try {
            System.out.println("Input XML:\n" + inputXml);
            SearchRequest sr = (SearchRequest) mUnmarshaller.unmarshall(inputXml);

            marshallUnmarshall(sr, 0);

            Query q = sr.getQuery();
            QueryClause[] clauses = q.getQueryClauses();
            assertTrue(clauses != null && clauses.length == 1);
            Filter filter = (Filter) clauses[0];
            return filter;
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        return null;
    }

    protected void addItemToMap(Map map, String name, String value) {
        List list = new ArrayList();
        list.add(value);
        map.put(name, list);
    }

    // but for now we want some basic tests...
    private static final String XML_SEARCH_REQUEST_WITH_FILTER_EQ =
            ""
            + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<spmlsearch:searchRequest xmlns:spml='urn:oasis:names:tc:SPML:2:0' xmlns:spmlsearch='urn:oasis:names:tc:SPML:2:0:search'>\n"
            + "   <spmlsearch:query scope='oneLevel'>\n"
            + "       <spml:basePsoID ID='CN=John Doe,OU=accounting,DC=acme.com' targetID='acme.com' />\n"
            + "       <dsml:filter xmlns:dsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "           <dsml:equalityMatch name='attrName'>\n"
            + "             <dsml:value>dsmlValue</dsml:value>\n"
            + "           </dsml:equalityMatch>\n"
            + "       </dsml:filter>\n"
            + "   </spmlsearch:query>\n"
            + "</spmlsearch:searchRequest>\n";

    public void testEQ() {

        Map testMap = new HashMap();
        addItemToMap(testMap, "attrName", "dsmlValue");

        Filter filter = this.getFilterFromSearchRequest(XML_SEARCH_REQUEST_WITH_FILTER_EQ);
        assertTrue(filter != null);
        try {
            assertTrue(filter.matches(testMap));
        }
        catch (DSMLProfileException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testEQWithBuildUp() {

        try {
            // this should just build a search request using Java and test the eq beast...

            Map testMap = new HashMap();
            addItemToMap(testMap, "em1", "one");
            addItemToMap(testMap, "em2", "two");

            List matches = new ArrayList();
            for (Iterator i = testMap.entrySet().iterator(); i.hasNext();) {
                Map.Entry entry = (Map.Entry) i.next();
                String key = entry.getKey().toString();
                List value = (List) entry.getValue();
                EqualityMatch match = new EqualityMatch(key, value.get(0).toString());
                matches.add(match);
            }

            // test the matches - they should all be true.

            // now test an and.
            And and = new And();
            for (int k = 0; k < matches.size(); k++) {
                EqualityMatch equalityMatch = (EqualityMatch) matches.get(k);
                and.addItem(equalityMatch);
            }

            assertTrue(and.matches(testMap));
            ((EqualityMatch) matches.get(0)).setValue(new DSMLValue("not a match"));

            assertFalse(and.matches(testMap));

            EqualityMatch[] mArray = (EqualityMatch[]) matches.toArray(new EqualityMatch[matches.size()]);
            Or or = new Or(mArray);
            assertTrue(or.matches(testMap));

            Filter filter = new Filter();
            filter.setItem(or);
            assertTrue(filter.matches(testMap));

            Not not = new Not(or);
            assertFalse(not.matches(testMap));
            filter.setItem(not);

            String correctCall = filter.toXML();
            String shortedCall = filter.toXML(mMarshaller);
            assertTrue(correctCall.equals(shortedCall));

            System.out.println("XML of Not is:\n" + correctCall);

            // we could also test the visitor.
            TestFilterItemVisitor tfiv = new TestFilterItemVisitor();
            filter.applyVisitor(tfiv);

            FilterItem[] items = tfiv.getItemsInDepthFirstOrder();
            assertTrue(items.length == 4);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    private static final String XML_SEARCH_REQUEST_WITH_FILTER_GTE =
            ""
            + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<spmlsearch:searchRequest xmlns:spml='urn:oasis:names:tc:SPML:2:0' xmlns:spmlsearch='urn:oasis:names:tc:SPML:2:0:search'>\n"
            + "   <spmlsearch:query scope='oneLevel'>\n"
            + "       <spml:basePsoID ID='CN=John Doe,OU=accounting,DC=acme.com' targetID='acme.com' />\n"
            + "       <dsml:filter xmlns:dsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "           <dsml:greaterOrEqual name='attrName'>\n"
            + "             <dsml:value>aardvark</dsml:value>\n"
            + "           </dsml:greaterOrEqual>\n"
            + "       </dsml:filter>\n"
            + "   </spmlsearch:query>\n"
            + "</spmlsearch:searchRequest>\n";

    private static final String XML_SEARCH_REQUEST_WITH_FILTER_LTE =
            ""
            + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<spmlsearch:searchRequest xmlns:spml='urn:oasis:names:tc:SPML:2:0' xmlns:spmlsearch='urn:oasis:names:tc:SPML:2:0:search'>\n"
            + "   <spmlsearch:query scope='oneLevel'>\n"
            + "       <spml:basePsoID ID='CN=John Doe,OU=accounting,DC=acme.com' targetID='acme.com' />\n"
            + "       <dsml:filter xmlns:dsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "           <dsml:lessOrEqual name='attrName'>\n"
            + "             <dsml:value>aardvark</dsml:value>\n"
            + "           </dsml:lessOrEqual>\n"
            + "       </dsml:filter>\n"
            + "   </spmlsearch:query>\n"
            + "</spmlsearch:searchRequest>\n";

    private static final String XML_SEARCH_REQUEST_WITH_FILTER_AM =
            ""
            + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<spmlsearch:searchRequest xmlns:spml='urn:oasis:names:tc:SPML:2:0' xmlns:spmlsearch='urn:oasis:names:tc:SPML:2:0:search'>\n"
            + "   <spmlsearch:query scope='oneLevel'>\n"
            + "       <spml:basePsoID ID='CN=John Doe,OU=accounting,DC=acme.com' targetID='acme.com' />\n"
            + "       <dsml:filter xmlns:dsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "           <dsml:approxMatch name='attrName'>\n"
            + "             <dsml:value>approxMatch</dsml:value>\n"
            + "           </dsml:approxMatch>\n"
            + "       </dsml:filter>\n"
            + "   </spmlsearch:query>\n"
            + "</spmlsearch:searchRequest>\n";

    public void testAM() {

        Filter filter = this.getFilterFromSearchRequest(XML_SEARCH_REQUEST_WITH_FILTER_AM);
        assertTrue(filter != null);
        try {
            filter.matches(new HashMap());
        }
        catch (DSMLProfileException e) {
            return;
        }
        fail("Should have seen a DSMLProfileException.");
    }
}
