package kr.or.ddit.controller.intercept;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessLoggingInterceptor implements HandlerInterceptor {
	
	PrintWriter writer;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("preHandle() 실행...!");
		File file = new File("C:/logs/ddit_logging.log");
		writer = new PrintWriter(new FileWriter(file, true), true);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String requestURL = request.getRequestURL().toString();
		String requestURI = request.getRequestURI();
		
		log.info("requestURL : " + requestURL);
		log.info("requestURI : " + requestURI);
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		
		// class kr.or.ddit.controller.BoardController와 같은 녀석
		Class clazz = method.getDeclaringClass();
		
		// kr.or.ddit.controller.BoardController 와 같은 녀석
		String className = clazz.getName();
		// BoardController와 같은 녀석
		String classSimpleName = clazz.getSimpleName();
		// boardList() 메소드 명
		String methodName = method.getName();
		
		writer.printf("현재 일시 : %s %n", getCurrntTime());
		writer.printf("Access Controller : %s %n", className + "." + methodName);
		writer.println("--------------------------------------------------------");
	}

	public String getCurrntTime() {
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		return formatter.format(cal.getTime());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("afterCompletion() 메소드로 최종 타겟 마무리");
	}

}









