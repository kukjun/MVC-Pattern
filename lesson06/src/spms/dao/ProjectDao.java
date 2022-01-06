package spms.dao;

import spms.vo.Project;

import java.util.List;

public interface ProjectDao {

  List<Project> selectList() throws Exception;

  int insert(Project project) throws Exception;

}
