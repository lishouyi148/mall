package com.mall.config;

import com.mall.config.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                // 1. 公开接口（无需认证，任何人可访问）
                .antMatchers("/api/auth/**").permitAll() // 登录/注册接口
                .antMatchers("/api/merchants/**").permitAll() // 商户所有接口（新增此行）
                .antMatchers("OPTIONS").permitAll() // 预检请求
                .antMatchers("/product/list").permitAll() // 允许所有人访问商品列表接口
                .antMatchers("/product/listByCondition").permitAll()
                .antMatchers("/product/insert").permitAll()
                .antMatchers("/product/{productId}").permitAll()
                .antMatchers("/api/orders/**").permitAll() // 允许订单接口匿名访问
                .antMatchers("/product/uploadImage").permitAll()
                .antMatchers("/uploads/**").permitAll() // 放行上传的静态资源
                // 2. 其他接口：需认证（如果有其他接口需要保护）
                .anyRequest().authenticated()
                .and()
                // 添加自定义JWT过滤器
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 无状态会话

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}