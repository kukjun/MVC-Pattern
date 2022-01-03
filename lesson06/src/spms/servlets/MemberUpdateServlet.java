package spms.servlets;

import spms.dao.MemberDao;
import spms.vo.Member;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")

@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            ServletContext sc = this.getServletContext();
            MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");

            // 프런트 컨트롤러가 저장한 VO 객체를 이용
            req.setAttribute("member", memberDao.selectOne(
                    Integer.parseInt(req.getParameter("no"))
            ));

            // 프런트 컨트롤러에게 뷰 정보를 전달
            req.setAttribute("viewUrl", "/member/MemberUpdate.jsp");

        } catch (Exception e) {
//            e.printStackTrace();
//            req.setAttribute("error", e);
//            RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
//            rd.forward(req, resp);
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = this.getServletContext();

        try {
            Member member = (Member) req.getAttribute("member");
            MemberDao MemberDao = (MemberDao) sc.getAttribute("memberDao");
            MemberDao.update(member);

            req.setAttribute("viewUrI", "redirect:list.do");
        } catch (Exception e) {
//            e.printStackTrace();
//            req.setAttribute("error", e);
//            RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
//            rd.forward(req, resp);
            throw new ServletException(e);
        }
    }
}
