package spms.listeners;

import spms.dao.MemberDao;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;


@WebListener
public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext sc = sce.getServletContext();

            // 톰캣 서버에서 자원을 찾기 위해 InitialContext 객체를 생성
            InitialContext initialContext = new InitialContext();

            Class.forName("org.postgresql.Driver");
            // lookup() 메서드를 이용해, JNDI 이름으로 등록된 서버 자원을 가져옴
            DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/postgresql");

//      ds = new BasicDataSource();
//      ds.setDriverClassName(sc.getInitParameter("driver"));
//      ds.setUrl(sc.getInitParameter("url"));
//      ds.setUsername(sc.getInitParameter("username"));
//      ds.setPassword(sc.getInitParameter("password"));

            MemberDao memberDao = new MemberDao();
            memberDao.setDataSource(ds);

            sc.setAttribute("memberDao", memberDao);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // 톰캣 서버가 종료될 때, 서버 자원에 대해 close()가 호출되도록 설정해놨기 때문에 따로 자원
    // 해제를 해줄 필요가 없다.
    @Override
    public void contextDestroyed(ServletContextEvent sce) {}

}