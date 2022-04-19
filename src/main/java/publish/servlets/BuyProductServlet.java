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

@WebServlet("/buyProduct")
public class BuyProductServlet extends HttpServlet {
    private static final org.apache.logging.log4j.Logger LOG = org.apache.logging.log4j.LogManager.getLogger(BuyProductServlet.class);
    private AccountService accountService = new AccountServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    private OrderService orderService = new OrderServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        LOG.trace("Starting buy product:");
        PrintWriter out = resp.getWriter();
        try {
            Product product = productService.getProductByName(req.getParameter("product"));
            double startAccountScore = accountService.findByLogin((String) req.getSession().getAttribute("login")).getScore();
            if (startAccountScore > product.getPrice()){
                Order order = OrderServiceImpl.getOrder(product.getPrice(),
                        accountService.findByLogin((String) req.getSession().getAttribute("login")).getId(), product.getId());
                double scoreAfterBuying = startAccountScore - product.getPrice();
                accountService.updateScore(scoreAfterBuying, (String) req.getSession().getAttribute("login"));
                req.getSession().removeAttribute("score");
                req.getSession().setAttribute("score", scoreAfterBuying);
                order.setDescription("");
                orderService.insertOrder(order);
                System.out.println("hello");
                LOG.trace("Trade was successfully.");
                out.println("<center>");
                out.println("<div style=\"position: absolute; " +
                        "top: 50%; " +
                        "left: 50%; " +
                        "transform: translate(-50%, -50%);\">");
                out.println("<h1>Trade was successfully! You can check your subscription in your profile. Thank you!</h1>");
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
            else {
                LOG.trace("Account's score < price.");
                out.println("<center>");
                out.println("<div style=\"position: absolute; " +
                        "top: 50%; " +
                        "left: 50%; " +
                        "transform: translate(-50%, -50%);\">");
                out.println("<h1>Your score less then product price. Please top up the score!</h1>");
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
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
