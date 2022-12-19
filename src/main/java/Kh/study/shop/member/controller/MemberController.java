package Kh.study.shop.member.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import Kh.study.shop.config.MemberRole;
import Kh.study.shop.config.MemberStatus;
import Kh.study.shop.member.service.MemberService;
import Kh.study.shop.member.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Resource(name = "memberService")
	private MemberService memberService;
	
	//---- config에서 만든 암호화 객체 불러와서 사용하기-----------------------------------------------------//
	@Autowired
	private PasswordEncoder encoder;
	
	//회원가입
	@PostMapping("/join")
	public String join(MemberVO memberVO) {
		// 쿼리문이기때문에 통상적으로는 컨트롤러에 작성하나
		// serviceImpl에 한번에 작성하기도 한다. (문제없음)
		// memberVO값에 status값 세팅해주기
		// null값들어가지않도록 Enum파일에 있는 'ACTIVE' 값넣어주기
		memberVO.setMemberStatus(MemberStatus.ACTIVE.toString());
		memberVO.setMemberRole(MemberRole.MEMBER.toString());
		
		// 위에서 불러온 암호화 객체를 사용해서 암호화한 비밀번호값 넣어 디비저장해준다.
		memberVO.setMemberPw(encoder.encode(memberVO.getMemberPw()));
		
		memberService.join(memberVO);
		
		return "redirect:/item/list";//redirect: 사용해야 컨트롤러 사용한다.(상품목록페이지 )
	}
	
// --------------------------------   시큐리티 적용  로그인--------------------------------------------------------//
	// !!!! 시큐리티 적용하면 post매핑 로그인 자동호출되어 필요 없다 !!!!! //

//	//로그인(ajax.ver)
//	@ResponseBody//ajax사용할때(단,리턴값은 필요한 데이터만! html페이지가 아님!)
//	@PostMapping("/ajaxLogin")
//	public boolean ajaxLogin(HttpSession session, MemberVO memberVO) {
//        MemberVO loginInfo = memberService.login(memberVO);
//        // 로그인 정보 세션 저장
//        if(loginInfo != null) {
//			session.setAttribute("loginInfo", loginInfo);
//        } 
//        // 바로 loginInfo를 주지않고 삼항연산자 사용한다
//		return loginInfo == null? false :true;//자료형 boolean
//	}
	
	
	// 로그인성공시,로그인실패시 -> 로그인페이지로 이동
	// 스프링 시큐리티 config에서 설정한 경로대로 보내준다.
	@GetMapping("/loginResult")
	public String loginResult() {
		System.out.println("로그인 결과");
		return "content/member/login_result";
	}
//------------------------------------------------------------------------------------------------------------//	
	

	
	
	
// --------------------------------   시큐리티 적용 전 --------------------------------------------------------//
//	//로그인(ajax.ver)
//	@ResponseBody//ajax사용할때(단,리턴값은 필요한 데이터만! html페이지가 아님!)
//	@PostMapping("/ajaxLogin")
//	public boolean ajaxLogin(HttpSession session, MemberVO memberVO) {
//		MemberVO loginInfo = memberService.login(memberVO);
//		// 로그인 정보 세션 저장
//		if(loginInfo != null) {
//			session.setAttribute("loginInfo", loginInfo);
//		} 
//		// 바로 loginInfo를 주지않고 삼항연산자 사용한다
//		return loginInfo == null? false :true;//자료형 boolean
//	}
//	//로그인(alert.ver)
//	@PostMapping("/login")
//	public String login(HttpSession session, MemberVO memberVO) {
//		//System.out.println(memberVO);
//		MemberVO loginInfo = memberService.login(memberVO);
//		// 로그인 정보 세션 저장
//		if(loginInfo != null) {
//			session.setAttribute("loginInfo", loginInfo);
//			
//		} else {
//			System.out.println("null!!!!!");
//			session.setAttribute("loginInfo", null);
//		}
//		return "content/member/login_result";
//	}
//---------------------------------------------------------------------------------------------------------------//	
	
	//로그아웃
	//get방식과 post방식의 차이점
	//GET 방식 : 어떠한 정보를 가져와서 조회하기 위해 사용되는 방식
	//POST 방식: 데이터를 서버로 제출하여 추가 또는 수정하기 위해서 데이터를 전송하는 방식
//	@GetMapping("/logout")//get메소드사용해야 에러가 안난다
//	public String logout(HttpSession session ) {
//		session.removeAttribute("loginInfo");
//		
//		return "redirect:/item/list";
//	}
	
	
}
