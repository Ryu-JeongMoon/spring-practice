package com.springservlet.web.servlet;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/members/list")
public class MemberListServlet extends HttpServlet {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html");
    resp.setCharacterEncoding("utf-8");
    List<Member> members = memberRepository.findAll();

    PrintWriter w = resp.getWriter();
    w.write("""
      <html>
        <head>
          <meta charset="UTF-8">
          <title>Title</title>
        </head>
        <body>
          <a href="/index.html">메인</a>
          <table>
            <thead>
              <th>id</th>
              <th>username</th>
              <th>age</th>
            </thead>
          <tbody>
        """
    );

    for (Member member : members) {
      w.write("        <tr>");
      w.write("        <td>" + member.getId() + "</td>");
      w.write("        <td>" + member.getUsername() + "</td>");
      w.write("        <td>" + member.getAge() + "</td>");
      w.write("        </tr>");
    }

    w.write("      </tbody>");
    w.write("    </table>");
    w.write("  </body>");
    w.write("</html>");
  }
}