package deprecated;

import cn.ecust.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Valinaa
 * @Date : 2022/7/16
 * @Description : Spring Security 配置类
 */

@EnableWebSecurity
public class SecurityConfiguration {
    
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    /**
     * * 跨域配置
     *
     * @return {@link CorsConfigurationSource}
     * @see CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // 允许跨域访问的 URL
        List<String> allowedOriginsUrl = new ArrayList<>();
        allowedOriginsUrl.add("http://localhost:8081");
        allowedOriginsUrl.add("http://127.0.0.1:8081");
        allowedOriginsUrl.add("http://localhost:3000");
        allowedOriginsUrl.add("http://127.0.0.1:3000");
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置允许跨域访问的 URL
        config.setAllowedOrigins(allowedOriginsUrl);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .formLogin().loginPage("/Login")
                .loginProcessingUrl("/LoginCheck")
                .successForwardUrl("/mainView").failureForwardUrl("/Login")
                .usernameParameter("admin").passwordParameter("password")
                .permitAll();
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManagerBean(ObjectPostProcessor<Object> objectPostProcessor) throws Exception {
        return new AuthenticationManagerBuilder(objectPostProcessor)
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(this.passwordEncoder())
                .and()
                .build();
    }
}
