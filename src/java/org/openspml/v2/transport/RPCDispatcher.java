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
package org.openspml.v2.transport;

import java.util.Map;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Jan 20, 2006
 */
public interface RPCDispatcher {

    /**
     * Called during router initialization.
     * The map contains init parameters.  For example,
     * this might be a Map made from a ServletConfig.
     */
    public void init(Map config) throws Spml2TransportException;

    /**
     * Process a request.
     * <p/>
     * If the dispatcher does not recognize the message, it must return null.
     * If the handler recognizes the message, it must return a properly
     * formatted string of data containing the entire response; including
     * error responses.
     * <p/>
     * The dispatcher is allowed to throw any exception, it will be caught
     * by the router and converted into a generic http error response; that
     * will include the stacktrace, or a reference to where it can be found.
     */
    public String dispatchRequest(String message)  throws Spml2TransportException;

    /**
     * If you handle a request of a given type, you also need to
     * provide us with contentType string to return.
     *
     * @return String representing the type.
     */
    public String getContentType();
}

