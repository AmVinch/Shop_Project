package Kh.study.shop.admin.service;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Kh.study.shop.item.vo.CategoryVO;
import Kh.study.shop.item.vo.ItemVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	//----------------------------------------//
	@Autowired//어노테이션으로 객체생성
	private SqlSessionTemplate sqlSession;
	//----------------------------------------//

	//목록조회 all
	@Override
	public List<CategoryVO> cateListAll( ) {
		return 	sqlSession.selectList("adminMapper.cateListAll");

	}
	//목록조회 use : 사용여부가 USE 인 것만 목록조회하여 상품등록하기위하여
	@Override
	public List<CategoryVO> cateListUse() {
		return 	sqlSession.selectList("adminMapper.cateListUse");

	}
	//카테고리 등록
	@Override
	public void regCate(CategoryVO categoryVO) {
		sqlSession.insert("adminMapper.regCate",categoryVO);
	}
	//카테 상태 변경
	@Override
	public void changeStatus(CategoryVO categoryVO) {
		sqlSession.update("adminMapper.changeStatus",categoryVO);
	}
//-----------------------------------------------------------------------------------------------------//	
	// 상품등록( + 이미지 등록)
	@Transactional(rollbackFor = Exception.class)//트랜잭션처리 어노테이션/ 해석) 모든예외가 나면 무조건 롤백처리한다. 
	@Override
	public void regItem(ItemVO itemVO) {
		sqlSession.insert("adminMapper.regItem",itemVO);
		sqlSession.insert("adminMapper.insertImage",itemVO);
	}
	//다음 아이템코드 조회
	@Override
	public String getNextItemCode() {
		return sqlSession.selectOne("adminMapper.getNextItemCode");
	}
	
	
}
