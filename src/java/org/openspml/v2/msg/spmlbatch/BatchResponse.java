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
package org.openspml.v2.msg.spmlbatch;

import org.openspml.v2.msg.PrefixAndNamespaceTuple;
import org.openspml.v2.msg.spml.ErrorCode;
import org.openspml.v2.msg.spml.Response;
import org.openspml.v2.msg.spml.StatusCode;
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Collections;
import java.util.List;

/**
 * <complexType name="BatchResponseType">
 * <complexContent>
 * <extension base="spml:ResponseType">
 * <annotation>
 * <documentation>Elements that extend spml:ResponseType</documentation>
 * </annotation>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class BatchResponse extends Response {

    private static final String code_id = "$Id: BatchResponse.java,v 1.7 2006/08/30 18:02:59 kas Exp $";

    private ListWithType m_response = new ArrayListWithType(Response.class);

    public BatchResponse() { ; }

    public BatchResponse(String[] errorMessages,
                         StatusCode status,
                         String requestId,
                         ErrorCode errorCode) {
        super(errorMessages, status, requestId, errorCode);
    }

    public void addResponse(Response resp) {
        if (resp != null)
            m_response.add(resp);
    }

    public boolean removeResponse(Response resp) {
        return m_response.remove(resp);
    }

    public void clearResponses() {
        m_response.clear();
    }
    
    public List getResponses() {
    	return Collections.unmodifiableList(m_response);
    }

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        return PrefixAndNamespaceTuple.concatNamespacesInfo(super.getNamespacesInfo(),
                                                            NamespaceDefinitions.getMarshallableNamespacesInfo());
    }
}
