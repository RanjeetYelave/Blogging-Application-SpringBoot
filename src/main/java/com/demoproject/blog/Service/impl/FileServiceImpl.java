package com.demoproject.blog.Service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demoproject.blog.Service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		String name = file.getOriginalFilename();
		String randomUUID = UUID.randomUUID().toString();

		String fileExtensionCheck = name.substring(name.lastIndexOf("."));
		if (fileExtensionCheck.equalsIgnoreCase(".png") || fileExtensionCheck.equalsIgnoreCase(".jpg")
				|| fileExtensionCheck.equalsIgnoreCase(".jpeg")) {

			String Filename = randomUUID + name;
			String fullPath = path + File.separator + Filename;

			File folder = new File(path);
			if (!folder.exists()) {
				folder.mkdir();
			}
			Files.copy(file.getInputStream(), Paths.get(fullPath));

			return Filename;
		} else {
			return "File Rejected, Upload JPG, PNG or JPEG File Format";
		}
	}

	@Override
	public InputStream fetchImage(String path, String fileName) throws IOException {
		String fullPath = path + File.separator + fileName;
		InputStream inputStream = new FileInputStream(fullPath);

		return inputStream;
	}

}
