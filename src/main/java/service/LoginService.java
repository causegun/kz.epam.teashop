package service;

import dao.UserDao;
import dao.factory.DaoFactory;
import entity.Language;
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

public class LoginService implements Service {

    private final static Logger logger = Logger.getLogger(LoginService.class);

    static String hashPassword(String password) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error while hashing password. Message: " + e.getMessage());
            e.printStackTrace();
        }

        byte[] messageBytes = new byte[0];

        if (messageDigest != null) {
            messageBytes = messageDigest.digest(password.getBytes());
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : messageBytes) {
            stringBuilder.append(String.format("%02X", b));
        }
        return stringBuilder.toString();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String hashedPassword = null;
        UserDao userDao = DaoFactory.getUserDao();

        if (password != null) {
            hashedPassword = hashPassword(password);
        }

        User user = userDao.checkLogin(email, hashedPassword);
        String destPage = "login.jsp";
        HttpSession session = request.getSession();

        if (user != null) {
            session.setAttribute("customerUser", user);
            destPage = "home.jsp";
        } else setIfInvalidMessage(request, email, session);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/" + destPage);
        dispatcher.forward(request, response);
    }

    static void setIfInvalidMessage(HttpServletRequest request, String email, HttpSession session) {
        if (email != null) {
            Language language = (Language) session.getAttribute("language");
            String message = "Invalid email or password";
            String languageName = null;

            if (language != null)
                languageName = language.getName();

            if (languageName == null || languageName.equals("en"))
                message = "Invalid email or password";
            else if (languageName.equals("ru"))
                message = "Неправильный эл.адрес или пароль";
            request.setAttribute("message", message);
        }
    }
}
