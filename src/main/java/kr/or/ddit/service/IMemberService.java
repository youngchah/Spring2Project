package kr.or.ddit.service;

import java.io.IOException;
import java.util.List;

import kr.or.ddit.vo.crud.CrudMember;

public interface IMemberService {
	public void register(CrudMember member) throws IOException;
	public List<CrudMember> list();
	public CrudMember read(int userNo);
	public void modify(CrudMember member);
	public void remove(int userNo);
}
