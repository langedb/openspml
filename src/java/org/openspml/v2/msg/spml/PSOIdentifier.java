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


//<complexType name="PSOIdentifierType">
//    <complexContent>
//        <extension base="spml:IdentifierType">
//            <sequence>
//                <element name="containerID" type="spml:PSOIdentifierType" minOccurs="0" />
//            </sequence>
//            <attribute name="targetID" type="string" use="optional"/>
//        </extension>
//    </complexContent>
//</complexType>

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 2, 2006
 */
public class PSOIdentifier extends Identifier {

    private static final String code_id = "$Id: PSOIdentifier.java,v 1.3 2006/04/25 21:22:09 kas Exp $";

    // <element name="containerID" type="spml:PSOIdentifierType" minOccurs="0" />
    private PSOIdentifier m_containerID = null;

    // <attribute name="targetID" type="string" use="optional"/>
    private String m_targetID = null;

    public PSOIdentifier() { ; }

    public PSOIdentifier(String anID,
                         PSOIdentifier containerID,
                         String targetID) {
        super(anID);
        m_containerID = containerID;
        m_targetID = targetID;
    }

    public PSOIdentifier getContainerID() {
        return m_containerID;
    }

    public void setContainerID(PSOIdentifier containerID) {
        m_containerID = containerID;
    }

    public String getTargetID() {
        return m_targetID;
    }

    public void setTargetID(String targetID) {
        m_targetID = targetID;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSOIdentifier)) return false;
        if (!super.equals(o)) return false;

        final PSOIdentifier psoIdentifierType = (PSOIdentifier) o;

        if (m_containerID != null ? !m_containerID.equals(psoIdentifierType.m_containerID) : psoIdentifierType.m_containerID != null) return false;
        if (m_targetID != null ? !m_targetID.equals(psoIdentifierType.m_targetID) : psoIdentifierType.m_targetID != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_containerID != null ? m_containerID.hashCode() : 0);
        result = 29 * result + (m_targetID != null ? m_targetID.hashCode() : 0);
        return result;
    }
}
