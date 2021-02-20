package service;

import dao.UserDao;
import dao.factory.DaoFactory;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class AdminLoginService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String hashedPassword = null;
        UserDao userDao = DaoFactory.getUserDao();

        if (password != null) {
            hashedPassword = LoginService.hashPassword(password);
        }

        User user = userDao.checkAdminLogin(email, hashedPassword);
        String destPage = "adminLogin.jsp";
        HttpSession session = request.getSession();

        if (user != null) {
            session.setAttribute("customerUser", user);
            session.setAttribute("adminUser", user);
            destPage = "adminHome.jsp";
        } else LoginService.setIfInvalidMessage(request, email, session);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/" + destPage);
        dispatcher.forward(request, response);
    }
}
