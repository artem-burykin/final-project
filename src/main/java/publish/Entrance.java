package publish;

import publish.db.dao.AccountDao;
import publish.db.dao.DBException;
import publish.db.dao.DaoFactory;
import publish.db.dao.mysql.MysqlAccountDao;
import publish.db.dao.mysql.MysqlDaoFactory;
import publish.db.entity.Account;
import publish.service.AccountService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/entrance")
public class Entrance extends HttpServlet{
    private static final org.apache.logging.log4j.Logger LOG = org.apache.logging.log4j.LogManager.getLogger(Entrance.class);
    private static AccountService service;
    private ServletContext sc;

    public void init(ServletConfig config) throws ServletException {
        sc = config.getServletContext();
        service = (AccountService) sc.getAttribute("userService");
        sc.setAttribute("users", new ArrayList<>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("@doPost() start");
        System.out.println("Entarance@doPost() start");

        AccountDao AccountDao = MysqlDaoFactory.getInstance().getAccountDao();

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Account account;
        try {
            account = DaoFactory.getInstance().getAccountDao().findByLogin(login);
            LOG.debug("Account: " + account);
            System.out.println("account: " + account);
            if (account.getLogin().equals("Artem") && account.getPassword().equals("1234")){
                resp.getWriter().append("Hello, " + account.getLogin());
            }
        } catch (DBException e) {
            e.printStackTrace();
        }

    }
}
