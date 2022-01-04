package spms.controls;

import spms.Controller;
import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.vo.Member;

import java.util.Map;

public class MemberAddController implements Controller , DataBinding {

  MemberDao memberDao;

  public MemberAddController setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }

  @Override
  public String execute(Map<String, Object> model) throws Exception {

    // DataBinding 발생으로 인해 member 객체가 생성되므로, member 객체 유무로 구분하면 안된다.
    Member member = (Member) model.get("member");

    if (member.getEmail() == null) {
      return "/member/MemberAdd.jsp";
    }
    // 회원 등록을 요청할 때
    else {
      memberDao.insert(member);

      return "redirect:list.do";
    }
  }

  @Override
  public Object[] getDataBinders() {
    return new Object[]{
            "member", spms.vo.Member.class
    };
  }
}
