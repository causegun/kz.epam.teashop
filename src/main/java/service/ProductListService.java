package service;

import dao.factory.DaoFactory;
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
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class ProductListService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException, ConnectionPoolException, DAOException {

        long id = Long.parseLong(request.getParameter("id"));
        List<Product> products = DaoFactory.getProductDao().getByCategory(id);
        HttpSession session = request.getSession();

        Language language = (Language) session.getAttribute("language");
        request.setAttribute("products", products);
        request.setAttribute("language", language);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/productList.jsp");
        dispatcher.forward(request, response);
    }
}
