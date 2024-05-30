package com.project.jwt.member;

import org.apache.ibatis.annotations.Mapper;
import com.project.jwt.command.MemberVO;


@Mapper
public interface MemberMapper {

	void join(MemberVO vo);
	MemberVO login(String username);
}
