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

}