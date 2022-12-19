package Kh.study.shop.member.vo;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class MemberVO {
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberAddr;
	private String addrDetail;
	private String memberEmail;
	private String memberRole;
	private String memberStatus;
}
