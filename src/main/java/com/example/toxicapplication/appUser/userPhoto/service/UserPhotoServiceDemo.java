package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
import com.example.toxicapplication.appUser.userDetails.repository.AppUserRepository;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepositoryDemo;

import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.io.*;

import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserPhotoServiceDemo {
    private final AppUserRepository appUserRepository;
    private final ProfileUserRepository profileUserRepository;
    private final UserPhotoRepositoryDemo userPhotoRepository;

    public void removePhoto(Long idPhoto) {
        UserPhotoEntityDemo userPhoto = userPhotoRepository.findById(idPhoto).get();
        userPhotoRepository.delete(userPhoto);
    }

    private byte[] compressImage(byte[] imageBytes) throws IOException {
        // Прочитайте изображение из байтового массива
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

        // Создайте буфер для сжатого изображения
        ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();

        // Получите объект ImageWriter для формата JPEG
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();

        // Настройте параметры сжатия
        writer.setOutput(ImageIO.createImageOutputStream(compressedStream));
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.7f); // Здесь можно настроить уровень сжатия (0.0f - максимальное сжатие, 1.0f - без сжатия)

        // Запишите сжатое изображение в выходной поток
        writer.write(null, new IIOImage(image, null, null), param);
        writer.dispose();

        // Получите сжатое изображение в виде байтового массива
        byte[] compressedImageBytes = compressedStream.toByteArray();
        compressedStream.close();

        return compressedImageBytes;
    }


    public void savePhoto(AppUser appUser, MultipartFile circle, MultipartFile rectangle) throws IOException {

        appUser = appUserRepository.findById(appUser.getId()).orElse(appUser);
        ProfileUserEntity profileUserEntity = profileUserRepository.findById(appUser.getId()).get();
        byte[] circleBytes = circle.getBytes();
        byte[] rectangleBytes = rectangle.getBytes();
        UserPhotoEntityDemo photo = new UserPhotoEntityDemo(appUser, profileUserEntity);
        // photo.setPhotoCircle(circleBytes);
        //  photo.setPhotoRectangle(rectangleBytes);
        photo.setPhotoCircle(compressImage(circleBytes));
        photo.setPhotoRectangle(compressImage(rectangleBytes));
        photo.setDateCreated(LocalDateTime.now());
        profileUserEntity.setRatingUser(0.0);

        Long maxId = profileUserRepository.getMaxId();
        profileUserEntity.setTopUser(maxId);

        userPhotoRepository.save(photo);

        List<Long> allId = profileUserEntity.getAllIdPhotoUser();
        allId.add(photo.getId());
        profileUserEntity.setAllIdPhotoUser(allId);
        userPhotoRepository.save(photo);
    }

    public UserPhotoEntityDemo getPhotoById(Long photoId) {
        return userPhotoRepository.findById(photoId).orElse(null);
    }
//    public HttpHeaders getCacheHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setCacheControl("public, max-age=3600"); // Кэширование на 1 час
//        return headers;
//    }
}
