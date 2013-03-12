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
package org.openspml.v2.msg.spmlupdates;

import org.openspml.v2.util.BasicStringEnumConstant;
import org.openspml.v2.util.EnumConstant;

import java.util.List;

/**
 * <simpleType name="UpdateKindType">
 * <restriction base="string">
 * <enumeration value="add"/>
 * <enumeration value="modify"/>
 * <enumeration value="delete"/>
 * <enumeration value="capability"/>
 * </restriction>
 * </simpleType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class UpdateKind extends BasicStringEnumConstant {

    private static final String code_id = "$Id: UpdateKind.java,v 1.1 2006/03/15 20:40:00 kas Exp $";

    private UpdateKind(String val) {
        super(val);
    }

    //* <enumeration value="add"/>
    public static final UpdateKind ADD = new UpdateKind("add");
    //* <enumeration value="modify"/>
    public static final UpdateKind MODIFY = new UpdateKind("modify");
    //* <enumeration value="delete"/>
    public static final UpdateKind DELETE = new UpdateKind("delete");
    //* <enumeration value="capability"/>
    public static final UpdateKind CAPABILITY = new UpdateKind("capability");

    public static UpdateKind[] getConstants() {
        List temp = EnumConstant.getEnumConstants(UpdateKind.class);
        return (UpdateKind[]) temp.toArray(new UpdateKind[temp.size()]);
    }
}
