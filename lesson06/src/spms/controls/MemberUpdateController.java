package spms.controls;

import spms.Controller;
import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.dao.PsqlMemberDao;
import spms.vo.Member;

import java.util.Map;

public class MemberUpdateController implements Controller, DataBinding {

  MemberDao memberDao;

  public MemberUpdateController setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }

  @Override
  public String execute(Map<String, Object> model) throws Exception {
    Member member;
    // model 에서 member 라는 키의 값이 없으면
    if (model.get("member") == null) {
      // model 로 부터 수정할 맴버 객체 번호를 가져와서 데이터베이스에 조회
      member = memberDao.selectOne(Integer.parseInt(String.valueOf(model.get("no"))));
      // Map 객체에 저장
      model.put("member", member);
      // 업데이트 URL 반환
      return "/member/MemberUpdate.jsp";
    } else {
      // member 객체를 가져와서 Dao 를 통해 업데이트 요청
      member = (Member) model.get("member");
      memberDao.update(member);

      // 회원 목록 URL 로 리다이렉트 요청 URL 반환
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