package com.springservlet.basic.request;

import java.util.Arrays;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) {
		log.info("username => {}", req.getParameter("username"));
		log.info("age => {}", req.getParameter("age"));

		Arrays.stream(req.getParameterValues("username"))
			.forEach(s -> log.info("username = {}", s));
	}
}
