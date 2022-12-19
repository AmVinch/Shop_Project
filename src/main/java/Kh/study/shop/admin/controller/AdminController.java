package Kh.study.shop.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import Kh.study.shop.admin.service.AdminService;
import Kh.study.shop.config.ShopDateUtil;
import Kh.study.shop.config.BoardUploadFileUtil;
import Kh.study.shop.item.service.ItemService;
import Kh.study.shop.item.vo.CategoryVO;
import Kh.study.shop.item.vo.ImgVO;
import Kh.study.shop.item.vo.ItemVO;
import Kh.study.shop.member.service.MemberService;
import Kh.study.shop.member.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
////////////////////////////////////////////////////
	@Resource(name = "adminService")
	private AdminService adminService;
	@Resource(name = "memberService")
	private MemberService memberService;
	@Resource(name = "itemService")
	private ItemService itemService;
/////////////////////////////////////////////////////

	//-------------------------------------------------------------------------//
	//(Admin컨트롤러의) 모든 메소드가 실행되기 전부터 무조건 실행되는 메소드
	// : 모든 메소드마다 공통으로 진행되는 메소드
	// @RequestParam(defaultValue = "1") : 로그인후 들어오면, 그땐 name값이 받아오지못해 파란색이 뜨지않는다.
	// 이점을 보완하기위해서 디폴트값을 1로 주면 관리자 첫화면 '상품등록'페이지에서도 자동으로 파란색이 뜬다
	// side페이지에서 이동경로값에 처음부터 (name=1)이런식으로 데이터를 던지면 컨트롤러에서 stirng Menu매개변수로 받아
	// model객체에 넣어 사용해주면 별도로 하나하나 컨트롤러메소드마다 데이터 보내주지않아도 간략히 사용가능하다.(이전 model객체로 보낸거 삭제가능)
	
	@ModelAttribute
	public void test(@RequestParam(defaultValue = "1") String menu, Model model) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		model.addAttribute("menu",menu);
		System.out.println(menu);
		// 사이드메뉴 클릭시마다 이동경로가 보내준 데이터값이 아래처럼표시된다.
		// http://localhost:8081/admin/regCate?menu=1
	}
	//-------------------------------------------------------------------------//
	
	//[상품등록]
	//-------------------------------------------------------------------------//
	//(관리자_첫화면) :상품등록 및 상세페이지 동시
	@GetMapping("/regCate")
	public String admin(Model model, ItemVO itemVO) {
		//전체 카테고리  목록조회(좌측화면에만 데이터 뿌리기)
		model.addAttribute("cateListAll",adminService.cateListAll());
		//'사용' 중 카테고리 목록 조회(우측화면 상품등록시 적용되도록)
		model.addAttribute("cateListUse",adminService.cateListUse());
		
		// side 메뉴바 
		// menu라는 이름으로 1이라는 데이터를 보내면, side가 받는다.
		// 그러면 로딩을 하면 side도 항상 열려있기때문에 받아사용하면 active가 적용되어 파란색으로 표시된다.
		//model.addAttribute("menu","1");
		
		return "content/admin/reg_item";
	}
	
	//카테고리 등록
	@PostMapping("/regCate")
	public String regCate(CategoryVO categoryVO) {
		adminService.regCate(categoryVO);
		// 카테고리 등록 후, 다시 첫화면 페이지로 이동
		return "redirect:/admin/regCate";
	}
	
	//카테고리 '사용여부' 변경(ajax실행)
	//ajax사용하는 이유! 페이지이동없이하려고!!
	// -> 그래서 페이지이동 리턴값이 없다!
	@ResponseBody
	@PostMapping("/changeStatus")
	public void changeStatus(CategoryVO categoryVO) {
		//상태변경하기
		adminService.changeStatus(categoryVO);
	}
	
	// 상품등록_사용중 카테고리 목록 조회(ajax사용)
	@ResponseBody
	@PostMapping("/selectCategoryListInUseAjax")
	public List<CategoryVO>  selectCategoryListInUseAjax() {
		//사용중 카테고리 목록 재조회
		List<CategoryVO> cateList = adminService.cateListUse();
		return cateList;
	}
	
	// 상품등록 //
	// 일반적인 데이터는 커맨드 객체로 전달 받는다.
	// 첨부파일 데이터는 MultipartFile 객체를 통해 전달받아야한다.
	// 그리고 html에서도 name값 같은 변수명으로 일치해야한다.
	@PostMapping("/regItem")
	public String reg(ItemVO itemVO, ImgVO imgVO
									, MultipartFile mainImg 
									, List<MultipartFile> subImgs) {// 서브파일은 여러 파일이 들어가있으니 리스트!
		
		
		// main + sub 이미지를 한번에 데이터 보내기는 어떻게 해야할까?
		//단일 이미지 파일 첨부 - mainImg
		ImgVO  uploadInfo = BoardUploadFileUtil.uploadFile(mainImg);
		
		// 다중 이미지 파일 첨부 - subImgs
		List<ImgVO> uploadList = BoardUploadFileUtil.MultiUploadFile(subImgs);
		
		//다중서브이미지 리스트에 단일메인이미지까지 넣어준다.
		uploadList.add(uploadInfo);
		
		
		//-------------[상품정보 insert]----------------//
		// -- DB에 등록한 상품정보 insert 저장하기
		
		// (1)다음에 들어갈 item_code를 조회한다.
		// 왜? 원래 상품등록쿼리문에 같이 있던 조회문을 
		//별도로 빼서 item과 img 테이블에도 각각 itemCode를 조회하여 사용한다.
		String nextItemCode = adminService.getNextItemCode();
		itemVO.setItemCode(nextItemCode);// 아래 실행한 쿼리값에 빈값인 itemCode값을 채워줘야 쿼리실행된다 
		
		//image 정보를 insert하기 위한 데이터를 가진 uploadList에 
		//조회한 item_code값도 넣어준다.
		for(ImgVO vo : uploadList) {
			vo.setItemCode(nextItemCode);
		}
		// 실제로 첨부된 이미지리스트를 하나씩 빼서 출력하면 
		// 상품등록시 첨부한 이미지들을의 정보를 콘솔에서 확인가능하다.
//		for(ImgVO vo : uploadList) {
//			System.out.println(vo);
//		}
		
		
		//이전에 itemVO에 리스트변수의 setter가져오기
		//itemVO는 첨부된 이미지까지 모조리 다 갖는다.
		itemVO.setImgList(uploadList);
		
		//상품이미지정보 insert +  이미지정보 insert
		// (2)그 조회한 item_code로 insert를 진행한다.
		adminService.regItem(itemVO);
		
		
		
		// 내가 만든 쿼리 가져온거
		// 상품의 이미지정보도 insert
		//itemService.insertImage(imgVO);
		// 이미지 + 상품등록 
		//itemService.insertItem(itemVO, imgVO);
		
		return "redirect:/admin/regCate";//관리자 첫화면
	}
	
	
	//회원권한설정클릭시 첫화면
	@GetMapping("/setMemberRole")
	public String setMemberRoleProccess(Model model,MemberVO memberVO) {
		model.addAttribute("memberList",memberService.selectMemberList());
		
		//model.addAttribute("menu","3");
		
		return "content/admin/set_member_role" ;
	}
	
	
	 // (ajax)회원 상세정보조회
	@ResponseBody
	@PostMapping("/showDetailmemberInfo") 
	public MemberVO showDetailmemberInfo(String memberId) { 
		MemberVO memberInfo = memberService.selectMemberDetail(memberId);
		return memberInfo;
	}
	
	//상세조회 후 수정하기
	@PostMapping("/updateMemberRole")
	public String updateMemberRole(MemberVO memberVO) {
		// 수정쿼리문
		return "redirect:/admin/setMemberRole";
	}
