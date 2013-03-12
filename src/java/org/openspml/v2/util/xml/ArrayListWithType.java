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
package org.openspml.v2.util.xml;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For element fields that are 0,m - we want to convey type information.
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Mar 3, 2006
 */
public class ArrayListWithType extends ArrayList implements ListWithType {

    private static final String code_id = "$Id: ArrayListWithType.java,v 1.1 2006/03/15 20:40:00 kas Exp $";

    private final Class mType;

    public ArrayListWithType(Class type) {
        super();
        mType = type;
    }

    public ArrayListWithType(Class type, int initialCapacity) {
        super(initialCapacity);
        mType = type;
    }

    public ArrayListWithType(Class type, Collection c) {
        super(c);
        mType = type;
    }

    public Class getType() {
        return mType;
    }
}
