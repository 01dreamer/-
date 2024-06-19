package com.hy.newsbackend.common.utils;

import com.hy.newsbackend.common.exception.CustomerAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    // 有效期
    public static final Long JWT_TTL = 60*60*1000L; // 一个小时
    // 设置密钥明文
    public static final String JWT_KEY = "your-256-bit-secret-your-256-bit-secret";

    // 生成令牌
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 生成jwt
     * @param subject subject token中要存放的数据(json格式) 用户数据
     * @param ttlMillis token超时时间
     * @return 返回生成的jwt
     */
    public static String createJWT(String subject,Long ttlMillis){
        JwtBuilder builder = getJwtBuilder(subject,ttlMillis,getUUID());
        return builder.compact();
    }
    /**
     * 生成token
     * @param id 用户id
     * @param subject 主题
     * @param ttlMillis 过期时间
     * @return 返回生成的token
     */
    public static String createJWT(String id ,String subject,Long ttlMillis){
        JwtBuilder builder = getJwtBuilder(subject,ttlMillis,id);
        return builder.compact();
    }

    // 生成jwt的业务逻辑代码
    private static JwtBuilder getJwtBuilder(String subject,Long ttlMillis, String uuid){
        // 指定加密算法
        SecureDigestAlgorithm<SecretKey, SecretKey> algorithm = Jwts.SIG.HS256;
        // 密匙实例
        SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes());
        if (ttlMillis==null){
            ttlMillis=JwtUtil.JWT_TTL;
        }
        return  Jwts.builder()
                .id(uuid)                   //唯一的ID
                .subject(subject)           // 主题 可以是JSON数据
                .issuer("hy")            // 签发者
                .signWith(secretKey, algorithm)   //设置密钥和算法
//                .claim("userId", userId) //负载用户id
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ttlMillis)); //超时时间
//                .compact();                 //返回生成token字符串
    }

    /**
     * 生成加密后的密钥 secretKey
     * @return 返回加密后的密钥
     */

    public static Claims parseJWT(String jwt){
        SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes());
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();
        }catch (Exception e){
            throw new CustomerAuthenticationException("token校验失败");
        }
    }

//    /**
//     *  使用jwt工具根据id生成token信息
//     * @param userId    编号
//     * @return  token信息
//     */
//    public  String createToken(long userId) {
//        //指定加密算法
//        SecureDigestAlgorithm<SecretKey, SecretKey> algorithm = Jwts.SIG.HS256;
//        //密匙实例
//        SecretKey key = Keys.hmacShaKeyFor(tokenSignKey.getBytes());
//        System.out.println("tokenExpiration = " + tokenExpiration);
//        System.out.println("tokenSignKey = " + tokenSignKey);
//        return  Jwts.builder()
//                .signWith(key, algorithm)   //设置密钥和算法
//                .claim("userId", userId) //负载用户id
//                .expiration(new Date(System.currentTimeMillis() + tokenExpiration*1000*60)) //超时时间
//                .compact();                 //返回生成token字符串
//    }
//
//    //从token字符串获取userid
//    public  Long getUserId(String token) {
//        //密匙实例
//        SecretKey key = Keys.hmacShaKeyFor(tokenSignKey.getBytes());
//        if(StringUtils.isEmpty(token)) return null;
//        Jws<Claims> claimsJws = Jwts.parser()
//                .verifyWith(key)
//                .build()
//                .parseSignedClaims(token);
//        Integer userId = (Integer)claimsJws.getPayload().get("userId");
//        return userId.longValue();
//    }
//    public boolean validateToken(String token) {
//        //密匙实例
//        SecretKey key = Keys.hmacShaKeyFor(tokenSignKey.getBytes());
//        try {
//            Jwts.parser()
//                    .verifyWith(key)
//                    .build()
//                    .parseSignedClaims(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
}