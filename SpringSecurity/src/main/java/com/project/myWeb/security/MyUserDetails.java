package com.project.myWeb.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.myWeb.command.MemberVO;

public class MyUserDetails implements UserDetails {

	// 로그인해서 조회한 MemberVO 객체 
	private MemberVO memberVO;
	
	
	// 반드시 UserVO 객체를 멤버변수로 담고 생성
	public MyUserDetails(MemberVO vo) {
		this.memberVO = vo;
	}
	
	// role 리턴
	public String getRole() {
		return memberVO.getRole();
	}
	
	// 로그인시 권한을 리턴해주는 함수
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> list = new ArrayList<>();
		
		// 권한이 여러 개라면 반복문
		list.add( () -> memberVO.getRole() );
		
		return list;
	}

	// 유저의 비밀번호를 반환하는 자리
	@Override
	public String getPassword() {
		
		return memberVO.getPassword();
	}

	// 유저의 아이디를 반환하는 자리
	@Override
	public String getUsername() {
		
		return memberVO.getUsername();
	}

	// 계정 만료 여부 확인 ( true = 네 )
	@Override
	public boolean isAccountNonExpired() {
			
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true; // 계정 락 여부, 락이 걸리지 않았습니까?
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true; // 비밀번호 만료 여부 / true 만료 x
	}

	@Override
	public boolean isEnabled() {
		
		return true; // 계정 사용 여뷰
	}

}
