package dev.mvc.memberprofile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FileCont {
  private final FileService fileService;
  
  @GetMapping("success")
  public String success() {
    return "success";
  }
  
  
  @PostMapping("/api/vi/file")
  public String uploadFiel(@RequestParam("file") MultipartFile file) {
    fileService.fileUpload(file);
    return "redirect:/success";
  }
}
