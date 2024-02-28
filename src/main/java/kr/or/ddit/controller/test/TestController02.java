package kr.or.ddit.controller.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.vo.AddressBook;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController02 {
	//DB대용 리스트 생성
	public static ArrayList<AddressBook> addressBookList;
	public static ArrayList<AddressBook> bookmarkList;
	
	// TestController02  객체 생성 -> 본인 생성자 실행 -> 
	// @PostConstruct 어노테이션 조회 -> 찾으면 가장 먼저 실행 
	
	//리스트 초기화 메서드	//가장먼저 실행되도록 어노테이션 붙여줌
	@PostConstruct		
	public void init() {
		addressBookList = new ArrayList<>();
		bookmarkList = new ArrayList<>();
		createAddressBook();
		
	}
	
	public void createAddressBook() {
		AddressBook ab1 = new AddressBook();
		AddressBook ab2 = new AddressBook();
		AddressBook ab3 = new AddressBook();
		
		ab1.setName("최윤서");
		ab1.setGender("남자");
		ab1.setNickName("공부하고싶다");
		ab1.setProfileImg("1.png");
		ab1.setEmail("abstj9409@naver.com");
		ab1.setJob("개발자꿈나무");
		ab1.setPhone1("010");
		ab1.setPhone2("1234");
		ab1.setPhone3("5678");
		
		ab2.setName("홍진영");
		ab2.setGender("남자");
		ab2.setNickName("모범생");
		ab2.setProfileImg("2.png");
		ab2.setEmail("wlsdud1234@naver.com");
		ab2.setJob("학생");
		ab2.setPhone1("010");
		ab2.setPhone2("2345");
		ab2.setPhone3("6789");
		
		ab3.setName("임민혁");
		ab3.setGender("남자");
		ab3.setNickName("몸짱");
		ab3.setProfileImg("3.png");
		ab3.setEmail("alsgur1122@naver.com");
		ab3.setJob("헬스트레이너");
		ab3.setPhone1("010");
		ab3.setPhone2("9999");
		ab3.setPhone3("8888");
		
		addressBookList.add(ab1);
		addressBookList.add(ab2);
		addressBookList.add(ab3);
		
	}
	
	@RequestMapping(value="/addressBook.do")
	public String addressBook(Model model) {
		
		//model.addAttribute("addressBookList", addressBookList);
		return "script/addressBook";
	}
	
	//비동기
	@RequestMapping(value = "/getList.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> getList() {
		String jsonData = getListForJsonData(addressBookList);
		log.info(jsonData);
		
		return new ResponseEntity<String>(jsonData, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getBookMarkList.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> getBookMarkList(){
		String jsonData = getListForJsonData(bookmarkList);
		log.info("jsonData >> " + jsonData);
		return new ResponseEntity<String>(jsonData,HttpStatus.OK);
	}
	

	@RequestMapping(value="/addAddress.do", method = RequestMethod.GET)
	public String addAddress(){
		return "script/addAddress";
	}
	
	@RequestMapping(value="/addAddress.do", method = RequestMethod.POST)
	public ResponseEntity<String> addAddressPost(@RequestBody AddressBook addressBook){
		
		String gender = "남자";
		if(addressBook.getGender().equals("female"))
			gender = "여자";
		
		addressBook.setGender(gender);
		addressBookList.add(addressBook);
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/removeAddress.do", method = RequestMethod.POST)
	public ResponseEntity<String> removeAddress(@RequestBody String id){
		
		
		ObjectMapper objMapper = new ObjectMapper();
		
		try {
			JsonNode node = objMapper.readTree(id);
			// HTML NODE ==> TREE 형식으로 읽어옴 
			// XML <html><head></head><body><div></div></body>
			
			int no = node.get("id").asInt();
			
			Iterator<AddressBook> it = addressBookList.iterator();
			
			while(it.hasNext()) {
				AddressBook item = it.next();
				if(item.getNo() == no) {
					bookmarkList.add(item);
					it.remove();
					break;
				}
			}
			
			log.info("{}", addressBookList);
			log.info("{}", bookmarkList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/removeBookMark.do", method = RequestMethod.POST)
	public ResponseEntity<String> removeBookMark(@RequestBody Map<String, String> map) {
		
		int no = Integer.parseInt(map.get("id"));
		
		ArrayList<AddressBook> copyList = new ArrayList<>(bookmarkList);
		copyList.forEach(item -> {
			if(item.getNo() == no) {
				addressBookList.add(item);
				bookmarkList.remove(item);
			}
		});
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	//jsonData바인딩 라이브러리 활용
	private String getListForJsonData(List list) {
		
		String jsonData = "";
		// 반환해줄 JSON DATA
		
		ObjectMapper objMapper = new ObjectMapper();
		// JACKSON DATABIND 라이브러리 안에 들어있는 클래스
		try {
			jsonData = objMapper.writeValueAsString(list);
			
			// List 데이터를 JSON 스트링 형태로 변환 시킨다
			// {"no":1, "name":"이명문"...}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return jsonData;
	}
	
}









