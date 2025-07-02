package com.mall.service.impl;

import com.mall.dao.UserDao;
import com.mall.entity.*;
import com.mall.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserDao userDao;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        System.out.println("JWT 密钥加载成功，长度: " + secretKey.length() + " 字符");
    }

    @Override
    public AuthResult authenticate(String username, String password, UserRole role) {
        AuthResult result = new AuthResult();
        result.setRole(role);

        try {
            Object user = null;
            if (role == UserRole.ADMIN) {
                Admin admin = userDao.findAdminByUsername(username);
                if (admin != null && passwordMatches(password, admin.getPassword())) {
                    user = admin;
                }
            } else if (role == UserRole.MERCHANT) {
                Merchant merchant = userDao.findMerchantByUsername(username);
                if (merchant != null && merchant.getStatus() == 1
                        && passwordMatches(password, merchant.getPassword())) {
                    user = merchant;
                }
            } else if (role == UserRole.USER) {
                User foundUser = userDao.findUserByUsername(username);
                if (foundUser != null && foundUser.getStatus() == 1
                        && passwordMatches(password, foundUser.getPassword())) {
                    user = foundUser;
                }
            }

            if (user != null) {
                result.setSuccess(true);
                result.setMessage("登录成功");
                result.setUserInfo(user);

                Integer userId = null;
                if (role == UserRole.ADMIN) {
                    userId = ((Admin) user).getId();
                } else if (role == UserRole.MERCHANT) {
                    userId = ((Merchant) user).getId();
                } else if (role == UserRole.USER) {
                    userId = ((User) user).getId();
                }

                result.setToken(generateToken(userId, role));
            } else {
                result.setSuccess(false);
                result.setMessage("用户名、密码错误或账户已禁用");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("认证过程发生错误: " + e.getMessage());
        }

        return result;
    }

    @Override
    public String generateToken(Integer userId, UserRole role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role.name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    @Override
    public AuthInfo validateToken(String token) {
        AuthInfo info = new AuthInfo();
        info.setValid(false);

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();

            info.setUserId(Integer.parseInt(claims.get("userId").toString()));
            info.setRole(UserRole.valueOf(claims.get("role").toString()));
            info.setValid(true);
        } catch (ExpiredJwtException e) {
            System.out.println("Token已过期: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("签名验证失败: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Token解析错误: " + e.getMessage());
        }

        return info;
    }

    private boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}