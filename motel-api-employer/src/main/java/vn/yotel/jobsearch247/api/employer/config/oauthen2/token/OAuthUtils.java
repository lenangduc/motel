package vn.yotel.jobsearch247.api.employer.config.oauthen2.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.util.CollectionUtils;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.jobsearch247.api.employer.config.oauthen2.OAuth2ServerConfiguration;
import vn.yotel.jobsearch247.api.employer.web.enums.UserRoleEnum;

import java.io.Serializable;
import java.util.*;

public class OAuthUtils {
    /**
     * Manual tạo access_token
     *
     * @param username
     * @param password
     * @param requestParameters
     * @return
     */
    public static OAuth2AccessToken createdAccessToken(String username, String password,
                                                       Map<String, String> requestParameters, DefaultTokenServices defaultTokenServices) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        if (password == null) {
            password = "";
        }
        String clientId = "web-api";// request.getHeader(ApiConstantsEnum.OAuthen2ClientParam.client_id.value());
        boolean approved = true;
        Set<String> scope = new HashSet<>();
        scope.add("read");
        scope.add("write");
        scope.add("trust");

        Set<String> resourceIds = new HashSet<>();
        resourceIds.add(OAuth2ServerConfiguration.RESOURCE_ID);
        Set<String> responseTypes = new HashSet<>();
        responseTypes.add("code");

        Map<String, Serializable> extensionProperties = new HashMap<>();

        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, clientId, authorities, approved, scope,
                resourceIds, null, responseTypes, extensionProperties);

        User userPrincipal = new User(username, password, true, true, true, true, authorities);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
        OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, authenticationToken);

        OAuth2AccessToken token = defaultTokenServices.createAccessToken(auth);

        return token;
    }

    public static OAuth2AccessToken createAccessToken(String username, String password, List<GrantedAuthority> lsAuthorities,
                                                      Map<String, String> requestParameters, DefaultTokenServices defaultTokenServices) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        if (!CollectionUtils.isEmpty(lsAuthorities)) {
            authorities.addAll(lsAuthorities);
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRoleEnum.ROLE_RENTER.getName()));
        }

        if (password == null) {
            password = "";
        }
        String clientId = "wapapp";// request.getHeader(ApiConstantsEnum.OAuthen2ClientParam.client_id.value());
        boolean approved = true;
        Set<String> scope = new HashSet<>();
        scope.add("read");
        scope.add("write");
        scope.add("trust");

        Set<String> resourceIds = new HashSet<>();
        resourceIds.add(OAuth2ServerConfiguration.RESOURCE_ID);
        Set<String> responseTypes = new HashSet<>();
        responseTypes.add("code");

        Map<String, Serializable> extensionProperties = new HashMap<>();

        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, clientId, authorities, approved, scope,
                resourceIds, null, responseTypes, extensionProperties);

        User userPrincipal = new User(username, password, true, true, true, true, authorities);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
        OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, authenticationToken);

        OAuth2AccessToken token = defaultTokenServices.createAccessToken(auth);

        return token;
    }

    public static HashMap<String, Object> createAccessToken(AuthUser authUser, DefaultTokenServices defaultTokenServices) {
        Map<String, Object> response = new HashMap<>();
        List<GrantedAuthority> lsAuthorities = (List<GrantedAuthority>) authUser.getAuthorities();

        HashMap<String, String> requestParameters = new HashMap<String, String>();
        OAuth2AccessToken accessToken = createAccessToken(authUser.getUsername(), authUser.getPassword(), lsAuthorities,
                requestParameters, defaultTokenServices);
        response.put(OAuth2AccessToken.ACCESS_TOKEN, accessToken.getValue());
        if (accessToken.getExpiresIn() > 0) {
            response.put(OAuth2AccessToken.EXPIRES_IN, accessToken.getExpiresIn());
        }
        response.put(OAuth2AccessToken.TOKEN_TYPE, accessToken.getTokenType());
        if (accessToken.getRefreshToken() != null) {
            response.put(OAuth2AccessToken.REFRESH_TOKEN, accessToken.getRefreshToken().getValue());
        }
        response.put("Authorities", lsAuthorities);
        Set<String> scope = accessToken.getScope();
        Iterator<String> scopeIter = scope.iterator();
        String scopeAsStr = "";
        while (scopeIter.hasNext()) {
            scopeAsStr = scopeAsStr + " " + scopeIter.next();
        }

        response.put(OAuth2AccessToken.SCOPE, scopeAsStr.trim());
        response.put("username", authUser.getUserName().trim());
        return (HashMap<String, Object>) response;
    }
}
