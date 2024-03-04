package kr.or.ddit.service.impl;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.mapper.INoticeMapper;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.crud.NoticeVO;

@Service
public class NoticeServiceImpl implements INoticeService {
	
	@Inject
	private INoticeMapper noticeMapper;
	
	@Override
	public ServiceResult insertNotice(NoticeVO noticeVO) {
		ServiceResult result = null;
		
		int status = noticeMapper.insertNotice(noticeVO);
		
		if(status > 0)
			result = ServiceResult.OK;
		else
			result = ServiceResult.FAILED;
		
		return result;
	}

	@Override
	public NoticeVO selectNotice(int boNo) {
		noticeMapper.incrementHit(boNo);	// 게시글 조회수 증가
		return noticeMapper.selectNotice(boNo);	// 게시글 번호에 해당하는 게시글 정보 가져오기
	}
}