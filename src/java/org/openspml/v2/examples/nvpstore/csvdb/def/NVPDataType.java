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
package org.openspml.v2.examples.nvpstore.csvdb.def;

import org.openspml.v2.util.BasicStringEnumConstant;
import org.openspml.v2.util.EnumConstant;

import java.util.List;

public class NVPDataType extends BasicStringEnumConstant {

    private static final String code_id = "$Id: NVPDataType.java,v 1.1 2006/10/04 01:14:13 kas Exp $";

    public static final NVPDataType TEXT = new NVPDataType("T");
    public static final NVPDataType MULTITEXT = new NVPDataType("MT");
    public static final NVPDataType BINARY = new NVPDataType("B");
    public static final NVPDataType MULTIBINARY = new NVPDataType("MB");

    public static NVPDataType[] getConstants() {
        List temp = EnumConstant.getEnumConstants(NVPDataType.class);
        return (NVPDataType[]) temp.toArray(new NVPDataType[temp.size()]);
    }

    private NVPDataType(String value) {
        super(value);
    }
}
