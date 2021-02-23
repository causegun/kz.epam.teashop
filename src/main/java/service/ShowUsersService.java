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
import java.util.List;

public class ShowUsersService implements Service {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String IS_ADMIN = "isAdmin";
    private static final String USERS = "users";
    private static final String USER = "user";

    UserDao userDao = DaoFactory.getUserDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {

        String action = request.getServletPath();

        try {
            switch (action) {
                case "/admin/users/new":
                    showNewForm(request, response);
                    break;
                case "/admin/users/insert":
                    insertUser(request, response);
                    break;
                case "/admin/users/delete":
                    deleteUser(request, response);
                    break;
                case "/admin/users/edit":
                    showEditForm(request, response);
                    break;
                case "/admin/users/update":
                    updateUser(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException | ConnectionPoolException | DAOException ex) {
            throw new ServletException(ex);
        }
    }

    public void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ConnectionPoolException, DAOException {

        List<User> users = userDao.getAll();
        request.setAttribute(USERS, users);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/showUser.jsp");
        dispatcher.forward(request, response);
    }

    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/userForm.jsp");
        dispatcher.forward(request, response);
    }

    public void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ConnectionPoolException, DAOException {

        String email = request.getParameter(EMAIL);
        User existingUser = userDao.getByEmail(email);
        if (existingUser != null) {
            request.setAttribute("userExistMessage", "User already exist");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/userForm.jsp");
            dispatcher.forward(request, response);
        } else {

            String userPassword = request.getParameter(PASSWORD);
            String phoneNumber = request.getParameter(PHONE_NUMBER);

            if (ServiceUtils.validateUser(email, phoneNumber, userPassword, request, response, "/userForm.jsp")) {

                String name = request.getParameter(NAME);
                boolean isAdmin = Boolean.parseBoolean(request.getParameter(IS_ADMIN));
                String hashedPassword = ServiceUtils.hashPassword(userPassword);
                User user = new User(isAdmin, name, email, hashedPassword, phoneNumber);
                userDao.insert(user);

                response.sendRedirect("/teashop/admin/users");
            }
        }
    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ConnectionPoolException, DAOException {

        long id = Long.parseLong(request.getParameter(ID));
        User user = new User();
        user.setId(id);
        userDao.delete(user);

        response.sendRedirect("/teashop/admin/users");
    }

    public void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ConnectionPoolException, DAOException {

        long id = Long.parseLong(request.getParameter(ID));
        User user = userDao.get(id);
        request.setAttribute(USER, user);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/userForm.jsp");
        dispatcher.forward(request, response);
    }

    public void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ConnectionPoolException, DAOException {

        String email = request.getParameter(EMAIL);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        long id = Long.parseLong(request.getParameter(ID));
        User userUpdate = userDao.get(id);
        request.setAttribute(USER, userUpdate);

        if (ServiceUtils.validateUser(email, phoneNumber, request, response, "/userForm.jsp")) {
            String name = request.getParameter(NAME);

            boolean isAdmin = Boolean.parseBoolean(request.getParameter(IS_ADMIN));

            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setAdmin(isAdmin);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            userDao.update(user);

            response.sendRedirect("/teashop/admin/users");
        }

    }
}
