package kr.or.ddit.controller.crud.notice;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.crud.NoticeFileVO;

@Controller
public class NoticeDownloadController {
	
	@Inject
	private INoticeService noticeService;
	
	@GetMapping("/notice/download.do")
	public View noticeDownload(
			int fileNo, ModelMap model
			) {
		// 선택한 파일을 다운로드 하기 위한 정보를 파일번호에 해당하는 파일 정보를 얻어온다.
		NoticeFileVO noticeFileVO = noticeService.noticeDownload(fileNo);
		
		Map<String, Object> noticeFileMap = new HashMap<String, Object>();
		// 파일명 추가
		noticeFileMap.put("fileName", noticeFileVO.getFileName());
		// 파일크기 추가
		noticeFileMap.put("fileSize", noticeFileVO.getFileSize());
		// 파일 저장 경로 추가
		noticeFileMap.put("fileSavepath", noticeFileVO.getFileSavepath());
		// 데이터 전달자로 전달
		model.addAttribute("noticeFileMap", noticeFileMap);
		
		// 리턴되는 NoticeDownloadView는 jsp페이지로 존재하는 페이지 Name을 요청하는게 아니라,
		// 클래스를 요청하는 것인데 해당 클래스가 스프링에서 AbstractView 클래스를 상속받는 클래스입니다
		// 그 클래스는 AbstractView를 상속받아 renderMergedOutputModel 함수를 재정의할 때 View로
		// 취급될 수 있게 해줍니다.
		return new NoticeDownloadView();
	
	}
}



