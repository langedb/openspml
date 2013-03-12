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
package org.openspml.v2.util;

import org.openspml.v2.msg.spml.Response;

/**
 * Spml2Client.send() throws this because some clients will want
 * to look at the contents of the Response in the exception handler.
 */
public class Spml2ExceptionWithResponse extends Spml2Exception {
	
    private Response _response = null;
    
    public final Response getResponse() {
    	return _response;
    }
    
    public Spml2ExceptionWithResponse( Response response ) {
        super();
        _response = response;
    }
    
    public Spml2ExceptionWithResponse( String message, Response response ) {
        super(message);
        _response = response;
    }

    public Spml2ExceptionWithResponse( Throwable cause, Response response ) {
        super(cause);
        _response = response;
    }

    public Spml2ExceptionWithResponse( String message, Throwable cause, Response response ) {
        super(message, cause);
        _response = response;
    }

}
