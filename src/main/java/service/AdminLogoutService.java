package service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class AdminLogoutService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("customerUser");
            session.removeAttribute("adminUser");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/adminLogin.jsp");
            dispatcher.forward(request, response);
        }
    }
}
