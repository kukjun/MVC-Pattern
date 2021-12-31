package spms.controls;

import spms.Controller;
import spms.dao.MemberDao;
import spms.vo.Member;

import java.util.Map;

public class MemberUpdateController implements Controller {
  @Override
  public String execute(Map<String, Object> model) throws Exception {
    MemberDao memberDao = (MemberDao) model.get("memberDao");
    Member member;
    if (model.get("member") == null) {
      // model 로 부터 수정할 맴버 객체 번호를 가져와서 데이터베이스에 조회
      member = memberDao.selectOne(Integer.parseInt(String.valueOf(model.get("no"))));
      // Map 객체에 저장
      model.put("member", member);
      // 업데이트 URL 반환
      return "/member/MemberUpdate.jsp";
    } else {
      // member 객체를 가져와서 Dao를 통해 업데이트 요청
      member = (Member) model.get("member");
      memberDao.update(member);

      // 회원 목록 URL 로 리다이렉트 요청 URL 반환
      return "redirect:list.do";
    }
  }
}