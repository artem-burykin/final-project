package publish.filters;

import publish.db.dao.DBException;
import publish.db.entity.Category;
import publish.service.AccountService;
import publish.service.AccountServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/administration/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("login") != null;
        if (loggedIn && request.getSession().getAttribute("role").equals("admin")){
            filterChain.doFilter(servletRequest, servletResponse);
            response.sendRedirect(request.getContextPath() + "/admin.jsp");
        }
        else {
            System.out.println("Filter false");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
