package publish.listeners;

import publish.service.AccountService;
import publish.service.AccountServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener, ServletContextAttributeListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AccountService userService = new AccountServiceImpl();
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("accountService", userService);
        System.out.println(sce.getServletContext().getAttribute("accountService"));
    }
}
