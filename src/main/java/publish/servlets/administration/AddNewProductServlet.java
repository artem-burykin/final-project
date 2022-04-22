package publish.servlets.administration;

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
import java.io.PrintWriter;

/**
 * Admin servlet for adding new product into db.
 * @author Burykin
 */
@WebServlet("/administration/addNewProduct")
public class AddNewProductServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AddNewProductServlet.class);
    private final ProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String status = "";
        String color = "";
        LOG.info("Forming object of product for adding.");
        Product product = ProductServiceImpl.getProduct(req.getParameter("name"), Double.parseDouble(req.getParameter("price")), Integer.parseInt(req.getParameter("category_id")));
        product.setLogo(req.getParameter("logo"));
        product.setDescription(req.getParameter("description"));
        try {
            if (productService.getProductByName(req.getParameter("name")) == null){
                LOG.info("Doing insertion of product into db.");
                productService.insertProduct(product);
                LOG.info("Product is inserted successfully!");
                req.getSession().setAttribute("products", productService.findAllProducts());
                status= "Product is inserted successfully!";
                color = "#0fdc70";
            }
            else {
                LOG.warn("There is product with the same name in db!");
                status = "There is product with the same name in db!";
                color = "#fb0349";
            }
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
        }
        req.getSession().setAttribute("status", status);
        req.getSession().setAttribute("color", color);
    }
}
