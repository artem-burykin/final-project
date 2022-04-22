package publish.filters;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filter for validating input's data from registration form.
 * @author Burykin
 */
public class RegistrationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        String name = servletRequest.getParameter("firstName");
        String surname = servletRequest.getParameter("lastName");
        String email = servletRequest.getParameter("email");
        String login = servletRequest.getParameter("login");
        String password = servletRequest.getParameter("password");

        StringBuffer incorrect = new StringBuffer();

        Pattern forName = Pattern.compile("^[А-ЯA-Z][а-яa-z]{2,}$");
        Matcher first = forName.matcher(name);
        Pattern forSurname = Pattern.compile("^[А-ЯA-Z][а-яa-z]{2,}$");
        Matcher second = forSurname.matcher(surname);
        Pattern forEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher third = forEmail.matcher(email);
        Pattern forLogin = Pattern.compile("^[a-z0-9]{2,}$");
        Matcher forth = forLogin.matcher(login);
        Pattern forPassword = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{4,}$");
        Matcher fifth = forPassword.matcher(password);

        if(!first.matches()) {
            incorrect.append(" " + name);
        }
        if(!second.matches()) {
            incorrect.append(" " + surname);
        }
        if(!third.matches()) {
            incorrect.append(" " + email);
        }
        if(!forth.matches()) {
            incorrect.append(" " + login);
        }
        if(!fifth.matches()) {
            incorrect.append(" " + password);
        }

        if (incorrect.length() == 0) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            servletResponse.setContentType("text/html; charset=UTF-8");
            PrintWriter out = servletResponse.getWriter();
            out.println("<center>");
            out.println("<div style=\"position: absolute; " +
                    "top: 50%; " +
                    "left: 50%; " +
                    "transform: translate(-50%, -50%);\">");
            out.println("<h1>Next data is incorrect:" + incorrect.toString() + "</h1>");
            out.println("<div style=\"margin-top: 40px;\"><a href=\"/publish/registration.jsp\" style=\"" +
                    "text-align: center; " +
                    "font-size: 18pt; " +
                    "color: #F8F2CA; " +
                    "border-radius: 4px; " +
                    "background-color: #925C32; " +
                    "padding: 20px 14px; " +
                    "text-decoration: none;\">On the page registration</a></div>");
            out.println("</div>");
            out.println("</center>");
        }
    }

}
