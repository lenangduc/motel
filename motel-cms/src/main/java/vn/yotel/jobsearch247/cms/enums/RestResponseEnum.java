package vn.yotel.jobsearch247.cms.enums;

import org.springframework.http.HttpStatus;

public enum RestResponseEnum {
    SUCCESS(1, "success", HttpStatus.OK),
    VALID_ERROR(0, "valid error", HttpStatus.OK),
    RUNTIME_ERROR(-1, "system errors", HttpStatus.OK),
    AUTH_DENY(-2, "access denied", HttpStatus.OK),
    AUTH_EXPIRED(-3, "auth expired, please exchange", HttpStatus.OK);

    private int returnCode;
    private String message;
    private HttpStatus httpStatus;

    RestResponseEnum(int returnCode, String message, HttpStatus httpStatus) {
        this.returnCode = returnCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int status() {
        return this.returnCode;
    }

    public String message() {
        return this.message;
    }

    public HttpStatus httpStatus() {
        return this.httpStatus;
    }
}