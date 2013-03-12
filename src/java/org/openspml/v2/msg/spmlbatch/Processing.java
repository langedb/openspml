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
package org.openspml.v2.msg.spmlbatch;

import org.openspml.v2.util.BasicStringEnumConstant;
import org.openspml.v2.util.EnumConstant;

import java.util.List;

/**
 * <simpleType name="ProcessingType">
 * <restriction base="string">
 * <enumeration value="sequential"/>
 * <enumeration value="parallel"/>
 * </restriction>
 * </simpleType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class Processing extends BasicStringEnumConstant {

    private static final String code_id = "$Id: Processing.java,v 1.1 2006/03/15 20:40:00 kas Exp $";

    public static final Processing SEQUENTIAL = new Processing("sequential");
    public static final Processing PARALLEL = new Processing("parallel");

    public static Processing[] getConstants() {
        List temp = EnumConstant.getEnumConstants(Processing.class);
        return (Processing[]) temp.toArray(new Processing[temp.size()]);
    }

    private Processing(String mValue) {
        super(mValue);
    }
}
