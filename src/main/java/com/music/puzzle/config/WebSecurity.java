package com.music.puzzle.config;

import com.music.puzzle.authorization.AuthenticationFilter;
import com.music.puzzle.authorization.AuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.music.puzzle.authorization.SecurityConstants.SIGN_UP_URL;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    public WebSecurity(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.POST, "/user/sign-in").permitAll()
                .antMatchers(HttpMethod.GET, "/health").permitAll()
                .antMatchers(HttpMethod.GET, "/user/forgot-password").permitAll()

                .antMatchers(HttpMethod.GET, "/music/guest/puzzle").permitAll()
                .antMatchers(HttpMethod.GET, "/music/save").permitAll()
                .antMatchers(HttpMethod.POST, "/mail/").permitAll()

                // For admin
                .antMatchers(HttpMethod.GET, "/user/all").permitAll()
                .antMatchers(HttpMethod.DELETE, "/user").permitAll()
                .antMatchers(HttpMethod.GET, "/user").permitAll()
                .antMatchers(HttpMethod.GET, "/user/count").permitAll()

                .antMatchers(HttpMethod.GET, "/puzzle/all").permitAll()
                .antMatchers(HttpMethod.DELETE, "/puzzle/").permitAll()
                .antMatchers(HttpMethod.GET, "/puzzle/genre").permitAll()
                .antMatchers(HttpMethod.GET, "/puzzle/").permitAll()

                .antMatchers(HttpMethod.POST, "/mail").permitAll()

                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager()))
                .addFilter(new AuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
