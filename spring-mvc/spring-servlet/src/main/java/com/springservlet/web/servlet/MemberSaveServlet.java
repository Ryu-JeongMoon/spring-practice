package com.springservlet.web.servlet;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/members/save")
public class MemberSaveServlet extends HttpServlet {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String username = req.getParameter("username");
    int age = Integer.parseInt(req.getParameter("age"));

    Member member = Member.builder()
      .username(username)
      .age(age)
      .build();

    memberRepository.save(member);
    resp.setContentType("text/html");
    resp.setCharacterEncoding("utf-8");
    PrintWriter w = resp.getWriter();
    w.write(
      """
        <html>
        <head>
         <meta charset="UTF-8">
        </head>
        <body>
        성공
        <ul>
            <li>id=%s</li>
            <li>username=%s</li>
         <li>age=%s</li>
        </ul>
        <a href="/index.html">메인</a>
        </body>
        </html>
        """.formatted(member.getId(), member.getUsername(), member.getAge()));
  }
}
