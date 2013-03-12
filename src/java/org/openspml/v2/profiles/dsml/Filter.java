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
package org.openspml.v2.profiles.dsml;

import org.openspml.v2.msg.OpenContentElement;
import org.openspml.v2.msg.XMLMarshaller;
import org.openspml.v2.util.Spml2Exception;

import java.util.Map;
/**
 * This is from the DSML 2.0 Specification.
 *
 * <pre>
 * &lt;xsd:complexType name="Filter"&gt;
 *   &lt;xsd:group ref="FilterGroup"/&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 */
public class Filter extends BasicFilter implements OpenContentElement {

    private static final String code_id = "$Id: Filter.java,v 1.7 2006/06/29 22:31:46 kas Exp $";

    public Filter() { ; }

    public Filter(FilterItem item) {
        super(item);
    }

    public String toXML(int indent) throws DSMLProfileException {
        return super.toXML("filter", indent, true);
    }

    public String toXML() throws DSMLProfileException {
        return toXML(0);
    }

    final public boolean matches(DSMLAttr[] attrs) throws DSMLProfileException {
        return getItem().matches(attrs);
    }

    final public boolean matches(Map map) throws DSMLProfileException {
        return getItem().matches(map);
    }

    public void applyVisitor(FilterItemVisitor v) throws DSMLProfileException {
        getItem().accept(v);
    }

    /**
     * This method was introduced by the QueryClause and doesn't do
     * the right thing here as we use a different XML protocol - so
     * we map it into our system - ignoring the XMLMarhsaller.
     *
     * @param m
     * @return an XML representation of the object.
     * @throws Spml2Exception
     */
    public String toXML(XMLMarshaller m) throws Spml2Exception {
        return toXML();
    }
}
