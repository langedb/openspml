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

import org.openspml.v2.util.Spml2Exception;

/**
 * We  are unmarshalling XML to objects and cannot convert
 * an Element to objects, we'll assume it is openContent and
 * put it into an adapter the maintains it in String form.
 * That should allow the client to do whatever additional parsing
 * is necessary.
 */
public class OCEtoXMLStringAdapter implements OpenContentElementAdapter {

    private static final String code_id = "$Id: OCEtoXMLStringAdapter.java,v 1.6 2006/05/05 15:58:30 kas Exp $";

    private static final boolean COMMENT_STRINGS = true;

    private final String mXml;

    public OCEtoXMLStringAdapter(String xml) throws Spml2Exception {
        if (xml == null) {
            throw new Spml2Exception("Cannot adapt a null String as an OpenContentElement.");
        }
        mXml = xml;
    }

    public String getXMLString() {
        return mXml;
    }

    public Object getAdaptedObject() {
        return getXMLString();
    }

    public String toXML(int indent) throws Spml2Exception {
        // TODO: we could indent each line, but we're not going to for now.
        return toXML();
    }

    public String toXML() throws Spml2Exception {
        String result = mXml;
        if (COMMENT_STRINGS) {
            String  begin = "<!-- Begin Adapted OpenContent String -->\n";
            String  end   = "<!-- End Adapted OpenContent String -->\n";
            result = begin + mXml + end;
        }
        return result;
    }

    public OpenContentElement fromXML(Object xmlRep) throws Spml2Exception {
        if (xmlRep instanceof String) {
            return new OCEtoXMLStringAdapter((String) xmlRep);
        }
        throw new Spml2Exception("Expected a String as the xmlRep.");
    }
}
