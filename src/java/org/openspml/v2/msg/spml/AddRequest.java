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

import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Arrays;

/**
 * <complexType name="AddRequestType">
 * <complexContent>
 * <extension base="spml:RequestType">
 * <sequence>
 * <element name="psoID" type="spml:PSOIdentifierType" minOccurs="0"/>
 * <element name="containerID" type="spml:PSOIdentifierType" minOccurs="0"/>
 * <element name="data" type="spml:ExtensibleType" />
 * <element name="capabilityData" type="spml:CapabilityDataType" minOccurs="0" maxOccurs="unbounded"/>
 * </sequence>
 * <attribute name="targetID" type="string" use="optional"/>
 * <attribute name="returnData" type="spml:ReturnDataType" use="optional" default="everything"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 7, 2006
 */
public class AddRequest extends BatchableRequest {

    private static final String code_id = "$Id: AddRequest.java,v 1.6 2006/06/29 21:01:21 rfrech Exp $";

    // <element name="psoID" type="spml:PSOIdentifierType" minOccurs="0"/>
    private PSOIdentifier m_psoID = null;

    // <element name="containerID" type="spml:PSOIdentifierType" minOccurs="0"/>
    private PSOIdentifier m_containerID = null;

    // <element name="data" type="spml:ExtensibleType" />
    private Extensible m_data = null; // required

    // <element name="capabilityData" type="spml:CapabilityDataType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_capabilityData = new ArrayListWithType(CapabilityData.class);

    // <attribute name="targetID" type="string" use="optional"/>
    private String m_targetId = null;

    // <attribute name="returnData" type="spml:ReturnDataType" use="optional" default="everything"/>
    private ReturnData m_returnData = ReturnData.EVERYTHING;

    public AddRequest() {} ;

    public AddRequest(String requestId,
                      ExecutionMode executionMode,
                      PSOIdentifier type,
                      PSOIdentifier containerID,
                      Extensible data,
                      CapabilityData[] capabilityData,
                      String targetId,
                      ReturnData returnData) {
        super(requestId, executionMode);
        m_psoID = type;
        m_containerID = containerID;

        assert (data != null);
        m_data = data;

        if (capabilityData != null)
            m_capabilityData.addAll(Arrays.asList(capabilityData));

        m_targetId = targetId;
        m_returnData = returnData;
    }

    public PSOIdentifier getPsoID() {
        return m_psoID;
    }

    public void setPsoID(PSOIdentifier psoID) {
        m_psoID = psoID;
    }

    public PSOIdentifier getContainerID() {
        return m_containerID;
    }

    public void setContainerID(PSOIdentifier containerID) {
        m_containerID = containerID;
    }

    public Extensible getData() {
        return m_data;
    }

    public void setData(Extensible data) {
        assert (data != null);
        m_data = data;
    }

    public CapabilityData[] getCapabilityData() {
        return (CapabilityData[]) m_capabilityData.toArray(new CapabilityData[m_capabilityData.size()]);
    }

    public void addCapabilityData(CapabilityData capabilityData) {
        assert (capabilityData != null);
        m_capabilityData.add(capabilityData);
    }

    public boolean removeCapabilityData(CapabilityData capabilityData) {
        assert (capabilityData != null);
        return m_capabilityData.remove(capabilityData);
    }

    public void clearCapabilityData() {
        m_capabilityData.clear();
    }

    public String getTargetId() {
        return m_targetId;
    }

    public void setTargetId(String targetId) {
        m_targetId = targetId;
    }

    public ReturnData getReturnData() {
        return m_returnData;
    }

    public void setReturnData(ReturnData returnData) {
        m_returnData = returnData;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddRequest)) return false;
        if (!super.equals(o)) return false;

        final AddRequest addRequest = (AddRequest) o;

        if (!m_capabilityData.equals(addRequest.m_capabilityData)) return false;
        if (m_containerID != null ? !m_containerID.equals(addRequest.m_containerID) : addRequest.m_containerID != null) return false;
        if (!m_data.equals(addRequest.m_data)) return false;
        if (m_returnData != null ? !m_returnData.equals(addRequest.m_returnData) : addRequest.m_returnData != null) return false;
        if (m_targetId != null ? !m_targetId.equals(addRequest.m_targetId) : addRequest.m_targetId != null) return false;
        if (m_psoID != null ? !m_psoID.equals(addRequest.m_psoID) : addRequest.m_psoID != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_psoID != null ? m_psoID.hashCode() : 0);
        result = 29 * result + (m_containerID != null ? m_containerID.hashCode() : 0);
        result = 29 * result + (m_data != null ? m_data.hashCode() : 0);
        result = 29 * result + m_capabilityData.hashCode();
        result = 29 * result + (m_targetId != null ? m_targetId.hashCode() : 0);
        result = 29 * result + (m_returnData != null ? m_returnData.hashCode() : 0);
        return result;
    }
}