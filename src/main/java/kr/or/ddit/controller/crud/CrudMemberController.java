package kr.or.ddit.controller.crud;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.service.IMemberService;
import kr.or.ddit.vo.crud.CrudMember;

@Controller
@RequestMapping("/crud/member")
public class CrudMemberController {
	
	@Inject
	private IMemberService service;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String crudMemberRegisterForm() {
		return "crud/member/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String crudMemberRegister(CrudMember member, Model model) {
		service.register(member);
		model.addAttribute("msg", "등록이 완료되었습니다!");
		return "crud/member/success";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String crudMemberList(Model model) {
		List<CrudMember> memberList = service.list();
		model.addAttribute("memberList", memberList);
		return "crud/member/list";
	}
}

















