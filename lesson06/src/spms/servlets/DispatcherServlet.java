package spms.servlets;

import spms.Controller;
import spms.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

// 프런트 컨트롤러도 서블릿이기 때문에 HttpServlet 을 상속받는다.
// /member/list.do, /member/add.do 와 같이 .do 로 끝나느 모든 URL 요청은 이 서블릿을 거친다.
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html; charset=UTF-8");
    String servletPath = req.getServletPath();

    try {
      ServletContext sc = this.getServletContext();

      HashMap<String, Object> model = new HashMap<>();
      // memberDao 객체는 더이상 Map 객체에 담을 필요가 없다.
//      model.put("memberDao", sc.getAttribute("memberDao"));

      // 이 보관소에서 페이지 컨트롤러를 꺼낼 때는 서블릿 URL 을 사용한다.
      Controller pageController = (Controller) sc.getAttribute(servletPath);

      // 이 보관소에서 페이지 컨트롤러가 사용할 데이터를 준비하는 부분을 제외하고는 모두 제거한다.
      if ("/member/list.do".equals(servletPath)) {
      } else if ("/member/add.do".equals(servletPath)) {
        if (req.getParameter("email") != null) {
          model.put("member", new Member()
                  .setEmail(req.getParameter("email"))
                  .setPassword(req.getParameter("password"))
                  .setName(req.getParameter("name")));
        }
      } else if ("/member/update.do".equals(servletPath)) {
        // "no" 정보를 맵 객체에 담는다.
        model.put("no", req.getParameter("no"));
        if (req.getParameter("email") != null) {
          // 맵 객체에 member 객체를 담는다.
          model.put("member", new Member()
                  .setNo(Integer.parseInt(req.getParameter("no")))
                  .setEmail(req.getParameter("email"))
                  .setName(req.getParameter("name")));
        }
      } else if ("/member/delete.do".equals(servletPath)) {
        // 맵 객체에 "no"정보를 담는다.
        model.put("no", req.getParameter("no"));
      } else if ("/auth/login.do".equals(servletPath)) {
        // 맵 객체에 member 객체 정보와 session 객체를 담는다.
        if (req.getParameter("email") != null) {
          model.put("member", new Member()
                  .setEmail(req.getParameter("email"))
                  .setPassword(req.getParameter("password")));
          model.put("session", req.getSession());
        }
      } else if ("/auth/logout.do".equals(servletPath)) {
        // 맵 객체에 session 객체를 담는다.
        model.put("session", req.getSession());
      }

      String viewUrl = pageController.execute(model);

      for (String key : model.keySet()) {
        req.setAttribute(key, model.get(key));
      }

      if (viewUrl.startsWith("redirect:")) {
        resp.sendRedirect(viewUrl.substring(9));
      } else {
        RequestDispatcher rd = req.getRequestDispatcher(viewUrl);
        rd.include(req, resp);
      }
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("error", e);
      RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
      rd.forward(req, resp);
    }
  }
}
