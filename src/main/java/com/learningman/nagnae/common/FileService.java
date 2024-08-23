package com.learningman.nagnae.common;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload.windows}")
    private String windowsUploadDir;

    @Value("${file.upload.linux}")
    private String linuxUploadDir;

    private String uploadDir;

    @PostConstruct
    public void init() {
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("Detected OS: " + osName);
        uploadDir = osName.contains("win") ? windowsUploadDir : linuxUploadDir;
        System.out.println("Selected upload directory: " + uploadDir);
        createUploadDirectory();
    }

    private void createUploadDirectory() {
        try {
            Path directory = Paths.get(uploadDir);
            Files.createDirectories(directory);
            System.out.println("Upload directory created: " + directory.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public String saveImageAndGetUrl(MultipartFile file) throws IOException {
        String fileName = saveFile(file);
        return "/upload/" + fileName;
    }

    private String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + getFileExtension(originalFilename);
        Path filePath = Paths.get(uploadDir, filename);
        System.out.println("Saving file to: " + filePath.toAbsolutePath());
        Files.copy(file.getInputStream(), filePath);
        return filename;
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }
}