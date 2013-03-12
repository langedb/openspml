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
package org.openspml.v2.util;

import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.msg.XMLUnmarshaller;
import org.openspml.v2.msg.spml.Request;
import org.openspml.v2.msg.spml.Response;
import org.openspml.v2.provider.SPMLMarshaller;
import org.openspml.v2.util.xml.ReflectiveDOMXMLUnmarshaller;
import org.openspml.v2.util.xml.ReflectiveXMLMarshaller;

import java.util.Map;

/**
 * This is a class that we can use in the web.xml to marshall and
 * unmarshall requests and responses.  It's suitable for
 * use as a bootstrap.
 */
public class SimpleSPMLMarshaller implements SPMLMarshaller {

    private static final String code_id = "$Id: SimpleSPMLMarshaller.java,v 1.1 2006/03/15 23:56:51 kas Exp $";

    private static boolean _trace = false;

    public void init(Map map) throws Spml2Exception {

        Object trace = map.get("trace");
        if (trace != null) {
            Boolean b = new Boolean(trace.toString());
            _trace = b.booleanValue();
        }
    }

    public String getUniqueName() {
        return "SimpleSPMLMarshaller";
    }

    private XMLUnmarshaller _unmarshaller = new ReflectiveDOMXMLUnmarshaller();

    public Request unmarshallRequest(String s) throws Spml2Exception {
        try {
            if (_trace) {
                System.out.println("unmarshalling:\n " + s);
            }
            return (Request) _unmarshaller.unmarshall(s);
        }
        catch (ClassCastException cce) {
            throw new Spml2Exception(cce);
        }
    }

    private XMLMarshaller _marshaller = new ReflectiveXMLMarshaller();

    public String marshallResponse(Response response) throws Spml2Exception {
        String xml = response.toXML(_marshaller);
        if (_trace) {
            System.out.println("marshalled response to:\n" + xml);
        }
        return xml;
    }
}
