package dev.mvc.memberprofile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    private String uploadDir = "C:\\kd\\deploy\\team6_v2sbm3c\\member\\storage";

    public void fileUpload(MultipartFile multipartFile) {
        Path serverPath = Paths.get(
                uploadDir +
                File.separator +
                StringUtils.cleanPath(multipartFile.getOriginalFilename()));

        try {
            Files.copy(multipartFile.getInputStream(), serverPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to store file: name={}, exception={}",
                      multipartFile.getOriginalFilename(),
                      e.getMessage());
        }
    }
}
