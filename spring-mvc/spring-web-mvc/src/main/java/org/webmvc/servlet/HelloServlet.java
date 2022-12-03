package org.webmvc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

	@Override
	public void init() {
		System.out.println("HelloServlet.init");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("HelloServlet.doGet");
		PrintWriter writer = resp.getWriter();

		writer.println("<html>");
		writer.println("<head>");
		writer.println("<title>Hello Servlet</title>");
		writer.println("</head>");
		writer.println("<body>");
		writer.println("<h1>%s Bear</h1>".formatted(getName()));
		writer.println("</body>");
		writer.println("</html>");
	}

	private Object getName() {
		return getServletContext().getAttribute("name");
	}

	@Override
	public void destroy() {
		System.out.println("HelloServlet.destroy");
	}
}
