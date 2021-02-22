package service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class RegisterPageService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/registerForm.jsp");
        dispatcher.forward(request, response);
    }
}
