package spms.controls;

import spms.Controller;
import spms.dao.MemberDao;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class LogoutController implements Controller {

  @Override
  public String execute(Map<String, Object> model) throws Exception {
    // 맵 객체로부터 Session 객체를 가져와서 초기화 해준 뒤 로그인 화면으로 리다이렉트 하는 URL 반환
    HttpSession session = (HttpSession) model.get("session");
    session.invalidate();
    return "redirect:login.do";
  }
}
