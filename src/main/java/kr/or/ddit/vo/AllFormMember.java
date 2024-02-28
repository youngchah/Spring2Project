package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AllFormMember {
	
	private String userId;		//아이디
	private String password;		//패스워드
	private String userName;	//이름
	private String email;	//이메일
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;		//생년월일
	private String gender;	//성별
	private String developer;		//개발자여부
	private boolean foreigner;	//외국인여부
	private String nationality;	//국적
	private String[] cars;		//소유차량
	private String hobby;		//취미
	
	private Address address;	//우편번호, 주소
	private List<Card> cardList;	//카드번호, 유효년월
	private String introduction; //소개
	private String personalInfo; //개인정보
	
	
	/*
	 * 유저ID : input text
		패스워드 : input password
		이름 : input text
		Email : input text
		생년월일 : input text
		성별 : radio
		개발자 여부 : checkbox
		외국인 여부: checkbox
		국적 : select
		소유차량 : select
		취미 : checkbox
		우편번호 : text
		주소 : text
		카드(번호/유효년월) text
		소개 : textarea
		개인정보 동의: checkbox
	 */
}
