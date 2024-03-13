package kr.or.ddit.controller.intercept;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.vo.crud.CrudMember;

@Controller
@RequestMapping("/intercept")
public class LoginController {
	
	@GetMapping("/login")
	public String loginForm() {
		return "login/loginForm";
	}
	
	@PostMapping("/login")
	public String login(CrudMember member, Model model) {
		member.setUserName("유재석");
		model.addAttribute("user", member);
		return "login/success";
	}
}
