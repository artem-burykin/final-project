package publish.servlets.administration;

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
 * Admin servlet for unblocking user.
 * @author Burykin
 */
@WebServlet("/administration/unblockAccount")
public class UnblockAccountServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(UnblockAccountServlet.class);
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        String status = "";
        String color = "";
        LOG.info("Checking state of blocking of user.");
        try {
            if(accountService.checkingUserBlock(req.getParameter("login")) == 1) {
                LOG.info("User was blocked.");
                accountService.changingUserBlock(0, req.getParameter("login"));
                LOG.info("User is unblocked successfully!");
                status = "User is unblocked successfully!";
                color = "#0fdc70";
                req.getSession().setAttribute("accounts", accountService.findAllAccounts());
            }
            else {
                LOG.warn("Unblocking is impossible because user has already unblocked.");
                status = "Unblocking is impossible because user has already unblocked!";
                color = "#fb0349";
            }
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
        }
        req.getSession().setAttribute("admin_status", status);
        req.getSession().setAttribute("admin_color", color);
    }
}
