package vn.salary.calculator.authentication.infras.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vn.salary.calculator.authentication.infras.JwtAuthenticationFilter;
import vn.salary.calculator.authentication.infras.JwtProvider;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtProvider jwtProvider) {
        this.jwtAuthFilter = new JwtAuthenticationFilter(jwtProvider);
    }

    @Bean
    public SecurityFilterChain externalFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, POST_PERMIT)
                .permitAll()
                .requestMatchers(HttpMethod.GET, GET_PERMIT)
                .permitAll()
                .requestMatchers(HttpMethod.PUT, PUT_PERMIT)
                .permitAll()
                .requestMatchers(HttpMethod.DELETE, DELETE_PERMIT)
                .permitAll()
                .requestMatchers(HttpMethod.PATCH, PATCH_PERMIT)
                .permitAll()
                .anyRequest()
                .authenticated());
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {

        });
        http
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] POST_PERMIT = {"/internal/**", "/public/**", "/login", "/register"};

    private static final String[] GET_PERMIT = {"/swagger*/**", "/ping/**", "/v3/api-docs/**",
            "/favicon.ico", "/error", "/actuator/info", "/actuator/health", "/home", "/internal/**",
            "/web/**", "/images/**", "/resources/**", "/public/**", "/health/**", "/info/**",
            "/docs/**", "/webjars/springfox-swagger-ui/**", "/swagger-ui/**",
            "/swagger-ui.html", "/public/**"};

    private static final String[] PUT_PERMIT = {"/public/**", "/internal/**"};
    private static final String[] DELETE_PERMIT = {"/public/**", "/internal/**"};
    private static final String[] PATCH_PERMIT = {"/public/**", "/internal/**", "/error"};
}
