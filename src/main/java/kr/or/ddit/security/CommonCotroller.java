package kr.or.ddit.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonCotroller {
	
	Logger log = LoggerFactory.getLogger(CommonCotroller.class);
	
	@GetMapping("/accessError")
	public String accessDenied(Authentication auth, Model model) {
		log.info("accessDenied() 실행...!");
		log.info("accessDenied()" + auth);
		
		// org.springframework.security.authentication.UsernamePasswordAuthenticationToken@34234
		// Principal : org.springframework.security.core.userdetails.User@ahfh784
		// Username : member;
		// password	: [PROTECTED];
		// Enabled : true;
		// AccountNonExpired : true;
		// credentialsNonExpired : true;
		// AccountNonLocked : true;
		// Granted Authorities : ROLE_MEMBER;
		// Credentials : [PROTECTED];
		// Authenticated : true;
		// Details : org.springframework.security.web.authentication.
		//			WebAuthenticationDetails@166c822
		// SessionId : B8u8897y8456y213456KASDIJ**JKFSDFJIOX;
		// Granted Authorities : ROLE_MEMBER
		
		model.addAttribute("msg", "Access Denied");
		return "accessError";
	}
}
