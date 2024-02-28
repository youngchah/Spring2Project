package kr.or.ddit.controller.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/test")
public class TestController {
	
	private List<String> imageList;
	
	@PostConstruct
	public void init() {
		String[] imageFileName = {
			"audi01.png",
			"audi02.png",
			"audi03.png",
			"audi04.png",
			"bmw.png",
			"bmw01.jpg",
			"bmw02.jpg",
			"bmw03.jpg",
			"bmw04.jpg",
			"bmw05.jpg",
			"gif01.gif",
			"gif02.gif",
			"gif03.gif",
			"gif04.gif",
			"jeep01.jpg",
			"jeep02.jpg",
			"jeep03.jpg",
			"jeep04.jpg",
			"jeep05.jpg",
			"jeep06.jpg"
		};
		imageList = new ArrayList<String>();
		for(int i = 0; i < imageFileName.length; i++) {
			imageList.add(imageFileName[i]);
		}
	}
	
	@RequestMapping(value="/test01.do")
	public String test(Model model) {
		model.addAttribute("imageFileList", imageList);
		return "script/test01";
	}
	
	@RequestMapping(value="/changeImage.do", method=RequestMethod.POST)
	public ResponseEntity<List<String>> imageChange(
			@RequestBody Map<String, String> map
			){
		// # 비동기 통신 시, 단일 데이터를 받는 방법
		// 1) 클라이언트에서 data : selectedValue로 설정하고 컨트롤러에서는 @RequestBody String type 이와 같이 받는다.
		// 이때, 컨트롤러에서는 받는 파라미터명은 자유롭게 설정 가능 
		// 2) 클라이언트에서 data : JSON.stringify(data) 로 설정하고 컨트롤러에서는 @RequestBody Map<String, String> map 과 같이 받는다.
		List<String> typeImageList = new ArrayList<String>();
		
		if(map.get("type").toString().equals("all")) {
			typeImageList = imageList;
		}else {
			for(int i = 0; i < imageList.size(); i++) {
				if(imageList.get(i).contains(map.get("type"))) {
					String image = imageList.get(i);
					typeImageList.add(image);
				}

			}

		}

		return new ResponseEntity<List<String>>(typeImageList, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value="/thumbnail.do", method = RequestMethod.GET)
	public ResponseEntity<byte[]> thumbnail(
			HttpServletRequest req, String fileName
			){
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		HttpHeaders headers = new HttpHeaders();
		String savePath = req.getServletContext().getRealPath("/resources/image/");
		try {
			in = new FileInputStream(savePath + "/" + fileName);
			headers.setContentType(MediaType.IMAGE_JPEG);
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			try {
				in.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/download.do", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(
			HttpServletRequest req, String fileName
			){
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		HttpHeaders headers = new HttpHeaders();
		
		String savePath =  req.getServletContext().getRealPath("/resources/image/");
		try {
			in = new FileInputStream(savePath + "/" + fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.add("Content-Disposition", "attachment; filename=\"" +
					new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
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
	
}
