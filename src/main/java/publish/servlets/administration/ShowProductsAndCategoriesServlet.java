package publish.servlets.administration;

import publish.db.dao.DBException;
import publish.db.entity.Account;
import publish.db.entity.Category;
import publish.db.entity.Product;
import publish.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Admin servlet for showing all product, categories and accounts.
 * @author Burykin
 */
@WebServlet("/administration/showProductsAndCategories")
public class ShowProductsAndCategoriesServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ShowProductsAndCategoriesServlet.class);
    private final ProductService productService = new ProductServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImp();
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LOG.info("Getting list with all products, accounts and categories.");
            List<Product> productList = productService.findAllProducts();
            List<Category> categoryList = categoryService.findAllCategories();
            List<Account> accountList = accountService.findAllAccounts();
            LOG.info("List with all products, accounts and categories was taken.");
            req.getSession().setAttribute("products", productList);
            req.getSession().setAttribute("categories", categoryList);
            req.getSession().setAttribute("accounts", accountList);
            req.getSession().setAttribute("status", "No action.");
            req.getSession().setAttribute("color", "#212529");
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
