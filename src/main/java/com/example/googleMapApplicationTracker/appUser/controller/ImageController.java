package com.example.googleMapApplicationTracker.appUser.controller;

import com.example.googleMapApplicationTracker.appUser.entity.AppUser;
import com.example.googleMapApplicationTracker.appUser.repository.ImageRepository;
import com.example.googleMapApplicationTracker.appUser.service.ImageService;
import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@CrossOrigin(origins = "https://gamefool.gamefi.codes")
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageService imageService, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
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

    @PostMapping("/save")
    public ApiResponse<String> saveImage( @RequestBody String base64Image) throws JsonProcessingException {
      //  try {
        System.out.println("image :" + base64Image);
            if (base64Image == null || base64Image.isEmpty()) {
                return new ApiResponse<>("Base64 image data is missing or empty.", true);
            }
      //  System.out.println("userNew :" + appUser.getUsername() + appUser.getId());

            // Parse the base64Image as a JSON object
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(base64Image);

            // Extract the JSON result from the parsed object
            String jsonResult = jsonNode.toString();

            System.out.println("result :" + jsonResult);

            String trimmedBase64Data = jsonResult.substring(16, jsonResult.length() - 2);
            // You may want to log the received JSON result for debugging
            // logger.info("Received JSON result: {}", jsonResult);

            return imageService.saveImage( trimmedBase64Data);
     //   } catch (Exception e) {
            // Log the exception for debugging purposes
            // logger.error("Error saving image: {}", e.getMessage(), e);

          //  return new ApiResponse<>("Failed to save image.", true);
    //    }
    }
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
    @GetMapping("/{userId}")
    public ApiResponse<String> getImageByAppUserId(@PathVariable("userId") Long userId) {
        return imageService.getImageByAppUserId(userId);
    }
}
