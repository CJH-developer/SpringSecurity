package com.project.myWeb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.myWeb.command.MemberVO;
import com.project.myWeb.member.MemberMapper;

//빈으로 등록되어 있으면, 스프링이 UserDetailsService 타입을 찾아서, 사용자가 로그인시
//loadUserByUsername을 실행시킨다.
@Service 
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private MemberMapper memberMapper;
	
	//loginProcessingUrl에 로그인 URL을 등록
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("사용자가 로그인을 시도함");
		System.out.println("사용자가 입력한 아아디 : " + username);
		
		// 로그인 시도 (비밀번호는 시큐리티가 알아서 비교 후에 처리한다.)
		// 로그인을 하여 vo객체를 얻어온다.
		
		MemberVO vo = memberMapper.login(username);
		
		// 회원정보 존재
		// 비밀번호 비교를 위해, userDeatils 타입으로 리턴
		if(vo != null) {
			return new MyUserDetails(vo); // 스프링 시큐리티가 비밀번호 비교, 권한 확인한 이후 로그인 시도 처리
		}
		// 스프링 시큐리티의 설정한 형식대로, 권한까지 처리를 한다.
		// 아이디가 없거나 비밀번호가 틀리면 login?error 페이지로 기본 이동 ( 설정으로 변경 가능 )
		// 시큐리티는 특별한 세션의 모형을 사용 -> 시큐리티세션(new Authentication(new MyUserDetails() ) ) 모형으로 저장
		
		return null;
	}
	

}