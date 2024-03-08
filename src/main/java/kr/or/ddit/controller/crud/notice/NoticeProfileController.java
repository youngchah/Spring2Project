package kr.or.ddit.controller.crud.notice;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.crud.NoticeMemberVO;

@Controller
@RequestMapping("/notice")
public class NoticeProfileController {
	
	@Inject
	private INoticeService noticeService;
	
	@RequestMapping(value = "/profile.do", method = RequestMethod.GET)
	public String noticeProfile(
			HttpSession session, RedirectAttributes ra, Model model
			) {
		String goPage = "";
		
		NoticeMemberVO sessionMember = (NoticeMemberVO) session.getAttribute("SessionInfo");
		
		if(sessionMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용 가능합니다!");
			return "redirect:/notice/login.do";
		}
		
		NoticeMemberVO member = noticeService.selectMember(sessionMember.getMemId());
		
		if(member != null) {
			model.addAttribute("member", member);
			goPage = "notice/profile";
		}else {
			ra.addFlashAttribute("message", "로그인 후 이용 가능합니다!");
			goPage = "redirect:/notice/login.do";
		}
		
		return goPage;
	}
	
	@PostMapping("/profileUpdate.do")
	public String noticeProfileUpdate(
			HttpServletRequest req,
			NoticeMemberVO memberVO, RedirectAttributes ra, Model model
			) {
		String goPage = "";
		ServiceResult result = noticeService.profileUpdate(req, memberVO);
		if(result.equals(ServiceResult.OK)) {
			ra.addFlashAttribute("message","회원정보 수정이 완료되었습니다!");
			goPage = "redirect:/notice/profile.do";
		}else {
			model.addAttribute("member", memberVO);
			model.addAttribute("message", "서버에러, 다시 시도해주세요!");
			goPage = "notice/profile";
		}
		return goPage;
	}
}




