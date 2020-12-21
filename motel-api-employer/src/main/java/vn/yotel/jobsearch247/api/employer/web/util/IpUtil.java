package vn.yotel.jobsearch247.api.employer.web.util;

import javax.servlet.ServletRequest;

public class IpUtil {
    public static String getIp(ServletRequest servletRequest) {
        return servletRequest.getRemoteAddr();
    }
}
