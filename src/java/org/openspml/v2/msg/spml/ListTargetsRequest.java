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

import java.net.URI;

/**
 * <complexType name="ListTargetsRequestType">
 * <complexContent>
 * <extension base="spml:RequestType">
 * <attribute name="profile" type="anyURI" use="optional" />
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 8, 2006
 */
public class ListTargetsRequest extends Request {

    private static final String code_id = "$Id: ListTargetsRequest.java,v 1.2 2006/04/21 23:09:02 kas Exp $";

    // <attribute name="profile" type="anyURI" use="optional" />
    private URI m_profile = null;

    public ListTargetsRequest() { ; }

    public ListTargetsRequest(String requestId,
                              ExecutionMode executionMode,
                              URI profile) {
        super(requestId, executionMode);
        m_profile = profile;
    }

    public URI getProfile() {
        return m_profile;
    }

    public void setProfile(URI profile) {
        m_profile = profile;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListTargetsRequest)) return false;
        if (!super.equals(o)) return false;

        final ListTargetsRequest listTargetsRequestType = (ListTargetsRequest) o;

        if (m_profile != null ? !m_profile.equals(listTargetsRequestType.m_profile) : listTargetsRequestType.m_profile != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (m_profile != null ? m_profile.hashCode() : 0);
        return result;
    }
}
