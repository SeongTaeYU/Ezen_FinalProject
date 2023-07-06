package com.small.group.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.Data;

@RequestMapping("/image")
@RestController
public class ImageController {
	
	@PostMapping("/saveImage")
	public String saveImage(@RequestBody ImageItem imageItem) {
		String base64Data = imageItem.getImageData();
		String fileName = imageItem.getFileName();
		
		try {
			String base64Image = base64Data.split(",")[1];
			byte[] imageBytes = Base64Utils.decodeFromString(base64Image);
			
			String imagePath = "src/main/resources/static/image/groupMain/" + fileName;
			FileOutputStream outputStream = new FileOutputStream(imagePath);
			outputStream.write(imageBytes);
			outputStream.close();
			
			return "이미지 저장에 대한 처리 실행 완료.";
		} catch (Exception e) {
			e.printStackTrace();
			return "파일 입출력에 대한 에러가 발생하였습니다.";
		} 
	}
	
	
	// 파일의 존재여부에 따른 결과 값을 반환하는 함수
	@PostMapping("/fileExists")
	public String fileExists(@RequestBody String filePath) {
		String[] allowedExtension = {"jpg", "jpeg", "png", "gif"};
		/*
		 *  해당 확장자들을 전부 순회하며 경로에 파일명으로 된 이미지가 있는지
		 *  검색한 후 일치하는 파일명을 결과 값으로 반환함.
		 */
		for(String extension : allowedExtension) {
			File file = new File(filePath + extension);
			if(file.isFile()) {
				// 파일명 부분만 가져오기
				int indexOf = filePath.lastIndexOf("/") + 1;
				String fileName = filePath.substring(indexOf, filePath.length());
				return fileName + extension;
			}
		}
		
		// 일치하지 않으면 notFound 반환
		return "notFound";
		
		
	}
}

@Data
class ImageItem {
	private String imageData;
	private String fileName;
}
