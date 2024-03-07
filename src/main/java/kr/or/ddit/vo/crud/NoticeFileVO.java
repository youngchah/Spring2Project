package kr.or.ddit.vo.crud;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NoticeFileVO {
	private MultipartFile item;
	private int fileNo;
	private int boNo;
	private String fileName;
	private long fileSize;
	private String fileFancysize;
	private String fileMime;
	private String fileSavepath;
	private int fileDowncount;
	
	public NoticeFileVO() {}
	
	//3개의 파일 중 1개로 아래 값을 자동으로 만드는 중(이름/크기/타입/팬시크기)
	public NoticeFileVO(MultipartFile item) {
		this.item = item;
		this.fileName = item.getOriginalFilename();
		this.fileSize = item.getSize();
		this.fileMime = item.getContentType();
		this.fileFancysize = FileUtils.byteCountToDisplaySize(fileSize);
	}
}
