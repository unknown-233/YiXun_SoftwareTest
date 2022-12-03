package com.yixun.yixun_backend.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTutils {
    //7天过期
    public static final long EXPIRE= 604800;
    //32位秘钥
    public static final String APP_SECRET= "abcdfghiabcdfghiabcdfghiabcdfghi";
    //生成token
    public static String generateToken(int userId){
        Date now= new Date();
        Date expiration= new Date(now.getTime()+1000* EXPIRE);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(Integer.toString(userId))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET)
                .compact();
    }
    //验证token
    public static boolean ifLegal(String token){
        try {
            Jwts.parser()
                    .setSigningKey(APP_SECRET)
                    .parseClaimsJws(token);
            // OK, we can trust this token
            return true;
        } catch (JwtException e) {
            //don't trust the token!
            return false;
        }
    }
    //解析token
    public static Claims getclaimsByToken(String token){
        return Jwts.parser()
                .setSigningKey(APP_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

}
