package com.project.jwt.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JWTService {

	private static String secretKey = "coding404"; // 시그니처를 만들기 위한 비밀키

    // JWT 토큰 생성
    public static String createToken(String username) {
    	//암호화알고리즘
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        
        //만료시간
        long expire = System.currentTimeMillis() + 3600000; //1시간뒤
        
        // 페이로드 생성
        Builder builder = JWT.create()
                .withSubject(username) // 토큰의 주제(subject) 설정
                .withIssuedAt(new Date()) // 토큰 발급 시간 설정 (현재 시간)
                .withExpiresAt(new Date(expire)) // 토큰 만료 시간 설정
                .withIssuer("coding404서버") //토큰발행자
        		.withClaim("admin", "나야나~"); //공개클래임

        return builder.sign(algorithm); // 비밀 키로 서명하여 토큰 생성
    }
	
    //토큰검증
    public static boolean validateToken(String token) throws JWTVerificationException { //확인단계중하나라도 실패
        Algorithm algorithm = Algorithm.HMAC256(secretKey); //검증 알고리즘
        JWTVerifier verifier = JWT.require(algorithm).build(); //token을 검증할 객체생성
         
        verifier.verify(token); // 토큰 검증을 수행하며, 만료 시간도 자동으로 검사됨

        return true; // 검증 성공 시 true 반환
    }
    
	
}
