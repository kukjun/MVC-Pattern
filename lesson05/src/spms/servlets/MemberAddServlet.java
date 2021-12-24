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
import java.io.IOException;

@WebServlet("/member/add")

public class MemberAddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(
                "/member/MemberAdd.jsp"
        );

        rd.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = this.getServletContext();

        try {
            MemberDao dao = (MemberDao) sc.getAttribute("memberDao");

            Member member = new Member()
                    .setEmail(req.getParameter("email"))
                    .setPassword(req.getParameter("password"))
                    .setName(req.getParameter("name"));

            dao.insert(member);

            resp.sendRedirect("list");

        } catch (Exception e) {
            req.setAttribute("error", e);
            RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
            rd.forward(req, resp);

        }
    }
}
