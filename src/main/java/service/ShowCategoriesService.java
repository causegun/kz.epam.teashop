package service;

import dao.CategoryDao;
import dao.LanguageDao;
import dao.factory.DaoFactory;
import entity.Category;
import entity.Language;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class ShowCategoriesService implements Service {
    CategoryDao categoryDao = DaoFactory.getCategoryDao();
    LanguageDao languageDao = DaoFactory.getLanguageDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {

        String action = request.getServletPath();

        try {
            switch (action) {
                case "/admin/categories/new":
                    showNewForm(request, response);
                    break;
                case "/admin/categories/insert":
                    insertCategory(request, response);
                    break;
                case "/admin/categories/delete":
                    deleteCategory(request, response);
                    break;
                case "/admin/categories/edit":
                    showEditForm(request, response);
                    break;
                case "/admin/categories/update":
                    updateCategory(request, response);
                    break;
                case "/admin/categories/ru":
                    listCategoryRu(request, response);
                    break;
                default:
                    listCategory(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    public void listCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Category> categories = categoryDao.getAll();
        request.setAttribute("categories", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/showCategory.jsp");
        dispatcher.forward(request, response);
    }

    public void listCategoryRu(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Category> categories = categoryDao.getAll();
        request.setAttribute("categories", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/showCategoryRu.jsp");
        dispatcher.forward(request, response);
    }

    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Language> languages = languageDao.getAll();
        List<Category> categories = categoryDao.getAll();
        request.setAttribute("languages", languages);
        request.setAttribute("categories", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/categoryForm.jsp");
        dispatcher.forward(request, response);
    }

    public void insertCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        long languageId = Long.parseLong(request.getParameter("languageId"));
        String categoryName = request.getParameter("name");
        Category category = new Category(languageId, categoryName);
        categoryDao.insert(category);

        response.sendRedirect("/teashop/admin/categories");
    }

    public void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        long id = Long.parseLong(request.getParameter("id"));
        Category category = new Category();
        category.setId(id);
        categoryDao.delete(category);

        response.sendRedirect("/teashop/admin/categories");
    }

    public void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        long id = Long.parseLong(request.getParameter("id"));
        Category category = categoryDao.get(id);
        List<Language> languages = languageDao.getAll();
        request.setAttribute("languages", languages);
        request.setAttribute("category", category);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/categoryForm.jsp");
        dispatcher.forward(request, response);
    }

    public void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        long id = Long.parseLong(request.getParameter("id"));
        long languageId = Long.parseLong(request.getParameter("languageId"));
        String categoryName = request.getParameter("name");

        Category category = new Category();
        category.setId(id);
        category.setLanguageId(languageId);
        category.setName(categoryName);

        response.sendRedirect("/teashop/admin/categories");
    }
}
