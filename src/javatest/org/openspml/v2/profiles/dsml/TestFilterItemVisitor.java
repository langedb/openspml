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

import java.util.ArrayList;
import java.util.List;

public class TestFilterItemVisitor implements FilterItemVisitor {

    private static final String code_id = "$Id: TestFilterItemVisitor.java,v 1.1 2006/06/29 22:31:46 kas Exp $";

    private List mItems = new ArrayList();

    public FilterItem[] getItemsInDepthFirstOrder() {
       return (FilterItem[]) mItems.toArray(new FilterItem[mItems.size()]);
    }

    protected void visitFilterSet(FilterSet set) throws DSMLProfileException {
        FilterItem[] item = set.getItems();
        for (int i = 0; i < item.length; i++) {
            FilterItem filterItem = item[i];
            filterItem.accept(this);
        }
        System.out.println("visiting " + set);
        mItems.add(set);

    }
    public void visitAnd(And fi) throws DSMLProfileException {
        visitFilterSet(fi);
    }

    public void visitOr(Or fi) throws DSMLProfileException {
        visitFilterSet(fi);
    }

    public void visitNot(Not fi) throws DSMLProfileException {
        fi.getItem().accept(this);
        System.out.println("visiting " + fi);
        mItems.add(fi);
    }

    public void visitEqualityMatch(EqualityMatch fi) throws DSMLProfileException {
        System.out.println("visiting " + fi);
        mItems.add(fi);
    }

    public void visitSubstrings(Substrings fi) throws DSMLProfileException {
        System.out.println("visiting " + fi);
        mItems.add(fi);
    }

    public void visitGreaterOrEqual(GreaterOrEqual fi) throws DSMLProfileException {
        System.out.println("visiting " + fi);
        mItems.add(fi);
    }

    public void visitLessOrEqual(LessOrEqual fi) throws DSMLProfileException {
        System.out.println("visiting " + fi);
        mItems.add(fi);
    }

    public void visitPresent(Present fi) throws DSMLProfileException {
        System.out.println("visiting " + fi);
        mItems.add(fi);
    }

    public void visitApproxMatch(ApproxMatch fi) throws DSMLProfileException {
        System.out.println("visiting " + fi);
        mItems.add(fi);
    }

    public void visitExtensibleMatch(ExtensibleMatch fi) throws DSMLProfileException {
        System.out.println("visiting " + fi);
        mItems.add(fi);
    }
}
