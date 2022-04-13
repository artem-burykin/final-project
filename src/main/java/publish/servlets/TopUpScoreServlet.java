package publish.servlets;

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

@WebServlet("/topUpScore")
public class TopUpScoreServlet extends HttpServlet {
    private static final org.apache.logging.log4j.Logger LOG = org.apache.logging.log4j.LogManager.getLogger(BuyProductServlet.class);
    private AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        LOG.trace("Starting buy product:");
        PrintWriter out = resp.getWriter();
        double newScore = 0;
        try {
            newScore = accountService.findByLogin((String) LoginServlet.session.getAttribute("login")).getScore() + Double.parseDouble(req.getParameter("score"));
            accountService.updateScore(newScore, (String) LoginServlet.session.getAttribute("login"));
            LoginServlet.session.removeAttribute("score");
            LoginServlet.session.setAttribute("score", newScore);
            LOG.trace("Topping up was successfully.");
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
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}