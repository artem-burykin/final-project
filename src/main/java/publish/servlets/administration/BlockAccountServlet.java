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
 * Admin servlet for blocking user.
 * @author Burykin
 */
@WebServlet("/administration/blockAccount")
public class BlockAccountServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BlockAccountServlet.class);
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        String status = "";
        String status_uk = "";
        String color = "";
        LOG.info("Checking state of blocking of user.");
        try {
            if(accountService.checkingUserBlock(req.getParameter("login")) == 0) {
                LOG.info("User was unblocked.");
                accountService.changingUserBlock(1, req.getParameter("login"));
                LOG.info("User is blocked successfully!");
                status = "User is blocked successfully!";
                status_uk = "Користувача було заблоковано успішно!";
                color = "#0fdc70";
                req.getSession().setAttribute("accounts", accountService.findAllAccounts());
            }
            else {
                LOG.warn("Blocking is impossible because user has already blocked.");
                status = "Blocking is impossible because user has already blocked!";
                status_uk = "Блокування неможливо, тому що користувач вже заблокований!";
                color = "#fb0349";

            }
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            throw new ServletException(e.getMessage());
        }
        req.getSession().setAttribute("admin_status", status);
        req.getSession().setAttribute("admin_status_uk", status_uk);
        req.getSession().setAttribute("admin_color", color);
    }
}
