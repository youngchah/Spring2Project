package kr.or.ddit.controller.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/modelattribute")
@Slf4j
public class ModelAttributeMemberController {
	
	/*
	 * 3. @ModelAttribute 어노테이션
	 * - 매개변수로 전달받은 데이터를 전달할 때 사용한다.
	 */
	
	@RequestMapping(value = "/registerForm", method = RequestMethod.GET)
	public String registerForm() {
		log.info("registerForm() 실행...!");
		return "member/registerModelAttributeForm";
	}
	// 1) 기본 자료형은 매개변수로 선언했을 때 전달되는지 확인합니다.
	@RequestMapping(value = "/register01", method = RequestMethod.POST)
	public String register01(String userId, String password) {
		log.info("register01() 실행..!");
		log.info("userId : " + userId);
		log.info("password : " + password);
		
		return "result";
	}
	// 2) 기본 자료형인 매개변수에  @ModelAttribute 어노테이션을 지정하여 데이터를 전달한다.
	@RequestMapping(value = "/register02", method = RequestMethod.POST)
	public String register02(
			@ModelAttribute("userId") String userId, 
			@ModelAttribute("password") String password) {
		log.info("register02() 실행..!");
		log.info("userId : " + userId);
		log.info("password : " + password);
		
		return "result";
	}
	// 3) 자바빈즈 규칙에 맞는 객체는 매개변수로 선언하면 기본적으로 전달된다.
	@RequestMapping(value = "/register03", method = RequestMethod.POST)
	public String register03(Member member) {
		// 규칙이 잘 구성되어 있는 자바빈즈 클래스 객체를 파라미터로 설정한 후에
		// 데이터 전달자 또는 @ModelAttribute 어노테이션을 설정하여 데이터를 전달하지 않더라도
		// return에 설정되어 있는 결과 페이지로 데이터가 자동 넘어간다.
		// 이때, 자바빈즈 클래스 명을 이용하여 데이터를 출력할 때 사용할 key로 사용된다.  
		// (클래스 명의 맨 앞글자가 소문자로 변해 key로 사용된다)
		log.info("register03() 실행..!");
		log.info("userId : " + member.getUserId());
		log.info("password : " + member.getPassword());
		
		return "result";
	}
	
	
}















