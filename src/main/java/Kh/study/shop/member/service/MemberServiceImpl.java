package Kh.study.shop.member.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Kh.study.shop.member.vo.MemberVO;
@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired//어노테이션으로 객체생성
	private SqlSessionTemplate sqlSession;
	
	//join
	@Override
	public void join(MemberVO memberVO) {
		 sqlSession.insert("memberMapper.join", memberVO);
	}
	//login
	@Override
	public MemberVO login(MemberVO memberVO) {
		return sqlSession.selectOne("memberMapper.login",memberVO);
	}
	//목록조회
	@Override
	public List<MemberVO> selectMemberList() {
		return sqlSession.selectList("memberMapper.selectMemberList");
	}
	//상세조회
	@Override
	public MemberVO selectMemberDetail(String memberId) {
		return sqlSession.selectOne("memberMapper.selectMemberDetail",memberId);
	}

}
