package service;

import dao.CategoryDao;
import dao.LanguageDao;
import dao.ProductDao;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class ShowProductsService implements Service {
    CategoryDao categoryDao = DaoFactory.getCategoryDao();
    ProductDao productDao = DaoFactory.getProductDao();
    LanguageDao languageDao = DaoFactory.getLanguageDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
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
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    public void listProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Product> products = productDao.getAll();
        List<Category> categories = categoryDao.getAll();
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/showProduct.jsp");
        dispatcher.forward(request, response);
    }

    public void listProductRu(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Product> products = productDao.getAll();
        List<Category> categories = categoryDao.getAll();
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/showProductRu.jsp");
        dispatcher.forward(request, response);
    }

    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        setLanguagesCategoriesAttributes(request, response);
    }

    private void setLanguagesCategoriesAttributes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Language> languages = languageDao.getAll();
        List<Category> categories = categoryDao.getAll();
        request.setAttribute("languages", languages);
        request.setAttribute("categories", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/productForm.jsp");
        dispatcher.forward(request, response);
    }

    public void insertProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        long languageId = Long.parseLong(request.getParameter("languageId"));
        long categoryId = Long.parseLong(request.getParameter("categoryId"));

        HttpSession session = request.getSession();
        Language language = (Language) session.getAttribute("language");

        Category category = categoryDao.get(categoryId);

        if (category.getLanguageId() != languageId) {
            String mismatchError = "";
            String languageName = language.getName();

            if (languageName == null || languageName.equals("en"))
                mismatchError = "Language mismatch";
            else if (languageName.equals("ru"))
                mismatchError = "Несоответствие языков";

            request.setAttribute("errorMessage", mismatchError);
            setLanguagesCategoriesAttributes(request, response);
        } else {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            String pathToPicture = request.getParameter("pathToPicture");
            Product product = new Product(languageId, categoryId, name, description, price, pathToPicture);
            productDao.insert(product);

            response.sendRedirect("/teashop/admin/products");
        }
    }

    public void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        long id = Long.parseLong(request.getParameter("id"));
        Product product = new Product();
        product.setId(id);
        productDao.delete(product);

        response.sendRedirect("/teashop/admin/products");
    }

    public void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        long id = Long.parseLong(request.getParameter("id"));
        setProductLanguagesCategoriesAttributes(request, id);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/productForm.jsp");
        dispatcher.forward(request, response);
    }

    public void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        long id = Long.parseLong(request.getParameter("id"));
        long languageId = Long.parseLong(request.getParameter("languageId"));
        long categoryId = Long.parseLong(request.getParameter("categoryId"));

        HttpSession session = request.getSession();
        Language language = (Language) session.getAttribute("language");

        Category category = categoryDao.get(categoryId);

        if (category.getLanguageId() != languageId) {
            String mismatchError = "";
            String languageName = language.getName();

            if (languageName == null || languageName.equals("en"))
                mismatchError = "Language mismatch";
            else if (language.getName().equals("ru"))
                mismatchError = "Несоответствие языков";

            request.setAttribute("errorMessage", mismatchError);
            setProductLanguagesCategoriesAttributes(request, id);
        }

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceString = request.getParameter("price");
        BigDecimal price = new BigDecimal(priceString);
        String pathToPicture = request.getParameter("pathToPicture");
        Product product = new Product(id, languageId, categoryId, name, description, price, pathToPicture);
        productDao.update(product);

        response.sendRedirect("/teashop/admin/products");
    }

    private void setProductLanguagesCategoriesAttributes(HttpServletRequest request, long id) {
        Product product = productDao.get(id);
        List<Language> languages = languageDao.getAll();
        List<Category> categories = categoryDao.getAll();
        request.setAttribute("product", product);
        request.setAttribute("languages", languages);
        request.setAttribute("categories", categories);
    }
}
