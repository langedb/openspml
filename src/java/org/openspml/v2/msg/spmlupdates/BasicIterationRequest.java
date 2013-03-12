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

import org.openspml.v2.msg.spml.ExecutionMode;

/**
 * <complexType name="CloseIteratorRequestType">
 * <complexContent>
 * <extension base="spml:RequestType">
 * <sequence>
 * <element name="iterator" type="spmlupdates:ResultsIteratorType"/>
 * </sequence>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
abstract class BasicIterationRequest extends BasicRequest {

    private static final String code_id = "$Id: BasicIterationRequest.java,v 1.3 2006/04/21 23:09:02 kas Exp $";

    //* <element name="iterator" type="spmlupdates:ResultsIteratorType"/>
    private ResultsIterator m_iterator = null; // required

    protected BasicIterationRequest() { ; }

    public BasicIterationRequest(String requestId,
                                 ExecutionMode executionMode,
                                 ResultsIterator iterator) {
        super(requestId, executionMode);
        assert (iterator != null);
        m_iterator = iterator;
    }

    public ResultsIterator getIterator() {
        return m_iterator;
    }

    public void setIterator(ResultsIterator iterator) {
        assert (iterator != null);
        m_iterator = iterator;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicIterationRequest)) return false;
        if (!super.equals(o)) return false;

        final BasicIterationRequest closeIteratorRequest = (BasicIterationRequest) o;

        if (m_iterator != null ? !m_iterator.equals(closeIteratorRequest.m_iterator) : closeIteratorRequest.m_iterator != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_iterator != null ? m_iterator.hashCode() : 0);
        return result;
    }
}
