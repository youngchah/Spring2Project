package kr.or.ddit.controller.file.item01;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.service.IItemService;
import kr.or.ddit.vo.Item;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/item")
@Slf4j
public class FileUploadController01 {
	/*
	 * 13장 파일 업로드
	 * 
	 * 1. 파일업로드 설명
	 * - 서블릿 3.0에서 지원하는 파일 업로드 기능과 스프링 웹에서 제공하는 컴포넌트를 사용하여 파일을 업로드한다.
	 * 
	 * 		# 파일 업로드 설정
	 * 		1) 서블릿 3.0이상 지원
	 * 		2) 서블릿 표준 파일 업로드 기능을 활성화
	 * 		3) 스프링 MVC와 연계
	 * 		4) 업로드 된 파일 저장 위치 설정
	 * 
	 * 		# 환경설정
	 * 
	 * 			1) 의존 관계 정의
	 * 			- 파일을 처리하기 위해서 의존 라이브러리를 추가한다.
	 * 				> pom.xml commons 종류의 io, fileupload 추가
	 * 
	 * 			2) 웹 컨테이너 설정
	 * 				> web.xml 서블릿 표준 버전 3.1로 설정
	 * 				> multipart-config 활성화 (web.xml의 servlet 태그 내 설정)
	 * 		
	 * 			3) 스프링 웹 설정
	 * 				> servlet-context.xml
	 *				> multipartResolver Bean 등록(id는 multipartResolver로 설정)
	 *				
	 *				[파일 업로드 설정]
	 *				파일 업로드를 설정하는 방식은 2가지가 있다.
	 * 				
	 * 				- StandardServletMultipartResolver	사용 시 설정,
	 * 					> Servlet 3.0의 part를 이용한 MultipartFile 데이터 처리
	 * 					> servlet-context.xml에 설정
	 * 						> StandardServletMultipartResolver Bean 등록
	 * 					> web.xml에 설정
	 * 						> multipart-config (servlet 태그 안에 설정)
	 * 
	 * 				- CommonsMultipartResolver 사용 시 설정, (우리가 사용할 방식)
	 * 					> Commons FileUpload API를 이용한 MultipartFile 데이터 처리
	 * 					> bean 속성으로 maxUploadSize, maxInMemorySize 및  defaultEncoding 설정을 제공
	 * 					> 기본값 및 허용되는 값에 대한 자세한 내용은 각 DiskFileUpload 속성을 참조
	 * 					> pom.xml 설정
	 * 						> commons-fileupload 추가
	 * 					> root-context.xml 에 설정
	 * 						> CommonsMultipartResolver Bean 등록
	 * 
	 * 				> root-context.xml 에 설정
	 * 					> uploadPath bean 등록
	 * 
	 * 				> multipartFilter 필터 등록
	 * 					> web.xml
	 * 
	 * 		# 데이터베이스 준비
	 * 		- item 테이블 생성 (item, item2, item3, item3_attach)
	 * 
	 * 2. 이미지 업로드
	 * - 한 개의 이미지를 업로드하는 기본 파일 업로드 기능을 구현합니다.
	 * 
	 * 		# 파일 업로드 구현 설명
	 * 
	 * 			- 파일 업로드 등록 화면 컨트롤러 만들기 (FileUploadController01)
	 * 			- 파일 업로드 등록 화면 컨트롤러 메소드 만들기 (itemRegisterForm:get)
	 * 			- 파일 업로드 등록 화면 만들기 (item/register.jsp)
	 * 			- 여기까지 확인
	 * 			
	 * 			- 파일업로드 등록 기능 컨트롤러 메소드 만들기 (itemRegister:post)
	 * 			- 파일업로드 등록 기능 서비스 인터페이스 메소드 만들기
	 * 			- 파일업로드 등록 기능 서비스 클래스 메소드 만들기
	 * 			- 파일업로드 등록 기능 Mapper 인터페이스 메소드 만들기
	 * 			- 파일업로드 등록 기능 Mapper xml 쿼리 만들기
	 * 			- 파일업로드 등록 완료 페이지 만들기
	 * 			- 여기까지 확인 
	 * 
	 * 			- 파일업로드 목록 화면 컨트롤러 메소드 만들기 (itemList:get)
	 * 			- 파일업로드 목록 화면 서비스 인터페이스 메소드 만들기
	 * 			- 파일업로드 목록 화면 서비스 클래스 메소드 만들기
	 * 			- 파일업로드 목록 화면 Mapper 인터페이스 메소드 만들기
	 * 			- 파일업로드 목록 화면 Mapper xml 쿼리 만들기
	 * 			- 파일업로드 목록 화면 만들기 (item/list.jsp)
	 * 			- 여기까지 확인
	 * 
	 * 			- 파일업로드 수정 화면 컨트롤러 메소드만들기(itemModifyForm:get)
	 * 			- 파일업로드 수정 화면 서비스 인터페이스 메소드 만들기
	 * 			- 파일업로드 수정 화면 서비스 클래스 메소드 만들기
	 * 			- 파일업로드 수정 화면 Mapper 인터페이스 메소드 만들기
	 * 			- 파일업로드 수정 화면 Mapper xml 쿼리 만들기
	 * 			- 파일업로드 수정 화면 만들기 (item/modify.jsp)
	 * 			- 여기까지 확인 
	 * 
	 * 			- 파일업로드 수정 기능 컨트롤러 메소드 만들기(itemModify:post)
	 * 			- 파일업로드 수정 기능 서비스 인터페이스 메소드 만들기
	 * 			- 파일업로드 수정 기능 서비스 클래스 메소드 만들기
	 * 			- 파일업로드 수정 기능 Mapper 인터페이스 메소드 만들기
	 * 			- 파일업로드 수정 기능 Mapper xml 쿼리 만들기
	 * 			- 여기까지 확인 
	 */
	
	// root-context.xml 에서 설정한 uploadPath 빈등록 path 경로를 사용한다.
	@Resource(name = "uploadPath")
	private String resourcePath;
	
	@Inject
	private IItemService itemService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String itemRegisterForm() {
		return "item/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String itemRegister(Item item, Model model) throws Exception {
		MultipartFile file = item.getPicture();
		
		log.info("originalFileName : " + file.getOriginalFilename());
		log.info("fileSize : " + file.getSize());
		log.info("contentType : " + file.getContentType());
		
		// 넘겨받은 파일을 이용하여 파일 업로드(복사)를 진행한다.
		// return : UUID + "_" + 원본파일명
		String createFileName = uploadFile(file.getOriginalFilename(), file.getBytes());
		
		item.setPictureUrl(createFileName);
		itemService.register(item);
		model.addAttribute("msg", "등록이 완료되었습니다!");
		return "item/success";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String itemList(Model model) {
		List<Item> itemList = itemService.list();
		model.addAttribute("itemList", itemList);
		return "item/list";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String itemModifyForm(int itemId, Model model) {
		Item item = itemService.read(itemId);
		model.addAttribute("item", item);
		return "item/modify";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String itemModify(Item item, Model model) throws Exception {
		MultipartFile file = item.getPicture();
		
		if(file != null && file.getSize() > 0) {
			log.info("originalFileName : " + file.getOriginalFilename());
			log.info("size : " + file.getSize());
			log.info("contentType : " + file.getContentType());
			
			String createFileName = uploadFile(file.getOriginalFilename(), file.getBytes());
			item.setPictureUrl(createFileName);
		}
		itemService.modify(item);
		model.addAttribute("msg", "수정이 완료되었습니다!");
		return "item/success";
		
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














