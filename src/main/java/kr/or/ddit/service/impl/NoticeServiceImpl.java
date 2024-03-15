package kr.or.ddit.service.impl;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.mapper.ILoginMapper;
import kr.or.ddit.mapper.INoticeMapper;
import kr.or.ddit.mapper.IProfileMapper;
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
	
	@Inject
	private IProfileMapper profileMapper;
	
	@Inject
	private PasswordEncoder pe;
	
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
	public ServiceResult updateNotice(HttpServletRequest req, NoticeVO noticeVO) {
		ServiceResult result = null;
		int status = noticeMapper.updateNotice(noticeVO);
		if(status > 0) {	// 수정 성공
			// 게시글 정보에서 파일 목록 가져오기 
			List<NoticeFileVO> noticeFileList = noticeVO.getNoticeFileList();
			
			try {
				// 공지사항 파일 업로드
				noticeFileUpload(noticeFileList, noticeVO.getBoNo(), req);
				
				// 기존에 등록되어 있는 파일 목록들 중, 수정하기 위해서 x버튼을 눌러
				// 삭제 처리로 넘겨준 파일 번호들
				Integer[] delNoticeNo = noticeVO.getDelNoticeNo();
				if(delNoticeNo != null) {
					for(int i = 0; i < delNoticeNo.length; i++) {
						// 삭제할 파일 번호 목록들 중, 파일 번호에 해당하는 공지사항 파일 정보를 가져온다.
						NoticeFileVO noticeFileVO = 
								noticeMapper.selectNoticeFile(delNoticeNo[i]);
						// 파일 번호에 해당하는 파일 데이터를 삭제
						noticeMapper.deleteNoticeFile(delNoticeNo[i]);
						File file = new File(noticeFileVO.getFileSavepath());
						file.delete();	// 기존 파일에 업로드 되어 있던 경로에 파일 삭제 
						
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			result = ServiceResult.OK;
		}else {	// 수정 실패
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public ServiceResult deleteNotice(HttpServletRequest req,int boNo) {
		ServiceResult result = null;
		
		// 공지사항 파일 데이터를 삭제하기 위한 준비(파일 적용시 사용)
		// 게시글 번호에 해당하는 공지사항 게시글 정보 가져오기(파일정보들을 가져오기위해서)
		NoticeVO noticeVO = noticeMapper.selectNotice(boNo);
		// 게시글 번호에 해당하는 파일 데이터 삭제 
		noticeMapper.deleteNoticeFileByBoNo(boNo);
		
		int status = noticeMapper.deleteNotice(boNo);	// 공지사항 게시글 삭제
		if(status > 0) {	// 삭제 성공
			// 공지사항 게시글 정보에서 파일 목록 가져오기
			List<NoticeFileVO> noticeFileList = noticeVO.getNoticeFileList();
			
			try {
				if(noticeFileList != null) {
					// E:\D_SETTING\02.SPRING\workspace_spring2\.metadata...\resources\notice\130
					// asdjkfhakjsdfhajksd_원본파일명
					// 두개의 구간으로 split된다.
					String[] filePath = noticeFileList.get(0).getFileSavepath().split("/");
					String path = filePath[0];
					deleteFolder(req, path);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
			// 파일 복사가 일어난 파일의 위치로 접근하기 위한 URI 설정
			proFileImg = "/resources/profile/" + fileName;
		}
		
		memberVO.setMemProfileimg(proFileImg);
		
		// 암호화 된 비밀번호 셋팅 
		// 스프링 시큐리티 적용 시, 비밀번호 암호화 설정 
		memberVO.setMemPw(pe.encode(memberVO.getMemPw()));
		
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
		String savePath = "/resources/notice/";
		
		if(noticeFileList != null) { //넘겨 받은 파일 데이터가 존재할떄
			if(noticeFileList.size() > 0) {
				for (NoticeFileVO noticeFileVO : noticeFileList) {
					String saveName = UUID.randomUUID().toString();//UUID의 파일명 생성
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
	
	private void deleteFolder(HttpServletRequest req, String path) {
		// UUID_원본파일명 전 폴더경로를 folder 파일객체로 잡는다.
		File folder = new File(path);
		try {
			if(folder.exists()) {	// 경로가 존재한다면
				// 폴더 안에 있는 파일들의 목록을 가져온다.
				File[] folderFileList = folder.listFiles();
			
				for(int i = 0; i < folderFileList.length; i++) {
					if(folderFileList[i].isFile()) {	// 폴더 안에 있는 파일이 파일일때
						// 폴더 안에 파일을 차례대로 삭제
						folderFileList[i].delete();
					}else {	// 폴더 안에 있는 파일이 폴더일때 
						// 폴더라면 재귀함수 호출(폴더 안으로 들어갈 수 있도록)
						deleteFolder(req, folderFileList[i].getPath());
					}
				}
				folder.delete();	// 폴더 삭제
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public NoticeFileVO noticeDownload(int fileNo) {
		// 파일 번호에 해당하는 파일 정보 가져오기 
		NoticeFileVO noticeFileVO = noticeMapper.noticeDownload(fileNo);
		if(noticeFileVO == null) {
			throw new RuntimeException();
		}
		
		noticeMapper.incrementNoticeDowncount(fileNo); // 다운로드 횟수 증가
		return noticeFileVO;
	}



	@Override
	public NoticeMemberVO selectMember(String memId) {
		return profileMapper.selectMember(memId);
	}



	@Override
	public ServiceResult profileUpdate(HttpServletRequest req, NoticeMemberVO memberVO) {
		ServiceResult result = null;
		
		String uploadPath = req.getServletContext().getRealPath("/resources/profile");
		File file = new File(uploadPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		String profileImg = "";
		try {
			MultipartFile proFileImgFile = memberVO.getImgFile();
			if(proFileImgFile != null && proFileImgFile.getOriginalFilename() != null &&
					!proFileImgFile.getOriginalFilename().equals("")) {
				String fileName = UUID.randomUUID().toString();
				fileName += "_" + proFileImgFile.getOriginalFilename();
				uploadPath += "/" + fileName;
				proFileImgFile.transferTo(new File(uploadPath));
				profileImg = "/resources/profile/" + fileName;
			}
			memberVO.setMemProfileimg(profileImg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int status = profileMapper.profileUpdate(memberVO);
		if(status > 0) {	// 수정 성공
			result = ServiceResult.OK;
		}else {				// 수정 실패
			result = ServiceResult.FAILED;
		}
		
		return result;
	}



	@Override
	public String idForgetProcess(NoticeMemberVO member) {
		return loginMapper.idForgetProcess(member);
	}



	@Override
	public String pwForgetProcess(NoticeMemberVO member) {
		return loginMapper.pwForgetProcess(member);
	}
}















