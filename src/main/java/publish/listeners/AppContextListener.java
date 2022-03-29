package publish.listeners;

import publish.db.dao.AccountDao;
import publish.db.dao.DaoFactory;
import publish.service.AccountService;
import publish.service.AccountServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener, ServletContextAttributeListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        AccountDao accountDao = DaoFactory.getInstance().getAccountDao();
        AccountService userService = new AccountServiceImpl(accountDao);
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("accountService", userService);
        System.out.println(sce.getServletContext().getAttribute("accountService"));
    }
}
