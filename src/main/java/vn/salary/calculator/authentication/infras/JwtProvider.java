package vn.salary.calculator.authentication.infras;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.salary.calculator.shared.DateUtils;
import vn.salary.calculator.shared.UserInfo;
import vn.salary.calculator.shared.exception.ErrorCode;
import vn.salary.calculator.shared.exception.ErrorCodeException;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    public String generateToken(UserInfo userInfo) {
        var now = DateUtils.now();
        return Jwts.builder()
                .setSubject(userInfo.id().toString())
                .claim("email", userInfo.email())
                .claim("fullName", userInfo.fullName())
                .claim("username", userInfo.username())
                .claim("id", userInfo.id())
                .claim("role", userInfo.role())
                .setIssuedAt(DateUtils.toJavaUtilDate(now))
                .setExpiration(DateUtils.toJavaUtilDate(now.plusSeconds(jwtExpiration)))
                .signWith(getSigningKey())
                .compact();
    }

    public UserInfo validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();

            return new UserInfo(
                    claims.get("id", Long.class),
                    claims.get("username", String.class),
                    claims.get("fullName", String.class),
                    claims.get("email", String.class),
                    claims.get("role", String.class)
            );
        } catch (JwtException e) {
            throw new ErrorCodeException(ErrorCode.UNAUTHORIZED, "Invalid JWT token: " + e.getMessage());
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
