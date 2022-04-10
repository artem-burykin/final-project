package publish.servlets;

import publish.db.dao.DBException;
import publish.db.entity.Account;
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
 * Servlet for registration new user.
 * @author Burykin
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private static final org.apache.logging.log4j.Logger LOG = org.apache.logging.log4j.LogManager.getLogger(RegistrationServlet.class);
    private AccountService accountService = new AccountServiceImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        LOG.trace("Starting user registration:");
        String result = "";
        LOG.trace("Forming object of user.");
        System.out.println(req.getParameter("password") + " " + req.getParameter(" login") + " " + req.getParameter("firstName"));
        if (req.getParameter("password").equals(req.getParameter("re_password"))) {
                Account account = AccountServiceImpl.getAccount(req.getParameter("login"), req.getParameter("password"),
                        req.getParameter("email"), req.getParameter("firstName"),
                        req.getParameter("lastName"), 2);
                try {
                    if (accountService.findByLogin(account.getLogin()) == null) {
                        LOG.trace("Username is free.");
                        accountService.insertAccount(account);
                        LOG.trace("User registered successfully!");
                        result = "Registered successfully!";
                    } else {
                        LOG.trace("User has already existed with login, which person who registers choose.");
                        result = "User has already existed with those login!";
                    }
                } catch (DBException e) {
                    e.printStackTrace();

                }
        }
        else {
            result = "Password and confirm password not similar, check it again!";
        }

        System.out.println(result);
        out.println("<center>");
        out.println("<div style=\"position: absolute; " +
                "top: 50%; " +
                "left: 50%; " +
                "transform: translate(-50%, -50%);\">");
        out.println("<h1>" + result + "</h1>");
        out.println("<div style=\"margin-top: 40px;\"><a href=\"/publish/\" style=\"" +
                "text-align: center; " +
                "font-size: 18pt; " +
                "color: #F8F2CA; " +
                "border-radius: 4px; " +
                "background-color: #925C32; " +
                "padding: 20px 14px; " +
                "text-decoration: none;\">На страницу входа</a></div>");
        out.println("</div>");
        out.println("</center>");
    }
}
