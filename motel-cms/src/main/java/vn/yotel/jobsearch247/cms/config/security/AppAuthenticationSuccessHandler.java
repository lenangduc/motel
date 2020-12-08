package vn.yotel.jobsearch247.cms.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import vn.yotel.admin.jpa.AuthUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppAuthenticationSuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        request.getSession().setAttribute("username", authUser.getUserName());
        String targetLink = "/";
        List<GrantedAuthority> lstAuthorities = new ArrayList<GrantedAuthority>(authentication.getAuthorities());
        boolean isAdmin = false;
        boolean isOwner = false;
        boolean isRenter = false;
        for (GrantedAuthority grantedAuthority : lstAuthorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_OWNER")) {
                isOwner = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_RENTER")) {
                isRenter = true;
                break;
            }
        }
        if (isAdmin) {
            targetLink = "/owner/list-post";
        } else if (isOwner) {
            targetLink = "/fb-group-post/list";
        } else if (isRenter) {
            targetLink = "/ctv/";
        }

        if (authUser.getStatus() == 1 || authUser.getStatus() == 2) {
            targetLink = request.getContextPath() + "/account/user-blocked.html";
            response.sendRedirect(targetLink);
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        } else {
            if (response.isCommitted()) {
                logger.warn("Response has already been committed. Unable to redirect to " + targetLink);
            } else {
                request.getSession().setAttribute("dashboardLink", targetLink);
                redirectStrategy.sendRedirect(request, response, targetLink);
            }
        }
    }

}
