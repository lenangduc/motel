package vn.yotel.jobsearch247.api.employer.ConextFacade;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationFacade {
    private final String ANONYMOUS_USER_VALUE = "anonymousUser";

    Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private boolean isEmptyAuthentication(Authentication authentication) {
        return authentication == null || authentication.getName() == null || ANONYMOUS_USER_VALUE.equals(authentication.getName());
    }

    public Optional<String> getName() {
        Authentication authentication = getAuthentication();
        String name = isEmptyAuthentication(authentication) ? null : authentication.getName();
        return Optional.ofNullable(name);
    }
}