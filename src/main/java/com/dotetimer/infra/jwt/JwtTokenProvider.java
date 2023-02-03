package com.dotetimer.infra.jwt;

import com.dotetimer.infra.exception.CustomException;
import com.dotetimer.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

import static com.dotetimer.infra.exception.ErrorCode.EXPIRE_AUTH_TOKEN;
import static com.dotetimer.infra.exception.ErrorCode.INVALID_AUTH_TOKEN;

// Jwt 토큰 발급 및 실제 검증
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider{
    @Value("${security.jwt.token.secret-key}") // 생성자 호출 시점 이후
    private String secretKey;
    private Key key;
    private final long accessTokenValidTime = 30 * 60 * 1000L; // access 토큰 유효시간 30분
    private final long refreshTokenValidTime = 60 * 60 * 24 * 12 * 1000L; // refresh 토큰 유효시간 한 달
    private final CustomUserDetailsService customuserDetailsService;

    // 초기화 @PostConstruct vs implements InitializingBean
    // 암호화 hmac Sha256 Base64 Bcrypt
    @PostConstruct
    private void init() {
        // byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 로그인 요청 시 AccessToken 생성
    public String createAccessToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk); // Jwt payload 정보단위, user 식별값
        claims.put("roles", roles); // key : value
        Date now = new Date();
        // Jwt header, payload, signature
        return Jwts.builder().setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 저장
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // 만료 시간 저장
                .signWith(key, SignatureAlgorithm.HS256) // signature에 key 저장
                .compact();
    }

    // 로그인 요청 시, refreshToken 생성
    public String createRefreshToken(String value) {
        Claims claims = Jwts.claims(); // Jwt payload 정보단위, user 식별값
        claims.put("value", value); // key : value
        Date now = new Date();
        // Jwt header, payload, signature
        return Jwts.builder().setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 저장
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // 만료 시간 저장
                .signWith(key, SignatureAlgorithm.HS256) // signature에 key 저장
                .compact();
    }

    // Jwt 토큰에서 인증 정보(authentication) 확인
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customuserDetailsService.loadUserByUsername(getClaims(token).getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 Claims 정보 확인 -> 회원 정보 확인 .getSubject();
    // parser().setSigningKey 사용X, parserBuilder().setSigningKey.build() 권장O
    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 토큰의 유효성 검증 및 만료 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
            // return claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.info(e.getMessage());
            throw new CustomException(INVALID_AUTH_TOKEN);
        } catch (ExpiredJwtException e) { // 토큰 만료
            log.info(e.getMessage());
            throw new CustomException(EXPIRE_AUTH_TOKEN);
        }
        // UnsupportedJwtException : 예상하는 형식과 일치하지 않는 특정 형식이나 구성의 JWT일 때
        // MalformedJwtException : JWT가 올바르게 구성되지 않았을 때
        // SignatureException :  JWT의 기존 서명을 확인하지 못했을 때
    }
}
