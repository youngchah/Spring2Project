package kr.or.ddit.controller.crud.notice;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.crud.NoticeVO;

@Controller
@RequestMapping("/notice")
public class NoticeRetrieveController {
	
	@Inject
	private INoticeService noticeService;
	
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String noticeList() {
		return "notice/list";
	}
	
	@RequestMapping(value = "/detail.do", method = RequestMethod.GET)
	public String noticeDetail(int boNo, Model model) {
		NoticeVO noticeVO = noticeService.selectNotice(boNo);
		model.addAttribute("notice", noticeVO);
		return "notice/detail";
	}
}

