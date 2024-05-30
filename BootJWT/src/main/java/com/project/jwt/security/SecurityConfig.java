package com.project.jwt.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.project.jwt.util.filter.CustomLoginFilter;
import com.project.jwt.util.filter.FilterOne;
import com.project.jwt.util.filter.FilterTwo;
import com.project.jwt.util.filter.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.csrf().disable();
		http.formLogin().disable();
		http.httpBasic().disable(); // http의 기본 인증 형태도 폐기 Authorization(아이디,비밀번호 형태 폐기) 
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션방식 비활성화
		
		// 2. 크로스 오리진 활성화
		http.cors( Customizer.withDefaults() );
		
		// 3. 시큐리티에 필터 추가
		//http.addFilter(new FilterOne()); //addFilter에는 시큐리티 타입의 필터만 등록이 된다.
		// 시큐리티 타입의 필터가 아니라 ,일반 필터라면 시큐리티 필터 전후로 필터를 추가한다.
		//http.addFilterBefore(new FilterOne(), UsernamePasswordAuthenticationFilter.class);
		//http.addFilterAfter(new FilterTwo(), FilterOne.class);
		
		// 스프링 시큐리티 타입의 필터를 커스터마이징하여 사용
		// 클라이언트에서 /login post방식으로 요청
		
		AuthenticationManager authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));		
		//http.addFilter(new CustomLoginFilter(authenticationManager) ); // 로그인 필터
		//http.addFilter(new JWTAuthenticationFilter(authenticationManager)); // JWT 인증 필터
		
		http.requestMatchers().antMatchers("/login")
							  .and()
							  .addFilter(new CustomLoginFilter(authenticationManager));
		
		http.requestMatchers().antMatchers("/api/v1/**")
							  .and()
							  .addFilter(new JWTAuthenticationFilter(authenticationManager));
		
		return http.build();
	}
	
	//1. 크로스 오리진 필터등록
	//서버가 다를 때, 옵션 요청을 보내게 되는데, 옵션 요청에 요청을 도메인 주소를 헤더에 담아 보내는 작업
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration configuration = new CorsConfiguration();
		//configuration.setAllowedOrigins(Arrays.asList("http://localhost:8181")); //부메랑에서 요청이 deny됨
		configuration.setAllowedOrigins(Arrays.asList("*")); //모든요청 주소를 허용 == CrossOrigin
		configuration.setAllowedMethods(Arrays.asList("*")); // * : 모든요청 메서드를 허용
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
		
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
