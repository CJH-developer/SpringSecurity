package com.project.jwt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class APIController {

	// JWT - API 기반의 인증 정보를 처리할 때, 토큰 발급 ( 발행자 )
	
//	@PostMapping("/login")
//	public ResponseEntity<Object> login(@RequestBody MemberVO vo){
//		
//		// 사용자 정보를 받아서 로그인 처리
//		// ok - 로그인 성공
//		// 토큰 발행
//		String token = JWTService.createToken(vo.getUsername());
//		
//		return new ResponseEntity<>(token, HttpStatus.OK);
//	}
	
//	@PostMapping("/api/v1/getInfo")
//	public ResponseEntity<Object> getInfo(HttpServletRequest request){
//		
//		// 클라이언트에서 토큰을 헤더라는 곳에 담는다.
//		// 토큰을 전달받아 유효성을 확인한 후 만료인지, 통과인지 확인
//		String token = request.getHeader("Authorization");
//		System.out.println(token);
//		
//		// 토큰이 유효한지 확인, 
//		try {
//			boolean result = JWTService.validateToken(token);
//			System.out.println("토큰의 무결성 여부 : " + result);
//		} catch (Exception e) {
//			e.printStackTrace(); //토큰이 위조 됐을 경우
//			return new ResponseEntity<>("토큰 위조", HttpStatus.UNAUTHORIZED); //토큰 위조
//		}
//		
//		
//		
//		
//		return new ResponseEntity<>("통과된 사람이면 여기에 회원정보 발급", HttpStatus.OK);
//	}
	
	// 필터 동작 확인
	@GetMapping("/api/v1/filter")
	public String hello() {
		return "<h3>hello</h3>";
	}
	
	@PostMapping("/api/v1/getInfo")
	public ResponseEntity<Object> getInfo(HttpServletRequest request){
		
		System.out.println("getInfo메서드는 토큰이 있는 자만 호출할 권리가 있다.");
		
		return new ResponseEntity<Object>("getInfo ~~", HttpStatus.OK);
	}
	
}
