package service;

import connection.ConnectionPool;
import dao.factory.DaoFactory;
import dao.impl.UserDaoImpl;
import entity.User;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

public class LoginService implements Service{

    private final static Logger logger = Logger.getLogger(LoginService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String hashedPassword = null;

        if (password != null) {
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                logger.error("Error while hashing password. Message: " + e.getMessage());
                e.printStackTrace();
            }

            byte[] messageBytes = messageDigest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b: messageBytes) {
                stringBuilder.append(String.format("%02X", b));
            }
            hashedPassword = stringBuilder.toString();
        }

        User user = DaoFactory.getUserDao().checkLogin(email, hashedPassword);

        String destPage = "login.jsp";

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("customerUser", user);
            destPage = "home.jsp";
        } else if (email != null) {
            String message = "Invalid email or password";
            request.setAttribute("message", message);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/" + destPage);
        dispatcher.forward(request, response);
    }
}
