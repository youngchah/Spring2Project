package kr.or.ddit.controller.file.item02;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.service.IItemService2;
import kr.or.ddit.vo.Item2;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.GetProxy;

@Controller
@RequestMapping("/item2")
@Slf4j
public class FileUploadController02 {
	/*
	 * 	13장 파일업로드
	 *
	 * 	3. 여러 개의 이미지 업로드
	 * 	- 한번에 여러 개의 이미지를 업로드하는 파일 업로드 기능을 구현한다.
	 *
	 * 		# 데이터베이스 만들기
	 * 		- FileUploadController01에서 데이터베이스 생성하고 넘어옴
	 *
	 * 		# 파일 업로드 구현 설명
	 * 			
	 * 			- 파일 업로드 등록 화면 컨트롤러 만들기 (FileUploadController02)
	 * 			- 파일 업로드 등록 화면 컨트롤러 메소드 만들기 (item2RegisterForm:get)
	 * 			- 파일 업로드 등록 화면 만들기 (item2/register.jsp)
	 * 			- 여기까지 확인
	 * 
	 * 			- 파일업로드 등록 기능 컨트롤러 메소드 만들기(item2Register:post)
	 * 			- 파일업로드 등록 기능 서비스 인터페이스 메소드 만들기
	 * 			- 파일업로드 등록 기능 서비스 클래스 메소드 만들기
	 * 			- 파일업로드 등록 기능 Mapper 인터페이스 메소드 만들기
	 * 			- 파일업로드 등록 기능 Mapper xml 쿼리 만들기
	 * 			- 여기까지 확인 
	 * 
	 * 			- 파일업로드 목록 화면 컨트롤러 메소드 만들기 (item2List:get)
	 * 			- 파일업로드 목록 화면 서비스 인터페이스 메소드 만들기
	 * 			- 파일업로드 목록 화면 서비스 클래스 메소드 만들기
	 * 			- 파일업로드 목록 화면 Mapper 인터페이스 메소드 만들기
	 * 			- 파일업로드 목록 화면 Mapper xml 쿼리 만들기
	 * 			- 파일업로드 목록 화면 만들기 (item2/list.jsp)
	 *  		- 여기까지 확인
	 *  
	 *  		- 파일업로드 수정 화면 컨트롤러 메소드 만들기 (item2ModifyForm:get)
	 *  		- 파일업로드 수정 화면 서비스 인터페이스 메소드 만들기
	 *  		- 파일업로드 수정 화면 서비스 클래스 메소드 만들기
	 *  		- 파일업로드 수정 화면 Mapper 인터페이스 메소드 만들기
	 *  		- 파일업로드 수정 화면 Mapper xml 쿼리 만들기
	 *  		- 파일업로드 수정 화면 만들기 (item2/modify.jsp)
	 *  		- 여기까지 확인 
	 *  		
	 *  		- 파일업로드 수정 기능 컨트롤러 메소드 만들기(item2Modify:post)
	 *  		- 파일업로드 수정 기능 서비스 인터페이스 메소드 만들기
	 *  		- 파일업로드 수정 기능 서비스 클래스 메소드 만들기
	 *  		- 파일업로드 수정 기능 Mapper 인터페이스 메소드 만들기
	 *  		- 파일업로드 수정 기능 Mapper xml 쿼리 만들기
	 *  		- 여기까지 확인
	 *  		
	 *  		- 파일업로드 삭제 화면 컨트롤러 메소드 만들기 (item2RemoveForm:get)
	 *  		- 파일업로드 삭제 화면 서비스 인터페이스 메소드 만들기
	 *  		- 파일업로드 삭제 화면 서비스 클래스 메소드 만들기
	 *  		- 파일업로드 삭제 화면 Mapper 인터페이스 메소드 만들기
	 *  		- 파일업로드 삭제 화면 Mapper xml 쿼리 만들기
	 *  		- 파일업로드 삭제 화면 만들기 (item2/remove.jsp)
	 *  
	 *  		- 파일업로드 삭제 기능 컨트롤러 메소드 만들기(item2Remove:post)
	 *  		- 파일업로드 삭제 기능 서비스 인터페이스 메소드 만들기
	 *  		- 파일업로드 삭제 기능 서비스 클래스 메소드 만들기
	 *  		- 파일업로드 삭제 기능 Mapper 인터페이스 메소드 만들기
	 *  		- 파일업로드 삭제 기능 Mapper xml 쿼리 만들기
	 *  		- 여기까지 확인
	 *  
	 *  
	 */
	
	// root-context.xml 에서 설정한 uploadPath 빈등록 path 경로를 사용한다.
	@Resource(name = "uploadPath")
	private String resourcePath;
	
	@Inject
	private IItemService2 itemService;
	
	
	// 이와 같은 어노테이션으로 맵핑도 가능 
	@GetMapping(value = "/register")
	public String item2RegisterForm() {
		return "item2/register";
		
	}
	
