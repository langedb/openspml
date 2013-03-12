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
package org.openspml.v2.provider;

import org.openspml.v2.msg.spml.Request;
import org.openspml.v2.msg.spml.Response;
import org.openspml.v2.util.Spml2Exception;

import java.util.Map;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Jan 26, 2006
 */
public interface SPMLMarshaller {

    /**
     * Called during initialization.
     */
    public void init(Map config) throws Spml2Exception;

    /**
     * Marshaller implementations should provide a unique name.
     *
     * @return get us a name to know this object by...
     */
    public String getUniqueName();

    /**
     * Process a request.
     * <p/>
     * This is expected to take XML (or some other text format) and convert
     * it to and from the Java classes in the msg package.
     * <p/>
     * If the marshaller does not recognize the request, it must
     * return null.
     * <p/>
     * If the marshaller recognizes the message, it must return a properly
     * formatted string (likely of XML) containing the entire response; including
     * error responses.
     * <p/>
     * The marshaller is expected to throw any exception, but should catch SPMLExceptions
     * and return the approriately formatted response.  Other exceptions will be handled
     * in the upper levels.
     */
    public Request unmarshallRequest(String message) throws Spml2Exception;

    public String marshallResponse(Response responseType) throws Spml2Exception;
}
