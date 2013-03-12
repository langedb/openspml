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

import org.openspml.v2.msg.MarshallableElement;
import org.openspml.v2.msg.PrefixAndNamespaceTuple;

/**
 * A starter class for the MarshallableElements
 *
 * @author Kent Spaulding
 */
abstract public class BasicMarshallableElement implements MarshallableElement {

    protected BasicMarshallableElement() {
    }

    public static String getElementName(MarshallableElement me) {
        String elementName = me.getClass().getName();
        elementName = elementName.substring(elementName.lastIndexOf(".") + 1);
        String firstChar = elementName.substring(0, 1).toLowerCase();
        elementName = firstChar + elementName.substring(1);
        return elementName;
    }

    public String getElementName() {
        return getElementName(this);
    }

    abstract public PrefixAndNamespaceTuple[] getNamespacesInfo();

    abstract public boolean isValid();
}
