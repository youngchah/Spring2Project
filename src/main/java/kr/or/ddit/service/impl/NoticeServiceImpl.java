package kr.or.ddit.service.impl;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.mapper.ILoginMapper;
import kr.or.ddit.mapper.INoticeMapper;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.PaginationInfoVO;
import kr.or.ddit.vo.crud.NoticeFileVO;
import kr.or.ddit.vo.crud.NoticeMemberVO;
import kr.or.ddit.vo.crud.NoticeVO;

@Service
public class NoticeServiceImpl implements INoticeService {
	
	@Inject
	private INoticeMapper noticeMapper;
	
	@Inject
	private ILoginMapper loginMapper;
	
	@Override
	public ServiceResult insertNotice(HttpServletRequest req,NoticeVO noticeVO) {
		ServiceResult result = null;
		
		int status = noticeMapper.insertNotice(noticeVO);
		
		if(status > 0) { //등록 처리가 완료되었을 때
			//공지사항 게시글 등록 시, 업로드 한 파일 목록을 가져온다.
			List<NoticeFileVO> noticeFileList = noticeVO.getNoticeFileList();
			
			//공지사항 파일 업로드 처리
			try {
				noticeFileUpload(noticeFileList, noticeVO.getBoNo(), req);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	

	@Override
	public NoticeVO selectNotice(int boNo) {
		noticeMapper.incrementHit(boNo);	// 게시글 조회수 증가
		return noticeMapper.selectNotice(boNo);	// 게시글 번호에 해당하는 게시글 정보 가져오기
	}

	@Override
	public ServiceResult updateNotice(NoticeVO noticeVO) {
		ServiceResult result = null;
		int status = noticeMapper.updateNotice(noticeVO);
		if(status > 0) {	// 수정 성공
			result = ServiceResult.OK;
		}else {	// 수정 실패
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public ServiceResult deleteNotice(int boNo) {
		ServiceResult result = null;
		int status = noticeMapper.deleteNotice(boNo);
		if(status > 0) {	// 삭제 성공
			result = ServiceResult.OK;
		}else {	// 삭제 실패
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public int selectNoticeCount(PaginationInfoVO<NoticeVO> pagingVO) {
		return noticeMapper.selectNoticeCount(pagingVO);
	}

	@Override
	public List<NoticeVO> selectNoticeList(PaginationInfoVO<NoticeVO> pagingVO) {
		return noticeMapper.selectNoticeList(pagingVO);
	}
	
	//파일업로드 notice - 로그인
	@Override
	public ServiceResult idCheck(String memId) {
		ServiceResult result = null;
		NoticeMemberVO member = loginMapper.idCheck(memId);
		if(member != null) {
			result = ServiceResult.EXIST;
		}else {
			result = ServiceResult.NOTEXIST;
		}
		
		return result;
	}

	@Override
	public ServiceResult signup(HttpServletRequest req, NoticeMemberVO memberVO) {
		ServiceResult result = null;
		
		// 회원가입 시, 프로필 이미지로 파일을 업로드 하는데 이때 업로드할 서버 경로
		String uploadPath = req.getServletContext().getRealPath("/resources/profile");
		File file = new File(uploadPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		String proFileImg = "";	// 회원정보에 추가될 프로필 이미지 경로
		
		// 넘겨받은 회원정보에서 파일 데이터 가져오기 
		MultipartFile proFileImgFile = memberVO.getImgFile();
		
		// 넘겨받은 파일 데이터가 존재 할 때 
		if(proFileImgFile != null && proFileImgFile.getOriginalFilename() != null &&
				!proFileImgFile.getOriginalFilename().equals("")) {
			String fileName = UUID.randomUUID().toString(); //UUID 파일명 생성
			// UUID_원본파일명으로 파일명 생성
			fileName += "_" + proFileImgFile.getOriginalFilename();
			// /resources/profile/uuid_원본파일명
			uploadPath += "/" + fileName;
			try {
				// 해당 위치에 파일 복사 
				proFileImgFile.transferTo(new File(uploadPath));
			} catch (Exception e) {
				e.printStackTrace();
			} 	
			// 해당 위치에 파일 복사
			// 파일 복사가 일어난 파일의 위치로 접근하기 위한 URI 설정
			proFileImg = "/resources/profile/" + fileName;
		}
		
		memberVO.setMemProfileimg(proFileImg);
		
		int status = loginMapper.signup(memberVO);
		if(status > 0) {
			// 한명의 회원이 등록 될 때에 하나의 권한을 무조건 가질 수 있도록 권한 등록(스프링 시큐리티 적용 시)
			loginMapper.signupAuth(memberVO.getMemNo());
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public NoticeMemberVO loginCheck(NoticeMemberVO memberVO) {
		
		return loginMapper.loginCheck(memberVO);
	}
	
	private void noticeFileUpload(List<NoticeFileVO> noticeFileList, int boNo, HttpServletRequest req) throws Exception  {
		// 공지사항 게시판에 등록된 파일은 기본 '/resources/notice/' 경로로 설정
		String savePath = "/resources/notice";
		
		if(noticeFileList != null) { //넘겨 받은 파일 데이터가 존재할떄
			if(noticeFileList.size() > 0) {
				for (NoticeFileVO noticeFileVO : noticeFileList) {
					String saveName = UUID.randomUUID().toString();//UUID의 파일명 ㅅ ㅐㅇ성
					// 파일명을 설정할때 원본 파일명과 UUID_ 와 합쳐서 파일명을 만든다.
					saveName += "_" + noticeFileVO.getFileName();
					String saveLocate = req.getServletContext().getRealPath(savePath + boNo);
					File file = new File(saveLocate);
					if(!file.exists()) { // 업로드 하기 위한 폴더 구조가 존재하지 않을때
						file.mkdirs(); //폴더 생성
					}
					saveLocate += "/" + saveName;	//실제 업로드 할 경로 (파일명 포함)
					
					noticeFileVO.setBoNo(boNo);		//게시글 번호 설정
					noticeFileVO.setFileSavepath(saveLocate); // 파일 업로드 경로 설정
					noticeMapper.insertNoticeFile(noticeFileVO);	//게시글 파일 데이터 추가
					
					File saveFile = new File(saveLocate);
					noticeFileVO.getItem().transferTo(saveFile); //파일복사
				}
			}
		}
	}
}















