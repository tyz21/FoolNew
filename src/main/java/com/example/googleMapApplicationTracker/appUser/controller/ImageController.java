package com.example.googleMapApplicationTracker.appUser.controller;

import com.example.googleMapApplicationTracker.appUser.entity.AppUser;
import com.example.googleMapApplicationTracker.appUser.entity.Image;
import com.example.googleMapApplicationTracker.appUser.repository.AppUserRepository;
import com.example.googleMapApplicationTracker.appUser.repository.ImageRepository;
import com.example.googleMapApplicationTracker.appUser.service.ImageService;
import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@RestController
@CrossOrigin(origins = "https://gamefool.gamefi.codes")
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final AppUserRepository appUserRepository;
    private static final String SAVE_DIR = "/";

    @Autowired
    public ImageController(ImageService imageService, ImageRepository imageRepository, AppUserRepository appUserRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
        this.appUserRepository = appUserRepository;
    }

    @DeleteMapping("/delete/{idPhoto}")
    public void deletePhoto(@PathVariable Long idPhoto) {
        imageService.removePhoto(idPhoto);
    }

    //    @PostMapping("/save")
//    public ApiResponse<String> saveImage(@AuthenticationPrincipal AppUser appUser,
//                                         @RequestBody Map<String, String> requestMap) {
//
//        String base64Image = requestMap.get("base64Image");
//        return imageService.saveImage(appUser, base64Image);
//    }
//    @PostMapping("/save")
//    public ApiResponse<String> saveImage(@AuthenticationPrincipal AppUser appUser,
//                                         @RequestBody String base64Image) {
//        try {
//            if (base64Image == null || base64Image.isEmpty()) {
//                return new ApiResponse<>("Base64 image data is missing or empty.", true);
//            }
//
//            // You may want to log the received Base64 image data for debugging
//            // logger.info("Received Base64 image data: {}", base64Image);
//
//            return imageService.saveImage(appUser, base64Image);
//        } catch (Exception e) {
//            // Log the exception for debugging purposes
//            // logger.error("Error saving image: {}", e.getMessage(), e);
//
//            return new ApiResponse<>("Failed to save image.", true);
//        }
//    }

//    @PostMapping("/save")
//    public ApiResponse<String> saveImage( @RequestBody String base64Image) throws JsonProcessingException {
//      //  try {
//        System.out.println("image :" + base64Image);
//            if (base64Image == null || base64Image.isEmpty()) {
//                return new ApiResponse<>("Base64 image data is missing or empty.", true);
//            }
//      //  System.out.println("userNew :" + appUser.getUsername() + appUser.getId());
//
//            // Parse the base64Image as a JSON object
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(base64Image);
//
//            // Extract the JSON result from the parsed object
//            String jsonResult = jsonNode.toString();
//
//            System.out.println("result :" + jsonResult);
//
//            String trimmedBase64Data = jsonResult.substring(16, jsonResult.length() - 2);
//            // You may want to log the received JSON result for debugging
//            // logger.info("Received JSON result: {}", jsonResult);
//
//            return imageService.saveImage( trimmedBase64Data);
//     //   } catch (Exception e) {
//            // Log the exception for debugging purposes
//            // logger.error("Error saving image: {}", e.getMessage(), e);
//
//          //  return new ApiResponse<>("Failed to save image.", true);
//    //    }
//    }

    @ResponseBody
    @RequestMapping(value = "/save", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST)
    public String upload(
            @RequestParam(value = "file", required = true) MultipartFile file)
//@RequestParam ()CommonsMultipartFile[] fileUpload
    {

        System.out.println(file);
        Image newImage = new Image();
        try {
            newImage.setImage(file.getBytes());
            imageRepository.save(newImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // @RequestMapping(value="/newDocument", , method = RequestMethod.POST)
        if (!file.isEmpty()) {
            try {
                byte[] fileContent = file.getBytes();
                System.out.println("fileContent" + fileContent);
                //fileSystemHandler.create(123, fileContent);
                return "You successfully uploaded !";
            } catch (Exception e) {
                return "You failed to upload   => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + " because the file was empty.";
        }
    }

    //    @PostMapping("/image/save")
//    public String uploadImage(@RequestParam("file") MultipartFile file) {
//
//        if (file.isEmpty()) {
//            return "Файл не передан";
//        }
//        try {
//            // Получение имени файла
//            String fileName = file.getOriginalFilename();
//            System.out.println("fileNAme work" + fileName);
//
//            // Сохранение файла на сервере
//           // file.transferTo(new java.io.File(SAVE_DIR + fileName));
//
//            return "Файл успешно загружен: " + fileName;
//        } catch (Exception e) {
//            return "Ошибка загрузки: " + e.getMessage();
//        }
//
//}
    private String addPadding(String base64Image) {
        int padding = 4 - base64Image.length() % 4;
        return base64Image + "=".repeat(padding);
    }

    //     @PostMapping("/save")
//    public ApiResponse<String> saveImage(@AuthenticationPrincipal AppUser appUser,
//                                         @RequestBody MultipartFile image) {
//
//        try {
//            System.out.println("user id" + appUser.getId());
//            System.out.println("image" + image);
//        } catch (Exception e) {
//            System.out.println("exception" + e);
//        }
//         System.out.println("error 1");
//        return imageService.saveImage(appUser, image);
//    }
//    @RequestMapping(value = "/save", method = RequestMethod.OPTIONS)
//    public ResponseEntity<?> handleOptionsRequest() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Access-Control-Allow-Origin", "https://gamefool.gamefi.codes");
//        headers.add("Access-Control-Allow-Methods", "POST");
//        headers.add("Access-Control-Allow-Headers", "Content-Type");
//
//        return ResponseEntity.ok().headers(headers).build();
//    }
    @Transactional(readOnly = true)
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> getImageByAppUserId(@RequestParam(value = "id", required = true)  Long id) {
        return imageService.getImageByAppUserId(id);
    }
}
