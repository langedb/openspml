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
package org.openspml.v2.msg.spml;

import org.openspml.v2.util.BasicStringEnumConstant;
import org.openspml.v2.util.EnumConstant;

import java.util.List;

//<simpleType name="ErrorCode">
//    <restriction base="string">
//        <enumeration value="malformedRequest"/>
//        <enumeration value="unsupportedOperation"/>
//        <enumeration value="unsupportedIdentifierType"/>
//        <enumeration value="noSuchIdentifier"/>
//        <enumeration value="customError"/>
//        <enumeration value="unsupportedExecutionMode"/>
//        <enumeration value="invalidContainment"/>
//        <enumeration value="noSuchRequest"/>
//        <enumeration value="unsupportedSelectionType"/>
//        <enumeration value="resultSetToLarge"/>
//        <enumeration value="unsupportedProfile"/>
//        <enumeration value="invalidIdentifier"/>
//        <enumeration value="alreadyExists"/>
//        <enumeration value="containerNotEmpty"/>
//    </restriction>
//</simpleType>

/**
 * @author kent.spaulding@sun.com
 *         <p/>
 *         Date: Feb 1, 2006
 */
public final class ErrorCode extends BasicStringEnumConstant {

    private static final String code_id = "$Id: ErrorCode.java,v 1.1 2006/03/15 20:40:00 kas Exp $";

    public static final ErrorCode MALFORMED_REQUEST = new ErrorCode("malformedRequest");
    public static final ErrorCode UNSUPPORTED_OPERATION = new ErrorCode("unsupportedOperation");
    public static final ErrorCode UNSUPPORTED_IDENTIFIER_TYPE = new ErrorCode("unsupportedIdentifierType");
    public static final ErrorCode NO_SUCH_IDENTIFIER = new ErrorCode("noSuchIdentifier");
    public static final ErrorCode CUSTOM_ERROR = new ErrorCode("customError");
    public static final ErrorCode UNSUPPORTED_EXECUTION_MODE = new ErrorCode("unsupportedExecutionMode");
    public static final ErrorCode INVALID_CONTAINMENT = new ErrorCode("invalidContainment");
    public static final ErrorCode NO_SUCH_REQUEST = new ErrorCode("noSuchRequest");
    public static final ErrorCode UNSUPPORTED_SELECTION_TYPE = new ErrorCode("unsupportedSelectionType");
    public static final ErrorCode RESULT_SET_TOO_LARGE = new ErrorCode("resultSetToLarge");
    public static final ErrorCode UNSUPPORTED_PROFILE = new ErrorCode("unsupportedProfile");
    public static final ErrorCode INVALID_IDENTIFIER = new ErrorCode("invalidIdentifier");
    public static final ErrorCode ALREADY_EXISTS = new ErrorCode("alreadyExists");
    public static final ErrorCode CONTAINER_NOT_EMPTY = new ErrorCode("containerNotEmpty");

    public static ErrorCode[] getConstants() {
        List temp = EnumConstant.getEnumConstants(ErrorCode.class);
        return (ErrorCode[]) temp.toArray(new ErrorCode[temp.size()]);
    }

    private ErrorCode(String value) {
        super(value);
    }

}
