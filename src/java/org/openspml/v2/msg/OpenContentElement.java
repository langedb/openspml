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
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 1, 2006
 */
public interface OpenContentElement {

    public static final String code_id = "$Id: OpenContentElement.java,v 1.7 2006/08/30 18:02:59 kas Exp $";

    /**
     * Convert this object to an XML string.
     *
     * @param indent how much indent to start with.
     * @return indented xml fragment
     * @throws Spml2Exception
     */
    public String toXML(int indent) throws Spml2Exception;

    /**
     * Would usually call toXml(int) with 0 indent.
     *
     * @return xml fragment
     * @throws Spml2Exception
     */
    public String toXML() throws Spml2Exception;

}
