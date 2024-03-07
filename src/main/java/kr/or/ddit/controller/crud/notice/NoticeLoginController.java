package kr.or.ddit.controller.crud.notice;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.crud.NoticeMemberVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/notice")
@Slf4j
public class NoticeLoginController {
	
	@Inject
	private INoticeService noticeService;
	
	// 로그인 페이지
	@GetMapping(value = "/login.do")
	public String noticeLogin(Model model) {
		model.addAttribute("bodyText", "login-page");
		return "conn/login";
	}
	
	// 회원가입 페이지
	@GetMapping(value = "/signup.do")
	public String noticeSignupForm(Model model) {
		model.addAttribute("bodyText", "register-page");
		return "conn/register";
	}
	
	// 아이디 중복확인 요청
	@ResponseBody
	@PostMapping(value = "/idCheck.do")
	public ResponseEntity<ServiceResult> idCheck(@RequestBody Map<String, String> map){
		log.info("넘겨받은 아이디: " + map.get("memId"));
		ServiceResult result = noticeService.idCheck(map.get("memId"));
		return new ResponseEntity<ServiceResult>(result, HttpStatus.OK);
	}
	
	@PostMapping("/signup.do")
	public String signup(
			HttpServletRequest req, RedirectAttributes ra,
			NoticeMemberVO memberVO, Model model) {
		String goPage = "";
		
		Map<String, String> errors = new HashMap<String, String>();
		if(StringUtils.isBlank(memberVO.getMemId())) {
			errors.put("memId", "아이디를 입력해주세요.");
		}
		if(StringUtils.isBlank(memberVO.getMemPw())) {
			errors.put("memPw", "비밀번호를 입력해주세요.");
		}
		if(StringUtils.isBlank(memberVO.getMemName())) {
			errors.put("memName", "이름을 입력해주세요.");
		}
		
		if(errors.size() > 0) {	// 빈값이 들어있는 에러정보가 있음
			model.addAttribute("bodyText", "register-page");
			model.addAttribute("errors", errors);
			model.addAttribute("member", memberVO);
			goPage = "conn/register";
		}else {	// 정상적인 데이터 입력
			ServiceResult result = noticeService.signup(req, memberVO);
			if(result.equals(ServiceResult.OK)) {	// 가입하기 성공
				//일회성 메세지 처리
				ra.addFlashAttribute("message", "회원가입을 완료하였습니다!");
				goPage = "redirect:/notice/login.do";
			}else {		// 가입하기 실패
				model.addAttribute("bodyText", "register-page");
				model.addAttribute("message", "서버에러, 다시 시도해주세요!");
				model.addAttribute("member", memberVO);
				goPage = "conn/register";
			}
		}
		return goPage;
	}
	
	@PostMapping("/loginCheck.do")
	public String loginCheck(
			HttpSession session,
			NoticeMemberVO memberVO, Model model) {
		String goPage = "";
		Map<String, String> errors = new HashMap<String, String>();
		if(StringUtils.isBlank(memberVO.getMemId())) {
			errors.put("memId", "아이디를 입력해주세요!");
		}
		if(StringUtils.isBlank(memberVO.getMemPw())) {
			errors.put("memPw", "비밀번호를 입력해주세요!");
		}
		if(errors.size() > 0) {	// 빈값이 들어있는 에러정보가 있음
			model.addAttribute("bodyText", "login-page");
			model.addAttribute("errors", errors);
			model.addAttribute("member", memberVO);
			goPage = "conn/login";
		}else {
			NoticeMemberVO member = noticeService.loginCheck(memberVO);
			if(member != null) {	// 로그인 성공
				// 로그인 성공 후, 세션처리 
				session.setAttribute("SessionInfo", member);
				goPage = "redirect:/notice/list.do";
			}else {
				model.addAttribute("message", "로그인 실패!, 로그인 정보를 정확하게 입력하세요");
				model.addAttribute("bodyText", "login-page");
				model.addAttribute("member", memberVO);
				goPage = "conn/login";
			}
		}
		return goPage;
	}
}














