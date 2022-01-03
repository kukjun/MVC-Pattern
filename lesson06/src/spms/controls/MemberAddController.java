package spms.controls;

import spms.Controller;
import spms.dao.MemberDao;
import spms.vo.Member;

import java.util.Map;

public class MemberAddController implements Controller {

  MemberDao memberDao;

  public MemberAddController setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }

  @Override
  public String execute(Map<String, Object> model) throws Exception {
    // 입력폼을 요청할 때
    if (model.get("member") == null) {
      return "/member/MemberAdd.jsp";
    }
    // 회원 등록을 요청할 때
    else {

      Member member = (Member) model.get("member");
      memberDao.insert(member);

      // 데이터 저장 후 회원 목록 페이지로 리다이렉트 할 수 있도록 반환 URL 앞에 "redirect:"를 붙인다.
      return "redirect:list.do";
    }
  }
}
