package com.springservlet.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springservlet.basic.HelloData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String messageBody = StreamUtils.copyToString(req.getInputStream(), StandardCharsets.UTF_8);

		// Data data = objectMapper.readValue(messageBody, Data.class);
		HelloData data = objectMapper.readValue(messageBody, HelloData.class);

		log.info("data = {}", data);
		resp.getWriter().write("ok~!");
	}
}
