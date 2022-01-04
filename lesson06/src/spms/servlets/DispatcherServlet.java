package spms.servlets;

import spms.Controller;
import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinder;
import spms.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

// 프런트 컨트롤러도 서블릿이기 때문에 HttpServlet 을 상속받는다.
// /member/list.do, /member/add.do 와 같이 .do 로 끝나는 모든 URL 요청은 이 서블릿을 거친다.
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html; charset=UTF-8");
    String servletPath = req.getServletPath();

    try {
      ServletContext sc = this.getServletContext();

      HashMap<String, Object> model = new HashMap<>();
      model.put("session", req.getSession());

      Controller pageController = (Controller) sc.getAttribute(servletPath);

      // VO 객체를 생성하고 Map 객체에 넣는 부분을 자동화
      if (pageController instanceof DataBinding) {
        prepareRequestsData(req, model, (DataBinding) pageController);
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

  private void prepareRequestsData(HttpServletRequest request,
                                   HashMap<String, Object> model, DataBinding dataBinding) throws Exception {
    // 페이지 컨트롤러에게 필요한 데이터가 무엇인지 가져온다.
    Object[] dataBinders = dataBinding.getDataBinders();

    // 배열에서 꺼낸 값을 보관할 임시 변수 준비
    String dataName;
    Class<?> dataType;
    Object dataObj;

    // 데이터 이름과 데이터 타입을 반복해서 꺼냄
    for (int i = 0; i < dataBinders.length; i += 2) {
      dataName = (String) dataBinders[i];
      dataType = (Class<?>) dataBinders[i + 1];
      // dataType, dataName 과 일치하는 요청 매개변수를 찾고 해당 클래스의 인스턴스를 생성한다.
      // 찾은 매개변수 값을 인스턴스에 저장하며 그 인스턴스를 반환한다.
      dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
      model.put(dataName, dataObj);

    }
  }

}
