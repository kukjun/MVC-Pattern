package spms.listeners;

import spms.controls.*;
import spms.dao.MemberDao;
import spms.dao.PsqlMemberDao;

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
            DataSource ds = (DataSource)initialContext.lookup(
                    "java:comp/env/jdbc/postgresql");

            MemberDao memberDao = new PsqlMemberDao();
            memberDao.setDataSource(ds);
//            sc.setAttribute("memberDao", memberDao);

            // ServletContext 에 미리 MemberDao 가 주입된 컨트롤러를 저장해놓는다.
            // 생성된 페이지 컨트롤러를 ServletContext 에 저장하는데, 저장할 때 서블릿 요청 URL 을 키로 하여 저장한다.
            sc.setAttribute("/auth/login.do",
                    new LoginController().setMemberDao(memberDao));
            sc.setAttribute("/auth/logout.do",
                    new LogoutController());
            sc.setAttribute("/member/list.do",
                    new MemberListController().setMemberDao(memberDao));
            sc.setAttribute("/member/add.do",
                    new MemberAddController().setMemberDao(memberDao));
            sc.setAttribute("/member/update.do",
                    new MemberUpdateController().setMemberDao(memberDao));
            sc.setAttribute("/member/delete.do",
                    new MemberDeleteController().setMemberDao(memberDao));

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // 톰캣 서버가 종료될 때, 서버 자원에 대해 close()가 호출되도록 설정해놨기 때문에 따로 자원 해제를 해줄 필요가 없다.
    @Override
    public void contextDestroyed(ServletContextEvent sce) {}

}