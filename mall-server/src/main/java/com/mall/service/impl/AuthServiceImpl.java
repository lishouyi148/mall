package com.mall.service.impl;

import com.mall.dao.UserDao;
import com.mall.entity.Admin;
import com.mall.entity.Merchant;
import com.mall.entity.AuthInfo;
import com.mall.entity.AuthResult;
import com.mall.entity.UserRole;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserDao userDao;

    // 从配置文件读取密钥（唯一来源，所有地方均使用此变量）
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 初始化检查：打印密钥（仅调试用，生产环境删除）
    @PostConstruct
    public void init() {
        // 注意：生产环境不要打印密钥，此处仅为确认密钥是否正确加载
        System.out.println("JWT 密钥加载成功，长度: " + secretKey.length() + " 字符");
    }

    @Override
    public AuthResult authenticate(String username, String password, UserRole role) {
        AuthResult result = new AuthResult();
        result.setRole(role);

        try {
            Object user = null;
            // 根据角色查询对应的用户表
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
            }

            if (user != null) {
                result.setSuccess(true);
                result.setMessage("登录成功");
                result.setUserInfo(user);

                // 生成令牌：使用统一的 secretKey
                Integer userId = role == UserRole.ADMIN
                        ? ((Admin) user).getId()
                        : ((Merchant) user).getId();
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
        // 打印密钥字节数组的哈希值（用于比对）
        int keyHash = Arrays.hashCode(secretKey.getBytes());
        System.out.println("生成Token时的密钥哈希值: " + keyHash);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role.name());

        // 生成签名时使用统一的 secretKey（与验证时保持一致）
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes(StandardCharsets.UTF_8)) // 统一使用字节数组形式
                .compact();
    }

    @Override
    public AuthInfo validateToken(String token) {
        AuthInfo info = new AuthInfo();
        info.setValid(false);

        // 打印密钥字节数组的哈希值（用于比对）
        int keyHash = Arrays.hashCode(secretKey.getBytes());
        System.out.println("验证Token时的密钥哈希值: " + keyHash);
        System.out.println("接收到的 Token: " + token);

        try {
            // 验证签名时使用与生成时完全一致的 secretKey（字节数组形式）
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)) // 与 generateToken 保持一致
                    .parseClaimsJws(token)
                    .getBody();

            // 打印解析结果（调试用）
            System.out.println("解析的JWT角色: " + claims.get("role"));
            System.out.println("解析的JWT用户ID: " + claims.get("userId"));

            // 提取用户ID和角色
            info.setUserId(Integer.parseInt(claims.get("userId").toString()));
            info.setRole(UserRole.valueOf(claims.get("role").toString()));
            info.setValid(true);

            System.out.println("Token验证成功，角色: " + info.getRole());
        } catch (ExpiredJwtException e) {
            System.out.println("Token已过期: " + e.getMessage());
        } catch (SignatureException e) {
            // 重点关注：签名不匹配错误（密钥不一致会触发此异常）
            System.out.println("签名验证失败！生成Token的密钥与验证密钥不一致: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Token解析错误: " + e.getMessage());
        }

        return info;
    }

    // 密码匹配方法
    private boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}