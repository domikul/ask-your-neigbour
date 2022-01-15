package pl.polsl.AskYourNeighbor.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final String secret;
    private final int expirationTimeAccessToken;
    private final int expirationTimeRefreshToken;

    public WebSecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
                             @Value("${jwt.secret}") String secret,
                             @Value("${jwt.int.expirationTimeAccessToken}") int expirationTimeAccessToken,
                             @Value("${jwt.int.expirationTimeRefreshToken}") int expirationTimeRefreshToken) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.secret = secret;
        this.expirationTimeAccessToken = expirationTimeAccessToken;
        this.expirationTimeRefreshToken = expirationTimeRefreshToken;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), secret, expirationTimeAccessToken, expirationTimeRefreshToken);
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers(POST, "/login/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/account/**").authenticated();
        http.authorizeRequests().antMatchers(DELETE, "/account/**").authenticated();
        http.authorizeRequests().antMatchers(POST, "/account/**").authenticated();
        //TODO:
        http.authorizeRequests().antMatchers(GET, "/forms/queue").permitAll();
        http.authorizeRequests().antMatchers(GET, "/forms/**").permitAll();
        http.authorizeRequests().antMatchers(POST, "/forms/**").permitAll();
        http.authorizeRequests().antMatchers(DELETE, "/forms/**").authenticated();
        http.authorizeRequests().antMatchers(PATCH, "/forms/**").authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(secret), UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
