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
import java.io.IOException;
import java.util.List;

/**
 * Servlet for show all product for non logged user and show all product without subscription for logged user.
 * @author Burykin
 */
@WebServlet("/showAllProducts")
public class ShowAllProductsServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ShowAllProductsServlet.class);
    private final ProductService productService = new ProductServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImp();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LOG.info("Start searching all product.");
            if (req.getSession().getAttribute("login") == null) {
                List<Product> products = productService.findAllProducts();
                List<Category> categories = categoryService.findAllCategories();
                LOG.info("List with all product and category was taken.");
                req.setAttribute("products", products);
                req.setAttribute("categories", categories);
                req.getSession().setMaxInactiveInterval(-1);
                req.getSession().setAttribute("status", "No action");
                req.getSession().setAttribute("status_uk", "Дій немає.");
                req.getSession().setAttribute("color", "#212529");
            }
            else{
                List<Product> products = productService.findAllNotSubscribeProduct((String) req.getSession().getAttribute("login"));
                List<Category> categories = categoryService.findAllCategories();
                LOG.info("List with all product and category was taken.");
                req.setAttribute("products", products);
                req.setAttribute("categories", categories);
                if (!req.getSession().getAttribute("status").equals("Your score less then product price. Please top up the score!")
                        && !req.getSession().getAttribute("status")
                        .equals("Trade was successfully! You can check your subscription in your profile. Thank you!")){
                    req.getSession().setAttribute("status", "No action");
                    req.getSession().setAttribute("status_uk", "Дій немає.");
                    req.getSession().setAttribute("color", "#212529");
                }
            }
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            throw new ServletException(e.getMessage());
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}