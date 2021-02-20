package service;

import dao.factory.DaoFactory;
import entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterService implements Service {

    private final static Logger logger = Logger.getLogger(RegisterService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {


        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String userPassword = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        String hashedPassword = null;

        if (userPassword != null)
            hashedPassword = LoginService.hashPassword(userPassword);

        User user = new User(name, email, hashedPassword, phoneNumber);
        DaoFactory.getUserDao().insert(user);
        response.sendRedirect("/teashop/login");

    }
}
