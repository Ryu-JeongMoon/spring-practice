package com.springanything.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CookieController {

	@GetMapping("/test-cookie")
	public String cookie(HttpServletResponse response) {
		Cookie cookie = new Cookie("panda", "bear");
		cookie.setSecure(true);
		response.addCookie(cookie);
		return cookie.getPath();
	}

	@GetMapping("/test-cookies")
	public String cookies(HttpServletResponse response) {
		Cookie cookie1 = new Cookie("panda1", "bear1");
		Cookie cookie2 = new Cookie("panda2", "bear2");

		response.addCookie(cookie1);
		response.addCookie(cookie2);
		return cookie1.getPath();
	}
}
