package com.example.toxicapplication.utility;

import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class UserUtility {
    public static void folderCreate(byte[] imageBytes, String rectangleFolder, String imagePath) throws IOException {
        File folder = new File(rectangleFolder);
        if (!folder.exists()) {
            folder.mkdirs();
            //   Files.createDirectories(Paths.get("images", appUser.getId().toString()));
        }

        Files.write(Paths.get(imagePath), imageBytes);
    }
    public static HttpHeaders getHttpHeaders(byte[] imageBytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(imageBytes.length);
        return headers;
    }

    public static void checkNull(Object profileUser) {
        if (profileUser == null) {
            log.info("user not found");
        }
    }
}
