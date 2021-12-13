package spms.servlets;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SuppressWarnings("serial")
//@WebServlet(
//        urlPatterns = {"/member/update"},
//        initParams = {
//                @WebInitParam(name="driver", value="org.postgresql.Driver"),
//                @WebInitParam(name="url", value="jdbc:postgresql://localhost:5432/java_web_lesson04"),
//                @WebInitParam(name="username", value="web_lesson04"),
//                @WebInitParam(name="password", value="lesson04")
//        }
//)
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 요청 매개변수로 넘어온 회원 번호를 가지고 회원 정보를 질의
        String query = "select mno, email, mname, cre_date from members" +
                " where mno= " + req.getParameter("no");
        ServletContext sc = null;
        try {
            sc = this.getServletContext();
            Class.forName(sc.getInitParameter("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(
                // 초기화 매개변수를 이용하여 데이터베이스에 연결
                sc.getInitParameter("url"),
                sc.getInitParameter("username"),
                sc.getInitParameter("password"));
//                this.getInitParameter("url"),
//                this.getInitParameter("username"),
//                this.getInitParameter("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();

            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();

            // 회원 상세 정보 출력
            out.println("<html><head><title>회원정보</title></head>");
            out.println("<form action='update' method='post'>");
            out.println("번호: <input type='text' name='no' value='" + req.getParameter("no") + "'readonly><br>");
            out.println("이름: *<input type='text' name='name' value='" + resultSet.getString("mname") +
                    "'><br>");
            out.println("이메일: <input type='text' name='email' value='" + resultSet.getString("email") +
                    "'><br>");
            out.println("가입일: " + resultSet.getDate("cre_date") + "<br>");
            out.println("<input type='submit' value='저장'>");
            out.println("<input type='button' value='취소' onclick='location.href=\"list\"'>");
            out.println("</form>");
            out.println("</body></html>");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
        String query = "update members set email=?, mname=?, mod_date=now() where MNO=?";

        ServletContext sc = null;
        try {
            sc = this.getServletContext();
            Class.forName(sc.getInitParameter("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(
                sc.getInitParameter("url"),
                sc.getInitParameter("username"),
                sc.getInitParameter("password"));

//                this.getInitParameter("url"),
//                this.getInitParameter("username"),
//                this.getInitParameter("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, req.getParameter("email"));
            preparedStatement.setString(2, req.getParameter("name"));
            preparedStatement.setInt(3, Integer.parseInt(req.getParameter("no")));
            preparedStatement.executeUpdate();

            resp.sendRedirect("list");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
