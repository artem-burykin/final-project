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
 * Admin servlet for changing product price.
 * @author Burykin
 */
@WebServlet("/changeProductPrice")
public class ChangeProductPriceServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ChangeProductPriceServlet.class);
    private final ProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        LOG.info("Changing price of product.");
        try {
            productService.updateProductPrice(Double.parseDouble(req.getParameter("price")), req.getParameter("name"));
            LOG.info("Price is changed successfully!");
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            getServletContext().getRequestDispatcher("error.jsp").forward(req, resp);
        }

        out.println("<center>");
        out.println("<div style=\"position: absolute; " +
                "top: 50%; " +
                "left: 50%; " +
                "transform: translate(-50%, -50%);\">");
        out.println("<h1>Price is changed successfully!</h1>");
        out.println("<div style=\"margin-top: 40px;\"><a href=\"/publish/showProductsAndCategories\" style=\"" +
                "text-align: center; " +
                "font-size: 18pt; " +
                "color: #F8F2CA; " +
                "border-radius: 4px; " +
                "background-color: #925C32; " +
                "padding: 20px 14px; " +
                "text-decoration: none;\">On the admin page</a></div>");
        out.println("</div>");
        out.println("</center>");
    }
}