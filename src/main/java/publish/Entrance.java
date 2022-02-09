package publish;

import publish.db.entity.User;
import publish.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/entrance")
public class Entrance extends HttpServlet{

    private static UserService service = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Entarance@doPost() start");


        String name = req.getParameter("login");
        String password = req.getParameter("password");

        User user = service.getUser(0, name, password);

        if (user.getLogin().equals("Artem") && user.getPassword().equals("1234")){
            resp.getWriter().append("Hello, " + user.getLogin());
        }
    }
}
