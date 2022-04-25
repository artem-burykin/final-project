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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for log in account in website.
 * @author Burykin
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LoginServlet.class);
    final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        LOG.info("Start log in.");
        String status = "";
        String goTo = "";
        String goToLetter = "";
        HttpSession session = null;
        boolean b = false;
        try {
            b = accountService.findByLoginAndPassword(req.getParameter("login"), req.getParameter("password"));
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
        }
        try {
            if(b & accountService.isAdmin(req.getParameter("login"))){
               session = req.getSession(true);
               session.setAttribute("login", req.getParameter("login"));
               session.setAttribute("role", "admin");
               LOG.info("Admin loges in successfully!");
               resp.sendRedirect(req.getContextPath() + "/administration/showProductsAndCategories");
               return;
            }
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
        }
        int block = 0;
        try {
            block = accountService.checkingUserBlock(req.getParameter("login"));
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
        }
        if(b & block == 0) {
            LOG.info("User isn't blocked and registered.");
            session = req.getSession(true);
            session.setMaxInactiveInterval(-1);
            LOG.info("Got object of session.");
            String login = req.getParameter("login");
            double score = 0;
            try {
                score = accountService.findByLogin(login).getScore();
                LOG.info("Got user score.");
            } catch (DBException e) {
                LOG.error(e.getMessage(), e);
                req.setAttribute("message", e.getMessage());
                req.setAttribute("code", e.getErrorCode());
                getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
            }
            session.setAttribute("login", login);
            session.setAttribute("role", "user");
            session.setAttribute("score", score);
            LOG.info("User loges in successfully!");
            resp.sendRedirect(req.getContextPath());
        } else {
            Account account = null;
            try {
                account = accountService.findByLogin(req.getParameter("login"));
            } catch (DBException e) {
                LOG.error(e.getMessage(), e);
                req.setAttribute("message", e.getMessage());
                req.setAttribute("code", e.getErrorCode());
                getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
            }
            if (account == null) {
                LOG.warn("User isn't registered.");
                status = "User with those login not found! You can register with this login.";
                goTo = "\"/publish/registration.jsp\"";
                goToLetter = "On the page registration!";
            } else {
                if (block == 1) {
                    LOG.warn("User is blocked.");
                    status = "User is blocked! Please, register again.";
                    goTo = "\"/publish/registration.jsp\"";
                    goToLetter = "On the page registration!";
                }
                else {
                    LOG.warn("Password was incorrect.");
                    status = "Password was incorrect";
                    goTo = "\"/publish/login.jsp\"";
                    goToLetter = "On the login page!";
                }
            }
            
            out.println("<center>");
            out.println("<div style=\"position: absolute; " +
                    "top: 50%; " +
                    "left: 50%; " +
                    "transform: translate(-50%, -50%);\">");
            out.println("<h1>" + status + "</h1>");
            out.println("<div style=\"margin-top: 40px;\"><a href=" + goTo + " style=\"" +
                    "text-align: center; " +
                    "font-size: 18pt; " +
                    "color: #F8F2CA; " +
                    "border-radius: 4px; " +
                    "background-color: #925C32; " +
                    "padding: 20px 14px; " +
                    "text-decoration: none;\"> " + goToLetter +  " </a></div>");
            out.println("</div>");
            out.println("</center>");
        }
    }
}
