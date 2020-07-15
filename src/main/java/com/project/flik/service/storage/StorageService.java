package com.project.flik.service.storage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("upload-dir");

	public void store(MultipartFile file, String directory) {
		Path fileLocation = Paths.get(directory);
		try {
			Files.copy(file.getInputStream(), fileLocation.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}
	public Resource loadFile(String filename, String path) {
		try {
			Path rootLocation1 = Paths.get(rootLocation+path);
			Path file = rootLocation1.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}

	public String base64ToImage(MultipartFile[] image, String productName, String companyName) {
		String directory = "'";
		for (int i = 0; i < image.length; i++) {
			// Path directoryName = Paths.get("upload-dir");
			directory = "\\" + "Product" + "\\" + companyName + "\\" + productName + "\\";
			String fullDirecoryPath = rootLocation + directory;
			File file = new File(fullDirecoryPath);
			boolean dirCreated = file.mkdirs();
			store(image[i], fullDirecoryPath);
		}
		return directory;
	}

	public String saveUserProfilePic(MultipartFile profilePic, Long id) {
		String directory = "\\" + "User" + "\\" + id + "\\";
		String fullDirecoryPath = rootLocation + directory;
		File file = new File(fullDirecoryPath);
		boolean dirCreated = file.mkdirs();
		store(profilePic, fullDirecoryPath);
		return directory;
	}
}