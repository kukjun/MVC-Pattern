package spms.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(
        urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8")
        }
)
public class CharacterEncodingFilter implements Filter {
    FilterConfig config;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 서블릿이 실행되기 전에 해야 할 작업
        servletRequest.setCharacterEncoding(config.getInitParameter("encoding"));

        // 다읍 필터를 호출
        filterChain.doFilter(servletRequest, servletResponse);

        // 서블릿 실행 후 클라이언트에게 응답하기 전에 해야 할 작업
    }

    @Override
    public void destroy() {

    }
}
