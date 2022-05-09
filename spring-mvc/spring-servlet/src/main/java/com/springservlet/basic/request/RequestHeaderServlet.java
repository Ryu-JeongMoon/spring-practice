package com.springservlet.basic.request;

import java.util.Arrays;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) {
		printStartLine(req);
		printHeaders(req);
		printHeaderUtils(req);
		printEtc(req);
	}

	private void printStartLine(HttpServletRequest request) {
		log.info("--- REQUEST-LINE - start ---");
		log.info("request.getMethod() = {}", request.getMethod());
		log.info("request.getProtocol() = {}", request.getProtocol());
		log.info("request.getScheme() = {}", request.getScheme());

		// http://localhost:8080/request-header
		log.info("request.getRequestURL() = {}", request.getRequestURL());
		log.info("request.getRequestURI() = {}", request.getRequestURI());
		log.info("request.getQueryString() = {}", request.getQueryString());
		log.info("request.isSecure() = {}", request.isSecure());
		log.info("--- REQUEST-LINE - end ---\n");
	}

	private void printHeaders(HttpServletRequest request) {
		log.info("--- Headers - start ---");
		request.getHeaderNames()
			.asIterator()
			.forEachRemaining(headerName -> log.info("{}: {}", headerName, request.getHeader(headerName)));
		log.info("--- Headers - end ---\n");
	}

	private void printHeaderUtils(HttpServletRequest request) {
		log.info("--- Header 편의 조회 start ---");
		log.info("[Host 편의 조회]");
		log.info("request.getServerName() = {}", request.getServerName());
		log.info("request.getServerPort() = {}\n", request.getServerPort());

		log.info("[Accept-Language 편의 조회]");
		request.getLocales().asIterator()
			.forEachRemaining(locale -> log.info("locale = {}", locale));
		log.info("request.getLocale() = {}\n", request.getLocale());

		log.info("[cookie 편의 조회]");
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			Arrays.stream(cookies)
				.forEach(cookie -> log.info("{} : {}\n", cookie.getName(), cookie.getValue()));

		log.info("[Content 편의 조회]");
		log.info("request.getContentType() = {}", request.getContentType());
		log.info("request.getContentLength() = {}", request.getContentLength());
		log.info("request.getCharacterEncoding() = {}", request.getCharacterEncoding());
		log.info("--- Header 편의 조회 end ---\n");
	}

	private void printEtc(HttpServletRequest request) {
		log.info("--- 기타 조회 start ---");
		log.info("[Remote 정보]");
		log.info("request.getRemoteHost() = {}", request.getRemoteHost());
		log.info("request.getRemoteAddr() = {}", request.getRemoteAddr());
		log.info("request.getRemotePort() = {}\n", request.getRemotePort());

		log.info("[Local 정보]");
		log.info("request.getLocalName() = {}", request.getLocalName());
		log.info("request.getLocalAddr() = {}", request.getLocalAddr());
		log.info("request.getLocalPort() = {}", request.getLocalPort());
		log.info("--- 기타 조회 end ---\n");
	}
}
