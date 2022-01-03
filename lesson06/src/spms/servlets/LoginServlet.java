package spms.servlets;

import spms.dao.MemberDao;
import spms.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    // get 요청시 LoginForm 포워딩
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(
                "/auth/LoginForm.jsp"
        );
        rd.forward(req, resp);
    }

    // post 요청시 결과에 따라 Redirect or LoginFail 포워딩
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            ServletContext sc = this.getServletContext();
            MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
            Member member = memberDao.exist(req.getParameter("email"), req.getParameter("password"));
            if (member != null) {
                HttpSession session = req.getSession();
                session.setAttribute("member", member);
                req.setAttribute("viewUrl", "redirect:/member/list.do");
            } else {
                req.setAttribute("viewUrl", "auth/LoginFail.jsp");
            }
        } catch (Exception e) {
//            req.setAttribute("error", e);
//            e.printStackTrace();
//            RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
//            rd.forward(req, resp);
            throw new ServletException(e);
        }
    }
}