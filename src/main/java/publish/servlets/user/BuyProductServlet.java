package publish.servlets.user;

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
@WebServlet("/user/buyProduct")
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
        String status = "";
        String status_uk = "";
        String color = "";
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
                LOG.info("Trade was successful.");
                status = "Trade was successful! You can check your subscription in your profile. Thank you!";
                status_uk = "Покупка пройшла успішно! Ви можете перевірити підписку у своєму профілі. Дякую!";
                color = "#0fdc70";
            }
            else{
                LOG.warn("User score is less than the product price.");
                status = "Your score is less than the product price. Please top up the score!";
                status_uk = "Кількість коштів на вашому рахуноку нижча за ціну продукту. Будь ласка, поповніть рахунок!";
                color = "#fb0349";
            }
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.setAttribute("code", e.getErrorCode());
            throw new ServletException(e.getMessage());
        }
        req.getSession().setAttribute("status", status);
        req.getSession().setAttribute("status_uk", status_uk);
        req.getSession().setAttribute("color", color);
    }
}
