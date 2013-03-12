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
package org.openspml.v2.util.xml;

import org.openspml.v2.msg.PrefixAndNamespaceTuple;

/**
 * @author Kent Spaulding
 */
public class OperationalNameValuePair extends BasicOCEAndMarshallable {

    private String m_name = null;
    private String m_value = null;

    public OperationalNameValuePair() {
    }

    public OperationalNameValuePair(String name, String value) {
        m_name = name;
        m_value = value;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        if (name != null)
            m_name = name;
    }

    public String getValue() {
        return m_value;
    }

    public void setValue(String value) {
        m_value = value;
    }

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        return new PrefixAndNamespaceTuple[]{
            new PrefixAndNamespaceTuple(OperationalNameValuePairCreator.OP_ATTR_PREFIX,
                                        OperationalNameValuePairCreator.OP_ATTR_URI),
        };
    }

    public boolean isValid() {
        return true;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperationalNameValuePair)) return false;

        final OperationalNameValuePair operationalNameValuePair = (OperationalNameValuePair) o;

        if (m_name != null ? !m_name.equals(operationalNameValuePair.m_name) : operationalNameValuePair.m_name != null) return false;
        if (m_value != null ? !m_value.equals(operationalNameValuePair.m_value) : operationalNameValuePair.m_value != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (m_name != null ? m_name.hashCode() : 0);
        result = 29 * result + (m_value != null ? m_value.hashCode() : 0);
        return result;
    }
}
