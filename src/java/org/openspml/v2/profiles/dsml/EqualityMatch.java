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


/**
 * This is the for matching attribute values.
 * 
 * <pre> &lt;xsd:element name="equalityMatch" type="AttributeValueAssertion"/&gt; </pre>
 */
public class EqualityMatch extends AttributeValueAssertion {

    private static final String code_id = "$Id: EqualityMatch.java,v 1.8 2006/08/30 18:02:59 kas Exp $";

    protected EqualityMatch() { super(); }

    protected void toXML(XmlBuffer buffer) throws DSMLProfileException {
        super.toXML("equalityMatch", buffer);
    }

    public EqualityMatch(String name, String value) throws DSMLProfileException {
        super(name, value);
    }

    public EqualityMatch(String name, DSMLValue value) throws DSMLProfileException {
        super(name, value);
    }

    protected boolean valueAssertion(String thisValue, String testValue) {
        return thisValue.equals(testValue);
    }

    public void accept(FilterItemVisitor visitor) throws DSMLProfileException {
        visitor.visitEqualityMatch(this);
    }
}
