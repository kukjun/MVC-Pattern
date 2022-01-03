package spms.dao;

import spms.vo.Member;

import javax.sql.DataSource;
import java.util.List;

public interface MemberDao {

  public void setDataSource(DataSource dataSource);

  public List<Member> selectList() throws Exception;

  public int insert(Member member) throws Exception;

  public int delete(int no) throws Exception;

  public Member selectOne(int no) throws Exception;

  public int update(Member member) throws Exception;

  public Member exist(String email, String password) throws Exception;

}
