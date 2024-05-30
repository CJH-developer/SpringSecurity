package com.project.jwt.util.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.project.jwt.util.JWTService;

// BasicAuthenticationFilter는 HTTP 요청의 (BASIC) 인증 헤더를 처리하여 결과를 SecurityContextHolder에 저장
public class JWTAuthenticationFilter extends BasicAuthenticationFilter{

	// 생성자는 반드시 authenticationManager에서 받아줌
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("jwt basic 헤더 검사하는 필터 실행됌");
		
		// 헤더에 담긴 토큰을 검사해서 토큰의 무결성 여부를 확인해 에러를 띄우거나, 컨트롤러를 실행한다.
		
		// 1.헤더값 얻기
		String header = request.getHeader("Authorization");
		System.out.println(header);
		
		// 토큰을 보내지 않거나, 베어러로 시작하지 않으면
		if(header == null || header.startsWith("Bearer ") == false ) {
			response.setContentType("text/plain; charset-UTF-8");
			response.sendError(403, "토큰이 없습니다.");
			
			return; //함수 종료하지 않으면 Controller로 연결
		}
		
		// 토큰의 유효성 검사
		try {
			String token = header.substring(7);
			boolean result = JWTService.validateToken(token);
			
			// 정상적인 토큰인 경우
			if(result) {
				chain.doFilter(request, response); //컨트롤러로 연결
			}else { // 토큰의 유효기간이 만료된 경우
				response.setContentType("text/plain; charset-UTF-8");
				response.sendError(403, "만료된 토큰입니다..");
			}
		} catch (Exception e) {
			response.setContentType("text/plain; charset-UTF-8");
			response.sendError(403, "잘못된 토큰입니다.");
		}
		
		
	}

	
}
