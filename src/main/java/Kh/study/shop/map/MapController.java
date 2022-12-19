package Kh.study.shop.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Kh.study.shop.member.vo.MemberVO;

@Controller
@RequestMapping("/map")
public class MapController {

	@GetMapping("/test1")
	public String mapTest1(Model model) {
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "java1");
		map.put(2, "java2");
		map.put(3, "java3");
		
		// 아래 map 은 데이터가 여러개 들고간다. 
		model.addAttribute("mapData",map);
		
		return "test/map/test1";
	}
	
	//map에 객체가 들어가있을때 html에서 표현하는 방법
	@GetMapping("/test2")
	public String mapTest2(Model model) {
		Map<String, MemberVO> map = new HashMap<>();
		
		MemberVO m1 = new MemberVO();
		m1.setMemberId("java1");
		m1.setMemberName("자바봐라11");
		map.put("member1", m1);

		MemberVO m2 = new MemberVO();
		m2.setMemberId("java2");
		m2.setMemberName("자바봐라22");
		map.put("member2", m2);

		MemberVO m3 = new MemberVO();
		m3.setMemberId("java3");
		m3.setMemberName("자바봐라33");
		map.put("member3", m3);
		
		
		
		model.addAttribute("mapData",map);
		
		return "test/map/test2";
	}
	//map에 list 데이터가 저장되어있을 때 html에서 사용하는 방법
	@GetMapping("/test3")
	public String test3(Model model) {
		List<MemberVO> list1 = new ArrayList<>();
		MemberVO m1 = new MemberVO(); m1.setMemberId("자바봐라라라"); m1.setMemberName("내가자바다11");     list1.add(m1);
		MemberVO m2 = new MemberVO(); m2.setMemberId("자바봐라마바사"); m2.setMemberName("내가자바다22");   list1.add(m2);
		
		List<MemberVO> list2 = new ArrayList<>();
		MemberVO m3 = new MemberVO(); m3.setMemberId("자바봐라면땅"); m3.setMemberName("내가자바다33");       list2.add(m3);
		MemberVO m4 = new MemberVO(); m4.setMemberId("자바봐라일락꽃"); m4.setMemberName("내가자바다444");     list2.add(m4);
		 
		Map<String, List<MemberVO>> map = new HashMap<>();
		map.put("꽃잎반", list1);
		map.put("풀잎반", list2);
		
		model.addAttribute("mapData",map);
		
		return "test/map/test3";
	}
}
