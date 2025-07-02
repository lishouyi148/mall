package com.mall.config;

import com.mall.entity.AuthInfo;
import com.mall.entity.UserRole;
import com.mall.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    // 公开路径列表，无需 Token 验证
    private static final String[] PUBLIC_PATHS = {
            "/api/auth/",
            "/api/users",
            "/api/users/",
            "/api/merchants/"
    };

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 跳过公开路径
        if (isPublicPath(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从请求头获取 Authorization 信息
        String authHeader = request.getHeader("Authorization");

        // 检查并处理 Token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();
            AuthInfo authInfo = authService.validateToken(token);

            if (authInfo.isValid()) {
                // 仅设置用户角色，不分配任何权限
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        authInfo.getUserId(),
                        null,
                        null // 不设置任何权限
                );
                // 将用户角色信息存入 Authentication 对象
                authentication.setDetails(authInfo.getRole());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 无论是否有 Token，都继续执行过滤器链
        filterChain.doFilter(request, response);
    }

    // 判断请求路径是否为公开路径
    private boolean isPublicPath(String path) {
        for (String publicPath : PUBLIC_PATHS) {
            if (path.startsWith(publicPath)) {
                return true;
            }
        }
        return false;
    }
}