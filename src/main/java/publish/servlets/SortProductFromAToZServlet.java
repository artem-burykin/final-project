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
 * Servlet for sorting products from A to Z.
 * @author Burykin
 */
@WebServlet("/sortProductFromAToZ")
public class SortProductFromAToZServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SortProductFromAToZServlet.class);
    private final ProductService productService = new ProductServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImp();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LOG.info("Start sorting product from A to Z.");
            List<Product> products = productService.sortFromAToZ((String) req.getSession().getAttribute("login"));
            List<Category> categories = categoryService.findAllCategories();
            LOG.info("List with sorting product from A to Z was taken.");
            req.setAttribute("products", products);
            req.setAttribute("categories", categories);
            req.getSession().setAttribute("status", "Sorting from A to Z was successful!");
            req.getSession().setAttribute("status_uk", "Сортування від початку алфавіту до кінця виконано успішно!");
            req.getSession().setAttribute("color", "#0fdc70");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            throw new ServletException(e.getMessage());
        }
    }
}
