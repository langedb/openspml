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
 *
 * Use is subject to license terms.
 */
package org.openspml.v2.msg;


/**
 * We need classes to return some info about the namespaces that they use.
 */
public class PrefixAndNamespaceTuple {

    public final String prefix;
    public final String namespaceURI;
    public final boolean isDefault;

    public PrefixAndNamespaceTuple(String prefix,
                                   String namespaceURI,
                                   boolean isDefault) {
        this.prefix = prefix;
        this.namespaceURI = namespaceURI;
        this.isDefault = isDefault;
    }

    public PrefixAndNamespaceTuple(String prefix, String namespaceURI) {
        this(prefix, namespaceURI, false);
    }

    // make these easy to make unique
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrefixAndNamespaceTuple)) return false;

        final PrefixAndNamespaceTuple prefixAndNamespaceTuple = (PrefixAndNamespaceTuple) o;

        if (!prefix.equals(prefixAndNamespaceTuple.prefix)) return false;

        return true;
    }

    public int hashCode() {
        return prefix.hashCode();
    }

    public static PrefixAndNamespaceTuple[] concatNamespacesInfo(PrefixAndNamespaceTuple[] first,
                                                                 PrefixAndNamespaceTuple[] second) {

        PrefixAndNamespaceTuple[] res = new PrefixAndNamespaceTuple[first.length + second.length];
        System.arraycopy(first, 0, res, 0, first.length);
        System.arraycopy(second, 0, res, first.length, second.length);
        return res;
    }
}
