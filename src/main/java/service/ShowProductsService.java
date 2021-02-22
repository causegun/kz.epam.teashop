package service;

import dao.CategoryDao;
import dao.LanguageDao;
import dao.ProductDao;
import dao.factory.DaoFactory;
import entity.Category;
import entity.Language;
import entity.Product;
import exception.ConnectionPoolException;
import exception.DAOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class ShowProductsService implements Service {

    CategoryDao categoryDao = DaoFactory.getCategoryDao();
    ProductDao productDao = DaoFactory.getProductDao();
    LanguageDao languageDao = DaoFactory.getLanguageDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {

        String action = request.getServletPath();

        try {
            switch (action) {
                case "/admin/products/new":
                    showNewForm(request, response);
                    break;
                case "/admin/products/insert":
                    insertProduct(request, response);
                    break;
                case "/admin/products/delete":
                    deleteProduct(request, response);
                    break;
                case "/admin/products/edit":
                    showEditForm(request, response);
                    break;
                case "/admin/products/update":
                    updateProduct(request, response);
                    break;
                case "/admin/products/ru":
                    listProductRu(request, response);
                    break;
                default:
                    listProduct(request, response);
                    break;
            }
        } catch (SQLException | ConnectionPoolException | DAOException ex) {
            throw new ServletException(ex);
        }
    }

    public void listProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ConnectionPoolException, DAOException {

        List<Product> products = productDao.getAll();
        List<Category> categories = categoryDao.getAll();
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/showProduct.jsp");
        dispatcher.forward(request, response);
    }

    public void listProductRu(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ConnectionPoolException, DAOException {

        List<Product> products = productDao.getAll();
        List<Category> categories = categoryDao.getAll();
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/showProductRu.jsp");
        dispatcher.forward(request, response);
    }

    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ConnectionPoolException, DAOException {
        setLanguagesCategoriesAttributes(request, response);
    }

    private void setLanguagesCategoriesAttributes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ConnectionPoolException, DAOException {

        List<Language> languages = languageDao.getAll();
        List<Category> categories = categoryDao.getAll();
        request.setAttribute("languages", languages);
        request.setAttribute("categories", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/productForm.jsp");
        dispatcher.forward(request, response);
    }

    public void insertProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ConnectionPoolException, DAOException {

        long languageId = Long.parseLong(request.getParameter("languageId"));
        long categoryId = Long.parseLong(request.getParameter("categoryId"));

        HttpSession session = request.getSession();

        Category category = categoryDao.get(categoryId);

        if (category.getLanguageId() != languageId) {
            String mismatchErrorMessageEn = "Language mismatch";
            String mismatchErrorMessageRu = "Несоответствие языков";
            String attribute = "errorMessage";
            ServiceUtils.setIfInvalidMessage(mismatchErrorMessageEn, mismatchErrorMessageRu, attribute, request, session);
            setLanguagesCategoriesAttributes(request, response);
        } else {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            String pathToPicture = request.getParameter("pathToPicture");
            Product product = new Product(languageId, categoryId, name, description, price, pathToPicture);
            productDao.insert(product);
        }

        response.sendRedirect("/teashop/admin/products");
    }

    public void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ConnectionPoolException, DAOException {

        long id = Long.parseLong(request.getParameter("id"));
        Product product = new Product();
        product.setId(id);
        productDao.delete(product);

        response.sendRedirect("/teashop/admin/products");
    }

    public void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ConnectionPoolException, DAOException {
        long id = Long.parseLong(request.getParameter("id"));
        setProductLanguagesCategoriesAttributes(id, request, response);
    }

    public void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ConnectionPoolException, DAOException {

        long id = Long.parseLong(request.getParameter("id"));
        long languageId = Long.parseLong(request.getParameter("languageId"));
        long categoryId = Long.parseLong(request.getParameter("categoryId"));

        HttpSession session = request.getSession();

        Category category = categoryDao.get(categoryId);

        if (category.getLanguageId() != languageId) {
            String mismatchErrorMessageEn = "Language mismatch";
            String mismatchErrorMessageRu = "Несоответствие языков";
            String attribute = "errorMessage";
            ServiceUtils.setIfInvalidMessage(mismatchErrorMessageEn, mismatchErrorMessageRu, attribute, request, session);
            setProductLanguagesCategoriesAttributes(id, request, response);
        } else {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String priceString = request.getParameter("price");
            BigDecimal price = new BigDecimal(priceString);
            String pathToPicture = request.getParameter("pathToPicture");
            Product product = new Product(id, languageId, categoryId, name, description, price, pathToPicture);
            productDao.update(product);
        }

        response.sendRedirect("/teashop/admin/products");
    }

    private void setProductLanguagesCategoriesAttributes(long id, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ConnectionPoolException, DAOException {

        Product product = productDao.get(id);
        List<Language> languages = languageDao.getAll();
        List<Category> categories = categoryDao.getAll();
        request.setAttribute("product", product);
        request.setAttribute("languages", languages);
        request.setAttribute("categories", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/productForm.jsp");
        dispatcher.forward(request, response);
    }
}
