package org.openspml.v2.examples.nvpstore.csvdb.def;

import org.openspml.v2.msg.MarshallableElement;
import org.openspml.v2.msg.PrefixAndNamespaceTuple;

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
/*
 * Author(s): kent.spaulding@sun.com 
 * Date: Sep 25, 2006
 * Time: 5:44:06 PM
 */

/**
 * An NVP Group is a named set of NVPs.  The name is bascially a "type".
 * An NVP is a name-value-pair (aka Attribute).  Value can be multiple, or not.
 */
public class NVPObjectDefReference implements MarshallableElement {

    private static final String code_id = "$Id: NVPObjectDefReference.java,v 1.2 2006/10/06 02:23:10 kas Exp $";

    private String m_name;// what 'type' (just a name) does this refer to?

    protected NVPObjectDefReference() { ; }

    public String getNameOfNVPObjectDef() {
        return m_name;
    }

    public String getElementName() {
        return "objectdefref";
    }

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        return NVPStoreDefMarshallableCreator.staticGetNamespacesInfo();
    }

    public boolean isValid() {
        return m_name != null && !m_name.equals("");
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final NVPObjectDefReference that = (NVPObjectDefReference) o;

        if (m_name != null ? !m_name.equals(that.m_name) : that.m_name != null) return false;

        return true;
    }

    public int hashCode() {
        return (m_name != null ? m_name.hashCode() : 0);
    }
}
