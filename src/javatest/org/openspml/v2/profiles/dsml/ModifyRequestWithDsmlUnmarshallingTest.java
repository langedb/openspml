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

import junit.textui.TestRunner;
import org.openspml.v2.msg.OCEtoMarshallableAdapter;
import org.openspml.v2.msg.OCEtoXMLStringAdapter;
import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.msg.spml.Modification;
import org.openspml.v2.msg.spml.ModifyRequest;
import org.openspml.v2.msg.spml.Request;

/**
 * @author Blaine Busler
 */
public class ModifyRequestWithDsmlUnmarshallingTest extends RequestsWithDsmlTestBase {

    // ModifyRequest with namespaces for elements as follows: default for SPML, default for DSML
    static final String XML_DSPML_DDSML = "<modifyRequest xmlns=\"urn:oasis:names:tc:SPML:2:0\">\n"
            + "   <psoID ID=\"CN=John Doe,OU=accounting,DC=acme.com\" targetID=\"acme.com \"/>\n"
            + "   <modification>\n"
            + "      <data>\n"
            + "         <modification name=\"CN\" operation=\"replace\" xmlns=\"urn:oasis:names:tc:DSML:2:0:core\">\n"
            + "            <value>Jane Doe</value>\n"
            + "         </modification>\n"
            + "      </data>\n"
            + "   </modification>\n"
            + "</modifyRequest>\n";

    // ModifyRequest with namespaces for elements as follows: default for SPML, qualified for DSML
    static final String XML_DSPML_QDSML = "<modifyRequest xmlns=\"urn:oasis:names:tc:SPML:2:0\">\n"
            + "   <psoID ID=\"CN=John Doe,OU=accounting,DC=acme.com\" targetID=\"acme.com \"/>\n"
            + "   <modification>\n"
            + "      <data>\n"
            + "         <dsml:modification name=\"CN\" operation=\"replace\" xmlns:dsml=\"urn:oasis:names:tc:DSML:2:0:core\">\n"
            + "            <dsml:value>Jane Doe</dsml:value>\n"
            + "         </dsml:modification>\n"
            + "      </data>\n"
            + "   </modification>\n"
            + "</modifyRequest>\n";

    // ModifyRequest with namespaces for elements as follows: qualified for SPML, default for DSML
    static final String XML_QSPML_DDSML = "<spml:modifyRequest xmlns:spml=\"urn:oasis:names:tc:SPML:2:0\">\n"
            + "   <spml:psoID ID=\"CN=John Doe,OU=accounting,DC=acme.com\" targetID=\"acme.com \"/>\n"
            + "   <spml:modification>\n"
            + "      <spml:data>\n"
            + "         <modification name=\"CN\" operation=\"replace\" xmlns=\"urn:oasis:names:tc:DSML:2:0:core\">\n"
            + "            <value>Jane Doe</value>\n"
            + "         </modification>\n"
            + "      </spml:data>\n"
            + "   </spml:modification>\n"
            + "</spml:modifyRequest>\n";

    // ModifyRequest with namespaces for elements as follows: qualified for SPML, qualified for DSML
    static final String XML_QSPML_QDSML = "<spml:modifyRequest xmlns:spml=\"urn:oasis:names:tc:SPML:2:0\">\n"
            + "   <spml:psoID ID=\"CN=John Doe,OU=accounting,DC=acme.com\" targetID=\"acme.com \"/>\n"
            + "   <spml:modification>\n"
            + "      <spml:data>\n"
            + "         <dsml:modification name=\"CN\" operation=\"replace\" xmlns:dsml=\"urn:oasis:names:tc:DSML:2:0:core\">\n"
            + "            <dsml:value>Jane Doe</dsml:value>\n"
            + "         </dsml:modification>\n"
            + "      </spml:data>\n"
            + "   </spml:modification>\n"
            + "</spml:modifyRequest>\n";

    public static void main(String[] args) {
        TestRunner.run(ModifyRequestWithDsmlUnmarshallingTest.class);
    }

    public ModifyRequestWithDsmlUnmarshallingTest(String name) {
        super(name);
    }

    public void testDefaultSpmlDefaultDsml() {
        this.unmarshallMarshall(XML_DSPML_DDSML, ModifyRequest.class, "Default SPML, default DSML");
    }

    public void testDefaultSpmlQualifiedDsml() {
        this.unmarshallMarshall(XML_DSPML_QDSML, ModifyRequest.class, "Default SPML, qualified DSML");
    }

    public void testQualifiedSpmlDefaultDsml() {
        this.unmarshallMarshall(XML_QSPML_DDSML, ModifyRequest.class, "Qualified SPML, default DSML");
    }

    public void testQualifiedSpmlQualifiedDsml() {
        this.unmarshallMarshall(XML_QSPML_QDSML, ModifyRequest.class, "Qualified SPML, qualified DSML");
    }

    protected void showContents(Request req) {
        System.out.println(INDENT + "Class: " + req.getClass().getName());
        ModifyRequest mr = (ModifyRequest) req;
        Modification[] mods = mr.getModifications();
        if (mods == null)
            System.out.println(INDENT + "ModifyRequest contains no modifications");
        else {
            System.out.println(INDENT + "ModifyRequest element contains " + mods.length + " SPML modifications");
            for (int i = 0; i < mods.length; i++) {
                // Each SPML modification will have a "data" element, which contains <dsml:modification>
                // elements
                Extensible ext = mods[i].getData();
                if (ext == null)
                    System.out.println(INDENT + "SPML modification#" + i + " contains a null data element");
                else {
                    OpenContentElement[] oce = ext.getOpenContentElements();
                    System.out.println(INDENT
                            + "SPML modification#"
                            + i
                            + " contains "
                            + oce.length
                            + " open content elements");
                    for (int j = 0; j < oce.length; j++) {
                        String s = INDENT2 + "open content element #" + j + ": ";
                        if (oce[j] instanceof OCEtoMarshallableAdapter)
                            System.out.println(s
                                    + "OCEtoMarshallableAdapter instance containing: "
                                    + ((OCEtoMarshallableAdapter) oce[j]).getMarshallable());
                        else if (oce[j] instanceof OCEtoXMLStringAdapter)
                            System.out.println(s
                                    + "OCEtoXMLStringAdapter instance containing: "
                                    + ((OCEtoXMLStringAdapter) oce[j]).getXMLString());
                        else
                            System.out.println(s + oce[j]);
                    }
                }
            }
        }
    }

    protected String validate(Request req2) {
        Modification[] mods = ((ModifyRequest) req2).getModifications();
        if (mods == null || mods.length == 0)
            return "Unmarshalled ModifyRequest contains no modifications";
        else
            return null;
    }

}
