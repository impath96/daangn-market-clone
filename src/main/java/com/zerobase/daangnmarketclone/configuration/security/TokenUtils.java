package com.zerobase.daangnmarketclone.configuration.security;

import com.zerobase.daangnmarketclone.domain.entity.user.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenUtils {

    // secret-key 는 추후에 별도의 설정 파일로 분리
    private static final String secretKey = "ThisIsA_SecretKeyForJwtExample!!ThisIsA_SecretKeyForJwtExampleThisIsA_SecretKeyForJwtExample";

    public static String generateJwt(UserDetails userDetails) {

        JwtBuilder builder = Jwts.builder()
            .setHeader(createHeader())
            .setSubject(userDetails.getUsername())
            .setClaims(createClaims(userDetails))
            .setExpiration(createExpiration())
            .signWith(SignatureAlgorithm.HS256, createSigningKey());

        return builder.compact();
    }

    public static String extractUsername(String token) {
        return (String) Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
            .parseClaimsJws(token).getBody().get("email");
    }

    public static UserRole extractRole(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
            .parseClaimsJws(token).getBody();

        String roleName = (String) ((Map) ((List) claims.get("roles")).get(0)).get("authority");
        return Enum.valueOf(UserRole.class, roleName);
    }

    public static boolean isValid(String token) {

        try {
            Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
        } catch (SignatureException ex) {
            throw new SignatureException("invalid token request exception - Incorrect signature");
        } catch (MalformedJwtException ex) {
            throw new RuntimeException("invalid token request exception - malformed jwt token");
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("invalid token request exception - 토큰이 만료. 갱신 필요");
        } catch (UnsupportedJwtException ex) {
            throw new RuntimeException("invalid token request exception - Illegal argument token");
        }

        return true;
    }

    private static Map<String, Object> createHeader() {

        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }


    private static Map<String, Object> createClaims(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("email", userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities());

        return claims;
    }

    private static Date createExpiration() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);

        return calendar.getTime();

    }

    private static Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }


}
