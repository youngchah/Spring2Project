package kr.or.ddit.controller.crud.notice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeLoginController {
	
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
}
