package com.springanything.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CookieController {

	@GetMapping("/test-cookie")
	public String cookie(HttpServletResponse response) {
		Cookie cookie = new Cookie("panda", "bear");
		cookie.setSecure(true);
		response.addCookie(cookie);
		return cookie.getPath();
	}
}
