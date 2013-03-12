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

import org.openspml.v2.util.xml.XmlBuffer;

import java.util.Map;

/**
 * From the DSML spec...
 * <p/>
 * <pre> &lt;xsd:element name="approxMatch" type="AttributeValueAssertion"/&gt; </pre>
 */
public class ApproxMatch extends AttributeValueAssertion {

    private static final String code_id = "$Id: ApproxMatch.java,v 1.6 2006/06/29 22:31:46 kas Exp $";

    protected void toXML(XmlBuffer buffer) throws DSMLProfileException {
        super.toXML("approxMatch", buffer);
    }

    public ApproxMatch() {
    }

    public ApproxMatch(String name, String value) throws DSMLProfileException {
        super(name, value);
    }

    public ApproxMatch(String name, DSMLValue value) throws DSMLProfileException {
        super(name, value);
    }

    /**
     * Servers will likely provide their own implementation of this -
     * See the DSMLProfileRegistar and DSMLUnmarshaller for details on
     * what to extend and override.
     *  <p>
     * @param attrs
     * @return   true if it matches.
     * @throws DSMLProfileException
     */
    public boolean matches(Map attrs) throws DSMLProfileException {
        throw new DSMLProfileException("Not implemented.");
    }

    public void accept(FilterItemVisitor visitor) throws DSMLProfileException {
        visitor.visitApproxMatch(this);
    }
}
