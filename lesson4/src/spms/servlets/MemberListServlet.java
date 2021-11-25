package spms.servlets;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

// 서블릿을 만들고자 서블릿 어노테이션을 쓰고 GenericServlet 을 상속
@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {

    // 사용할 변수를 미리 정의
    String url = "jdbc:postgresql://localhost:5432/java_web_lesson04";
    String username = "web_lesson04";
    String password = "lesson04";

    // 클래스 사용시 가장 먼저 실행하는 부분, Driver 로드가 이루어짐
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL DataSource unable to load PostgreSQL JDBC Driver");
        }
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        String query = "select mno, mname, email, cre_date" +
                " from members" +
                " order by mno";

        // 데이터 베이스 연결 및 쿼리 실행 try, catch 문 사용
        // DB 연결, Connection 객체, PreparedStatement 사용
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet resultSet = pstmt.executeQuery()) {
            // 반환하는 HTML Code, Encoding Type 을 미리 정해줌.
            servletResponse.setContentType("text/html; charset=UTF-8");

            PrintWriter out = servletResponse.getWriter();
            out.println("<html><head><title>회원목록</title></head>");
            out.println("<body><h1>회원목록</h1>");
            out.println("<p><a href='add'>신규회원</a></p>");

            // select 결과 가져오기
            while (resultSet.next()) {
                out.println(
                        resultSet.getInt("mno") + " , " +
//                                "<a href='update?no=" + resultSet.getInt("mno") + "'>" +
                                resultSet.getString("mname") + " , " +
//                                "</a>, " +
                                resultSet.getString("email") + " , " +
                                resultSet.getDate("cre_date") + "<br>");
            }

            out.println("</body></html>");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
