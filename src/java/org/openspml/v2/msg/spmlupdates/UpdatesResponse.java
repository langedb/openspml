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
package org.openspml.v2.msg.spmlupdates;

import org.openspml.v2.msg.spml.ErrorCode;
import org.openspml.v2.msg.spml.StatusCode;
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Arrays;

/**
 * <complexType name="UpdatesResponseType">
 * <complexContent>
 * <extension base="spml:ResponseType">
 * <sequence>
 * <element name="update" type="spmlupdates:UpdateType" minOccurs="0" maxOccurs="unbounded"/>
 * <element name="iterator" type="spmlupdates:ResultsIteratorType" minOccurs="0" />
 * </sequence>
 * <attribute name="token" type="xsd:string" use="optional"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class UpdatesResponse extends BasicResponse {

    private static final String code_id = "$Id: UpdatesResponse.java,v 1.4 2006/04/25 21:22:09 kas Exp $";

    //* <element name="update" type="spmlupdates:UpdateType" minOccurs="0" maxOccurs="unbounded"/>
    private ListWithType m_update = new ArrayListWithType(Update.class);

    //* <element name="iterator" type="spmlupdates:ResultsIteratorType" minOccurs="0" />
    private ResultsIterator m_iterator = null; //optional

    //* <attribute name="token" type="xsd:string" use="optional"/>
    private String m_token = null;

    public UpdatesResponse() { ; }

    public UpdatesResponse(String[] errorMessages,
                           StatusCode status,
                           String requestId,
                           ErrorCode errorCode,
                           Update[] update,
                           ResultsIterator iterator, String token) {
        super(errorMessages, status, requestId, errorCode);
        if (update != null) {
            m_update.addAll(Arrays.asList(update));
        }
        m_iterator = iterator;
        m_token = token;
    }

    public Update[] getUpdates() {
        return (Update[]) m_update.toArray(new Update[m_update.size()]);
    }

    public void addUpdate(Update update) {
        if (update != null) {
            m_update.add(update);
        }
    }

    public boolean removeUpdate(Update update) {
        return m_update.remove(update);
    }

    public void clearUpdates() {
        m_update.clear();
    }

    public ResultsIterator getIterator() {
        return m_iterator;
    }

    public void setIterator(ResultsIterator iterator) {
        m_iterator = iterator;
    }

    public String getToken() {
        return m_token;
    }

    public void setToken(String token) {
        m_token = token;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdatesResponse)) return false;
        if (!super.equals(o)) return false;

        final UpdatesResponse updatesResponse = (UpdatesResponse) o;

        if (m_iterator != null ? !m_iterator.equals(updatesResponse.m_iterator) : updatesResponse.m_iterator != null) return false;
        if (m_token != null ? !m_token.equals(updatesResponse.m_token) : updatesResponse.m_token != null) return false;
        if (!m_update.equals(updatesResponse.m_update)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_update.hashCode();
        result = 29 * result + (m_iterator != null ? m_iterator.hashCode() : 0);
        result = 29 * result + (m_token != null ? m_token.hashCode() : 0);
        return result;
    }
}
