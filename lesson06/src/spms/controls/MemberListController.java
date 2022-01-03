package spms.controls;

import spms.Controller;
import spms.dao.MemberDao;

import java.util.Map;

public class MemberListController implements Controller {

  MemberDao memberDao;

  public MemberListController setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }

  @Override
  public String execute(Map<String, Object> model) throws Exception {

    model.put("members", memberDao.selectList());
    return "/member/MemberList.jsp";
  }
}
