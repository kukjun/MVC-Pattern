package spms.dao;

import spms.annotation.Component;
import spms.vo.Project;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component("memberDao")
public class PsqlProjectDao implements ProjectDao {
  private DataSource ds;

  public void setDataSource(DataSource ds) {
    this.ds = ds;
  }

  @Override
  public List<Project> selectList() throws Exception {
    String query = "SELECT pno, pname, sta_date, end_date, state" +
            " FROM projects" +
            " ORDER BY pno DESC";
    try (Connection conn = ds.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
      ArrayList<Project> projects = new ArrayList<>();

      while (rs.next()) {
        projects.add(new Project()
                .setNo(rs.getInt("pno"))
                .setTitle(rs.getString("pname"))
                .setStartDate(rs.getDate("sta_date"))
                .setEndDate(rs.getDate("end_date"))
                .setState(rs.getInt("state")));
      }
      return projects;
    }
  }

  @Override
  public int insert(Project project) throws Exception {
    String query = "INSERT INTO projects" +
            " (pname, content, sta_date, end_date, state, cre_date, tag)" +
            " VALUES (?, ?, ?, ?, 0, now(), ?";
    int success;
    try (Connection conn = ds.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, project.getTitle());
      ps.setString(2, project.getContent());
      ps.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
      ps.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
      ps.setString(5, project.getTags());
      success = ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }

    return success;
  }
}
