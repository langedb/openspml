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
package org.openspml.v2.msg.spmlbulk;

import org.openspml.v2.msg.PrefixAndNamespaceTuple;
import org.openspml.v2.msg.spml.ExecutionMode;
import org.openspml.v2.msg.spml.Request;
import org.openspml.v2.msg.spmlsearch.Query;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * From both of the request types in this package.
 * // <element ref="spmlsearch:query" />
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 15, 2006
 */
abstract class BasicRequest extends Request {

    private static final String code_id = "$Id: BasicRequest.java,v 1.5 2006/08/01 21:46:54 rfrech Exp $";

    // <element ref="spmlsearch:query" />
    private Query m_ref = null; // required

    protected BasicRequest() { ; }

    protected BasicRequest(String requestId,
                           ExecutionMode executionMode,
                           Query ref) {
        super(requestId, executionMode);
        assert (ref != null);
        m_ref = ref;
    }

    public Query getQuery() {
    	return m_ref;
    }
    
    public void setQuery(Query _ref) {
    	m_ref = _ref;
    }
    
    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        PrefixAndNamespaceTuple[] ours =
                PrefixAndNamespaceTuple.concatNamespacesInfo(super.getNamespacesInfo(),
                                                             NamespaceDefinitions.getMarshallableNamespacesInfo());

        // walk over all the items in ref and find the tuples...
        Set all = new LinkedHashSet(Arrays.asList(ours));
        if (m_ref != null) {
            all.addAll(Arrays.asList(m_ref.getNamespacesInfo()));
        }
        return (PrefixAndNamespaceTuple[]) all.toArray(new PrefixAndNamespaceTuple[all.size()]);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicRequest)) return false;
        if (!super.equals(o)) return false;

        final BasicRequest basicRequest = (BasicRequest) o;

        if (m_ref != null ? !m_ref.equals(basicRequest.m_ref) : basicRequest.m_ref != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_ref != null ? m_ref.hashCode() : 0);
        return result;
    }
}
