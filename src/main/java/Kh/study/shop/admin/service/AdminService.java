package Kh.study.shop.admin.service;

import java.util.List;

import Kh.study.shop.item.vo.CategoryVO;
import Kh.study.shop.item.vo.ImgVO;
import Kh.study.shop.item.vo.ItemVO;

public interface AdminService {
	//카테고리 목록조회
	List<CategoryVO> cateListAll( );
	List<CategoryVO> cateListUse( );
	
	//카테고리 등록
	void regCate(CategoryVO categoryVO);
	
	//카테 상태 변경
	void changeStatus(CategoryVO categoryVO);
	// 다음 아이템코드
	String getNextItemCode();

	
	// 상품등록(+ 이미정보 같이 등록)
	// 따로 이미지등록은 서비스에는 만들지 않는다. 바로 서비스임플로 이미만들어진 상품등록메소드로 간다.
	void regItem(ItemVO itemVO );
	
}
