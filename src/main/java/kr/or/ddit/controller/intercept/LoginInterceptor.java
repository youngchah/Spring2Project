package kr.or.ddit.controller.intercept;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.vo.crud.CrudMember;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
	
	private static final String USER_INFO = "userInfo";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("############## preHandle() 실행...! ");
		
		//http://localhost/intercept/login
		String requestURL = request.getRequestURL().toString();
		// /intercept/login
		String requestURI = request.getRequestURI().toString();
		
		log.info("requestURL : " + requestURL);
		log.info("requestURI : " + requestURI);
		
		HandlerMethod method = (HandlerMethod) handler;
		Method methodObj = method.getMethod();
		
		// kr.or.ddit.controller.login.loginController@234h234
		log.info("Bean : " + method.getBean());
		// public java.lang.String kr.or.ddit.controller.login.LoginController.loginForm
		log.info("method : " + methodObj);
		
		HttpSession session = request.getSession();
		if(session.getAttribute(USER_INFO) != null) {
			session.removeAttribute(USER_INFO);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("############### postHandle() 실행...! ");
		
		//http://localhost/intercept/login
		String requestURL = request.getRequestURL().toString();
		// /intercept/login
		String requestURI = request.getRequestURI().toString();
		
		log.info("requestURL : " + requestURL);
		log.info("requestURI : " + requestURI);
		
		HandlerMethod method = (HandlerMethod) handler;
		Method methodObj = method.getMethod();
		
		// kr.or.ddit.controller.login.loginController@234h234
		log.info("Bean : " + method.getBean());
		// public java.lang.String kr.or.ddit.controller.login.LoginController.loginForm
		log.info("method : " + methodObj);
		
		HttpSession session = request.getSession();
		
		// 컨트롤러 메소드를 거쳤다가 postHandle()로 넘어오면서 전달된 user라는 키에 value로 member가
		// 담긴 값이 Model에 담겨져 있다. 그중에 'user'로 넘긴 값이 로그인 후 인증된 회원 1명의 정보가
		// 담긴 MemberVO 자바빈즈 객체가 되고 객체가 null이 아닌경우 메인화면으로 리다이렉트 처리한다.
		ModelMap modelMap = modelAndView.getModelMap();
		Object member = modelMap.get("user");
		
		// 넘겨받은 데이터가 null이 아닌 경우 세션에 등록한다.
		if(member != null && !((CrudMember)member).getUserId().equals("")) {
			log.info("member : " + member);
			log.info("member.getUserId() : " + ((CrudMember)member).getUserId());
			session.setAttribute(USER_INFO, member);
			response.sendRedirect("/");
		}else {
			request.getRequestDispatcher("/WEB-INF/views/login/loginForm.jsp")
				.forward(request, response);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("################## afterCompletion() 실행...! ");
		
		//http://localhost/intercept/login
		String requestURL = request.getRequestURL().toString();
		// /intercept/login
		String requestURI = request.getRequestURI().toString();
		
		log.info("requestURL : " + requestURL);
		log.info("requestURI : " + requestURI);
		
		HandlerMethod method = (HandlerMethod) handler;
		Method methodObj = method.getMethod();
		
		// kr.or.ddit.controller.login.loginController@234h234
		log.info("Bean : " + method.getBean());
		// public java.lang.String kr.or.ddit.controller.login.LoginController.loginForm
		log.info("method : " + methodObj);
	}

}
