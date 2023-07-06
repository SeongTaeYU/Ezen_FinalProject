package com.small.group.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

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
	public boolean fileExists(@RequestBody String filePath) {
		File file = new File(filePath);
		
		if(file.exists()) {
			if(file.isFile()) {
				return true;
			}
		}
		return false;
	}
}

@Data
class ImageItem {
	private String imageData;
	private String fileName;
}
