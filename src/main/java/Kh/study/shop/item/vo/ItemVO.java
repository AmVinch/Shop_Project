package Kh.study.shop.item.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ItemVO {
	private String	itemCode;
	private String	itemName;
	private int	itemPrice;
	private String	itemComment;
	private String	regDate;
	private int	itemStock;
	private String	cateCode;
	private String	itemStatus;
	
	private String	cateName;
	private String	cateStatus;
	
	//itemVO하나는 이미지imgVo를 여러개 갖을 수 있다.
	//이미지등록할때를 위해
	private List<ImgVO> imgList;
	// 아이템기준 카테고리 하나의 정보 (1:1,목록조회시 '한' 줄만)를 가질 수 있다.
	private CategoryVO categoryVO;
}


