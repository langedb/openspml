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

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Jan 20, 2006
 */
public interface RPCRouterMonitor {

    /**
     * Called immediately before a message is sent.
     * Should include the entire payload, but not the HTTP headers.
     */
    public void send(String payload);

    /**
     * Called immediately after a message is received.
     */
    public void receive(String payload);

    /**
     * Write whatever you want into the monitor with these trace messages.
     */
    public void trace(String msg);

    /**
     * Add something interested in knowing when the monitor closes.
     */
    public void addListener(RPCRouter l);

    /**
     * Remove a previously isntalled listener.
     */
    public void removeListener(RPCRouter l);
}
