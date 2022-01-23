package com.hnj.code.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.hnj.code.model.AuthenticatedUserInfo;
import com.hnj.code.service.JwtUserDetailsService;
import com.hnj.code.service.impl.RateExchangeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    private static final long JWT_TOKEN_VALIDITY = 10 * 60;
    private static final String SUCCESS = "Success";

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private static final Logger LOGGER = LoggerFactory.getLogger(RateExchangeServiceImpl.class);

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secret;

    public AuthenticatedUserInfo getAuthUserInfo(HttpServletRequest httpServletRequest) {

        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
        String userEmail;
        String jwt;

        AuthenticatedUserInfo authenticatedUserInfo = AuthenticatedUserInfo.builder().email(null).authenticated(false).build();

        try {
            if (!authorizationHeaderIsInvalid(authorizationHeader)) {
                jwt = authorizationHeader.substring(7);
                userEmail = getUsernameFromToken(jwt);
            } else {
                LOGGER.info("Header is Not Valid ");
                return authenticatedUserInfo;
            }

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (validateToken(jwt, userDetails)) {
                LOGGER.info("Token Is valid");
                authenticatedUserInfo.setEmail(userDetails.getUsername());
                authenticatedUserInfo.setAuthenticated(true);
                authenticatedUserInfo.setMessage(SUCCESS);
            }
        } catch (Exception e){
            LOGGER.error("Exception in token "+ e.getMessage());
            authenticatedUserInfo.setMessage(e.getMessage());
        }

        return authenticatedUserInfo;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean authorizationHeaderIsInvalid(String authorizationHeader) {
        return authorizationHeader == null
                || !authorizationHeader.startsWith(BEARER);
    }
}

