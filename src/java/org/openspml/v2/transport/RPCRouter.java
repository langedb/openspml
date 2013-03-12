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
public interface RPCRouter {

    /**
     * Called when the monitor is no longer responding to events.
     */
    public void closed(RPCRouterMonitor monitor);

    /**
     * Tell the router to use the given monitor, and
     * to register itself with the monitor as a listener.
     * <p/>
     * This allows the closed to be called by the monitor.
     * <p/>
     *
     * @param sm
     */
    public void setMonitor(RPCRouterMonitor sm);

}
