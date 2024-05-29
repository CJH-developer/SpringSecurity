package com.project.myWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.myWeb.command.MemberVO;
import com.project.myWeb.member.MemberMapper;
import com.project.myWeb.security.MyUserDetails;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private MemberMapper memberMapper;
	
	// 시큐리티 세션에 저장된 인증 객체 사용 방법
	@GetMapping("/hello")
	public String hello(Authentication auth) {
		
		if(auth != null) {
			MyUserDetails details =  (MyUserDetails)auth.getPrincipal();
			String username = details.getUsername();
			String password = details.getPassword();
			String role = details.getRole();
			
			System.out.println(username);
			System.out.println(password);
			System.out.println(role);
		}
		
		return "hello";
	}
	
	@GetMapping("/all")
	public String all() {
		return "all";
	}
	
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	@GetMapping("/login")
	public String login(@RequestParam(value="err", required=false) String err, Model model) {
		
		if(err != null ) {
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인하세요.");
		}
		
		return "login";
	}
	
	@GetMapping("/user/mypage")
	public @ResponseBody String userMypage() {
		return "REST 유저 페이지";
	}
	
	@GetMapping("/admin/mypage")
	public @ResponseBody String adminMypage() {
		return "REST 어드민 페이지";
	}
	
	@PostMapping("/joinForm")
	public String joinForm(MemberVO vo) {
		
		
		String pw = bCryptPasswordEncoder.encode(vo.getPassword());
		vo.setPassword(pw); // 암호화된 비밀번호로 처리
		
		memberMapper.join(vo);
		
		return "redirect:/login";
	}
	
	// 접근 권한 없음 핸들러
	@GetMapping("/deny")
	public @ResponseBody String deny() {
		return "권한이 없는 사용자입니다";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/test")
	public @ResponseBody String test() {
		return "어노테이션 권한";
	}
	
}