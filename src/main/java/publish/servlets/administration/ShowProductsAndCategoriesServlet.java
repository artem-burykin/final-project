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

@WebServlet("/showProductsAndCategories")
public class ShowProductsAndCategoriesServlet extends HttpServlet {
    private static final org.apache.logging.log4j.Logger LOG = org.apache.logging.log4j.LogManager.getLogger(ShowProductsAndCategoriesServlet.class);
    private ProductService productService = new ProductServiceImpl();
    private CategoryService categoryService = new CategoryServiceImp();
    private AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Product> productList = productService.findAllProducts();
            List<Category> categoryList = categoryService.findAllCategories();
            List<Account> accountList = accountService.findAllAccounts();
            LOG.trace("List with all product, account and category was taken:");
            req.setAttribute("products", productList);
            req.setAttribute("categories", categoryList);
            req.setAttribute("accounts", accountList);
            req.getRequestDispatcher("admin.jsp").forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
