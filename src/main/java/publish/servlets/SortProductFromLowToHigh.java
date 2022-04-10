package publish.servlets;

import publish.db.dao.DBException;
import publish.db.entity.Product;
import publish.service.ProductService;
import publish.service.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/sortProductFromLowToHigh")
public class SortProductFromLowToHigh extends HttpServlet {
    private static final org.apache.logging.log4j.Logger LOG = org.apache.logging.log4j.LogManager.getLogger(SortProductFromLowToHigh.class);
    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Product> products = productService.sortFromLowToHigh();
            LOG.trace("List with sorting product from low to high price was taken:");
            req.setAttribute("products", products);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
