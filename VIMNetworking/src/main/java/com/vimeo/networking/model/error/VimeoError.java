/*
 * Copyright (c) 2015 Vimeo (https://vimeo.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.vimeo.networking.model.error;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import retrofit.RetrofitError;
import retrofit.client.Header;

/**
 * Created by zetterstromk on 5/27/15.
 */
public class VimeoError extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -5252307626841557962L;

    private static final String AUTHENTICATION_HEADER = "WWW-Authenticate";
    private static final String AUTHENTICATION_TOKEN_ERROR = "Bearer error=\"invalid_token\"";

    private RetrofitError retrofitError;

    @SerializedName("error")
    private String errorMessage;
    @SerializedName("link")
    private String link;
    @SerializedName("developer_message")
    private String developerMessage;
    @SerializedName("error_code")
    private ErrorCode errorCode;
    @SerializedName("invalid_parameters")
    private List<InvalidParameter> invalidParameters;

    public VimeoError() {
    }

    public VimeoError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public RetrofitError getRetrofitError() {
        return retrofitError;
    }

    public void setRetrofitError(RetrofitError retrofitError) {
        this.retrofitError = retrofitError;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return this.link;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getDeveloperMessage() {
        return this.developerMessage;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode == null ? ErrorCode.DEFAULT : this.errorCode;
    }

    public void setInvalidParameters(List<InvalidParameter> invalidParameters) {
        this.invalidParameters = invalidParameters;
    }

    public List<InvalidParameter> getInvalidParameters() {
        return this.invalidParameters;
    }

    /**
     * Returns the first invalid parameter in the parameter list
     *
     * @return First InvalidParameter in the invalid parameters array
     */
    @Nullable
    public InvalidParameter getInvalidParameter() {
        return invalidParameters != null && invalidParameters.size() > 0 ? this.invalidParameters
                .get(0) : null;
    }

    public boolean isServiceUnavailable() {
        return (retrofitError != null) && (retrofitError.getKind() == RetrofitError.Kind.HTTP) &&
               (retrofitError.getResponse().getStatus() == 503);
    }

    public boolean isForbiddenError() {
        return (retrofitError != null) && (retrofitError.getKind() == RetrofitError.Kind.HTTP) &&
               (retrofitError.getResponse().getStatus() == 403);
    }

    public boolean isInvalidTokenError() {
        if ((retrofitError != null) && (retrofitError.getKind() == RetrofitError.Kind.HTTP) &&
            (retrofitError.getResponse().getStatus() == 401)) {
            List<Header> headers = retrofitError.getResponse().getHeaders();
            for (Header header : headers) {
                if (header.getName().equals(AUTHENTICATION_HEADER) &&
                    header.getValue().equals(AUTHENTICATION_TOKEN_ERROR)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPasswordRequiredError() {
        if (((getInvalidParameter() != null) &&
             (getInvalidParameter().getErrorCode() == ErrorCode.INVALID_INPUT_VIDEO_NO_PASSWORD)) ||
            ((getInvalidParameter() != null) &&
             (getInvalidParameter().getErrorCode() == ErrorCode.INVALID_INPUT_VIDEO_PASSWORD_MISMATCH)) ||
            (getErrorCode() == ErrorCode.INVALID_INPUT_VIDEO_NO_PASSWORD) ||
            (getErrorCode() == ErrorCode.INVALID_INPUT_VIDEO_PASSWORD_MISMATCH)) {
            return true;
        }
        return false;
    }

    public void addInvalidParameter(String field, ErrorCode code, String developerMessage) {
        InvalidParameter invalidParameter = new InvalidParameter(field, code, developerMessage);
        if (this.invalidParameters == null) {
            invalidParameters = new ArrayList<>();
        }
        this.invalidParameters.add(invalidParameter);
    }
}