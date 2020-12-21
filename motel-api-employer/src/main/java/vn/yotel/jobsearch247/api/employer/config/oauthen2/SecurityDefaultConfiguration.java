package vn.yotel.jobsearch247.api.employer.config.oauthen2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, proxyTargetClass = true)
@Profile("JobSearch")
public class SecurityDefaultConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService authUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * global config for all http
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.authUserDetailsService).passwordEncoder(this.passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/auth/login")
                .antMatchers(HttpMethod.OPTIONS);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/webjars/**", "/resources/**", "/assets/**", "/fonts/**", "/ws/**").permitAll()
//                .antMatchers("/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER", "ROLE_CUSTOMER", "ROLE_FINANCE")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/login.html?invalid=true") //?invalid
                .maximumSessions(-1) // -1  unlimit
                .expiredUrl("/login.html?expired=true")
                .maxSessionsPreventsLogin(false)// kho cho dang nhap neu da ton tai session
                .and()
                .enableSessionUrlRewriting(false);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }
}
