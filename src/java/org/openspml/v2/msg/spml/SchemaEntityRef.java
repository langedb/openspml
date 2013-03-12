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
 * <complexType name="SchemaEntityRefType">
 * <complexContent>
 * <extension base="spml:ExtensibleType">
 * <attribute name="targetID" type="string" use="optional"/>
 * <attribute name="entityName" type="string" use="optional" />
 * <attribute name="isContainer" type="xsd:boolean" use="optional" />
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 7, 2006
 */
public class SchemaEntityRef extends Extensible {

    private static final String code_id = "$Id: SchemaEntityRef.java,v 1.4 2006/04/25 21:22:09 kas Exp $";

    // <attribute name="targetID" type="string" use="optional"/>
    private String m_targetID = null;

    // <attribute name="entityName" type="string" use="optional" />
    private String m_entityName = null;

    // <attribute name="isContainer" type="xsd:boolean" use="optional" />
    private Boolean m_isContainer = null;

    protected SchemaEntityRef(String targetID,
                              String entityName,
                              Boolean isContainer) {
        m_targetID = targetID;
        m_entityName = entityName;
        m_isContainer = isContainer;
    }

    public SchemaEntityRef() { ;  }

    public SchemaEntityRef(String targetID,
                           String entityName,
                           boolean isContainer) {
        this(targetID, entityName, new Boolean(isContainer));
    }


    public SchemaEntityRef(String targetID,
                           String entityName) {
        this(targetID, entityName, null);
    }

    public String getTargetID() {
        return m_targetID;
    }

    public void setTargetID(String targetID) {
        m_targetID = targetID;
    }

    public String getEntityName() {
        return m_entityName;
    }

    public void setEntityName(String entityName) {
        m_entityName = entityName;
    }

    public boolean isContainer() {
        if (m_isContainer == null) return false;
        return m_isContainer.booleanValue();
    }

    public void setIsContainer(boolean isContainer) {
        m_isContainer = new Boolean(isContainer);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchemaEntityRef)) return false;
        if (!super.equals(o)) return false;

        final SchemaEntityRef schemaEntityRef = (SchemaEntityRef) o;

        if (m_entityName != null ? !m_entityName.equals(schemaEntityRef.m_entityName) : schemaEntityRef.m_entityName != null) return false;
        if (m_isContainer != null ? !m_isContainer.equals(schemaEntityRef.m_isContainer) : schemaEntityRef.m_isContainer != null) return false;
        if (m_targetID != null ? !m_targetID.equals(schemaEntityRef.m_targetID) : schemaEntityRef.m_targetID != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_targetID != null ? m_targetID.hashCode() : 0);
        result = 29 * result + (m_entityName != null ? m_entityName.hashCode() : 0);
        result = 29 * result + (m_isContainer != null ? m_isContainer.hashCode() : 0);
        return result;
    }
}
