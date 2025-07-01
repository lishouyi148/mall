package com.mall.config;

import com.mall.entity.AuthInfo;
import com.mall.entity.UserRole;
import com.mall.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 1. 从请求头获取 Authorization 信息
        String authHeader = request.getHeader("Authorization");

        // 2. 检查 Authorization 格式（必须以 Bearer 开头）
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 提取纯 token（去掉 "Bearer " 前缀）
            String token = authHeader.substring(7).trim();

            // 3. 调用 AuthService 验证 token 有效性
            AuthInfo authInfo = authService.validateToken(token);
            System.out.println("Token 验证结果: " + (authInfo.isValid() ? "有效" : "无效"));

            // 4. 如果 token 有效，设置用户权限到安全上下文
            if (authInfo.isValid()) {
                // 初始化权限列表
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();

                // 打印原始角色（调试用）
                System.out.println("原始角色: " + authInfo.getRole());

                // 根据角色添加对应权限（带 ROLE_ 前缀）
                if (authInfo.getRole() == UserRole.ADMIN) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                } else if (authInfo.getRole() == UserRole.MERCHANT) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_MERCHANT"));
                }

                // 打印最终权限（调试用）
                System.out.println("最终权限列表: " + authorities);

                // 创建认证对象并设置到安全上下文
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        authInfo.getUserId(),  // 用户名/用户ID
                        null,                 // 密码（已认证通过，无需存储）
                        authorities           // 权限列表
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // token 无效时，清除安全上下文（可选）
                SecurityContextHolder.clearContext();
                System.out.println("Token 无效，已清除认证信息");
            }
        }

        // 5. 继续执行过滤器链（必须调用，否则请求会被拦截）
        filterChain.doFilter(request, response);
    }
}