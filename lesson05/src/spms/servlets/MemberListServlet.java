package spms.servlets;

import spms.dao.MemberDao;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// UI 출력 코드를 제거하고, UI 생성 및 출력을 JSP에게 위임한다.
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

    private ServletContext sc;

    @Override
    public void init() throws ServletException {
        super.init();
        sc = this.getServletContext();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Connection을 생성하는 부분과 MemberDao를 생성하는 부분을 제거

        try {
            MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");

            req.setAttribute("members", memberDao.selectList());
            resp.setContentType("text/html; charset=UTF-8");

            RequestDispatcher rd = req.getRequestDispatcher(
                    "/member/MemberList.jsp"
            );
            rd.include(req, resp);
        } catch (Exception e) {

            e.printStackTrace();
            req.setAttribute("error", e);
            RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
            rd.forward(req, resp);

        } finally {
            System.out.println("Hello World!!");

        }
    }
}
