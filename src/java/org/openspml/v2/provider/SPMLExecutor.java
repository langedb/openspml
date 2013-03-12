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
public interface SPMLExecutor {

    /**
     * Called once, during initilazation.  The map might have keys of
     * interest.  The protocol of key names is private, between the
     * Dispatcher and the implementations it expects to use.
     *
     * @param params
     * @throws Spml2Exception
     */
    public void init(Map params) throws Spml2Exception;

    /**
     * And away we go - we saw a request.
     *
     * @param request The request to be executed.
     * @return The response after handling the request.
     * @throws Spml2Exception
     */
    public Response execute(Request request) throws Spml2Exception;

    /**
     * A unique name (per servlet) can be used to pass in arguments, for example
     *
     */
    public String getUniqueName();

}
