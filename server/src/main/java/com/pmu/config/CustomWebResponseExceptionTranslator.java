package com.pmu.config;

import com.pmu.exception.BaseException;
import com.pmu.exception.Error;
import com.pmu.exception.OAuth2ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import java.util.Objects;

public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        if (e instanceof BaseException) {
            BaseException ex = (BaseException) e;
            return new ResponseEntity<>(new Error(ex.getErrorCode(), e.getMessage()), ex.getErrorCode().getHttpStatus());
        } else if (e instanceof OAuth2Exception) {
            OAuth2Exception ex = (OAuth2Exception) e;
            OAuth2ErrorCode oAuth2ErrorCode = OAuth2ErrorCode.getErrorCodeFromErrorDescription(ex.getOAuth2ErrorCode());
            if (OAuth2ErrorCode.INVALID_GRANT == oAuth2ErrorCode) {
                oAuth2ErrorCode = OAuth2ErrorCode.getErrorCodeFromErrorDescription(ex.getMessage());
                if (Objects.isNull(oAuth2ErrorCode)) {
                    oAuth2ErrorCode = OAuth2ErrorCode.INVALID_GRANT;
                }
            }
            return new ResponseEntity<>(new Error(oAuth2ErrorCode, e.getMessage()), HttpStatus.valueOf(ex.getHttpErrorCode()));
        }
        return new ResponseEntity<>(new Error(OAuth2ErrorCode.OAUTH_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
