package kr.or.ddit.controller.file.item03_review;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils_Review {

	public static String uploadFile(String resourcePath, String originalFilename, byte[] bytes) throws Exception {
		String savedName = UUID.randomUUID().toString() + "_" + originalFilename;
		
		String savedPath = calcPath(resourcePath);
		
		//경로와 저장명 설정
		File target = new File(resourcePath + savedPath, savedName);
		//wst.server.core/.../upload
		//2024/03/06
		
		FileCopyUtils.copy(bytes, target);
		
		String formatName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		// asdfdassa.png || adasdf.jpeg .jpeg까지 짤리니까 .버려주는거임 
		String uploadedFileName = savedPath.replace(File.separatorChar, '/') + "/" + savedName;
		// \ 이렇게 되어있는걸 => / 이렇게 바꾼다 
		
		if(MediaUtils_Review.getMediaType(formatName) != null) {
			
			makeThumbnail(resourcePath, savedPath, savedName);
			// upload ... 2024/03/06 ... 우리가 저장할 파일명
		}
		
		return uploadedFileName;
	}

	private static void makeThumbnail(String resourcePath, String savedPath, String savedName) throws Exception {
		BufferedImage sourceImg = ImageIO.read(new File(resourcePath + savedPath, savedName));
		// 파일을 읽었습니다 BuffedImg로 
		
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		// Method.AUTOMATIC : 최소 시간 내에 가장 잘 보이는 이미지를 얻기 위한 사용 방식
		// Mode.FIT_TO_HEIGHT : 이미지 방향과 상관없이 주어진 높이 내에서 가장 잘 맞는 이미지로 계산
		// targetSize : 값 100, 정사각형 사이즈로 100x100
		
		String thumbnailName = resourcePath + savedPath + File.separator + "s_" + savedName;
		String formatName = savedName.substring(savedName.lastIndexOf(".") + 1);
		
		File newFile = new File(thumbnailName);
		// 파일의 데이터를 기록해둔거지 실제로 파일 생성 X
		
		ImageIO.write(destImg, formatName, newFile);
		// destImg를 formatName(확장자)로 newFile 경로로 만들어준다 
		// 썸네일 이미지가 생성됩니다
	}

	private static String calcPath(String resourcePath) {
		
		Calendar cal = Calendar.getInstance();
		
		String year = File.separator + cal.get(Calendar.YEAR);
		// /2024 or \\2024
		String month = year + File.separator + 
				new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		// int 10000 -> 10,000 원 
		String date = month + File.separator + 
				new DecimalFormat("00").format(cal.get(Calendar.DATE));
		makeDir(resourcePath, year, month, date);
				
		
		return date;
	}

	private static void makeDir(String resourcePath, String... paths) {
		
		if(new File(paths[paths.length-1]).exists()) {
			return;
		}
		
		for (String path : paths) {
			File dirPath = new File(resourcePath + path);
			
			if(!dirPath.exists()) {
				dirPath.mkdirs();
			}
			
			// 1번째 : year 폴더
			// 2번째 : month 폴더
			// 3번째 : date 폴더
		}
		
		// paths.length-1
		// 3개의 데이터가 있고 (0,1,2)
	}

}
