package kr.or.ddit.controller.test;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.controller.test.dao.Test03Repository;
import kr.or.ddit.controller.test.vo.StudentVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/test03")
@Slf4j
public class TestController03 {
	
	@Inject
	Test03Repository repo;
	
	@RequestMapping(value="/login.do", method = RequestMethod.GET)
	public String loginPage() {
		
		return "script/test03/login";
	}
	
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	public String login(StudentVO student, RedirectAttributes rttr) {
		
		log.info("{}",student);
		
		StudentVO st = repo.login(student);
		
		if(st != null) {
			rttr.addFlashAttribute("msg", st.getMemName() + "님! 환영합니다!");
			return "redirect:/test03/info.do?memId="+st.getMemId();
		}
		
		rttr.addFlashAttribute("msg", "FAILURE");	
		return "redirect:login.do";
	}
	
	
	@RequestMapping(value="/findInfo.do", method = RequestMethod.GET)
	public String findInfo() {
		return "script/test03/findInfo";
	}
	
	@RequestMapping(value="/findInfo.do/{type}", method = RequestMethod.POST)
	public ResponseEntity<String> findInfoPost(@PathVariable String type, @RequestBody StudentVO student) {
		
		String value = "";
		ResponseEntity<String> entity = null;
		
		if(type.equals("id")) {
			value = repo.findId(student);
			
		}else {
			value = repo.findPw(student);
		}
		
		if(value == null) {
			entity = new ResponseEntity<String>("FAILURE",HttpStatus.OK);
		}else {
			entity = new ResponseEntity<String>(value, HttpStatus.OK);
		}
		
		return entity;
	}
	
	
	
	@RequestMapping(value="/info.do", method = RequestMethod.GET)
	public String info(Model model, String memId) {
		StudentVO st = repo.getStudent(memId);
		model.addAttribute("student", st);
		return "script/test03/info";
	}
	
	
	
}
