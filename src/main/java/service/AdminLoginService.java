package service;

import dao.factory.DaoFactory;
import dao.impl.UserDaoImpl;
import entity.Language;
import entity.User;

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
import java.util.Locale;

import static java.util.Locale.ENGLISH;

public class AdminLoginService implements Service{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {
        Locale locale = Locale.getDefault();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String hashedPassword = null;

        if (password != null) {
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            byte[] messageBytes = messageDigest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b: messageBytes) {
                stringBuilder.append(String.format("%02X", b));
            }
            hashedPassword = stringBuilder.toString();
        }


        User user = DaoFactory.getUserDao().checkAdminLogin(email, hashedPassword);
        String destPage = "adminLogin.jsp";

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("customerUser", user);
            session.setAttribute("adminUser", user);
            destPage = "adminHome.jsp";
        } else if (email != null) {
            HttpSession session = request.getSession();
            Language language = (Language) session.getAttribute("language");
            String message = "Invalid email or password";
            if (locale == ENGLISH || language.getName().equals("en"))
                message = "Invalid email or password";
            else if (language.getName().equals("ru"))
                message = "Неправильный эл.адрес или пароль";
            request.setAttribute("message", message);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/" + destPage);
        dispatcher.forward(request, response);
    }
}
