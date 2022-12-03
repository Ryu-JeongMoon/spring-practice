package com.springanything.file;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FileUploadController {

	@PostMapping("/test/file-upload")
	public String upload(@RequestPart MultipartFile file) {
		String name = file.getName();
		log.info("file = {}", file);
		log.info("name = {}", name);
		return name;
	}
}
