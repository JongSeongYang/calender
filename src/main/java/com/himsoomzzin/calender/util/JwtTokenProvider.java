package com.himsoomzzin.calender.util;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {

    /*
    수정
     */
    public final static String secretKey = "b7d7b79c964ced82519d8d771a5814abed00d4c1b63496f5d7973c6313d9deb5"; // hotelorder hs256 값

    public String createUserToken(Integer staffId, String loginId, String pwd) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        String key = secretKey;
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        Key signKey = new SecretKeySpec(keyBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, String> claimMap = new HashMap<>();
        /*
        수정
         */
        claimMap.put("staffId", staffId.toString());
        claimMap.put("loginId", loginId);
        claimMap.put("pwd", pwd);

        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(claimMap)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 6))) // 6시간
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
        /*
        수정
         */
        map.put("staffId", claims.get("staffId", String.class));


        return map;
    }
}
