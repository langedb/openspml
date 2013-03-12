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
package org.openspml.v2.util;

import java.util.List;

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public abstract class BasicStringEnumConstant extends EnumConstant {

    private static final String code_id = "$Id: BasicStringEnumConstant.java,v 1.2 2006/08/30 18:02:59 kas Exp $";

    private final String mValue;

    protected BasicStringEnumConstant(String mValue) {
        this.mValue = mValue;
    }

    public final String toString() {
        return mValue;
    }

    public static EnumConstant getConstant(Class cls, String value) {
        List temp = EnumConstant.getEnumConstants(cls);
        for (int k = 0; k < temp.size(); k++) {
            EnumConstant ec = (EnumConstant) temp.get(k);
            if (ec.toString().equals(value)) {
                return ec;
            }
        }
        return null;
    }
}
