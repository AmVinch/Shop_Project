package Kh.study.shop.member.service;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Kh.study.shop.member.vo.MemberVO;
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVO loginInfo = sqlSession.selectOne("memberMapper.login",username);
		
		// 로그인시 아아디를 잘못 입력한 경우 null이 뜨기때문에 오류캐치
		if (loginInfo == null) {
			throw new UsernameNotFoundException("오류발생");
		}
		
		UserDetails userDetails = User
								.withUsername(loginInfo.getMemberId())
								.password(loginInfo.getMemberPw())
								.roles(loginInfo.getMemberRole())
								.build();
				
		
		return userDetails;
	}

}
