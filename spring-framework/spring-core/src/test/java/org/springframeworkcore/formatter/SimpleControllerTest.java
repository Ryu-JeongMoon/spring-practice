package org.springframeworkcore.formatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SimpleController.class)
@Import(PersonFormatter.class)
class SimpleControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  Marshaller marshaller;

  @Test
  @DisplayName("RequestMappingHandlerMapping 오께이")
  void hello() throws Exception {
    mockMvc.perform(get("/hello/panda"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("hello panda"));
  }

  @Test
  @DisplayName("정적 자원 오께이")
  void helloStatic() throws Exception {
    mockMvc.perform(get("/index.html"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.containsString("Hello World")));
  }

  @Test
  @DisplayName("정적 커스텀 자원 오께이")
  void helloMobileStatic() throws Exception {
    mockMvc.perform(get("/mobile/index.html"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(header().exists(HttpHeaders.CACHE_CONTROL))
        .andExpect(content().string(Matchers.containsString("Hello Mobile")));
  }

  @Test
  @DisplayName("RequestBody 오께이")
  void message() throws Exception {
    mockMvc.perform(get("/message")
            .content("hello"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("hello"));
  }

  @Test
  @DisplayName("json 오께이")
  void json() throws Exception {
    Person person = new Person(1L, "panda");

    String jsonValue = objectMapper.writeValueAsString(person);
    mockMvc.perform(get("/json")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(jsonValue))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(jsonValue))
        .andExpect(jsonPath("$.name").value("panda"));
  }

  /* xml <-> Object 매핑하기 위해서는 Setter 열려있어야 함 */
  @Test
  @DisplayName("xml 오께이")
  void xml() throws Exception {
    Person person = new Person(1L, "panda");

    StringWriter stringWriter = new StringWriter();
    Result result = new StreamResult(stringWriter);
    marshaller.marshal(person, result);
    String xmlValue = stringWriter.toString();

    mockMvc.perform(get("/json")
            .contentType(MediaType.APPLICATION_XML)
            .accept(MediaType.APPLICATION_XML)
            .content(xmlValue))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(xpath("person/name").string("panda"))
        .andExpect(xpath("person/id").string("1"));
  }
}