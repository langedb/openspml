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
package org.openspml.v2.msg.spml;

import org.openspml.v2.util.BasicStringEnumConstant;
import org.openspml.v2.util.EnumConstant;

import java.util.List;

//<simpleType name="ReturnDataType">
//    <restriction base="string">
//        <enumeration value="identifier"/>
//        <enumeration value="data"/>
//        <enumeration value="everything"/>
//    </restriction>
//</simpleType>

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 1, 2006
 */
public final class ReturnData extends BasicStringEnumConstant {

    private static final String code_id = "$Id: ReturnData.java,v 1.1 2006/03/15 20:40:00 kas Exp $";

    public static final ReturnData IDENTIFIER = new ReturnData("identifier");
    public static final ReturnData DATA = new ReturnData("data");
    public static final ReturnData EVERYTHING = new ReturnData("everything");

    public static ReturnData[] getConstants() {
        List temp = EnumConstant.getEnumConstants(ReturnData.class);
        return (ReturnData[]) temp.toArray(new ReturnData[temp.size()]);
    }

    private ReturnData(String value) {
        super(value);
    }
}
