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

/**
 * <simpleType name="ModificationModeType">
 * <restriction base="string">
 * <enumeration value="add"/>
 * <enumeration value="replace"/>
 * <enumeration value="delete"/>
 * </restriction>
 * </simpleType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 7, 2006
 */
public class ModificationMode extends BasicStringEnumConstant {

    private static final String code_id = "$Id: ModificationMode.java,v 1.2 2006/05/18 00:21:44 kas Exp $";

    public static final ModificationMode ADD = new ModificationMode("add");
    public static final ModificationMode REPLACE = new ModificationMode("replace");
    public static final ModificationMode DELETE = new ModificationMode("delete");

    public static ModificationMode[] getConstants() {
        List temp = EnumConstant.getEnumConstants(ModificationMode.class);
        return (ModificationMode[]) temp.toArray(new ModificationMode[temp.size()]);
    }

    public static ModificationMode getConstant(String name) {
        return (ModificationMode) BasicStringEnumConstant.getConstant(ModificationMode.class, name);
    }

    private ModificationMode(String value) {
        super(value);
    }
}
