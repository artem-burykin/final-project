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

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private static final org.apache.logging.log4j.Logger LOG = org.apache.logging.log4j.LogManager.getLogger(LoginServlet.class);
    static HttpSession session = null;
    AccountService accountService = new AccountServiceImpl();

    /**
     * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        LOG.trace("Starting log in:");
        PrintWriter out = resp.getWriter();
        LOG.trace("Starting login:");
        boolean b = false;
        try {
            b = accountService.findByLoginAndPassword(req.getParameter("login"), req.getParameter("password"));
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
            getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
        }
        try {
            if(b & accountService.isAdmin(req.getParameter("login"))){
               session = req.getSession(true);
               session.setMaxInactiveInterval(-1);
               session.setAttribute("login", req.getParameter("login"));
               LOG.trace("Admin loges in successfully!");
               resp.sendRedirect("/publish/showProductsAndCategories");
               return;
            }
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        int block = 0;
        try {
            block = accountService.checkingUserBlock(req.getParameter("login"));
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
        }
        if(b & block == 0) {
            LOG.trace("User isn't blocked and registered.");
            session = req.getSession(true);
            session.setMaxInactiveInterval(-1);
            LOG.trace("Getting object of session.");
            String login = req.getParameter("login");
            double score = 0;
            try {
                score = accountService.findByLogin(login).getScore();
            } catch (DBException e) {
                e.printStackTrace();
                req.setAttribute("message", e.getMessage());
                getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
            }
            session.setAttribute("login", login);
            session.setAttribute("score", score);
            LOG.trace("User loges in successfully!");
            resp.sendRedirect("/publish/");
        } else {
            Account account = null;
            try {
                account = accountService.findByLogin(req.getParameter("login"));
            } catch (DBException e) {
                e.printStackTrace();
            }
            if (account == null) {
                LOG.trace("User isn't registered.");
                out.println("<center>");
                out.println("<div style=\"position: absolute; " +
                        "top: 50%; " +
                        "left: 50%; " +
                        "transform: translate(-50%, -50%);\">");
                out.println("<h1>User with those login not found!</h1>");
                out.println("<div style=\"margin-top: 40px;\"><a href=\"registration.jsp\" style=\"" +
                        "text-align: center; " +
                        "font-size: 18pt; " +
                        "color: #F8F2CA; " +
                        "border-radius: 4px; " +
                        "background-color: #925C32; " +
                        "padding: 20px 14px; " +
                        "text-decoration: none;\">On the page registration</a></div>");
                out.println("</div>");
                out.println("</center>");
            } else {
                if (block == 1) {
                    LOG.trace("User is blocked.");
                    out.println("<center>");
                    out.println("<div style=\"position: absolute; " +
                            "top: 50%; " +
                            "left: 50%; " +
                            "transform: translate(-50%, -50%);\">");
                    out.println("<h1>User is blocked!</h1>");
                    out.println("<div style=\"margin-top: 40px;\"><a href=\"registration.jsp\" style=\"" +
                            "text-align: center; " +
                            "font-size: 18pt; " +
                            "color: #F8F2CA; " +
                            "border-radius: 4px; " +
                            "background-color: #925C32; " +
                            "padding: 20px 14px; " +
                            "text-decoration: none;\">On the page registration</a></div>");
                    out.println("</div>");
                    out.println("</center>");
                    return;
                }
                LOG.trace("Password was incorrect.");
                out.println("<center>");
                out.println("<div style=\"position: absolute; " +
                        "top: 50%; " +
                        "left: 50%; " +
                        "transform: translate(-50%, -50%);\">");
                out.println("<h1>Password was incorrect!</h1>");
                out.println("<div style=\"margin-top: 40px;\"><a href=\"login.jsp\" style=\"" +
                        "text-align: center; " +
                        "font-size: 18pt; " +
                        "color: #F8F2CA; " +
                        "border-radius: 4px; " +
                        "background-color: #925C32; " +
                        "padding: 20px 14px; " +
                        "text-decoration: none;\">On the page sing in</a></div>");
                out.println("</div>");
                out.println("</center>");
            }
        }
    }
}
