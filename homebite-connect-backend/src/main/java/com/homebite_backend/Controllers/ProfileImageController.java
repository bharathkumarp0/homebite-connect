package com.homebite_backend.Controllers;

import com.homebite_backend.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ProfileImageController {

    private static final String UPLOAD_DIR = "uploads/profile_images/";

    @PostMapping("/uploadProfileImage")
    @ResponseBody
    public ResponseEntity<?> uploadProfileImage(@RequestParam("image") MultipartFile multipartFile, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        if (multipartFile.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected");
        }

        try {
            // Create directory if not exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            String fileName = "user_" + loggedInUser.getId() + "." + extension;

            Path filePath = uploadPath.resolve(fileName);
            try (OutputStream os = Files.newOutputStream(filePath)) {
                os.write(multipartFile.getBytes());
            }

            // Return URL that user can call to get image
            String imageUrl = "/profileImage/" + loggedInUser.getId();

            return ResponseEntity.ok().body("{\"imageUrl\":\"" + imageUrl + "\"}");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file");
        }
    }

    @GetMapping("/profileImage/{userId}")
    public ResponseEntity<byte[]> serveProfileImage(@PathVariable Long userId) {
        try {
            // Try to load image file by userId
            Path uploadPath = Paths.get(UPLOAD_DIR);
            File dir = uploadPath.toFile();

            File[] matches = dir.listFiles((dir1, name) -> name.startsWith("user_" + userId + "."));
            if (matches == null || matches.length == 0) {
                return ResponseEntity.notFound().build();
            }
            File file = matches[0];

            byte[] imageBytes = Files.readAllBytes(file.toPath());
            HttpHeaders headers = new HttpHeaders();
            String mimeType = Files.probeContentType(file.toPath());
            headers.setContentType(MediaType.parseMediaType(mimeType));

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
