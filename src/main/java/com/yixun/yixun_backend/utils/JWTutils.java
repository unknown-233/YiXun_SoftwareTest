package com.yixun.yixun_backend.utils;


import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
@Component
public class JWTutils {
    //7天过期
    public static long EXPIRE= 604800;
    //32位秘钥
//    public static String APP_SECRET= "abcdfghiabcdfghiabcdfghiabcdfghi";
    public static String generateToken(String userId,String password) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(password)); // 以 password 作为 token 的密钥
    }

    //生成token
//    public static String generateToken(String userId){
//        Date now= new Date();
//        Date expiration= new Date(now.getTime()+1000* EXPIRE);
//
////        String JwtToken = Jwts.builder()
////                .setHeaderParam("typ", "JWT")
////                .setHeaderParam("alg", "HS256")
////                .setSubject("guli-user")
////                .setIssuedAt(new Date())
////                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
////                .claim("id", id)
////                .claim("nickname", nickname)
////                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
////                .compact();
//        String token=Jwts.builder()
//                .setHeaderParam("type","JWT")
//                .setSubject(userId)
//                .setIssuedAt(now)
//                .setExpiration(expiration)
//                .signWith(SignatureAlgorithm.HS512,APP_SECRET)
//                .compact();
//        return token;
//    }
    //验证token
//    public static boolean ifLegal(String token){
//        try {
//            Jwts.parser()
//                    .setSigningKey(APP_SECRET)
//                    .parseClaimsJws(token);
//            // OK, we can trust this token
//            return true;
//        } catch (JwtException e) {
//            //don't trust the token!
//            return false;
//        }
//    }
    //解析token
    public static int getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return Integer.parseInt(userId);
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

}
