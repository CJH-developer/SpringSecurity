package com.project.jwt.util.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.jwt.security.MyUserDetails;
import com.project.jwt.util.JWTService;

//LoadByUser를 호출하는 필터가 UsernamePasswordAuthenticationFilter이다.
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter{
	//UsernamePasswordAuthenticationFilter : 로그인을 담당하는 필터 중 하나
	// 사용자 로그인을 커스터마이징 시킬 때 상속받고, 연결해주면 된다.
	
	private AuthenticationManager authenticationManager;
	
	public CustomLoginFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	// Post방식의 /login 요청이 들어오면 attemptAuthentication으로 연결
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		System.out.println("attemptAuthentication Filter 실행");
		
		
		String username = request.getParameter("username"); // form 방식으로 받을 때 이렇게 사용
		String password = request.getParameter("password");
		
		System.out.println(username + " " + password);
		
		//UsernamePasswordAuthentication
		// 이 값을 인증 매니저 Authuentication Manager에게 전달
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		
		// 유저 서비스의 loadByUser가 호출된다.
		Authentication authentication = authenticationManager.authenticate(token); // 로그인 실패시 null 반환
		
		System.out.println("필터 로그인 성공 시 : " + authentication);
		
		return authentication; // 스프링 시큐리티가 이 값을 가져가 스피링 세션에 등록시킴
	}

	// 로그인 성공 시 이 메서드가 실행
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		System.out.println("로그인 sucees 후에 실행됌");
		// jwt 토큰 발행
		
		MyUserDetails details = (MyUserDetails)authResult.getPrincipal();
		
		
		String token = JWTService.createToken(details.getUsername()); // 토큰 생성
		// 헤더에 담는다.
		response.setContentType("text/html; charset-UTF8");
		response.setHeader("Authorization", token);
		// 데이터도 보낼 수 있는데
		response.getWriter().println("서버에서 보낸 메세지");
	}

	// 로그인 실패 시에는 이 메서드가 실행
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		System.out.println("로그인 실패시 실행");
		
		response.setContentType("text/plain; charset-UTF8");
		response.sendError(500, "아이디와 비밀번호를 확인하세요");
	}

	
	
	
}
