package vn.yotel.jobsearch247.cms.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import vn.yotel.commons.web.security.CustomAuthenticationProvider;
import vn.yotel.commons.web.security.MyPermissionEvaluator;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, proxyTargetClass = true)
@Profile("FootballGame")
public class WebSecurityDefaultConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService authUserDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean("userLoginSuccessHandler")
	public AuthenticationSuccessHandler userLoginSuccessHandler() {
		return new AppAuthenticationSuccessHandler();
	}

	@Bean("customAuthenticationProvider")
	public AuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider();
	}

	@Bean("myPermissionEvaluator")
	public PermissionEvaluator myPermissionEvaluator() {
		return new MyPermissionEvaluator();
	}

	/**
	 * global config for all http
	 */
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.authUserDetailsService).passwordEncoder(this.passwordEncoder);
	}

    @Override
    public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**", "/resources/**", "/static/**", "/repository/**", "/assets/**",
				"/fonts/**", "/ws/**", "/v1/**","/public/**");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
    	   http
        		.authorizeRequests()
				.antMatchers("/webjars/**", "/resources/**", "/assets/**", "/fonts/**", "/ws/**").permitAll()
				.antMatchers("/ctv/**").hasAnyAuthority("ROLE_PARTNER_USER", "ROLE_ADMIN")
				.antMatchers("/fb-group-post/show/**", "/fb-group-post/update/**", "/candidate-job/show/**", "/candidate-job/update/**", "/job/listJob", "/location-area/list/location").hasAnyAuthority("ROLE_PARTNER_USER", "ROLE_ADMIN")
				.antMatchers("/job/**", "/service-package/**", "/fb-candidate/**", "/fb-group-post/**", "/candidate-job/**", "/location-area/**").hasAnyAuthority("ROLE_ADMIN")
				.anyRequest().authenticated()
        	.and()
        	.exceptionHandling()
        		.accessDeniedPage("/error/403.html?authorization_error=true")
        	.and()
            .csrf().disable()
            .headers().frameOptions().disable().and()
        	.formLogin()
				.usernameParameter("j_username")
				.passwordParameter("j_password")
        		.loginPage("/login.html")
        		.failureUrl("/login.html?authentication_error=true")
        		.loginProcessingUrl("/login.html")
        		.successHandler(userLoginSuccessHandler())
        		.permitAll()
//        		.failureHandler(userLoginFailureHandler)
        	.and()
        	.logout()
               .logoutSuccessUrl("/login.html?logout=true")
               .logoutUrl("/logout.html")
               .logoutRequestMatcher(new AntPathRequestMatcher("/logout.html")) // for POST and GET
               .deleteCookies( "JSESSIONID" )
               .invalidateHttpSession(true)
               .permitAll()
             .and()
             	.sessionManagement()
	        		.invalidSessionUrl("/login.html?invalid=true") //?invalid
	        		.maximumSessions(-1) // -1  unlimit
	        		.expiredUrl("/login.html?expired=true")
	        		.maxSessionsPreventsLogin(false)// kho cho dang nhap neu da ton tai session
        	.and()
        		.enableSessionUrlRewriting(false)
        	;
        // @formatter:on
    }

    // define beans for remember-me func
    String applicationKey = "application.key";
}
