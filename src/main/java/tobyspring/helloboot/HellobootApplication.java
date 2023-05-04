package tobyspring.helloboot;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {

	public static void main(String[] args) {
		// ServletWebServerFactory로 사용해도 됨.
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		// WebServer 라는 타입으로 받는데 스프링부트가 톰캣 이외에 다른 서블릿 컨테이너도 지원할 수 있도록 하기 위해 추상화를 해놨음.
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			servletContext.addServlet("hello", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					String name = req.getParameter("name");

					/**
					 * 응답부분
					 * */
					//resp.setStatus(200);
					resp.setStatus(HttpStatus.OK.value());
					// 오타의 위험성이 있음.. 스프링에서 미리 등록해놓은 enum들이 있음
					//resp.setHeader("Content-Type", "text/plain");
					resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
					resp.getWriter().println("Hello " + name);
				}
			}).addMapping("/hello");
		});
		webServer.start();

	}
}
