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
package org.openspml.v2.profiles.dsml;

public interface FilterItemVisitor {

    public static final String code_id = "$Id: FilterItemVisitor.java,v 1.1 2006/06/29 22:31:46 kas Exp $";

    public void visitAnd(And fi) throws DSMLProfileException;
    public void visitOr(Or fi) throws DSMLProfileException;
    public void visitNot(Not fi) throws DSMLProfileException;
    public void visitEqualityMatch(EqualityMatch fi) throws DSMLProfileException;
    public void visitSubstrings(Substrings fi) throws DSMLProfileException;
    public void visitGreaterOrEqual(GreaterOrEqual fi) throws DSMLProfileException;
    public void visitLessOrEqual(LessOrEqual fi) throws DSMLProfileException;
    public void visitPresent(Present fi) throws DSMLProfileException;
    public void visitApproxMatch(ApproxMatch fi) throws DSMLProfileException;
    public void visitExtensibleMatch(ExtensibleMatch fi) throws DSMLProfileException;
    
}
