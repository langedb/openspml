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
package org.openspml.v2.util.xml;

import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.OCEtoMarshallableAdapter;
import org.openspml.v2.msg.OCEtoXMLStringAdapter;
import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.msg.XMLUnmarshaller;
import org.openspml.v2.util.Spml2Exception;

/**
 * Starter class.
 *
 * @author Kent Spaulding
 */
abstract public class BasicOCEAndMarshallable extends BasicMarshallable implements OpenContentElement {

    protected final XMLMarshaller mMarshaller = new ReflectiveXMLMarshaller();
    protected final XMLUnmarshaller mUnmarshaller = new ReflectiveDOMXMLUnmarshaller();

    protected BasicOCEAndMarshallable() {
    }

    // OpenContentElement implementation methods

    public String toXML(int indent) throws Spml2Exception {
        mMarshaller.setIndent(indent);
        String xml = mMarshaller.marshall(this);
        return xml;
    }

    public String toXML() throws Spml2Exception {
        return toXML(0);
    }

    /**
     * We know how to handle Strings that are passed in.
     * Other types will return a null.
     * <p/>
     * Override this if you want to support more types for xmlRep.
     *
     * @param xmlRep
     * @return  an object, in this case, an adapter.
     * @throws org.openspml.v2.util.Spml2Exception
     */
    public OpenContentElement fromXML(Object xmlRep) throws Spml2Exception {
        if (xmlRep instanceof String) {
            Marshallable m = null;
            try {
                m = mUnmarshaller.unmarshall(xmlRep.toString());
                if (m != null) {
                    return new OCEtoMarshallableAdapter(m);
                }
            }
            catch (UnknownSpml2TypeException e) {
                return new OCEtoXMLStringAdapter(xmlRep.toString());
            }
        }
        return null;
    }
}
