package publish.servlets.administration;

import publish.db.dao.DBException;
import publish.service.AccountService;
import publish.service.AccountServiceImpl;
import publish.servlets.BuyProductServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/unblockAccount")
public class UnblockAccountServlet extends HttpServlet {
    private static final org.apache.logging.log4j.Logger LOG = org.apache.logging.log4j.LogManager.getLogger(UnblockAccountServlet.class);
    private AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String blockingUser = "";
        LOG.trace("Checking state of blocking of user.");
        try {
            if(accountService.checkingUserBlock(req.getParameter("login")) == 1) {
                LOG.trace("User was blocked.");
                accountService.changingUserBlock(0, req.getParameter("login"));
                LOG.trace("User is unblocked successfully!");
                blockingUser = "User is unblocked successfully!";
            }
            else {
                LOG.trace("Unblocking is impossible because user has already unblocked.");
            blockingUser = "Unblocking is impossible because user has already unblocked!";
            }
        } catch (DBException e) {
            e.printStackTrace();
        }

        out.println("<center>");
        out.println("<div style=\"position: absolute; " +
                "top: 50%; " +
                "left: 50%; " +
                "transform: translate(-50%, -50%);\">");
        out.println("<h1>" + blockingUser + "</h1>");
        out.println("<div style=\"margin-top: 40px;\"><a href=\"/publish/showProductsAndCategories\" style=\"" +
                "text-align: center; " +
                "font-size: 18pt; " +
                "color: #F8F2CA; " +
                "border-radius: 4px; " +
                "background-color: #925C32; " +
                "padding: 20px 14px; " +
                "text-decoration: none;\">On the admin page</a></div>");
        out.println("</div>");
        out.println("</center>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
