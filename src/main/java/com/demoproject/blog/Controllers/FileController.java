package com.demoproject.blog.Controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demoproject.blog.Service.impl.FileServiceImpl;

import ResponseEntityForListwithPagination.fileResponse;

@RestController
@RequestMapping("/api/file")
public class FileController {
	@Autowired
	FileServiceImpl FileService;
	@Value("${image.source}")
	String path;

	@PostMapping("/image/")
	ResponseEntity<fileResponse> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
		String uploadImage = FileService.uploadImage(path, image);
		fileResponse fileResponse = new fileResponse(uploadImage, true);
		return new ResponseEntity<>(fileResponse, HttpStatus.OK);
	}

	@GetMapping("/image/{name}")
	public void downloadImage(@PathVariable String name, HttpServletResponse response) throws IOException {
		InputStream fetchedImage = FileService.fetchImage(path, name);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(fetchedImage, response.getOutputStream());

	}
}
