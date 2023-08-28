package com.demoproject.blog.Service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	String uploadImage(String path, MultipartFile file) throws IOException;

	InputStream fetchImage(String path, String fileName) throws IOException;
}
