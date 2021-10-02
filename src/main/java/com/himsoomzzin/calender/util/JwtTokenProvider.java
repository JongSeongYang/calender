package com.himsoomzzin.calender.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    /*
    수정
     */
    public final static String secretKey = "de391c95fe8f4ae3d50adbdbcf2d7e7658638051c7c8cf27a3568a5fcb89ca20"; // 힘순찐 hs256 값

    public String createUserToken(Integer userId, String loginId, String pwd) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        String key = secretKey;
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        Key signKey = new SecretKeySpec(keyBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, String> claimMap = new HashMap<>();
        claimMap.put("userId", userId.toString());
        claimMap.put("loginId", loginId);
        claimMap.put("pwd", pwd);

        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(claimMap)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24))) // 24시간
                .signWith(signKey, signatureAlgorithm);

        return builder.compact();
    }

    public boolean checkExpToken(String token) {
        try {
            String key = secretKey;
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Map<String, String> getClaims(String token) {
        String key = secretKey;
        Claims claims = Jwts.parserBuilder().setSigningKey(key.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        Map<String, String> map = new HashMap<>();
        map.put("staffId", claims.get("userId", String.class));
        map.put("loginId", claims.get("loginId", String.class));
        map.put("pwd", claims.get("pwd", String.class));

        return map;
    }
}
