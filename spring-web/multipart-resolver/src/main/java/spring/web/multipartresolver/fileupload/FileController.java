package spring.web.multipartresolver.fileupload;

import java.util.List;

import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FileController {

  private final MultipartProperties multipartProperties;

  @GetMapping("/")
  public String homepage() {
    return "redirect:/files/new";
  }

  @GetMapping("/files/new")
  public String newFile() {
    log.info("multipartProperties.getEnabled(): {}", multipartProperties.getEnabled());
    log.info("multipartProperties.getMaxFileSize(): {}", multipartProperties.getMaxFileSize());
    log.info("multipartProperties.getMaxRequestSize(): {}", multipartProperties.getMaxRequestSize());
    log.info("multipartProperties.getFileSizeThreshold(): {}", multipartProperties.getFileSizeThreshold());

    return "upload-form";
  }

  @PostMapping("/files/upload")
  public String uploadFile(Model model, @RequestParam("files") List<MultipartFile> files) {
    logFiles(files);
    model.addAttribute("message", "received!");
    return "upload-form";
  }

  @PostMapping("/test/files/upload")
  @ResponseBody
  public String testUploadFile(@RequestParam("files") List<MultipartFile> files) {
    logFiles(files);
    return "received!";
  }

  private void logFiles(List<MultipartFile> files) {
    files.forEach(file -> {
      log.info("file: {}", file);
      log.info("file name: {}", file.getOriginalFilename());
      log.info("file content type: {}", file.getContentType());
      log.info("file size: {}", file.getSize());
    });
  }
}
