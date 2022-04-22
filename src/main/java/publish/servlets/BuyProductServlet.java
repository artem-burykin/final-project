package publish.servlets;

import publish.db.dao.DBException;
import publish.db.entity.Order;
import publish.db.entity.Product;
import publish.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for buying product.
 * @author Burykin
 */
@WebServlet("/buyProduct")
public class BuyProductServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BuyProductServlet.class);
    private final AccountService accountService = new AccountServiceImpl();
    private final ProductService productService = new ProductServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        LOG.info("Starting buy product.");
        PrintWriter out = resp.getWriter();
        String status = "Your score less then product price. Please top up the score!";
        try {
            Product product = productService.getProductByName(req.getParameter("product"));
            double startAccountScore = accountService.findByLogin((String) req.getSession().getAttribute("login")).getScore();
            if (startAccountScore >= product.getPrice()){
                LOG.info("User score bigger than product price, so let's start to buy.");
                Order order = OrderServiceImpl.getOrder(product.getPrice(),
                        accountService.findByLogin((String) req.getSession().getAttribute("login")).getId(), product.getId());
                double scoreAfterBuying = startAccountScore - product.getPrice();
                accountService.updateScore(scoreAfterBuying, (String) req.getSession().getAttribute("login"));
                req.getSession().removeAttribute("score");
                req.getSession().setAttribute("score", scoreAfterBuying);
                order.setDescription("");
                orderService.insertOrder(order);
                LOG.info("Trade was successfully.");
                status = "Trade was successfully! You can check your subscription in your profile. Thank you!";
            }
            else{
                LOG.warn("User score less then product price.");
            }
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
        out.println("<h1>" + status + "</h1>");
        out.println("<div style=\"margin-top: 40px;\"><a href=\"/publish/\" style=\"" +
                "text-align: center; " +
                "font-size: 18pt; " +
                "color: #F8F2CA; " +
                "border-radius: 4px; " +
                "background-color: #925C32; " +
                "padding: 20px 14px; " +
                "text-decoration: none;\">On the main page</a></div>");
        out.println("</div>");
        out.println("</center>");
    }
}
