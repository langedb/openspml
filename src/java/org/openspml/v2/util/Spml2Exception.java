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

/**
 * Just making it clear which namespace/version of spec we are binding to.
 * 
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Jan 26, 2006
 */
public class Spml2Exception extends Exception {

    private static final String code_id = "$Id: Spml2Exception.java,v 1.3 2006/08/29 23:42:03 kas Exp $";

    private static final String LEGAL_NOTICE =
    "Copyright 2006 Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,\n" +
    "California 95054, U.S.A. All rights reserved.\n" +
    "\n" +
    "U.S. Government Rights - Commercial software. Government users are subject\n" +
    "to the Sun Microsystems, Inc. standard license agreement and applicable\n" +
    "provisions of the FAR and its supplements.\n" +
    "\n" +
    "Use is subject to license terms.\n" +
    "\n" +
    "This distribution may include materials developed by third parties.\n" +
    "\n" +
    "Sun, Sun Microsystems, the Sun logo and Java are trademarks or registered\n" +
    "trademarks of Sun Microsystems, Inc. in the U.S. and other countries.\n";

    public Spml2Exception() {
        super();
    }

    public Spml2Exception(String message) {
        super(message);
    }

    public Spml2Exception(Throwable cause) {
        super(cause);
    }

    public Spml2Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
