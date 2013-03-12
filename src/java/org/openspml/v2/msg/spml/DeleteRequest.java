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


/**
 * <complexType name="DeleteRequestType">
 * <complexContent>
 * <extension base="spml:RequestType">
 * <sequence>
 * <element name="psoID" type="spml:PSOIdentifierType"/>
 * </sequence>
 * <attribute name="recursive" type="xsd:boolean" use="optional" default="false"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 7, 2006
 */
public class DeleteRequest extends BatchableRequest {

    private static final String code_id = "$Id: DeleteRequest.java,v 1.5 2006/06/29 21:01:21 rfrech Exp $";

    // <element name="psoID" type="spml:PSOIdentifierType"/>
    private PSOIdentifier m_psoID = null; // required

    // <attribute name="recursive" type="xsd:boolean" use="optional" default="false"/>
    private boolean m_recursive = false;

    public DeleteRequest() { ; }

    public DeleteRequest(String requestId,
                         ExecutionMode executionMode,
                         PSOIdentifier psoID,
                         boolean recursive) {
        super(requestId, executionMode);
        assert (psoID != null);
        m_psoID = psoID;
        m_recursive = recursive;
    }

    public PSOIdentifier getPsoID() {
        return m_psoID;
    }

    public void setPsoID(PSOIdentifier psoID) {
        assert (psoID != null);
        m_psoID = psoID;
    }

    public boolean isRecursive() {
        return m_recursive;
    }

    public void setRecursive(boolean recursive) {
        m_recursive = recursive;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeleteRequest)) return false;
        if (!super.equals(o)) return false;

        final DeleteRequest deleteRequest = (DeleteRequest) o;

        if (m_recursive != deleteRequest.m_recursive) return false;
        if (!m_psoID.equals(deleteRequest.m_psoID)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_psoID.hashCode();
        result = 29 * result + (m_recursive ? 1 : 0);
        return result;
    }
}

