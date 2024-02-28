package kr.or.ddit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RestHomeController {
	
	//반환값이 객체 타입이면 json 타입으로 자동 변환된다.
	@RequestMapping(value="/goRestHome0301", method = RequestMethod.GET)
	public Member restHome0301() {
		log.info("restHome0301() 실행...!");
		return new Member();
	}
	
	@RequestMapping(value="/goRestHome0401", method = RequestMethod.GET)
	public List<Member> restHome0401(){
		log.info("restHome0401() 실행...!");
		
		List<Member> list = new ArrayList<Member>();
		Member member = new Member();
		list.add(member);
		Member member2 = new Member();
		list.add(member2);
		
		return list;
	}
	
	@RequestMapping(value="/goRestHome0501", method=RequestMethod.GET)
	public Map<String, Member> restHome0501(){
		log.info("restHome0501() 실행...!");
		Map<String, Member> map = new HashMap<String, Member>();
		Member member = new Member();
		Member member2 = new Member();
		
		map.put("key1", member);
		map.put("key2", member2);
		return map;
	}
	
	
	@RequestMapping(value="/goRestHome0601", method=RequestMethod.GET)
	public ResponseEntity<Void> restHome0601(){
		log.info("restHome0601() 실행...!");
		return new ResponseEntity<Void>(HttpStatus.OK);
		//내가 요청한 url로 응답이 나가면서 응답데이터로 아무런 값이 전달되지 않는다.
		//해당 URL 요청 후, 브라우저에서 개발자 도구를 이용해서 네트워크 탭을 확인해보면
		//응답으로 URL이 응답으로 나간걸 확인할 수 있는데, 
		//이때 상태코드 200으로 정상 응답이 나간걸 확인할 수 있다.
		//그리고, 다른 기능으로 아무런 형태 없이 응답으로 나가지만
		//응답에 대한 header 정보를 변경하고자 할 때 사용할 수 있다.
	}
	
	
	@RequestMapping(value="/goRestHome0701", method=RequestMethod.GET)
	public ResponseEntity<String> restHome0701(){
		log.info("restHome0701() 실행...!");
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/goRestHome0801", method=RequestMethod.GET)
	public ResponseEntity<Member> restHome0801(){
		log.info("restHome0801() 실행...!");
		Member member = new Member();
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/goRestHome0901", method=RequestMethod.GET)
	public ResponseEntity<List<Member>> restHome0901(){
		log.info("restHome0901() 실행...!");
		List<Member> list = new ArrayList<Member>();
		Member member = new Member();
		Member member2 = new Member();
		list.add(member);
		list.add(member2);
		
		return new ResponseEntity<List<Member>>(list, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/goRestHome1001", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Member>> restHome1001(){
		log.info("restHome1001() 실행...!");
		Map<String, Member> map = new HashMap<String, Member>();
		Member member = new Member();
		Member member2 = new Member();
		map.put("key1", member);
		map.put("key2", member2);
		return new ResponseEntity<Map<String,Member>>(map, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/goRestHome1101", method=RequestMethod.GET)
	public ResponseEntity<byte[]> restHome1101(){
		log.info("restHome1101() 실행...!");
		//이미지 파일을 읽어오기 위한 stream
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		//인스턴스화 해놓고
		HttpHeaders headers = new HttpHeaders();
		
		try {
			in = new FileInputStream("D:/cat.jpeg");
			headers.setContentType(MediaType.IMAGE_JPEG);
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			try {
				in.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
	
	
	@RequestMapping(value="/goRestHome1102", method=RequestMethod.GET)
	public ResponseEntity<byte[]> restHome1102(){
		log.info("restHome1102() 실행...!");
		
		String fileName = "ddit_download_file.zip";
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		HttpHeaders headers = new HttpHeaders();
		try {
			in = new FileInputStream("D:/cat.jpeg");
			//MediaType.APPLICATION_OCTET_STREAM은 이진 파일을 위한 기본값입니다.
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.add("Content-Disposition", "attachment; filename=\""+
					new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\"");
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers,
					HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
	
	
}
