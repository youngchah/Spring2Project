package kr.or.ddit.controller.file.item03_review;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

public class MediaUtils_Review {

	private static Map<String, MediaType> mediaMap;
	
	static {
		mediaMap = new HashMap<>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("JPEG", MediaType.IMAGE_JPEG);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
	}
	
	public static MediaType getMediaType(String type) {
		
		return mediaMap.get(type.toUpperCase());
	}

}
