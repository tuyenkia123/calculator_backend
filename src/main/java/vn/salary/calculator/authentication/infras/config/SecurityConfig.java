package vn.salary.calculator.authentication.infras.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import vn.salary.calculator.authentication.infras.JwtAuthenticationFilter;
import vn.salary.calculator.authentication.infras.JwtProvider;

import java.util.List;

@Configuration
@Slf4j
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    @Value("${url.frontend}")
    private String urlFE;

    public SecurityConfig(JwtProvider jwtProvider) {
        this.jwtAuthFilter = new JwtAuthenticationFilter(jwtProvider);
    }

    @Bean
    public SecurityFilterChain externalFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(urlFE));
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }

    @PostConstruct
    public void init() {
        log.info("SecurityConfig initialized with allowed origins: {}", urlFE);
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
