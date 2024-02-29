package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.vo.crud.CrudMember;

public interface IMemberService {
	public void register(CrudMember member);
	public List<CrudMember> list();
}
