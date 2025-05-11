package com.delivery.mydelivery.utility.password;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FIleUtil {
    public static String saveFileToDir(MultipartFile file, String folderPath, boolean addPrefix) throws IOException {
        try{
            File directory = new File(folderPath);
            if (!directory.exists())
                directory.mkdirs();
            String filename = (addPrefix ? generateUniqueFilename(file.getOriginalFilename()) : file.getOriginalFilename());
            String filePath = folderPath + filename;
            File destinationFile = new File(filePath);
            file.transferTo(destinationFile);
            return getStaticPath(folderPath) + filename;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
    public static Boolean deleteFileFromDir(String fileName, String folderPath) throws IOException {
        try{
            Path filePath = Paths.get(folderPath, fileName);
            return Files.deleteIfExists(filePath);
        } catch (Exception e) {
            //throw new RegularException("Error while deleting file", HttpStatus.INTERNAL_SERVER_ERROR.value());
            System.err.println("Error while deleting file: " + e.getMessage());
            throw new IOException("Error while deleting file: " + e.getMessage());
        }
    }
    private static String generateUniquePrefix() {
        long currentTimeMillis = System.currentTimeMillis();
        //int randomNumber = new Random().nextInt(1000);
        return String.valueOf(currentTimeMillis);
    }
    private static String generateUniqueFilename(String filename) {
        String prefix = generateUniquePrefix();
        String baseName = filename.substring(0, filename.lastIndexOf("."));
        int lastDotIndex = filename.lastIndexOf(".");

        if (lastDotIndex == -1) {
            return filename + "_" + prefix;
        }

        String fileExtension = filename.substring(filename.lastIndexOf("."));
        return baseName + "_" + prefix + fileExtension;
    }
    private static String getStaticPath(String absolutePath){
        String path = Paths.get(absolutePath).getFileName().toString();
        return (!path.endsWith("/")) ? path+"/" : path;
    }
    public static String extractFilename(String path){
        return path.replaceFirst(".*/", "");
    }
}
