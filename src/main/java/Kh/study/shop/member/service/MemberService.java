package Kh.study.shop.member.service;

import java.util.List;

import Kh.study.shop.member.vo.MemberVO;

public interface MemberService {
	//회원가입
	void join(MemberVO memberVO);
	//로그인
	MemberVO login(MemberVO memberVO);
	//목록조회
	List<MemberVO> selectMemberList();
	//상세조회
	MemberVO selectMemberDetail(String memberId);
}
