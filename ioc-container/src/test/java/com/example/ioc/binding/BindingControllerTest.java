package com.example.ioc.binding;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
    BindingController.class, BindingObjectFormatter.class
})
@AutoConfigureMockMvc
class BindingControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("")
  void getBindingObject() throws Exception {
    mockMvc.perform(get("/bind/1"))
        .andExpect(status().isOk())
        .andExpect(content().string("1"));
  }
}