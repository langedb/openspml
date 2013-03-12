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

import org.openspml.v2.msg.spml.ExecutionMode;
import org.openspml.v2.msg.spmlsearch.Query;

/**
 * <complexType name="BulkDeleteRequestType">
 * <complexContent>
 * <extension base="spml:RequestType">
 * <sequence>
 * <element ref="spmlsearch:query" />
 * </sequence>
 * <attribute name="recursive" type="boolean" use="optional"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class BulkDeleteRequest extends BasicRequest {

    private static final String code_id = "$Id: BulkDeleteRequest.java,v 1.3 2006/04/25 21:22:09 kas Exp $";

    // <attribute name="recursive" type="boolean" use="optional"/>
    private Boolean m_recursive = null;

    protected BulkDeleteRequest(String requestId,
                                ExecutionMode executionMode,
                                Query ref,
                                Boolean recursive) {
        super(requestId, executionMode, ref);
        m_recursive = recursive;
    }

    public BulkDeleteRequest() { ; }

    public BulkDeleteRequest(String requestId,
                             ExecutionMode executionMode,
                             Query ref) {
        this(requestId, executionMode, ref, null);
    }

    public BulkDeleteRequest(String requestId,
                             ExecutionMode executionMode,
                             Query ref,
                             boolean recursive) {
        this(requestId, executionMode, ref, new Boolean(recursive));
    }

    public boolean getRecursive() {
        return m_recursive == null ? false : m_recursive.booleanValue();
    }

    public void setRecursive(boolean recursive) {
        m_recursive = new Boolean(recursive);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BulkDeleteRequest)) return false;
        if (!super.equals(o)) return false;

        final BulkDeleteRequest bulkDeleteRequest = (BulkDeleteRequest) o;

        if (m_recursive != null ? !m_recursive.equals(bulkDeleteRequest.m_recursive) : bulkDeleteRequest.m_recursive != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_recursive != null ? m_recursive.hashCode() : 0);
        return result;
    }
}
