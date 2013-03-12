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
package org.openspml.v2.profiles;

import org.openspml.v2.profiles.dsml.DSMLUnmarshaller;
import org.openspml.v2.profiles.spmldsml.DSMLMarshallableCreator;
import org.openspml.v2.util.xml.ObjectFactory;
import org.openspml.v2.util.Spml2Exception;

import java.net.URI;
import java.net.URISyntaxException;

public class DSMLProfileRegistrar implements ObjectFactory.ProfileRegistrar {

    private static final String code_id = "$Id: DSMLProfileRegistrar.java,v 1.6 2006/10/04 01:06:12 kas Exp $";

    private ObjectFactory.OCEUnmarshaller mDsml = null;
    private ObjectFactory.MarshallableCreator mSpmlDsml = null;

    /**
     * If you extended DSMLUnmarshaller and want to use that
     * implementation, extend this class too and the new subclass
     * as your Registrar.
     * <p>
     * @return a new ObjectFactory.OCEUnmarshaller
     */
    protected ObjectFactory.OCEUnmarshaller createDSMLUnmarshaller() {
        return new DSMLUnmarshaller();
    }

    /**
     * If you implemented your own creator for DSML and want to
     * use that implementation; extend this class and override this method.
     * Use the new subclass as the Registrar.
     * <p>
     * @return a new DSMLUnmarshaller
     */
    protected ObjectFactory.MarshallableCreator createDSMLMarshallableCreator() {
        return new DSMLMarshallableCreator();
    }

    public DSMLProfileRegistrar() {
        mDsml = createDSMLUnmarshaller();
        mSpmlDsml = createDSMLMarshallableCreator();
    }

    public static final String PROFILE_URI_STRING = "urn:oasis:names:tc:SPML:2:0:DSML";
    public static final String PROFILE_ID = "SPMLv2 DSMLv2 Profile";

    public String getProfileId() {
        return PROFILE_ID;
    }

    public URI getProfileURI() throws Spml2Exception {
        try {
            return new URI(PROFILE_URI_STRING);
        }
        catch (URISyntaxException e) {
            throw new Spml2Exception(e);
        }
    }

    public void register(ObjectFactory of) {
        of.addOCEUnmarshaller(mDsml);
        of.addCreator(mSpmlDsml);
    }

    public void unregister(ObjectFactory of) {
        of.removeOCEUnmarshaller(mDsml);
        of.removeCreator(mSpmlDsml);
    }
}
