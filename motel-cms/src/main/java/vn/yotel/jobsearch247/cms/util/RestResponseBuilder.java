package vn.yotel.jobsearch247.cms.util;

import org.springframework.http.ResponseEntity;
import vn.yotel.jobsearch247.cms.Model.ResponseData;
import vn.yotel.jobsearch247.cms.enums.RestResponseEnum;

public class RestResponseBuilder {
    public static ResponseEntity<ResponseData> build(RestResponseEnum restResponseEnum, Object response) {
        return new ResponseEntity<>(new ResponseData(restResponseEnum.status(),
                restResponseEnum.message(), response), restResponseEnum.httpStatus());
    }

    public static ResponseEntity<ResponseData> buildSuccess(Object response) {
        return build(RestResponseEnum.SUCCESS, response);
    }

    public static ResponseEntity<ResponseData> buildValidError(Object response) {
        return build(RestResponseEnum.VALID_ERROR, response);
    }

    public static ResponseEntity<ResponseData> buildRuntimeError(Object response) {
        return build(RestResponseEnum.RUNTIME_ERROR, response);
    }

    public static ResponseEntity<ResponseData> buildAuthDeny(Object response) {
        return build(RestResponseEnum.AUTH_DENY, response);
    }

    public static ResponseEntity<ResponseData> buildAuthExpired(Object response) {
        return build(RestResponseEnum.AUTH_EXPIRED, response);
    }
}
