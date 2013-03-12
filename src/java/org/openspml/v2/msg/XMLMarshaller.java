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
 * Visitor Pattern - used to create XML from Marshallable objects.
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 9, 2006
 */
public interface XMLMarshaller {

    public static final String code_id = "$Id: XMLMarshaller.java,v 1.3 2006/05/17 20:59:20 kas Exp $";

    /**
     * Indent the XML by this amount.
     * 
     * @param indent
     * @throws Spml2Exception
     */
    public void setIndent(int indent) throws Spml2Exception;

    /**
     * Marshall with a 0 indent.
     *
     * @param object
     * @return xml fragment
     * @throws Spml2Exception
     */
    public String marshall(Marshallable object) throws Spml2Exception;
}
