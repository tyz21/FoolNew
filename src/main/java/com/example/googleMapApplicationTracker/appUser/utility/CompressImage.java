package com.example.googleMapApplicationTracker.appUser.utility;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CompressImage {
    public static byte[] compressImage(byte[] imageBytes) throws IOException {
        System.out.println("error 5");
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

        ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();

        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();

        writer.setOutput(ImageIO.createImageOutputStream(compressedStream));
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.9f);

        writer.write(null, new IIOImage(image, null, null), param);
        writer.dispose();

        byte[] compressedImageBytes = compressedStream.toByteArray();
        compressedStream.close();

        return compressedImageBytes;
    }
}
