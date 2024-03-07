package kr.or.ddit.vo.crud;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NoticeVO {
	private int boNo;
	private String boTitle;
	private String boContent;
	private String boWriter;
	private String boDate;	//날짜 데이터를 꼭 Date가 아닌 String으로 해도 된다 ex)풀캘린더api에서는 String으로 받아야 사용가능함
	private int boHit;
	
	private Integer[] delNoticeNo;
	private MultipartFile[] boFile;
	private List<NoticeFileVO> noticeFileList;
	
	//ex)boFile에 파일 3개가 담겨오면
	//서버에 도착하기도 전에 넘겨받은 파일 정보를 vo에서 생성하게 만드는 코드
	public void setBoFile(MultipartFile[] boFile) {
		this.boFile = boFile;
		if(boFile != null) {
			//리스트에 담을 공간 만들고
			List<NoticeFileVO> noticeFileList = new ArrayList<NoticeFileVO>();
			
			//for문을 통해 3개가 꺼내짐
			for (MultipartFile item : boFile) {
				//첫번째 담긴 item이 비어있다면 continue=다시 for문
				if(StringUtils.isBlank(item.getOriginalFilename())) {
					continue;
				}
				//item을 던져서 NoticeFileVO에 넣음
				NoticeFileVO noticeFileVO = new NoticeFileVO(item);
				//item정보를 list에 하나하나 담음 => 총 3개의 파일이 쌓여짐
				noticeFileList.add(noticeFileVO);
			}
			//전역으로 선언해놓은 list안에 집어넣음
			this.noticeFileList = noticeFileList;
		}
	}
}
