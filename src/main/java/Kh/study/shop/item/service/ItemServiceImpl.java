package Kh.study.shop.item.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Kh.study.shop.item.vo.ImgVO;
import Kh.study.shop.item.vo.ItemVO;

@Service("itemService")
public class ItemServiceImpl implements ItemService {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override//모든상품목록조회
	public List<ItemVO> selectItemList() {
		return sqlSession.selectList("itemMapper.selectItemList");
	}
	@Override//등록된상품만 목록조회
	public List<ItemVO> selectRegItemList(Map<String, String> paramMap) {
		return sqlSession.selectList("itemMapper.selectRegItemList",paramMap);
	}
	@Override//성품수량변경
	public void updateStock(ItemVO itemVO) {
		sqlSession.update("itemMapper.updateStock",itemVO);
	}
	@Override//상품상태변경
	public void updateStautus(ItemVO itemVO) {
		sqlSession.update("itemMapper.updateStautus",itemVO);
		
	}
	@Override //상세조회
	public ItemVO selectDetailItem(String itemCode) {
		return sqlSession.selectOne("itemMapper.selectDetailItem",itemCode);
	}
	
//------ 첨부된 이미지 상품 등록 ----------------------------------------------//	
	
	
	/* 내가한것....
	 * //이미지정보 등록 // 첨부파일은 위에서 만들어진 file을 가지고 와야한다!!!
	 * 
	 * @Override public void insertImage(ImgVO imgVO) {
	 * sqlSession.update("itemMapper.insertImage",imgVO); }
	 * 
	 * // 이미지 + 상품등록
	 * 
	 * @Override public void insertItem(ItemVO itemVO, ImgVO imgVO) { try {
	 * sqlSession.update("itemMapper.insertItem",itemVO);
	 * sqlSession.insert("itemMapper.insertImage",imgVO);//상품이미지등록 // 커밋따로 안해도 됨!
	 * sqlSession.commit(); } catch (Exception e) { sqlSession.rollback(); }
	 * 
	 * }
	 */
	

	
	/* 상세보기 조회
	 * @Override public ItemVO selectDetailItem(String itemCode) { return
	 * sqlSession.selectOne("itemMapper.selectDetailItem",itemCode); }
	 */
}
