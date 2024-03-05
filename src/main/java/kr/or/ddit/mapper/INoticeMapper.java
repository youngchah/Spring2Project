package kr.or.ddit.mapper;

import java.util.List;

import kr.or.ddit.vo.PaginationInfoVO;
import kr.or.ddit.vo.crud.NoticeVO;

public interface INoticeMapper {

	public int insertNotice(NoticeVO noticeVO);
	public void incrementHit(int boNo);
	public NoticeVO selectNotice(int boNo);
	public int updateNotice(NoticeVO noticeVO);
	public int deleteNotice(int boNo);
	public int selectNoticeCount(PaginationInfoVO<NoticeVO> pagingVO);
	public List<NoticeVO> selectNoticeList(PaginationInfoVO<NoticeVO> pagingVO);

}
