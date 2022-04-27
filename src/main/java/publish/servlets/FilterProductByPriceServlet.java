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
 * Servlet for filtration product by price.
 * @author Burykin
 */
@WebServlet("/filterProductByPrice")
public class FilterProductByPriceServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(FilterProductByPriceServlet.class);
    private final ProductService productService = new ProductServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImp();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LOG.info("Starting filtration between price.");
            List<Product> products = null;
            if (req.getParameter("startPrice").equals("") && req.getParameter("endPrice").equals("")){
                products = productService.findProductByPrice((String) req.getSession().getAttribute("login"),
                        0, Double.MAX_VALUE);
            }
            else {
                if (req.getParameter("startPrice").equals("")){
                    products = productService.findProductByPrice((String) req.getSession().getAttribute("login"),
                            0, Double.parseDouble(req.getParameter("endPrice")));
                }
                else {
                    if (req.getParameter("endPrice").equals("")) {
                        products = productService.findProductByPrice((String) req.getSession().getAttribute("login"),
                                Double.parseDouble(req.getParameter("startPrice")), Double.MAX_VALUE);
                    }
                    else {
                        products = productService.findProductByPrice((String) req.getSession().getAttribute("login"),
                                Double.parseDouble(req.getParameter("startPrice")), Double.parseDouble(req.getParameter("endPrice")));
                    }
                }
            }
            List<Category> categories = categoryService.findAllCategories();
            LOG.info("List with all products between prices was taken.");
            req.setAttribute("products", products);
            req.setAttribute("categories", categories);
            req.getSession().setAttribute("status", "Filtration by price was successful!");
            req.getSession().setAttribute("status_uk", "Фільтрацію за ціною виконано успішно!");
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
