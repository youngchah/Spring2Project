package kr.or.ddit.controller.crud.notice;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

// AbstractView 클래스를 상속받아 renderMergedOutputModel 메소드를 재정의하여 사용하면
// 해당 클래스가 View의 역할을 하는 페이지의 형태가 된다.
public class NoticeDownloadView extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> noticeFileMap = (Map<String, Object>) model.get("noticeFileMap");
	
		File saveFile = new File(noticeFileMap.get("fileSavepath").toString());
		String fileName = (String) noticeFileMap.get("fileName");
		
		// User-Agent : 내가 사용하는 브라우저, 기기, OS 등등의 정보를 가지고 있다.
		// 요청 Heager 정보들 중, User-Agent 영역 안에 여러 키워드 정보들을 가지고
		// 특정 키워드가 포함되어 있는지를 체크해서 파일명의 출력 인코딩 부분을 설정합니다.
		// 사용 브라우저 또는 현상에 따라 발생하는 알고리즘 이므로, 내가 사용하게 되는
		// 브라우저의 버전이나 얻어온 header 정보들의 값에 따라 차이가 발생할 수 있습니다.
		// 그래서 조건절로 사용하는 브라우저 등등의 종류에 따라서 파일명의 출력 형태를 달리 만들어줍니다.
		
		String agent = request.getHeader("User-Agent");
		if(StringUtils.containsIgnoreCase(agent, "msie") ||
				StringUtils.containsIgnoreCase(agent, "trident")) {	// IE, Chrome
			fileName = URLEncoder.encode(fileName, "UTF-8");
		}else {	// filefox, chrome
			fileName = new String(fileName.getBytes(), "ISO-8859-1");
		}
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + 
				fileName + "\"");
		response.setHeader("Content-Length", noticeFileMap.get("fileSize").toString());
		
		// try-with-resource
		// ()안에 명시한 객체는 finally로 최종 열린 객체에 대한 close()를 처리하지 않아도
		// 자동으로 close()가 이루어진다.
		try (
				OutputStream os = response.getOutputStream();
		){
			FileUtils.copyFile(saveFile, os);
		}
		
		
	}

}








