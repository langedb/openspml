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
package org.openspml.v2.msg.spmlsearch;

import org.openspml.v2.msg.Marshallable;
import org.openspml.v2.msg.PrefixAndNamespaceTuple;
import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.msg.spml.Extensible;
import org.openspml.v2.util.Spml2Exception;

/**
 * <complexType name="ResultsIteratorType">
 * <complexContent>
 * <extension base="spml:ExtensibleType">
 * <attribute name="ID" type="xsd:ID"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class ResultsIterator extends Extensible implements Marshallable {

    private static final String code_id = "$Id: ResultsIterator.java,v 1.4 2006/05/15 23:31:00 kas Exp $";

    // TODO: ID syntax?   <attribute name="ID" type="xsd:ID"/>
    private String m_ID = null; // required.

    public ResultsIterator() { ; }

    public ResultsIterator(String ID) {
        m_ID = ID;
    }

    public String getID() {
        return m_ID;
    }

    public void setID(String ID) {
        m_ID = ID;
    }

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        return PrefixAndNamespaceTuple.concatNamespacesInfo(
                super.getNamespacesInfo(),
                NamespaceDefinitions.getMarshallableNamespacesInfo());
    }

    public String toXML(XMLMarshaller m) throws Spml2Exception {
        return m.marshall(this);
    }

    public boolean isValid() {
        return true;
    }
}
