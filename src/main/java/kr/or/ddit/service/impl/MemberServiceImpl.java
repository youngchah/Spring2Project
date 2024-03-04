package kr.or.ddit.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.IMemberMapper;
import kr.or.ddit.service.IMemberService;
import kr.or.ddit.vo.crud.CrudMember;
import kr.or.ddit.vo.crud.CrudMemberAuth;

@Service
public class MemberServiceImpl implements IMemberService {

   @Inject
   private IMemberMapper mapper;
   
   @Override
   public void register(CrudMember member) {
      mapper.create(member);
      
      CrudMemberAuth memberAuth = new CrudMemberAuth();
      memberAuth.setUserNo(member.getUserNo());
      memberAuth.setAuth("ROLE_USER");
      
      mapper.createAuth(memberAuth);
      
   }

	@Override
	public List<CrudMember> list() {
		return mapper.list();
	}

	@Override
	public CrudMember read(int userNo) {
		return mapper.read(userNo);
	}

	@Override
	public void modify(CrudMember member) {
		mapper.modify(member);
		
		int userNo = member.getUserNo();	// 회원번호 가져오기
		mapper.deleteAuth(userNo);	// 회원 번호에 해당하는 권한들 모두 지우기
		
		List<CrudMemberAuth> authList = member.getAuthList();
		for(int i = 0; i < authList.size(); i++) {
			CrudMemberAuth memberAuth = authList.get(i);
			String auth = memberAuth.getAuth();
			if(auth == null) {
				continue;
			}
			if(auth.trim().length() == 0) {
				continue;
			}
			
			memberAuth.setUserNo(userNo);
			mapper.createAuth(memberAuth);
		}
	}

	@Override
	public void remove(int userNo) {
		mapper.deleteAuth(userNo);	//자식을 먼저 지워줌
		mapper.delete(userNo);
	}

}










