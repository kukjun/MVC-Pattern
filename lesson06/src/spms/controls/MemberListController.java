package spms.controls;

import spms.Controller;
import spms.dao.MemberDao;
import spms.vo.Member;

import java.util.Map;

public class MemberListController implements Controller {

  @Override
  public String execute(Map<String, Object> model) throws Exception {
    MemberDao memberDao = (MemberDao) model.get("memberDao");
    model.put("members", memberDao.selectList());
    return "/member/MemberList.jsp";
  }
}
