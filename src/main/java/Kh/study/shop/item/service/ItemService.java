package Kh.study.shop.item.service;

import java.util.List;
import java.util.Map;

import Kh.study.shop.item.vo.ImgVO;
import Kh.study.shop.item.vo.ItemVO;

public interface ItemService {
	List<ItemVO> selectItemList();
	
	List<ItemVO> selectRegItemList(Map<String, String> paramMap);
	
	void updateStock(ItemVO itemVO);
	void updateStautus(ItemVO itemVO);
	//item상세조회 : 결과적으로는 list이지만 실제로는 vo하나다
	ItemVO selectDetailItem(String itemCode);
	
	//상품 등록 + 상품이미지 등록
	//void insertItem(ItemVO itemVO,ImgVO imgVO);
	//이미지 정보 등록
	//void insertImage(ImgVO imgVO);
	
	/* ItemVO selectDetailItem(String itemCode); */
}
