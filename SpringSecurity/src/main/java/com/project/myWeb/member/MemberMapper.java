package com.project.myWeb.member;

import org.apache.ibatis.annotations.Mapper;

import com.project.myWeb.command.MemberVO;

@Mapper
public interface MemberMapper {

	void join(MemberVO vo);
	MemberVO login(String username);
}
