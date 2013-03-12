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
package org.openspml.v2.profiles.spmldsml;

import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.PrefixAndNamespaceTuple;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.xml.ObjectFactory;
import org.openspml.v2.profiles.DSMLProfileRegistrar;

public class DSMLMarshallableCreator implements ObjectFactory.MarshallableCreator {

    private static final String code_id = "$Id: DSMLMarshallableCreator.java,v 1.5 2006/10/04 01:06:12 kas Exp $";

    private static final String DSML_PREFIX = "spmldsml";

    static PrefixAndNamespaceTuple[] staticGetNamespacesInfo() {
        return new PrefixAndNamespaceTuple[]{
            new PrefixAndNamespaceTuple(DSML_PREFIX, DSMLProfileRegistrar.PROFILE_URI_STRING),
        };
    }

    public Marshallable createMarshallable(String nameAndPrefix, String uri) throws Spml2Exception {

        if (DSMLProfileRegistrar.PROFILE_URI_STRING.equals(uri)) {
            int idx = nameAndPrefix.indexOf("schema");
            if (idx >= 0) {
                return new DSMLSchema();
            }
        }
        return null;
    }
}
