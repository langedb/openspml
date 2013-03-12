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

import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Arrays;

/**
 * <complexType name="ListTargetsResponseType">
 * <complexContent>
 * <extension base="spml:ResponseType">
 * <sequence>
 * <element name="target" type="spml:TargetType" minOccurs="0" maxOccurs="unbounded"/>
 * </sequence>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 8, 2006
 */
public class ListTargetsResponse extends Response {

    private static final String code_id = "$Id: ListTargetsResponse.java,v 1.3 2006/04/25 21:22:09 kas Exp $";

    // <element name="target" type="spml:TargetType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_target = new ArrayListWithType(Target.class);

    public ListTargetsResponse() { ; }

    public ListTargetsResponse(String[] errorMessages,
                               StatusCode status,
                               String requestId,
                               ErrorCode errorCode,
                               Target[] targets) {
        super(errorMessages, status, requestId, errorCode);
        if (targets != null) {
            m_target.addAll(Arrays.asList(targets));
        }
    }

    public Target[] getTargets() {
        return (Target[]) m_target.toArray(new Target[m_target.size()]);
    }

    public void addTarget(Target target) {
        assert (target != null);
        m_target.add(target);
    }

    public boolean removeTarget(Target target) {
        assert (target != null);
        return m_target.remove(target);
    }

    public void clearTargets() {
        m_target.clear();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListTargetsResponse)) return false;
        if (!super.equals(o)) return false;

        final ListTargetsResponse listTargetsResponseType = (ListTargetsResponse) o;

        if (!m_target.equals(listTargetsResponseType.m_target)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_target.hashCode();
        return result;
    }
}
