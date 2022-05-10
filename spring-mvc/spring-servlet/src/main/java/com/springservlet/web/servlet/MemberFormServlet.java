package com.springservlet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/members/form")
public class MemberFormServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html");
    resp.setCharacterEncoding("utf-8");

    PrintWriter w = resp.getWriter();
    w.write("""
      <!DOCTYPE html>
      <html>
      <head>
          <meta charset="UTF-8">
          <title>Title</title>
      </head>
      <body>
      <form action="/members/save" method="post">
          username: <input type="text" name="username" />
          age:      <input type="text" name="age" />
       <button type="submit">전송</button>
      </form>
      </body>
      </html>
      """);
  }
}
