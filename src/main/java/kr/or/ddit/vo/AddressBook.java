package kr.or.ddit.vo;

import lombok.Data;

@Data
public class AddressBook {
	private static int sequence = 1;	//데이터베이스 사용안하고 sequence 증가를 줘야함 
	private int no;
	private String name;
	private String gender;
	private String nickName;
	private String profileImg = "0.png";
	private String email;
	private String job;
	private String phone1;
	private String phone2;
	private String phone3;
	
	public AddressBook() {
		this.no = sequence++;
	}
	
	// 객체 생성 -> 객체 생성자가 실행된다 -> no = 시퀀스값을 대입한다
	// 시퀀스값은 1 증가한다 
}
