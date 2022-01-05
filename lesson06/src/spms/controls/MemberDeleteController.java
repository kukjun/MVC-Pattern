package spms.controls;

import spms.Controller;
import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.dao.PsqlMemberDao;
import spms.vo.Member;

import java.util.Map;
@Component("/member/delete.do")
public class MemberDeleteController implements Controller, DataBinding {

  MemberDao memberDao;

  public MemberDeleteController setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }

  @Override
  public String execute(Map<String, Object> model) throws Exception {
    Member member = (Member) model.get("member");

    // 맵 객체로부터 DAO 와 no 를 가져와서 DAO 의 메소드를 호출
    memberDao.delete(member.getNo());

    // 회원 목록으로 리다이렉트 URL 반환
    return "redirect:list.do";
  }

  @Override
  public Object[] getDataBinders() {
    return new Object[]{
            "member", spms.vo.Member.class
    };
  }
}
