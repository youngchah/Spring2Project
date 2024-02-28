package kr.or.ddit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.vo.Address;
import kr.or.ddit.vo.Card;
import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/ajax")
@Slf4j
public class AjaxMemberController {
	
	// ajax 방식 요청 처리 페이지
	@RequestMapping(value = "/registerForm", method = RequestMethod.GET)
	public String ajaxRegisterForm() {
		log.info("ajaxRegisterForm() 실행...!");
		return "member/ajaxRegisterForm";
	}
	
	// RequestBody를 사용하는 이유: 요청 본문을 받아보기 위해 
	// 3) 객체 타입의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 자바빈즈 매개변수로 처리한다.
	@RequestMapping(value = "/register03", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister03(@RequestBody Member member){
		log.info("ajaxRegister03() 실행...!");
		log.info("member.getUserId() : " + member.getUserId());
		log.info("member.getPassword() : " + member.getPassword());
		
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	
	// 쿼리스트링으로 들어와서
	// userId : a001
	// password : null 의 결과가 나옴 
	// @RequestBody Map<String, String> map 으로 꺼내야 ...  단일데이터면 map으로 받아도 괜찮지만
	// 그 이상의 여러개의 데이터라면 MemberVO와 같은 자바빈즈 클래스 객체타입으로 받는게 낫다 
	
	// 5) 요청 URL에 쿼리 파라미터를 붙여서 전달하면 문자열 매개변수로 처리한다.
	@RequestMapping(value = "/register05", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister05(String userId,@RequestBody Map<String, String> map){
		// userId는 쿼리스트링에 담겨져 오는 데이터이기 때문에, 일반적인 방식으로도 데이터를 받을 수 있지만,
		// password는 요청 본문에서 데이터를 바인딩해 받아오지 못하므로 null이 출력된다.
		// 비동기 통신이면서 요청본문에 설정되어 넘어오는 단일 파라미터같은 경우에는 @RequestBody 어노테이션을 설정하고
		// 파라미터를 받기 위한 데이터 타입을 Map<String, String>과 같은 형태의 컬렉션으로 채택하여 사용한다.
		// 2개이상의 많은 데이터가 요청본문 내 설정되어 서버로 넘어오는 경우라면 Map 보다는 자바빈즈 클래스 객체 타입으로
		// 설정하는걸 추천한다! (까먹지말기!!)
		log.info("ajaxRegister05() 실행...!");
		log.info("userId : "  + userId);
		log.info("password : " + map.get("password"));
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
	// 7) 객체 배열 타입의 JSON 요청 데이터를 자바빈즈 요소를 가진 리스트 컬렉션 매개변수에 @RequestBody를 지정하여 처리한다.
	@RequestMapping(value = "/register07", method = RequestMethod.POST)
	public ResponseEntity<String> register07(
			@RequestBody List<Member> memberList){
		// 동기방식과 비동기방식을 처리하는데에 있어서 List타입을 운용하는 걸 꼭 기억합시다!
		// 비동기 처리시, List 컬렉션으로 데이터를 받을때에는 @RequestBody 라는 어노테이션을 이용하여 바인딩할 수 있다.
		// 동기 처리시에는 컨트롤러 메서드 내에서  List 타입으로 값을 바인딩 할  수 없지만, 객체 내 존재하는 List타입으로는
		// 데이터를 바인딩할 수 있습니다.
		// 동기와 비동기 처리시의 차이점을 꼭 기억합시다!!!
		
		log.info("register07() 실행...!");
		
		for (Member member : memberList) {
			log.info("member.getUserId() : " + member.getUserId());
			log.info("member.getPassword() : " + member.getPassword());
		}
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	// 8) 중첩된 객체 타입의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 중첩된 자바빈즈 매개변수로 처리한다.
	
	@RequestMapping(value = "/register08", method = RequestMethod.POST)
	public ResponseEntity<String> register08(
			@RequestBody Member member){
		log.info("register08() 실행...!");
		log.info("member.getUserId() : " + member.getUserId());
		log.info("member.getPassword() : " + member.getPassword());
		
		Address address = member.getAddress();
		if(address != null) {
			log.info("address.getPostCode() : " + address.getPostCode());
			log.info("address.getLocation() : " + address.getLocation());
		}else {
			log.info("address is null");
		}
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	
	// 9) 중첩된 객체 타입(컬렉션 List)의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 중첩된 자바빈즈 매개변수로 처리한다.
	
	@RequestMapping(value = "/register09", method = RequestMethod.POST)
	public ResponseEntity<String> register09(
			@RequestBody Member member){
		log.info("register09() 실행...!");
		log.info("member.getUserId() : " + member.getUserId());
		log.info("member.getPassword() : " + member.getPassword());
		
		List<Card> cardList = member.getCardList();
		
		if(cardList != null) {
			log.info("cardList.size() : " + cardList.size());
		
			for (Card card : cardList) {
				log.info("card.getNo() : " + card.getNo());
				log.info("card.getValidMonth() : " + card.getValidMonth());
			}
		}else {
			log.info("cardList is null");
		}
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		
	}
}















