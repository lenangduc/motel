package vn.yotel.jobsearch247.api.employer.config.oauthen2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import vn.yotel.jobsearch247.api.employer.config.oauthen2.token.SingleAuthenticationKeyGenerator;

import javax.sql.DataSource;


@Configuration
public class OAuth2ServerConfiguration {

    public static final String RESOURCE_ID = "jobsearch.vn";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        UserDetailsService authUserDetailsService;

        @Autowired
        PasswordEncoder passwordEncoder;


        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            // @formatter:off
            resources.stateless(true)
                    .resourceId(RESOURCE_ID)
                    .accessDeniedHandler(new OAuth2AccessDeniedHandler());
            ;
            // @formatter:on
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
//					.csrf().disable()
                    .authorizeRequests()
                    .regexMatchers(HttpMethod.OPTIONS, "/oauth/expire").permitAll()
                    .regexMatchers(HttpMethod.POST, "/oauth/expire").permitAll()
//                    .antMatchers("/index/**").hasRole("CUSTOMER")
//                    .antMatchers("/rotation-game-img/**").permitAll()
//                    .antMatchers("/hot-songs/**").permitAll()
                    .anyRequest().permitAll()
                    .and()
//				.csrf().disable()
                    .userDetailsService(this.authUserDetailsService)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ;
            // @formatter:on
//			http.cors();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        DataSource dataSource;

        @Autowired
        PasswordEncoder passwordEncoder;

        @Autowired
        AuthenticationManager authenticationManager;


        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            // @formatter:off
            endpoints
                    .tokenServices(tokenServices())
                    .authenticationManager(this.authenticationManager)
            ;
            // @formatter:on
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer.checkTokenAccess("permitAll()");
            oauthServer.tokenKeyAccess("permitAll()");
            oauthServer.realm(RESOURCE_ID + "/client");
        }

        /**
         * cd8aa153 - 682f3fe0 //Basic Y2Q4YWExNTM6NjgyZjNmZTA=
         * aa486ce9 - a2257b2c //Basic YWE0ODZjZTk6YTIyNTdiMmM=
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // @formatter:off
            clients
                    .inMemory()
                    .withClient("cd8aa153")
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                    .authorities("USER")
                    .scopes("read", "write", "trust")
                    .resourceIds(RESOURCE_ID)
                    .secret("682f3fe0")
//						.accessTokenValiditySeconds(60)
//						.refreshTokenValiditySeconds(0)
//						.accessTokenValiditySeconds(60 * 60 * 12) // default 12 hours.
//						.refreshTokenValiditySeconds(60 * 60 * 24 * 30) //  default 30 days.
                    .and()
                    .withClient("aa486ce9")
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                    .authorities("USER")
                    .scopes("read", "write", "trust")
                    .resourceIds(RESOURCE_ID)
                    .secret("a2257b2c")
//						.accessTokenValiditySeconds(60 * 60 * 24)
//						.refreshTokenValiditySeconds(0)

//						.accessTokenValiditySeconds(60 * 60 * 24 * 15) // default 12 hours.current set 15days
//						.refreshTokenValiditySeconds(60 * 60 * 24 * 30) //  default 30 days.
            ;
            // @formatter:on
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setAccessTokenValiditySeconds(0); //  non-expiring tokens
            tokenServices.setRefreshTokenValiditySeconds(0);
            tokenServices.setSupportRefreshToken(false);
            tokenServices.setReuseRefreshToken(false);
            tokenServices.setTokenStore(tokenStore());
            //tokenServices.setTokenEnhancer(this.tokenEnhancer);
            return tokenServices;
        }

        @Bean
        @Primary
        public TokenStore tokenStore() {
            JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
            jdbcTokenStore.setAuthenticationKeyGenerator(new SingleAuthenticationKeyGenerator());
            return jdbcTokenStore;
        }
    }
    //  OAuth2

}

