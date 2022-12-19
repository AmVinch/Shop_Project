package Kh.study.shop.item.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Kh.study.shop.item.service.ItemService;
import Kh.study.shop.item.vo.ItemVO;
import Kh.study.shop.member.vo.MemberVO;

@Controller
@RequestMapping("/item")
class ItemController {
	@Resource(name = "itemService")
	private ItemService itemService;
	
	//상품목록페이지
	//boolean isLoginFail : 로그인 실패시 true/ 그렇지않으면 false
	@GetMapping("/list")
	public String itemList(boolean isLoginFail, Model model,ItemVO itemVO) {
		//-----로그인 성공 및 실패 여부를 html에 데이터 전달하기-------//
		// System.out.println("@@@@@@@@@@@@@@@@@@@" + isLoginFail);
		model.addAttribute("isLoginFail",isLoginFail);
		
		// 상품 목록 조회(이미지 첨부 )
		model.addAttribute("itemList", itemService.selectItemList());
		
		return "content/item/item_list";
	}
	//상품상세페이지
	@GetMapping("/detailItem")
	public String detailItem(Model model ,String itemCode) {
		
		model.addAttribute("item",itemService.selectDetailItem(itemCode));
		 
		return "content/item/detail_item";
	}
	//장바구니
	@GetMapping("/cart")
	public String cart(Model model ,String itemCode) {
		return "content/item/cart";
	}
}
