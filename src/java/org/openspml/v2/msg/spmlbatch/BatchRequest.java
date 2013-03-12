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
package org.openspml.v2.msg.spmlbatch;

import org.openspml.v2.msg.PrefixAndNamespaceTuple;
import org.openspml.v2.msg.spml.BatchableRequest;
import org.openspml.v2.msg.spml.ExecutionMode;
import org.openspml.v2.msg.spml.Request;
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * <complexType name="BatchRequestType">
 * <complexContent>
 * <extension base="spml:RequestType">
 * <annotation>
 * <documentation>Elements that extend spml:RequestType</documentation>
 * </annotation>
 * <attribute name="processing" type="spmlbatch:ProcessingType" use="optional" default="sequential"/>
 * <attribute name="onError" type="spmlbatch:OnErrorType" use="optional" default="exit"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 16, 2006
 */
public class BatchRequest extends Request {

    private static final String code_id = "$Id: BatchRequest.java,v 1.7 2006/08/30 18:02:59 kas Exp $";

    // TODO: Instead of Request.class -> BatchableRequest.class
    
    // These are the request objects... we ask them for namespace info....
    private ListWithType m_request = new ArrayListWithType(BatchableRequest.class);

    // <attribute name="processing" type="spmlbatch:ProcessingType" use="optional" default="sequential"/>
    private Processing m_processing = Processing.SEQUENTIAL;

    // <attribute name="onError" type="spmlbatch:OnErrorType" use="optional" default="exit"/>
    private OnError m_onError = OnError.EXIT;

    public BatchRequest() { ; }

    public BatchRequest(String requestId,
                        ExecutionMode executionMode,
                        Processing processing,
                        OnError onError) {
        super(requestId, executionMode);
        m_processing = processing;
        m_onError = onError;
    }

    // Take objects of type BatchableRequest
    public void addRequest(BatchableRequest req) {
        if (req != null)
            m_request.add(req);
    }

    public boolean removeRequest(BatchableRequest req) {
        return m_request.remove(req);
    }

    public void clearRequests() {
        m_request.clear();
    }

    /**
     * This returns a List (unmodifiable) of the
     * individual requests.
     * @return a List of BatchableRequest objects.
     */
    public List getRequests() {
        return Collections.unmodifiableList(m_request);
    }

    public Processing getProcessing() {
        return m_processing;
    }

    public void setProcessing(Processing processing) {
        m_processing = processing;
    }

    public OnError getOnError() {
        return m_onError;
    }

    public void setOnError(OnError onError) {
        m_onError = onError;
    }

    public PrefixAndNamespaceTuple[] getNamespacesInfo() {
        PrefixAndNamespaceTuple[] supers = super.getNamespacesInfo();
        PrefixAndNamespaceTuple[] ours =
                PrefixAndNamespaceTuple.concatNamespacesInfo(supers,
                                                             NamespaceDefinitions.getMarshallableNamespacesInfo());

        Set all = null;
        if (ours!=null)
            all = new LinkedHashSet(Arrays.asList(ours));
        else
        	all = new LinkedHashSet();
        if (m_request!=null) {
            Iterator iter = m_request.iterator();
            while (iter.hasNext()) {
                Request r = (Request) iter.next();
                PrefixAndNamespaceTuple[] rqni = r.getNamespacesInfo();
                if (rqni!=null)
                	all.addAll(Arrays.asList(rqni));
            }
        }
        return (PrefixAndNamespaceTuple[]) all.toArray(new PrefixAndNamespaceTuple[all.size()]);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BatchRequest)) return false;
        if (!super.equals(o)) return false;

        final BatchRequest batchRequest = (BatchRequest) o;

        if (m_onError != null ? !m_onError.equals(batchRequest.m_onError) : batchRequest.m_onError != null) return false;
        if (m_processing != null ? !m_processing.equals(batchRequest.m_processing) : batchRequest.m_processing != null) return false;
        if (!m_request.equals(batchRequest.m_request)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + m_request.hashCode();
        result = 29 * result + (m_processing != null ? m_processing.hashCode() : 0);
        result = 29 * result + (m_onError != null ? m_onError.hashCode() : 0);
        return result;
    }
}
