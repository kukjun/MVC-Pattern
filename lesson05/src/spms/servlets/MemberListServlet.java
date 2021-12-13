package spms.servlets;

import spms.vo.Member;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

// UI 출력 코드를 제거하고, UI 생성 및 출력을 JSP에게 위임한다.
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = "select mno, mname, email, cre_date" +
                " from members" +
                " order by mno ASC";

        // 데이터 베이스 연결 및 쿼리 실행 try, catch 문 사용
        // DB 연결, Connection 객체, PreparedStatement 사용
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet resultSet = pstmt.executeQuery()) {
            // 반환하는 HTML Code, Encoding Type 을 미리 정해줌.
            resp.setContentType("text/html; charset=UTF-8");

            ArrayList<Member> members = new ArrayList<>();

            while (resultSet.next()) {
                members.add(new Member()
                        .setNo(resultSet.getInt("mno"))
                        .setName(resultSet.getString("mname"))
                        .setEmail(resultSet.getString("email"))
                        .setCreateDate(resultSet.getDate("cre_date")));
            }

            req.setAttribute("members", members);

            RequestDispatcher rd = req.getRequestDispatcher(
                    "/member/memberList.jsp"
            );
            rd.include(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
