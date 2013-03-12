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
package org.openspml.v2.msg.spmlbulk;

import org.openspml.v2.msg.spml.ExecutionMode;
import org.openspml.v2.msg.spml.Modification;
import org.openspml.v2.msg.spmlsearch.Query;
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Arrays;

/**
 * <complexType name="BulkModifyRequestType">
 * <complexContent>
 * <extension base="spml:RequestType">
 * <sequence>
 * <element ref="spmlsearch:query" />
 * <element name="modification" type="spml:ModificationType" maxOccurs="unbounded"/>
 * </sequence>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class BulkModifyRequest extends BasicRequest {

    private static final String code_id = "$Id: BulkModifyRequest.java,v 1.4 2006/04/25 21:22:09 kas Exp $";

    private ListWithType m_modification = new ArrayListWithType(Modification.class);

    public BulkModifyRequest() { ; }

    public BulkModifyRequest(String requestId,
                             ExecutionMode executionMode,
                             Query ref,
                             Modification[] modifications) {
        super(requestId, executionMode, ref);
        assert (modifications != null);
        assert (modifications.length != 0);
        m_modification.addAll(Arrays.asList(modifications));
    }

    public Modification[] getModifications() {
        return (Modification[]) m_modification.toArray(new Modification[m_modification.size()]);
    }

    public void addModification(Modification modification) {
        if (modification != null) {
            m_modification.add(modification);
        }
    }

    public boolean removeModification(Modification modification) {
        return m_modification.remove(modification);
    }

    public void clearModifications() {
        m_modification.clear();
    }

    public boolean isValid() {
        return super.isValid() && !m_modification.isEmpty();
    }
}
