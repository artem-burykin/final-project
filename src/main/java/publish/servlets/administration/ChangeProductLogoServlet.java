package publish.servlets.administration;

import publish.db.dao.DBException;
import publish.service.ProductService;
import publish.service.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Admin servlet for changing product logo.
 * @author Burykin
 */
@WebServlet("/administration/changeProductLogo")
public class ChangeProductLogoServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ChangeProductLogoServlet.class);
    private final ProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        LOG.info("Changing product logo.");
        try {
            productService.updateProductLogo(req.getParameter("logo"), req.getParameter("name"));
            LOG.info("Logo is changed successfully!");
            req.getSession().setAttribute("products", productService.findAllProducts());
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            throw new ServletException(e.getMessage());
        }
        req.getSession().setAttribute("admin_status", "Logo is changed successfully!");
        req.getSession().setAttribute("admin_status_uk", "Логотип було змінено успішно!");
        req.getSession().setAttribute("admin_color", "#0fdc70");
    }
}
