package btongtong.btongtalkback.config;

import btongtong.btongtalkback.handler.CustomAccessDeniedHandler;
import btongtong.btongtalkback.handler.CustomAuthenticationHandler;
import btongtong.btongtalkback.handler.Oauth2FailureHandler;
import btongtong.btongtalkback.handler.Oauth2SuccessHandler;
import btongtong.btongtalkback.filter.JwtFilter;
import btongtong.btongtalkback.util.JwtUtil;
import btongtong.btongtalkback.service.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationHandler customAuthenticationHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final Oauth2FailureHandler oauth2FailureHandler;
    private final Oauth2UserService oauth2UserService;
    private final JwtUtil jwtUtil;
    @Value("${domain.name}")
    private String domainName;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        configureCors(http);
        disableDefaults(http);
        configureSession(http);
        configureAuthorization(http);
        configureExceptionHandling(http);
        configureOauth2Login(http);
        addJwtFilter(http);

        return http.build();

    }

    private void configureCors(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(getCorsConfigurationSource()));
    }

    private static void disableDefaults(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);
    }

    private static void configureSession(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private static void configureAuthorization(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/reissue", "/api/oauth2/**", "/api/login/**", "/api/categories/**")
                .permitAll()
                .anyRequest()
                .authenticated()
        );
    }

    private void configureExceptionHandling(HttpSecurity http) throws Exception {
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(customAuthenticationHandler)
                .accessDeniedHandler(customAccessDeniedHandler)
        );
    }

    private void configureOauth2Login(HttpSecurity http) throws Exception {
        http.oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(endpoint -> endpoint.baseUri("/api/oauth2/authorization"))
                .redirectionEndpoint(endpoint -> endpoint.baseUri("/api/login/oauth2/code/*"))
                .userInfoEndpoint(userInfo -> userInfo.userService(oauth2UserService))
                .successHandler(oauth2SuccessHandler)
                .failureHandler(oauth2FailureHandler)
        );
    }

    private void addJwtFilter(HttpSecurity http) {
        http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource getCorsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(domainName);
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
