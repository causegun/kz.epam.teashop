package service;

import dao.UserDao;
import dao.factory.DaoFactory;
import entity.User;
import exception.ConnectionPoolException;
import exception.DAOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class RegisterService implements Service {
    UserDao userDao = DaoFactory.getUserDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException, ConnectionPoolException, DAOException {
        String email = request.getParameter("email");
        String pathIfNotValid = "/registerForm.jsp";
        User existingUser = userDao.getByEmail(email);

        if (existingUser != null) {
            request.setAttribute("userExistMessage", "User already exist");

            RequestDispatcher dispatcher = request.getRequestDispatcher(pathIfNotValid);
            dispatcher.forward(request, response);
        } else {

            String phoneNumber = request.getParameter("phoneNumber");
            String userPassword = request.getParameter("password");


            if (ServiceUtils.validateUser(email, phoneNumber, userPassword, request, response, pathIfNotValid)) {
                String name = request.getParameter("name");
                String hashedPassword = ServiceUtils.hashPassword(userPassword);

                User user = new User(name, email, hashedPassword, phoneNumber);
                DaoFactory.getUserDao().insert(user);

                response.sendRedirect("/teashop/login");
            }
        }
    }
}
