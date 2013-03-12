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
package org.openspml.v2.msg;

public interface MarshallableElement {

    public static final String code_id = "$Id: MarshallableElement.java,v 1.4 2006/06/27 17:28:46 kas Exp $";

    /**
     * This should return the name of the element to be placed in the XML
     * @return  just the string (no prefix) representing the elementName
     */
    public String getElementName();

    /**
     * We need to know the namespace of the class to include.
     *
     * @return  set of tuples defining the namespaces to use.
     */
    public PrefixAndNamespaceTuple[] getNamespacesInfo();

    /**
     * Is this ready to go or are there syntax errors, like missing required fields?
     *
     * @return is this valid?  do the values of the fields make legal sense?
     */
    public boolean isValid();

}
