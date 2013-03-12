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

//<simpleType name="StatusCodeType">
//    <restriction base="string">
//        <enumeration value="success"/>
//        <enumeration value="failure"/>
//        <enumeration value="pending"/>
//    </restriction>
//</simpleType>

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 1, 2006
 */
public final class StatusCode extends BasicStringEnumConstant {

    private static final String code_id = "$Id: StatusCode.java,v 1.1 2006/03/15 20:40:00 kas Exp $";

    public static final StatusCode SUCCESS = new StatusCode("success");
    public static final StatusCode FAILURE = new StatusCode("failure");
    public static final StatusCode PENDING = new StatusCode("pending");

    public static StatusCode[] getConstants() {
        List temp = EnumConstant.getEnumConstants(StatusCode.class);
        return (StatusCode[]) temp.toArray(new StatusCode[temp.size()]);
    }

    private StatusCode(String value) {
        super(value);
    }
}
