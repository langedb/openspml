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

/**
 * The objects that can redispatch themselves to an XMLMarshaller
 * will implement this.  These are the top-level SPML objects, like
 * AddRequest and AddResponse.
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 10, 2006
 */
public interface Marshallable extends MarshallableElement {

    public static final String code_id = "$Id: Marshallable.java,v 1.5 2006/05/17 20:59:20 kas Exp $";

    /**
     * Visitor pattern - dispatch this type to the visitor (m) please.
     *
     * @param m
     * @return xml
     * @throws Spml2Exception
     */
    public String toXML(XMLMarshaller m) throws Spml2Exception;

}
