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
package org.openspml.v2.examples.nvpstore.csvdb.def;

import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.PrefixAndNamespaceTuple;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.xml.ObjectFactory;
import org.openspml.v2.util.xml.ReflectiveDOMXMLUnmarshaller;
import org.openspml.v2.util.xml.ReflectiveXMLMarshaller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NVPStoreDefMarshallableCreator implements ObjectFactory.MarshallableCreator {

    private static final String code_id = "$Id: NVPStoreDefMarshallableCreator.java,v 1.3 2006/10/06 17:47:57 kas Exp $";

    private static final String URI = "urn:org:openspml:v2:examples:nvpstore:csvdb:def";
    private static final String PREFIX = "ods";

    /**
     * For subelements to access
     *
     * @return the namespaces info
     */
    static PrefixAndNamespaceTuple[] staticGetNamespacesInfo() {
        return new PrefixAndNamespaceTuple[]{
                new PrefixAndNamespaceTuple(PREFIX, URI),
        };
    }

    public Marshallable createMarshallable(String nameAndPrefix, String uri) throws Spml2Exception {

        if (URI.equals(uri)) {
            int idx = nameAndPrefix.indexOf("objectstoredef");
            if (idx >= 0) {
                return new NVPObjectStoreDef();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {

            BufferedReader in = null;
            StringBuffer xml = new StringBuffer();
            try {
                File file = new File(args[0]);
                in = new BufferedReader(new FileReader(file));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    xml.append(inputLine);
                    xml.append("\n");
                }
            }
            catch (IOException e) {
                throw new Spml2Exception("Bad URL? " + args[0], e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    }
                    catch (IOException e) {
                        // ignore this.
                    }
                }
            }

            ObjectFactory.getInstance().addCreator(new NVPStoreDefMarshallableCreator());

            // parse the XML
            NVPObjectStoreDef osd = (NVPObjectStoreDef) (new ReflectiveDOMXMLUnmarshaller()).unmarshall(xml.toString());
            System.out.println(osd.toXML(new ReflectiveXMLMarshaller()));
        }
        catch (Spml2Exception e) {
            e.printStackTrace();
        }
    }

}
