package publish.servlets.user;

import publish.db.dao.DBException;
import publish.service.AccountService;
import publish.service.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for topping up user's score.
 * @author Burykin
 */
@WebServlet("/user/topUpScore")
public class TopUpScoreServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TopUpScoreServlet.class);
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        LOG.info("Start top up user's score.");
        PrintWriter out = resp.getWriter();
        double newScore = 0;
        try {
            LOG.info("Receiving a new user's score.");
            newScore = accountService.findByLogin((String) req.getSession().getAttribute("login")).getScore() + Double.parseDouble(req.getParameter("score"));
            accountService.updateScore(newScore, (String) req.getSession().getAttribute("login"));
            LOG.info("User's score was updated into db.");
            req.getSession().removeAttribute("score");
            req.getSession().setAttribute("score", newScore);
            LOG.info("Topping up was successfully.");
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            throw new ServletException(e.getMessage());
        }
        
        out.println("<center>");
        out.println("<div style=\"position: absolute; " +
                "top: 50%; " +
                "left: 50%; " +
                "transform: translate(-50%, -50%);\">");
        out.println("<h1>Topping up was successfully! You can check your score in your profile. Thank you!</h1>");
        out.println("<div style=\"margin-top: 40px;\"><a href=\"/publish/showProductWithSubscription\" style=\"" +
                "text-align: center; " +
                "font-size: 18pt; " +
                "color: #F8F2CA; " +
                "border-radius: 4px; " +
                "background-color: #925C32; " +
                "padding: 20px 14px; " +
                "text-decoration: none;\">On the profile page</a></div>");
        out.println("</div>");
        out.println("</center>");
    }
}
