package kr.or.ddit.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomNoOpPasswordEncoder implements PasswordEncoder{
	
	private static final Logger log = LoggerFactory.getLogger(CustomNoOpPasswordEncoder.class);
	
	/*
	 * kr.or.ddit.security.CustomNoOpPasswordEncoder 위치에 해당하는 클래스를
	 * customPasswordEncoder 아이디로 빈 등록 하고, security:authentication-manager 태그 내에
	 * 인증 제공자를 등록합니다.
	 * > security:password-encoder ref="customPasswordEncoder"
	 */
	
	// 내가 넘긴 비밀번호를 암호화 할때 사용
	@Override
	public String encode(CharSequence rawPassword) {
		log.info("before encode: " + rawPassword);
		return rawPassword.toString();
	}

	// 그 비밀번호가 암호화돼서 처리되고있는 텍스트와 비교할 때 
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		log.info("mathes : " + rawPassword + " : " + encodedPassword);
		return rawPassword.toString().equals(encodedPassword);
	}

}








