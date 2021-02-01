package service;

import dao.factory.DaoFactory;
import entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

public class RegisterService implements Service{

    private final static Logger logger = Logger.getLogger(RegisterService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {


            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String userPassword = request.getParameter("password");
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                logger.error("Error while hashing password. Message: " + e.getMessage());
                e.printStackTrace();
            }

            byte[] messageBytes = messageDigest.digest(userPassword.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b: messageBytes) {
                stringBuilder.append(String.format("%02X", b));
            }
            String hashedPassword = stringBuilder.toString();

            String phoneNumber = request.getParameter("phoneNumber");
            User user = new User(name, email, hashedPassword, phoneNumber);
            DaoFactory.getUserDao().insert(user);
            response.sendRedirect("/teashop/login");

    }
}
