package com.springspartans.shopkart.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUploadValidator {

	private static final long MAX_FILE_SIZE = 200 * 1024;
	
    private static final int MAX_WIDTH = 500;
	private static final int MAX_HEIGHT = 500;
	
	private static final String[] ALLOWED_FILES = {"image/jpg", "image/jpeg", "image/png"};
	
	/**
     * Validates the uploaded image file.
     *
     * @param file the image file to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            return false;
        }
        if (!isValidImageType(file.getContentType())) {
            return false;
        }
        try {
            BufferedImage img = ImageIO.read(file.getInputStream());
            if (img == null || img.getWidth() > MAX_WIDTH || img.getHeight() > MAX_HEIGHT) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the content type is a valid image type.
     *
     * @param contentType the content type of the file
     * @return true if valid, false otherwise
     */
    private boolean isValidImageType(String contentType) {
        for (String allowedType : ALLOWED_FILES) {
            if (allowedType.equals(contentType)) {
                return true;
            }
        }
        return false;
    }
    
}
