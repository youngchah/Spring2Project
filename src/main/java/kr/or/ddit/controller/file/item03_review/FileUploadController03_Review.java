package kr.or.ddit.controller.file.item03_review;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.controller.file.item03.MediaUtils;
import kr.or.ddit.service.IItemService3;
import kr.or.ddit.vo.Item3;

@Controller
@RequestMapping("/review")
public class FileUploadController03_Review {
	
	@Resource(name = "uploadPath")
	private String resourcePath;
	
	@Inject
	private IItemService3 itemService;
	
	@GetMapping("/register")
	public String item3RegisterForm() {
		return "item3_review/register";
	}
	
	@PostMapping(value = "/uploadAjax", produces = "text/plain; charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		
		String savedName = UploadFileUtils_Review.uploadFile(resourcePath, 
				file.getOriginalFilename(), file.getBytes());
		
		return new ResponseEntity<String>(savedName,HttpStatus.OK);
	}
	
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName){
		ResponseEntity<byte[]> entity = null;
		
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		MediaType mType = MediaUtils_Review.getMediaType(formatName);
		HttpHeaders headers = new HttpHeaders();
		
		try {
			File file = new File(resourcePath + File.separator + fileName);
			// 파일에 대한 정보를 설정한다
			byte[] data = FileUtils.readFileToByteArray(file);
			// 파일의 실제 데이터를 바이너리 데이터로 뽑아낸다
			if(mType != null) {
				headers.setContentType(mType);
			}else {
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add(headers.CONTENT_DISPOSITION, "attachment; filename=" + 
						new String(fileName.getBytes(StandardCharsets.UTF_8)
								, StandardCharsets.ISO_8859_1));
				// Content-Disposition
				// StandardCharsets.UTF-8 = UTF-8
			}
			
			entity = new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@PostMapping("/register")
	public String item3Register(Item3 item, Model model) {
		 String[] files = item.getFiles();
		 
		 itemService.register(item);
		 model.addAttribute("msg", "등록이 완료되었습니다");
		 
		 return "item3_review/success";
	}
	
	@GetMapping("/list")
	public String item3List(Model model) {
		List<Item3> itemList = itemService.list();
		
		model.addAttribute("itemList", itemList);
		
		return "item3_review/list";
	}
	
	@GetMapping("/modify")
	public String item3ModifyForm(int itemId, Model model) {
		Item3 item = itemService.read(itemId);
		model.addAttribute("item", item);
		
		return "item3_review/modify";
		
	}
	
	@PostMapping("/modify")
	public String item3Modify(Item3 item, Model model) {
		
		itemService.modify(item);
		
		model.addAttribute("msg", "수정이 완료되었습니다!!!!!");
		
		return "item3_review/success";
	}
	
	// 	@ResponseBody 와 ResponseEntity의 차이점
	// 	@ResponseBody = 값을 본문 내용으로 응답 (클라이언트로)
	// ResponseEntity = 헤더와 응답 상태코드 그리고 값을 본문 내용으로 전달해줌(클라이언트로) 
	@ResponseBody
	@GetMapping("/getAttach/{itemId}")	// 경로안에 값을 넣어주기위해 pathVariable
	public List<String> getAttach(@PathVariable("itemId") int itemId){
		return itemService.getAttach(itemId);
	}
	
}










