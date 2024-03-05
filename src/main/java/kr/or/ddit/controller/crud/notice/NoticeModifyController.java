package kr.or.ddit.controller.crud.notice;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.crud.NoticeVO;

@Controller
@RequestMapping("/notice")
public class NoticeModifyController {
	
	@Inject
	private INoticeService noticeService;
	
	@RequestMapping(value = "/update.do", method = RequestMethod.GET)
	public String noticeUpdateForm(int boNo, Model model) {
		NoticeVO noticeVO = noticeService.selectNotice(boNo);
		model.addAttribute("notice", noticeVO);
		model.addAttribute("status", "u");
		return "notice/form";
	}
	
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public String noticeUpdate(
			RedirectAttributes ra,
			NoticeVO noticeVO, Model model) {
		String goPage = "";
		ServiceResult result = noticeService.updateNotice(noticeVO);
		if(result.equals(ServiceResult.OK)) {	//수정 성공
			ra.addFlashAttribute("message", "게시글 수정이 완료되었습니다!");
			goPage = "redirect:/notice/detail.do?boNo="+noticeVO.getBoNo();
			
		}else {	//수정 실패
			model.addAttribute("notice", noticeVO);
			model.addAttribute("message", "서버에러, 다시 시도해주세요!");
			model.addAttribute("status", "u");
			goPage = "notice/form";
		}
		return goPage;
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public String noticeDelete(
			RedirectAttributes ra,
			int boNo, Model model) {
		String goPage = "";
		
		ServiceResult result = noticeService.deleteNotice(boNo);
		if(result.equals(ServiceResult.OK)) {	// 삭제 성공
			ra.addFlashAttribute("message", "게시글 삭제가 완료되었습니다!");
			goPage = "redirect:/notice/list.do";
		}else {
			ra.addFlashAttribute("message", "서버오류, 다시 시도해주세요!");
			goPage = "redirect:/notice/detail.do?boNo=" + boNo;
		}
		return goPage;
	}
}
















