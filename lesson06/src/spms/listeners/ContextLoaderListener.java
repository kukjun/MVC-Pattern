package spms.listeners;

import context.ApplicationContext;
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

    static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext sc = sce.getServletContext();

            String propertiesPath = sc.getRealPath(
                    sc.getInitParameter("contextConfigLocation"));
            applicationContext = new ApplicationContext(propertiesPath);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // 톰캣 서버가 종료될 때, 서버 자원에 대해 close()가 호출되도록 설정해놨기 때문에 따로 자원 해제를 해줄 필요가 없다.
    @Override
    public void contextDestroyed(ServletContextEvent sce) {}

}