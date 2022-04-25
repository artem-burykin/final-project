package publish.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/setLocale")
public class SetLocaleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String localeName = req.getParameter("locale");
        String ref = req.getHeader("referer");
        if (localeName != null && !localeName.isEmpty()) {
            session.setAttribute("locale", localeName);
        }
        if (ref == null || ref.isEmpty()) {
            ref = "/publish/";
        }
        resp.sendRedirect(ref);
    }
}
