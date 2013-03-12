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
package org.openspml.v2.msg.spmlasync;

import org.openspml.v2.msg.OCEtoMarshallableAdapter;
import org.openspml.v2.msg.spml.ErrorCode;
import org.openspml.v2.msg.spml.Response;
import org.openspml.v2.msg.spml.StatusCode;
import org.openspml.v2.util.Spml2Exception;
import org.openspml.v2.util.xml.ArrayListWithType;
import org.openspml.v2.util.xml.ListWithType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <complexType name="StatusResponseType">
 * <complexContent>
 * <extension base="spml:ResponseType">
 * <attribute name="asyncRequestID" type="xsd:string" use="optional"/>
 * </extension>
 * </complexContent>
 * </complexType>
 *
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 15, 2006
 */
public class StatusResponse extends BasicResponse {

    private static final String code_id = "$Id: StatusResponse.java,v 1.6 2006/08/30 18:02:59 kas Exp $";

    public StatusResponse() { ; }

    public StatusResponse(String[] errorMessages,
                          StatusCode status,
                          String requestId,
                          ErrorCode errorCode,
                          String asyncRequestID) {
        super(errorMessages,
              status,
              requestId,
              errorCode,
              asyncRequestID,
              false);
    }
    
    private ListWithType m_response = new ArrayListWithType(Response.class);

    public void addResponse(Response resp) throws Spml2Exception {
        if (resp != null) {
            m_response.add(resp);
//            this.addOpenContentElement(new OCEtoMarshallableAdapter(resp));
        }
    }

    public boolean removeResponse(Response resp) {
        return m_response.remove(resp);
    }

    public void clearResponses() {
        m_response.clear();
    }
    
    public List getRawResponses() {
        return m_response;
    }
    
    public List getResponses() {
		ArrayList responses = new ArrayList();
		
		Iterator rawResponseIterator = this.getRawResponses().iterator();
		
		try {
			for (; rawResponseIterator.hasNext(); ) {
				Object o = rawResponseIterator.next();
				if (o instanceof Response) {
					responses.add((Response)o);
				}
				else  if (o instanceof OCEtoMarshallableAdapter) {
					OCEtoMarshallableAdapter oc = 
						(OCEtoMarshallableAdapter)o;
					Object adaptedObject = oc.getAdaptedObject();
					if (adaptedObject instanceof Response) {
						responses.add((Response)adaptedObject);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return responses;
    }
    
    public Iterator responseIterator() {
    	//return m_response.iterator();
    	
		Iterator responseIterator = getResponses().iterator();    
		
		return responseIterator;
    	
    	//return this.getOpenContentElements(Response.class).iterator();
    }
    
    

}
