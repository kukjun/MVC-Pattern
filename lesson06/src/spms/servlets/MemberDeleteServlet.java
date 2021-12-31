package spms.servlets;

import spms.dao.MemberDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            ServletContext sc = this.getServletContext();
            MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
            memberDao.delete(Integer.parseInt(req.getParameter("no")));
            req.setAttribute("viewUrl", "redirect:list.do");

        } catch (Exception e) {
//            req.setAttribute("error", e);
//            e.printStackTrace();
//            RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
//            rd.forward(req, resp);
            throw new ServletException(e);
        }
    }
}
