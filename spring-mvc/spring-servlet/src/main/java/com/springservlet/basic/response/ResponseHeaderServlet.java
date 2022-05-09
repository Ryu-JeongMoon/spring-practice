package com.springservlet.basic.response;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//[status-line]
		response.setStatus(HttpServletResponse.SC_OK); //200

		//[response-headers]
		response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
		response.setHeader(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.name());
		response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		response.setHeader(HttpHeaders.PRAGMA, "no-cache");
		response.setHeader("my-header", "hello");

		//[Header 편의 메서드]
		content(response);
		cookie(response);
		redirect(response);

		//[message body]
		PrintWriter writer = response.getWriter();
		writer.println("ok");
	}

	private void content(HttpServletResponse response) {
		response.setContentType(MediaType.TEXT_PLAIN_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
	}

	private void cookie(HttpServletResponse response) {
		//Set-Cookie: myCookie=good; Max-Age=600;
		//response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
		Cookie cookie = new Cookie("myCookie", "good");
		cookie.setMaxAge(600); //600초
		response.addCookie(cookie);
	}

	private void redirect(HttpServletResponse response) throws IOException {
		//Status Code 302
		//Location: /hello-form.html
		response.setStatus(HttpServletResponse.SC_FOUND);
		response.sendRedirect("/basic-form.html");
	}
}
