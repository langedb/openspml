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
import org.openspml.v2.util.Spml2Exception;

class OperationalNameValuePairCreator implements ObjectFactory.MarshallableCreator {

    private static final String code_id = "$Id: OperationalNameValuePairCreator.java,v 1.1 2006/05/15 23:31:00 kas Exp $";

    protected static final String OP_ATTR_URI = "urn:org:openspml:v2:util:xml";
    protected static final String OP_ATTR_PREFIX = "openspml";

    public Marshallable createMarshallable(String nameAndPrefix, String uri) throws Spml2Exception {
        if (OP_ATTR_URI.equals(uri)) {
            if ((nameAndPrefix.indexOf("operationalNameValuePair")) >= 0) {
                return new OperationalNameValuePair();
            }
        }
        return null;
    }
}

