package publish.servlets;

import publish.db.dao.DBException;
import publish.db.entity.Category;
import publish.db.entity.Product;
import publish.service.CategoryService;
import publish.service.CategoryServiceImp;
import publish.service.ProductService;
import publish.service.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for show all product for non logged user and show all product without subscription for logged user.
 * @author Burykin
 */
@WebServlet("/showAllProducts")
public class ShowAllProducts extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ShowAllProducts.class);
    private final ProductService productService = new ProductServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImp();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LOG.info("Start searching all product.");
            if (req.getSession() == null) {
                List<Product> products = productService.findAllProducts();
                List<Category> categories = categoryService.findAllCategories();
                LOG.info("List with all product and category was taken.");
                req.setAttribute("products", products);
                req.setAttribute("categories", categories);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
            else{
                List<Product> products = productService.findAllNotSubscribeProduct((String) req.getSession().getAttribute("login"));
                List<Category> categories = categoryService.findAllCategories();
                LOG.info("List with all product and category was taken.");
                req.setAttribute("products", products);
                req.setAttribute("categories", categories);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}