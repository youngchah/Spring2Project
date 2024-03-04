package kr.or.ddit.service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.crud.NoticeVO;

public interface INoticeService {
	public ServiceResult insertNotice(NoticeVO noticeVO);
	public NoticeVO selectNotice(int boNo);

}
