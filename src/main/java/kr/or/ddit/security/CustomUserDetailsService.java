package kr.or.ddit.security;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.or.ddit.mapper.ILoginMapper;
import kr.or.ddit.vo.CustomUser;
import kr.or.ddit.vo.crud.NoticeMemberVO;

public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger log = 
			LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Inject
	private ILoginMapper loginMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 인증된 사용자 정보를 담을 공간 선언 
		NoticeMemberVO member;
		try {
			member = loginMapper.readByUserId(username);
			log.info("queried by member mapper : " + member);
			return member == null ? null : new CustomUser(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}











