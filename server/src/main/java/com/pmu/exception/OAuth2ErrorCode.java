package com.pmu.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.util.Arrays;

@AllArgsConstructor
public enum OAuth2ErrorCode implements ErrorCodeInterface {
    OAUTH_ERROR(OAuth2Exception.ERROR),
    ERROR_URI(OAuth2Exception.URI),
    INVALID_REQUEST(OAuth2Exception.INVALID_REQUEST),
    INVALID_CLIENT(OAuth2Exception.INVALID_CLIENT),
    UNAUTHORIZED_CLIENT(OAuth2Exception.UNAUTHORIZED_CLIENT),
    INVALID_GRANT(OAuth2Exception.INVALID_GRANT),
    INVALID_SCOPE(OAuth2Exception.INVALID_SCOPE),
    INSUFFICIENT_SCOPE(OAuth2Exception.INSUFFICIENT_SCOPE),
    INVALID_TOKEN(OAuth2Exception.INVALID_TOKEN),
    REDIRECT_URI_MISMATCH(OAuth2Exception.REDIRECT_URI_MISMATCH),
    UNSUPPORTED_GRANT_TYPE(OAuth2Exception.UNSUPPORTED_GRANT_TYPE),
    UNSUPPORTED_RESPONSE_TYPE(OAuth2Exception.UNSUPPORTED_RESPONSE_TYPE),
    ACCESS_DENIED(OAuth2Exception.ACCESS_DENIED),
    BAD_CREDENTIALS("Bad credentials"),
    USER_ACCOUNT_IS_LOCKED("User account is locked"),
    USER_ACCOUNT_HAS_EXPIRED("User account has expired"),
    USER_CREDENTIALS_HAVE_EXPIRED("User credentials have expired"),
    USER_IS_DISABLED("User is disabled");

    @Getter
    private String oauth2Error;

    public static OAuth2ErrorCode getErrorCodeFromErrorDescription(String oauth2ErrorCode) {
        return Arrays.stream(OAuth2ErrorCode.values())
                .filter(oAuth2ErrorCode -> oAuth2ErrorCode.getOauth2Error().equals(oauth2ErrorCode))
                .findFirst().orElse(null);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}