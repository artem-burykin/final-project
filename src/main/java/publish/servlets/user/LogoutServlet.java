package publish.servlets.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Servlet for user's logout.
 * @author Burykin
 */
@WebServlet("/user/logout")
public class LogoutServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Start log out.");
        HttpSession session = req.getSession();
        if(session != null) {
            session.setAttribute("login", null);
            session.setAttribute("role", null);
            LOG.info("User loges out successfully!");
        }
    }
}
