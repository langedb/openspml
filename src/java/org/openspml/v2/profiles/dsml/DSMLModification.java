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
 * Copyright 2006 Tripod Technology Group, Inc.
 * All Rights Reserved.
 * Use is subject to license terms.
 */
/*
 * Copyright 2006 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */
package org.openspml.v2.profiles.dsml;

import org.openspml.v2.msg.spml.ModificationMode;
import org.openspml.v2.util.xml.XmlBuffer;

/**
 * From the DSML spec...
 *
 * <pre>
 * &lt;xsd:complexType name="Modification"&gt;
 * &lt;xsd:sequence&gt;
 *     &lt;xsd:element name="value" type="DsmlValue" minOccurs="0" maxOccurs="unbounded"/&gt;
 * &lt;/xsd:sequence&gt;
 * &lt;xsd:attribute name="name" type="AttributeDescriptionValue" use="required"/&gt;
 * &lt;xsd:attribute name="operation" use="required"&gt;
 *     &lt;xsd:simpleType&gt;
 *         &lt;xsd:restriction base="xsd:string"&gt;
 *             &lt;xsd:enumeration value="add"/&gt;
 *             &lt;xsd:enumeration value="delete"/&gt;
 *             &lt;xsd:enumeration value="replace"/&gt;
 *         &lt;/xsd:restriction&gt;
 *     &lt;/xsd:simpleType&gt;
 * &lt;/xsd:attribute&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 *
 * @author Blaine Busler and Kent Spaulding
 */
public class DSMLModification extends DSMLAttr {

    protected DSMLModification() { ; }

    // <xsd:simpleType>
    // <xsd:restriction base="xsd:string">
    // <xsd:enumeration value="add"/>
    // <xsd:enumeration value="delete"/>
    // <xsd:enumeration value="replace"/>
    // </xsd:restriction>
    // </xsd:simpleType>

    private ModificationMode mOperation = null;

    public DSMLModification(String name, DSMLValue[] values, ModificationMode operation)
            throws DSMLProfileException {
        super(name, values);
        mOperation = operation;
    }

    public DSMLModification(String name, String value, ModificationMode operation)
            throws DSMLProfileException {
        super(name, value);
        mOperation = operation;
    }

    public ModificationMode getOperation() {
        return mOperation;
    }

    public void setOperation(ModificationMode operation) {
        mOperation = operation;
    }

    public void parseXml(DSMLUnmarshaller um, Object xmlObj)
            throws DSMLProfileException {
        um.visitDSMLModification(this, xmlObj);
    }

    protected void addSubclassAttributes(XmlBuffer buffer) throws DSMLProfileException {
        super.addSubclassAttributes(buffer);
        if (mOperation != null) {
            buffer.addAttribute("operation", mOperation.toString());
        }
    }

    public String toXML(int indent) throws DSMLProfileException {
        return super.toXML("modification", indent);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DSMLModification)) return false;
        if (!super.equals(o)) return false;

        final DSMLModification modification = (DSMLModification) o;

        if (mOperation != null ? !mOperation.equals(modification.mOperation) : modification.mOperation != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (mOperation != null ? mOperation.hashCode() : 0);
        return result;
    }
}
