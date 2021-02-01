package service;

import dao.factory.DaoFactory;
import entity.Category;
import entity.Language;
import entity.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class CategoryListService implements Service{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        List<Category> categories = DaoFactory.getCategoryDao().getAll();
        HttpSession session = request.getSession();
        Language language = (Language) session.getAttribute("language");
        request.setAttribute("categories", categories);
        request.setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/categoryList.jsp");
        dispatcher.forward(request, response);
    }
}
