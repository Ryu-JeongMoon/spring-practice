package spring.web.multipartresolver.fileupload;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class UploadControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void upload() throws Exception {
    File file = new File("src/main/resources/wheat.jpg");

    MockMultipartFile multipartFile = new MockMultipartFile(
      file.getName(),
      file.getName(),
      MediaType.IMAGE_JPEG_VALUE,
      new FileInputStream(file)
    );

    mockMvc.perform(
        multipart("/test/files/upload")
          .part(List.of(
            new MockPart("files", "file1", multipartFile.getBytes()),
            new MockPart("files", "file2", multipartFile.getBytes()),
            new MockPart("files", "file3", multipartFile.getBytes()),
            new MockPart("files", "file4", multipartFile.getBytes()),
            new MockPart("files", "file5", multipartFile.getBytes()),
            new MockPart("files", "file6", multipartFile.getBytes()),
            new MockPart("files", "file7", multipartFile.getBytes()),
            new MockPart("files", "file8", multipartFile.getBytes())
          ).toArray(new MockPart[0]))
      )
      .andExpect(status().isOk());
  }
}