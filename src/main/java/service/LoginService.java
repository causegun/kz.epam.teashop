package service;

import dao.UserDao;
import dao.factory.DaoFactory;
import entity.Language;
import entity.User;
import exception.ConnectionPoolException;
import exception.DAOException;
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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException, ConnectionPoolException, DAOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String hashedPassword = null;
        UserDao userDao = DaoFactory.getUserDao();

        if (password != null) {
            hashedPassword = ServiceUtils.hashPassword(password);
        }

        User user = userDao.checkLogin(email, hashedPassword);
        String destPage = "login.jsp";
        HttpSession session = request.getSession();

        if (user != null) {
            session.setAttribute("customerUser", user);
            destPage = "home.jsp";
        } else if (email != null) {
            String messageEn = "Invalid email or password";
            String messageRu = "Неправильный эл.адрес или пароль";
            String attribute = "message";
            ServiceUtils.setIfInvalidMessage(messageEn, messageRu, attribute, request, session);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/" + destPage);
        dispatcher.forward(request, response);
    }
}
