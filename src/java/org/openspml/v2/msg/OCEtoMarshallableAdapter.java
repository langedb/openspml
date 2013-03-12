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
import org.openspml.v2.util.xml.ReflectiveDOMXMLUnmarshaller;
import org.openspml.v2.util.xml.ReflectiveXMLMarshaller;

/**
 * This class is placed around "open content" when we encounter XML
 * during unmarshalling that we don't recognize.
 */
public class OCEtoMarshallableAdapter implements OpenContentElementAdapter {

    private static final String code_id = "$Id: OCEtoMarshallableAdapter.java,v 1.4 2006/05/03 23:28:30 kas Exp $";

    private XMLUnmarshaller mUnmarshaller = new ReflectiveDOMXMLUnmarshaller();
    private XMLMarshaller mMarshaller = new ReflectiveXMLMarshaller();

    private final Marshallable mAdaptedMarshallable;

    public OCEtoMarshallableAdapter(Marshallable m) throws Spml2Exception {
        if (m == null) {
            throw new Spml2Exception("Cannot adapt a null as a Marshallable.");
        }
        mAdaptedMarshallable = m;
    }

    public Marshallable getMarshallable() {
        return mAdaptedMarshallable;
    }

    public Object getAdaptedObject() {
        return getMarshallable();
    }

    public String toXML() throws Spml2Exception {
        return toXML(0);
    }

    public String toXML(int indent) throws Spml2Exception {
        mMarshaller.setIndent(indent);
        return mAdaptedMarshallable.toXML(mMarshaller);
    }

    public OpenContentElement fromXML(Object xmlRep) throws Spml2Exception {
        if (xmlRep instanceof String) {
            return new OCEtoMarshallableAdapter(mUnmarshaller.unmarshall((String) xmlRep));
        }
        throw new Spml2Exception("Expected a String as the xmlRep.");
    }
}
