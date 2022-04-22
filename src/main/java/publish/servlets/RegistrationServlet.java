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
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RegistrationServlet.class);
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        LOG.info("Start user registration.");
        String goTo = "";
        String goToLetter = "";
        String result = "";
        LOG.info("Check the correctness of the entered data.");
        if (req.getParameter("password").equals(req.getParameter("re_password"))) {
                Account account = AccountServiceImpl.getAccount(req.getParameter("login"), req.getParameter("password"),
                        req.getParameter("email"), req.getParameter("firstName"),
                        req.getParameter("lastName"), 0.0, 2);
                try {
                    if (accountService.findByLogin(account.getLogin()) == null) {
                        LOG.info("Username is free.");
                        accountService.insertAccount(account);
                        LOG.info("User registered successfully!");
                        goTo = "\"/publish/\"";
                        goToLetter = "On the main page!";
                        result = "Registered successfully!";
                    } else {
                        LOG.warn("User has already existed with login, which person who registers choose.");
                        goTo = "\"/publish/registration.jsp\"";
                        goToLetter = "On the page registration!";
                        result = "User has already existed with those login!";
                    }
                } catch (DBException e) {
                    LOG.error(e.getMessage(), e);
                    req.setAttribute("message", e.getMessage());
                    req.setAttribute("code", e.getErrorCode());
                    getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
                }
        }
        else {
            result = "Password and confirm password not similar, check it again!";
            goTo = "\"/publish/registration.jsp\"";
            goToLetter = "On the page registration!";
            LOG.warn("Password and confirm password not similar");
        }

        out.println("<center>");
        out.println("<div style=\"position: absolute; " +
                "top: 50%; " +
                "left: 50%; " +
                "transform: translate(-50%, -50%);\">");
        out.println("<h1>" + result + "</h1>");
        out.println("<div style=\"margin-top: 40px;\"><a href= " + goTo  + "style=\"" +
                "text-align: center; " +
                "font-size: 18pt; " +
                "color: #F8F2CA; " +
                "border-radius: 4px; " +
                "background-color: #925C32; " +
                "padding: 20px 14px; " +
                "text-decoration: none;\">" + goToLetter + "</a></div>");
        out.println("</div>");
        out.println("</center>");
    }
}
