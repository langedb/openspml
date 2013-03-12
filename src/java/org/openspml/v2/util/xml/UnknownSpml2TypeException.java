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

import org.openspml.v2.util.Spml2Exception;

public class UnknownSpml2TypeException extends Spml2Exception {

    private static final String code_id = "$Id: UnknownSpml2TypeException.java,v 1.1 2006/03/17 00:03:06 kas Exp $";

    public UnknownSpml2TypeException(String message) {
        super(message);
    }

    public UnknownSpml2TypeException(Throwable cause) {
        super(cause);
    }

    public UnknownSpml2TypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
