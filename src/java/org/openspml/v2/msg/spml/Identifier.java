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


//<complexType name="IdentifierType">
//    <complexContent>
//        <extension base="spml:ExtensibleType">
//            <attribute name="ID" type="string" use="optional"/>
//        </extension>
//    </complexContent>
//</complexType>

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Jan 26, 2006
 */
public abstract class Identifier extends Extensible {

    public static final String code_id = "$Id: Identifier.java,v 1.2 2006/04/21 23:09:02 kas Exp $";

    // <attribute name="ID" type="string" use="optional"/>
    private String m_ID = null;

    protected Identifier() {
    }

    protected Identifier(String anID) {
        m_ID = anID;
    }

    public String getID() {
        return m_ID;
    }

    public void setID(String anID) {
        m_ID = anID;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identifier)) return false;
        if (!super.equals(o)) return false;

        final Identifier identifier = (Identifier) o;

        if (m_ID != null ? !m_ID.equals(identifier.m_ID) : identifier.m_ID != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_ID != null ? m_ID.hashCode() : 0);
        return result;
    }
}
