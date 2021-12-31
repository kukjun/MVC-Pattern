package spms.controls;

import spms.Controller;
import spms.dao.MemberDao;

import java.util.Map;

public class MemberDeleteController implements Controller {
  @Override
  public String execute(Map<String, Object> model) throws Exception {

    // 맵 객체로부터 DAO 와 no 를 가져와서 DAO 의 메소드를 호출
    MemberDao memberDao = (MemberDao) model.get("memberDao");
    memberDao.delete(Integer.parseInt(String.valueOf(model.get("no"))));
    // 회원 목록으로 리다이렉트 URL 반환
    return "redirect:list.do";
  }
}
