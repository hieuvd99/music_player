package com.example.musicplayer.security.jwt;

import com.example.musicplayer.security.serviceImpl.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

//Gọi các hàm

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);	//ghi log kiểm tra token


    private String jwtSecret="jwtGrokonezSecretKey";	//key jwtSecret, đặt ngẫu nhiên tùy chọn


    private int jwtExpiration = 86400;	//thời gian sống trên hệ thống

    //hàm tạo token
    public String generateJwtToken(Authentication authentication) {

        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal(); 	//gán theo kiểu princal 

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())	//set tại thời điểm hiện tại
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration * 1000))	//set thời gian sống
                .signWith(SignatureAlgorithm.HS512, jwtSecret)	//đăng kí với mã 
                .compact();	//đóng lại
    }
    
    //check token xem hợp lệ hay không
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);	//key đăng kí token không đúng
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);	//không đúng định dạng
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);	//token đúng nhưng đã chết
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);	//định dạng không đúng
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);	//có khoảng trắng,..
        }

        return false;
    }

    //lấy username từ token
    public String getUserNameFromJwtToken(String token) {

        String userName = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return userName;
    }
}
