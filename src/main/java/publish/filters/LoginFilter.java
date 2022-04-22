package publish.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/user/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("login") != null;
        if (loggedIn){
            filterChain.doFilter(servletRequest, servletResponse);
            if (request.getRequestURI().equals("/publish/user/showProductWithSubscription") || request.getRequestURI().equals("/publish/user/topUpScore")) {
                response.sendRedirect(request.getContextPath() + "/profile.jsp");
                return;
            }
            else {
                response.sendRedirect(request.getContextPath());
            }
        }
        else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
