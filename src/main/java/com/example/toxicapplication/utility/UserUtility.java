package com.example.toxicapplication.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@Slf4j
public class UserUtility {

    public static HttpHeaders getHttpHeaders(byte[] imageBytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(imageBytes.length);
        return headers;
    }

}
