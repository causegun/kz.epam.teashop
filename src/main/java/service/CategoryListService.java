package service;

import dao.CategoryDao;
import dao.factory.DaoFactory;
import entity.Category;
import entity.Language;
import exception.ConnectionPoolException;
import exception.DAOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class CategoryListService implements Service {

    private final DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException, ConnectionPoolException, DAOException {

        HttpSession session = request.getSession();
        Language language = (Language) session.getAttribute("language");
        List<Category> categories;
        CategoryDao categoryDao = daoFactory.getCategoryDao();

        if (language == null)
            categories = categoryDao.getByLanguage(1);
        else
            categories = categoryDao.getByLanguage(language.getId());

        request.setAttribute("categories", categories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/categoryList.jsp");
        dispatcher.forward(request, response);
    }
}
