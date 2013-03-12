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
package org.openspml.v2.msg;

import junit.framework.TestCase;
import org.openspml.v2.msg.spml.CapabilityData;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.msg.spml.NamespacePrefixMapping;
import org.openspml.v2.msg.spml.PSO;
import org.openspml.v2.msg.spml.PSOIdentifier;
import org.openspml.v2.msg.spml.QueryClause;
import org.openspml.v2.msg.spml.Selection;
import org.openspml.v2.msg.spmlref.HasReference;
import org.openspml.v2.msg.spmlsearch.And;
import org.openspml.v2.msg.spmlsearch.Not;
import org.openspml.v2.msg.spmlsearch.Or;
import org.openspml.v2.msg.spmlsearch.Query;
import org.openspml.v2.msg.spmlsearch.Scope;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.xml.ObjectFactory;
import org.openspml.v2.util.xml.ReflectiveDOMXMLUnmarshaller;
import org.openspml.v2.util.xml.ReflectiveXMLMarshaller;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.net.URI;
import java.net.URISyntaxException;

/**
 */
public abstract class MarshallerTestCase extends TestCase {

    protected MarshallerTestCase(String name) {
        super(name);
    }

    private ObjectFactory.OCEUnmarshaller mOCEUnmarshaller;

    protected XMLMarshaller mMarshaller = null;
    protected XMLUnmarshaller mUnmarshaller = null;

    // The intent is to allow a sysprop (org.openspml.v2.msg.marshallerTC) harbor
    // the name of a marshaller to use.  For now, we only have one impl anyway.
    protected void setUp() throws Exception {
        if (mMarshaller == null) {
            mMarshaller = new ReflectiveXMLMarshaller();
        }

        // This order matters.  We want this ahead of the unmarshaller
        // below as it adds items to the factory that would trump us
        // if first.
        if (mOCEUnmarshaller == null) {
            mOCEUnmarshaller = new OCETestDOMUnmarshaller();
            ObjectFactory.getInstance().addOCEUnmarshaller(mOCEUnmarshaller);
        }

        if (mUnmarshaller == null) {
            mUnmarshaller = new ReflectiveDOMXMLUnmarshaller();
        }


    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    protected URI makeURI(String s) {
        try {
            return new URI(s);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        return null;
    }

    protected Extensible getOpenContentTestData() {
        return getOpenContentTestData(2, 2);
    }

    protected Extensible getOpenContentTestData(int attrs, int elements) {
        return getOpenContentTestData(attrs, elements, null);
    }

    protected Extensible getOpenContentTestData(int attrs, int elements, String prefix) {

        Extensible data = new Extensible();

        String attr = "ocAttr";
        String element = "elName";
        if (prefix != null) {
            attr = prefix + "_" + attr;
            element = prefix + "_" + element;
        }

        for (int k = 0; k < attrs; k++)
            data.addOpenContentAttr(attr + (k + 1), "bar" + (k + 1));

        for (int k = 0; k < elements; k++)
            data.addOpenContentElement(new OpenContent(element + (k + 1)));

        return data;
    }


    protected String[] getErrorMessages() {
        return getErrorMessages(0);
    }

    protected String[] getErrorMessages(int number) {
        if (number == 0) {
            return new String[]{"this is error g", "h is an error"};
        }
        ;

        String[] result = new String[Math.abs(number)];
        for (int k = 0; k < result.length; k++) {
            result[k] = k + ":error message:" + k;
        }

        return result;
    }

    public static class OpenContent implements OpenContentElement {

        private String mName = null;

        public OpenContent(String mName) {
            this.mName = mName;
        }

        public String toXML(int indent) throws Spml2Exception {
            String template = "                                                                   ";
            template = template.substring(0, indent);
            return template + toXML();
        }

        public String toXML() throws Spml2Exception {
            if (mName.trim().startsWith("<openContent"))
                return mName;
            else
                return "<openContent name=\"" + mName + "\"/>\n";
        }

        public OpenContentElement fromXML(Object xmlRep) throws Spml2Exception {
            return new OpenContent(xmlRep.toString());
        }
    }

    private static class OCETestDOMUnmarshaller implements ObjectFactory.OCEUnmarshaller {

        public OpenContentElement unmarshall(Object obj) throws Spml2Exception {
            if (obj instanceof Node) {
                Node node = (Node) obj;
                if (node.getLocalName().equals("openContent")) {
                    NamedNodeMap nnm = node.getAttributes();
                    Node attr = nnm.getNamedItem("name");
                    String value = attr.getNodeValue();
                    return new OpenContent(value);
                }
            }
            return null;
        }
    };

    protected PSO createPSO() {

        Extensible data = getOpenContentTestData(1, 1);

        CapabilityData[] cda = new CapabilityData[]{
            new CapabilityData(true, makeURI("foo:1")),
            new CapabilityData(true, makeURI("foo:2")),
        };

        PSOIdentifier psoID = new PSOIdentifier("psoId", null, "targetId");
        PSO pso = new PSO(psoID, data, cda);
        return pso;
    }

    protected NamespacePrefixMapping[] getNamespacePrefixMappings() {
        NamespacePrefixMapping[] nspm = new NamespacePrefixMapping[]{
            new NamespacePrefixMapping("prefixOne", "nsOne"),
            new NamespacePrefixMapping("prefixTwo", "nsTwo"),
        };
        return nspm;
    }

    protected Query getQuery() {

        Extensible data = getOpenContentTestData(1, 3);
        PSOIdentifier psoId = new PSOIdentifier("psoId", null, "psoTarget");
        QueryClause[] queryClauses = new QueryClause[]{
            new And(),
            new Or(),
            new Not(),
            new HasReference(psoId,
                             getOpenContentTestData(0, 1, "refData"),
                             "typeOfReference"),
            new Selection(getNamespacePrefixMappings(),
                          "/path/for/nothing", "uri:bogus:namespaceURI"),
        };
        Query q = new Query(queryClauses,
                            psoId,
                            "dummy_targetID",
                            Scope.PSO);

        q.setOpenContent(data.getOpenContentAttrs(),
                         data.getOpenContentElements());
        return q;
    }

    protected final void marshallUnmarshall(Marshallable m)
              throws Spml2Exception {
        marshallUnmarshall(m, 0);
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
            validateUnmarshalledObject(m2);
        }

        if (lines != 0) {
            String[] res2Lines = res2.split("\n");
            if (lines != res2Lines.length) {
                fail("There are missing lines... input has " + lines + " and output was " + res2Lines.length);
            }
        }
    }

    /**
     * Override this if you want to check values in the unmarshalled object.
     *
     * @param m2
     */
    protected void validateUnmarshalledObject(Marshallable m2) {
        System.out.println("WARNING: MarshallerTestCase.validateUnmarshalledObject not implemented in test.");
    }

}
