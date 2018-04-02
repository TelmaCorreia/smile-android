package com.thesis.smile.data.remote.services.base;

import com.thesis.smile.data.remote.exceptions.http.GenericErrorException;

import java.util.HashMap;
import java.util.Map;

public class ApiError {

    private static Map<String, ApiCode> errorMap = new HashMap<>();

    public Throwable getThrowable(String code) {
        ApiCode apiCode = getErrorCode(code);

        switch (apiCode){
            case OK:
                return null;
            case UNKNOWN_ERROR:
            default:
                return new GenericErrorException();
        }
    }

    private ApiCode getErrorCode(String code) {
        ensureMap();

        ApiCode type = errorMap.get(code);
        if (type == null) return ApiCode.UNKNOWN_ERROR;
        return type;
    }

    private static void ensureMap() {
        if (errorMap == null) {
            errorMap = new HashMap<>();
        }

        if (errorMap.isEmpty()) {
            for (ApiCode code : ApiCode.values()) {
                errorMap.put(code.getCode(), code);
            }
        }
    }

    private enum ApiCode {

        OK("OK"),
        USERS_INVALID_PASSWORD_FORMAT("Users_InvalidPasswrodFormat"),
        USERS_PASSWORD_NOT_SPECIFIED("Users_PasswordNotSpecified"),
        USERS_USER_EMAIL_ALREADY_EXISTS("Users_UserEmailAlreadyExists"),
        USERS_EXTERNAL_API_NOT_REACHABLE("Users_ExternalApiNotReachable"),
        USERS_EMAIL_NOT_SPECIFIED("Users_EmailNotSpecified"),
        USERS_FIRST_NAME_NOT_SPECIFIED("Users_FirstNameNotSpecified"),
        USERS_FAMILY_NAME_NOT_SPECIFIED("Users_FamilyNameNotSpecified"),
        USERS_USER_NOT_FOUND("Users_UserNotFound"),
        USERS_USER_WAS_INACTIVATED("Users_UserWasInactivated"),
        USERS_USER_ID_NOT_FOUND("Users_UserIdNotFound"),
        USERS_USERNAME_ALREADY_EXISTS("Users_UsernameAlreadyExists"),
        AUTH_INVALID_PASSWORD("Auth_InvalidPassword"),
        GENERIC_ERROR("GenericError"),
        UNKNOWN_ERROR("");

        private final String code;

        ApiCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