//-------------------------------------------------------------------------------//	
	//상품관리 페이지이동
	// itemVO 매개변수로 넘기면(커맨드객체로 사용하겠다는 뜻) 오류발생
	// itemStock은 int 로 정수형이기때문에 itemVO로 매개변수를 넘기면 검색버튼누르면
	// 재고에 아무값도 넣지 않으면 빈값(null아님)으로 넘어가기때문에 
	//빈값은 문자열로 인식하여 오류가 발생한다. 
	// 보내야하는 데이터가 무엇인지 명확히알고 itemVO라는 매개변수로 넘기지 않도록 주의할 것!!
	
	@RequestMapping("/itemManage")// para (파라매터) : 매개변수로받아오는데이터
	public String itemManage(@RequestParam Map<String, String> paramMap,Model model) {
		// html에 toDate와 fromDate 라는 변수명이 새로 생겼기 때문에 이를 어떻게 데이터를 받을것인가?
		// 커맨드객체인 itemVO를 사용하면, itemVO에 있는 데이터 이외의 추가로 생성한 데이터들은 못받는데
		
		// 단, Map 에도 단점이 있다.
		//단점① 잘못된 데이터나 오타가 나도 그대로 들고오기 때문에 거를 수 없다.
	    //단점② 자동 형변환이 안 된다.
		
		//관련이 없는 데이터들도 모두 들고오기때문에 
		// 또, map은 자동형변환이 되지 않는다.
		// Map을 쓰면 데이터를 추가로 만들어진 것들도 모두 받아올 수 있다.
		//paramMap.put("itemName", "java");
		
		// map의 itemName 이라는 이름을 가진 key 값의 value를 가져온다
		// 처음에는 null 값이 뜬다.
		System.out.println("상품명 : " + paramMap.get("itemName"));
		System.out.println("카테고리코드 : " + paramMap.get("cateCode"));
		System.out.println("재고 : " + paramMap.get("itemStock"));
		System.out.println("fromDate : " + paramMap.get("fromDate"));
		System.out.println("toDate : " + paramMap.get("toDate"));
		System.out.println("상품상태 : " + paramMap.get("itemStatus"));
		
		// 등록된 상품목록조회
		model.addAttribute("regItemList", itemService.selectRegItemList(paramMap));
		// 카테고리 목록조회
		model.addAttribute("cateListAll", adminService.cateListAll());
		
		//오늘날짜 
		String nowDate = ShopDateUtil.getNowDateToString("-");
		//한달전 날짜
		String aMonthAgoDate = ShopDateUtil.getMonthAgoDateToString(("-"));
		
		
		//-> 상품관리페이지 클릭시에도 날짜 기본값 설정하기
		//넘어오는 fromDate/toDate 데이터값이 없다면, 
		//한달전 날짜/오늘날짜로 세팅해준다. 
		if (paramMap.get("fromDate") == null) {
			paramMap.put("fromDate", aMonthAgoDate);
		}
		if (paramMap.get("toDate") == null) {
			paramMap.put("toDate", nowDate);
		}
		
		// 이제 한달전,오늘날짜를 아래 model객체로 parammap에 넣어 던지기때문에 
		// 굳이 다시 세팅하여 데이터 던질필요가 없으므로 삭제한다
		//model.addAttribute("nowDate",nowDate);
		//model.addAttribute("aMonthAgoDate",aMonthAgoDate);
		
		// html에서 파라매터를 사용하기위해 데이터던지기
		model.addAttribute("paramMap",paramMap);
		
		//side 메뉴에서 menu라는 이름으로 2라는 데이터를 던진다.
		//model.addAttribute("menu","2");
		
		return "content/admin/item_manage";
	}
	 
	//(ajax) 수량 변경
	@ResponseBody
	@PostMapping("/changeStock")
	public void changeStock(ItemVO itemVO) {
		itemService.updateStock(itemVO);
	}
	//(ajax) 상품판매여부 변경
	@ResponseBody
	@PostMapping("/changeItemStatus")
	public void changeItemStatus(ItemVO itemVO) {
		itemService.updateStautus(itemVO);
	}
	
	
	//메뉴관리 페이지
	@GetMapping("/setMenu")
	public String setMenu(Model model) {
		//model.addAttribute("menu","4");
		return "content/admin/set_menu";
	}
	
}














