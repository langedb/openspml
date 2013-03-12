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
import org.openspml.v2.msg.spml.AddRequest;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.msg.spml.Request;

/**
 * @author Blaine Busler
 */
public class AddRequestWithDsmlUnmarshallingTest extends RequestsWithDsmlTestBase {

    // AddRequest with namespaces for elements as follows: default for SPML, default for DSML
    static final String XML_DSPML_DDSML = "<addRequest xmlns='urn:oasis:names:tc:SPML:2:0'>\n"
                                          + "   <containerID  ID='OU=accounting,DC=acme.com' targetID='acme.com'/>\n"
                                          + "   <data>\n"
                                          + "      <attr name='CN' xmlns='urn:oasis:names:tc:DSML:2:0:core'>\n"
                                          + "         <value>John Doe</value>\n"
                                          + "      </attr>\n"
                                          + "      <attr name='uid' xmlns='urn:oasis:names:tc:DSML:2:0:core'>\n"
                                          + "         <value>jdoe</value>\n"
                                          + "      </attr>\n"
                                          + "      <attr name='objectclass' xmlns='urn:oasis:names:tc:DSML:2:0:core'>\n"
                                          + "         <value>user</value>\n"
                                          + "      </attr>\n"
                                          + "   </data>\n"
                                          + "</addRequest>\n";

    // AddRequest with namespaces for elements as follows: default for SPML, qualified for DSML
    static final String XML_DSPML_QDSML = "<addRequest xmlns='urn:oasis:names:tc:SPML:2:0'>\n"
                                          + "   <containerID  ID='OU=accounting,DC=acme.com' targetID='acme.com'/>\n"
                                          + "   <data>\n"
                                          + "      <qdsml:attr name='CN' xmlns:qdsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
                                          + "         <qdsml:value>John Doe</qdsml:value>\n"
                                          + "      </qdsml:attr>\n"
                                          + "      <qdsml:attr name='uid' xmlns:qdsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
                                          + "         <qdsml:value>jdoe</qdsml:value>\n"
                                          + "      </qdsml:attr>\n"
                                          + "      <qdsml:attr name='objectclass' xmlns:qdsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
                                          + "         <qdsml:value>user</qdsml:value>\n"
                                          + "      </qdsml:attr>\n"
                                          + "   </data>\n"
                                          + "</addRequest>\n";

    // AddRequest with namespaces for elements as follows: qualified for SPML, default for DSML
    static final String XML_QSPML_DDSML = "<qspml:addRequest xmlns:qspml='urn:oasis:names:tc:SPML:2:0'>\n"
                                          + "   <qspml:containerID  ID='OU=accounting,DC=acme.com' targetID='acme.com'/>\n"
                                          + "   <qspml:data>\n"
                                          + "      <attr name='CN' xmlns='urn:oasis:names:tc:DSML:2:0:core'>\n"
                                          + "         <value>John Doe</value>\n"
                                          + "      </attr>\n"
                                          + "      <attr name='uid' xmlns='urn:oasis:names:tc:DSML:2:0:core'>\n"
                                          + "         <value>jdoe</value>\n"
                                          + "      </attr>\n"
                                          + "      <attr name='objectclass' xmlns='urn:oasis:names:tc:DSML:2:0:core'>\n"
                                          + "         <value>user</value>\n"
                                          + "      </attr>\n"
                                          + "   </qspml:data>\n"
                                          + "</qspml:addRequest>\n";

    // AddRequest with namespaces for elements as follows: qualified for SPML, qualified for DSML
    static final String XML_QSPML_QDSML =
            ""
            + "<qspml:addRequest xmlns:qspml='urn:oasis:names:tc:SPML:2:0'>\n"
            + "   <qspml:containerID  ID='OU=accounting,DC=acme.com' targetID='acme.com'/>\n"
            + "   <qspml:data>\n"
            + "      <qdsml:attr name='CN' xmlns:qdsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "         <qdsml:value>John Doe</qdsml:value>\n"
            + "      </qdsml:attr>\n"
            + "      <qdsml:attr name='uid' xmlns:qdsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "         <qdsml:value>jdoe</qdsml:value>\n"
            + "      </qdsml:attr>\n"
            + "      <qdsml:attr name='objectclass' xmlns:qdsml='urn:oasis:names:tc:DSML:2:0:core'>\n"
            + "         <qdsml:value>user</qdsml:value>\n"
            + "      </qdsml:attr>\n"
            + "   </qspml:data>\n"
            + "</qspml:addRequest>\n";

    public static void main(String[] args) {
        TestRunner.run(AddRequestWithDsmlUnmarshallingTest.class);
    }

    public AddRequestWithDsmlUnmarshallingTest(String name) {
        super(name);
    }

    public void testDefaultSpmlDefaultDsml() {
        this.unmarshallMarshall(XML_DSPML_DDSML, AddRequest.class, "Default SPML, default DSML");
    }

    public void testDefaultSpmlQualifiedDsml() {
        this.unmarshallMarshall(XML_DSPML_QDSML, AddRequest.class, "Default SPML, qualified DSML");
    }

    public void testQualifiedSpmlDefaultDsml() {
        this.unmarshallMarshall(XML_QSPML_DDSML, AddRequest.class, "Qualified SPML, default DSML");
    }

    public void testQualifiedSpmlQualifiedDsml() {
        this.unmarshallMarshall(XML_QSPML_QDSML, AddRequest.class, "Qualified SPML, qualified DSML");
    }

    protected void showContents(Request req) {
        AddRequest ar = (AddRequest) req;
        Extensible ext = ar.getData();
        if (ext == null)
            System.out.println(INDENT + "AddRequest contains no data element");
        else if (ext.getOpenContentElements() == null)
            System.out.println(INDENT + "AddRequest data element contains no elements");
        else {
            OpenContentElement[] oce = ext.getOpenContentElements();
            System.out.println(INDENT + "AddRequest data element contains " + oce.length + " open content elements");
            for (int i = 0; i < oce.length; i++) {
                String s = INDENT + "data open content element #" + i + ": ";
                if (oce[i] instanceof OCEtoMarshallableAdapter)
                    System.out.println(s
                                       + "OCEtoMarshallableAdapter instance containing: "
                                       + ((OCEtoMarshallableAdapter) oce[i]).getMarshallable());
                else if (oce[i] instanceof OCEtoXMLStringAdapter)
                    System.out.println(s
                                       + "OCEtoXMLStringAdapter instance containing: "
                                       + ((OCEtoXMLStringAdapter) oce[i]).getXMLString());
                else
                    System.out.println(s + oce[i]);
            }
        }
    }

    protected String validate(Request req2) {
        if (((AddRequest) req2).getData() == null)
            return "Unmarshalled AddRequest contains no data element";
        else
            return null;
    }

}
