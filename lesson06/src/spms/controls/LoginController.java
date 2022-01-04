package spms.controls;

import spms.Controller;
import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.vo.Member;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class LoginController implements Controller, DataBinding {

  MemberDao memberDao;

  public LoginController setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }

  @Override
  public String execute(Map<String, Object> model) throws Exception {
    // 맵 객체에 member 가 존재하지 않을 시, 로그인 URL 반환
    if (model.get("member") == null) {
      return "/auth/LoginForm.jsp";
    }
    // 존재하면 맵 객체로부터 Dao, member, Session 객체를 가져와서 맴버가 존재하는지 확인 한 뒤, Session에 보관하고
    // 회원 목록으로 리다이렉트 하는 URL 을 반환하거나 로그인 실패 URL을 반환한다.
    else {
      Member member = (Member) model.get("member");
      Member existMember = memberDao.exist(member.getEmail(), member.getPassword());
      if (existMember != null) {
        ((HttpSession) model.get("session"))
                .setAttribute("member", existMember);
        return "redirect:/member/list.do";
      } else {
        return "/auth/LoginFail.jsp";
      }
    }
  }

  @Override
  public Object[] getDataBinders() {
    return new Object[]{
            "member", spms.vo.Member.class
    };
  }

}
