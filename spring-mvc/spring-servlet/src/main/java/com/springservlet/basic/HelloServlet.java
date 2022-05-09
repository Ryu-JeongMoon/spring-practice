package com.springservlet.basic;

import static java.nio.charset.StandardCharsets.*;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("helloServlet called");
		log.info("request = {}", request);
		log.info("response = {}", response);

		String name = request.getParameter("name");
		log.info("query-parameter = {}", name);

		response.setContentType(MediaType.TEXT_PLAIN_VALUE);
		response.setCharacterEncoding(UTF_8.name());
		response.getWriter().write("hello %s".formatted(request.getParameter("name")));
	}
}