	@PostMapping(value = "/register")
	public String item2Register(Item2 item, Model model) throws Exception {
		List<MultipartFile> pictures = item.getPictures();
		
		for(int i = 0; i < pictures.size(); i++) {
			MultipartFile file = pictures.get(i);
			
			log.info("fileName : " + file.getOriginalFilename());
			log.info("size : " + file.getSize());
			log.info("contentType : " + file.getContentType());
			
			String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
			
			if(i == 0) {	// 첫번째 URL 정보
				item.setPictureUrl(savedName);
			}else if(i == 1) {	// 두번째 URL 정보
				item.setPictureUrl2(savedName);
			}
		}
		
		itemService.register(item);
		model.addAttribute("msg", "등록이 완료되었습니다!");
		return "item2/success";
	}
	
	@GetMapping(value = "/list")
	public String itemList(Model model) {
		List<Item2> itemList = itemService.list();
		model.addAttribute("itemList", itemList);
		return "item2/list";
	}
	
	@GetMapping(value = "/modify")
	public String item2ModifyForm(int itemId, Model model) {
		Item2 item = itemService.read(itemId);
		model.addAttribute("item", item);
		return "item2/modify";
	}
	@PostMapping(value = "/modify")
	public String item2Modify(Item2 item, Model model) throws Exception {
		List<MultipartFile> pictures = item.getPictures();
		
		for(int i = 0; i < pictures.size(); i++) {
			MultipartFile file = pictures.get(i);
			
			if(file !=null && file.getSize() > 0) {
				log.info("fileName : " + file.getOriginalFilename());
				log.info("size : " + file.getSize());
				log.info("contentType : " + file.getContentType());
				
				String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
			
				if(i == 0) {	// 첫번째 URL 정보
					item.setPictureUrl(savedName);
				}else if(i == 1) {	// 두번째 URL 정보
					item.setPictureUrl2(savedName);
				}
			}
			
		}
		
		itemService.modify(item);
		model.addAttribute("msg", "수정이 완료되었습니다!");
		return "item2/success";
	}
	
	@GetMapping(value = "/remove")
	public String item2RemoveForm(int itemId, Model model) {
		Item2 item = itemService.read(itemId);
		model.addAttribute("item", item);
		return "item2/remove";
	}
	
	@PostMapping(value = "/remove")
	public String item2Remove(int itemId, Model model) {
		itemService.remove(itemId);
		model.addAttribute("msg", "삭제가 완료되었습니다!");
		return "item2/success";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(int itemId){
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		// itemId에 해당하는 이미지 파일명을 얻어온다.
		String fileName = itemService.getPicture(itemId);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);	// 확장자 추출
		// 확장자를 던져서 확장자와 일치하는 MimeType(미디어타입)을 리턴한다.
		// springframework.http에 있는 MediaType
		MediaType mType = getMediaType(formatName);
		HttpHeaders headers = new HttpHeaders();
		
		try {
			in = new FileInputStream(resourcePath + File.separator + fileName);
			if(mType != null) {
				headers.setContentType(mType);
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers,HttpStatus.CREATED);
		
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/display2", method = RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile2(int itemId){
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		// itemId에 해당하는 이미지 파일명을 얻어온다.
		String fileName = itemService.getPicture2(itemId);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);	// 확장자 추출
		// 확장자를 던져서 확장자와 일치하는 MimeType(미디어타입)을 리턴한다.
		// springframework.http에 있는 MediaType
		MediaType mType = getMediaType(formatName);
		HttpHeaders headers = new HttpHeaders();
		
		try {
			in = new FileInputStream(resourcePath + File.separator + fileName);
			if(mType != null) {
				headers.setContentType(mType);
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers,HttpStatus.CREATED);
			
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
	
	
	private MediaType getMediaType(String formatName) {
		if(formatName != null) {
			if(formatName.toUpperCase().equals("JPG")) {
				return MediaType.IMAGE_JPEG;
			}
			if(formatName.toUpperCase().equals("PNG")) {
				return MediaType.IMAGE_PNG;
			}
			if(formatName.toUpperCase().equals("GIF")) {
				return MediaType.IMAGE_GIF;
			}
		}
		return null;
	}
	
	
	// 파일 업로드 함수
	private String uploadFile(String originalFilename, byte[] fileData) throws Exception {
		UUID uuid = UUID.randomUUID();
		// UUID + "_" + 원본파일명
		String createdFileName = uuid.toString() + "_" + originalFilename;
		
		File file = new File(resourcePath); 
		if(!file.exists()) {
			file.mkdirs();
		}
		
		// 파일 업로드 준비 
		File target = new File(resourcePath, createdFileName);
		FileCopyUtils.copy(fileData, target);	// 파일 복사 진행
		return createdFileName;
	}
	
}
























