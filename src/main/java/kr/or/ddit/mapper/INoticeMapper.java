package kr.or.ddit.mapper;

import kr.or.ddit.vo.crud.NoticeVO;

public interface INoticeMapper {

	public int insertNotice(NoticeVO noticeVO);
	public void incrementHit(int boNo);
	public NoticeVO selectNotice(int boNo);

}
