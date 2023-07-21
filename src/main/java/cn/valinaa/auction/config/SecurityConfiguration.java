package cn.valinaa.auction.config;

import cn.valinaa.auction.security.custom.CustomAuthenticationHandler;
import cn.valinaa.auction.security.custom.CustomAuthorizationTokenFilter;
import cn.valinaa.auction.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;

/**
 * @author Valinaa
 * @Date : 2022/7/16
 * @Description : Spring Security 配置类
 */

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserServiceImpl userService;
    
    private final CustomAuthenticationHandler customAuthenticationHandler;
    private final CustomAuthorizationTokenFilter customAuthorizationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    /**
     * 跨域配置
     *
     * @return {@link CorsConfigurationSource}
     * @see CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // 允许跨域访问的 URL
        var allowedOriginsUrl = new ArrayList<String>();
        allowedOriginsUrl.add("*");
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置允许跨域访问的 URL
        config.setAllowedOrigins(allowedOriginsUrl);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    
    @Bean
    public StrictHttpFirewall httpFirewall() {
        var firewall = new StrictHttpFirewall();
        firewall.setAllowedHeaderNames((header) -> true);
        firewall.setAllowedHeaderValues((header) -> true);
        firewall.setAllowedParameterNames((parameter) -> true);
        return firewall;
    }
    @Bean
    public WebSecurityCustomizer securityCustomizer() {
        return (web) -> web.httpFirewall(this.httpFirewall());
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement)->sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationManager(this.authenticationManager())
                .addFilterBefore(customAuthorizationTokenFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exceptionHandling)->exceptionHandling
                        .authenticationEntryPoint(customAuthenticationHandler)
                        .accessDeniedHandler(customAuthenticationHandler))
                .formLogin((formLogin)->formLogin.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successForwardUrl("/index")
                        .permitAll())
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        // 允许所有OPTIONS请求
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // 允许直接访问授权登录、注册接口、MVC 默认错误地址
                        .requestMatchers("/login","/register","/error").permitAll()
                        .anyRequest().authenticated())
                .cors((cors) -> cors.configurationSource(this.corsConfigurationSource()))
                .logout((logout)->logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/index")
                        .logoutSuccessHandler(customAuthenticationHandler)
                        .permitAll())
                .formLogin(AbstractHttpConfigurer::disable);
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }
}
