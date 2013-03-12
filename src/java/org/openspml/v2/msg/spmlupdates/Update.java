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
import org.openspml.v2.msg.spml.PSOIdentifier;

/**
 * <complexType name="UpdateType">
 * <complexContent>
 * <extension base="spml:ExtensibleType">
 * <sequence>
 * <element name="psoID" type="spml:PSOIdentifierType" />
 * </sequence>
 * <attribute name="timestamp" type="xsd:dateTime" use="required"/>
 * <attribute name="updateKind" type="spmlupdates:UpdateKindType" use="required"/>
 * <attribute name="wasUpdatedByCapability" type="xsd:string" use="optional"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class Update extends BasicRequest {

    private static final String code_id = "$Id: Update.java,v 1.4 2006/04/25 21:22:09 kas Exp $";

    //* <element name="psoID" type="spml:PSOIdentifierType" />
    private PSOIdentifier m_psoID = null; // required

    //* <attribute name="timestamp" type="xsd:dateTime" use="required"/>
    private String m_timestamp = null; // required ; TODO DateTime

    //* <attribute name="updateKind" type="spmlupdates:UpdateKindType" use="required"/>
    private UpdateKind m_updateKind = null;

    //* <attribute name="wasUpdatedByCapability" type="xsd:string" use="optional"/>
    private String m_wasUpdatedByCapability = null;

    public Update() { ; }

    public Update(String requestId,
                  ExecutionMode executionMode,
                  PSOIdentifier psoID,
                  String timestamp,
                  UpdateKind updateKind,
                  String wasUpdatedByCapability) {
        super(requestId, executionMode);
        assert (psoID != null);
        m_psoID = psoID;
        assert (timestamp != null);
        m_timestamp = timestamp;
        assert (updateKind != null);
        m_updateKind = updateKind;

        m_wasUpdatedByCapability = wasUpdatedByCapability;
    }

    public PSOIdentifier getPsoID() {
        return m_psoID;
    }

    public void setPsoID(PSOIdentifier psoID) {
        assert (psoID != null);
        m_psoID = psoID;
    }

    public String getTimestamp() {
        return m_timestamp;
    }

    public void setTimestamp(String timestamp) {
        assert (timestamp != null);
        m_timestamp = timestamp;
    }

    public UpdateKind getUpdateKind() {
        return m_updateKind;
    }

    public void setUpdateKind(UpdateKind updateKind) {
        assert (updateKind != null);
        m_updateKind = updateKind;
    }

    public String getWasUpdatedByCapability() {
        return m_wasUpdatedByCapability;
    }

    public void setWasUpdatedByCapability(String wasUpdatedByCapability) {
        m_wasUpdatedByCapability = wasUpdatedByCapability;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Update)) return false;
        if (!super.equals(o)) return false;

        final Update update = (Update) o;

        if (!m_psoID.equals(update.m_psoID)) return false;
        if (!m_timestamp.equals(update.m_timestamp)) return false;
        if (!m_updateKind.equals(update.m_updateKind)) return false;
        if (m_wasUpdatedByCapability != null ? !m_wasUpdatedByCapability.equals(update.m_wasUpdatedByCapability) : update.m_wasUpdatedByCapability != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_psoID.hashCode();
        result = 29 * result + m_timestamp.hashCode();
        result = 29 * result + m_updateKind.hashCode();
        result = 29 * result + (m_wasUpdatedByCapability != null ? m_wasUpdatedByCapability.hashCode() : 0);
        return result;
    }
}
