package service;

import dao.CartDao;
import dao.CartItemDao;
import dao.factory.DaoFactory;
import entity.Cart;
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

public class ShowOrdersService implements Service {

    private static final String ID = "id";

    CartDao cartDao = DaoFactory.getCartDao();
    CartItemDao cartItemDao = DaoFactory.getCartItemDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException, DAOException, ConnectionPoolException {

        String action = request.getServletPath();

        if (action.equals("/admin/orders")) {
            List<Cart> carts = cartDao.getAll();

            request.setAttribute("carts", carts);

        } else if (action.equals("/admin/orders/delete")) {
            long id = Long.parseLong(request.getParameter(ID));
            cartItemDao.deleteByCartId(id);
            Cart cart = new Cart();
            cart.setId(id);
            cartDao.delete(cart);

        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/showOrder.jsp");
        dispatcher.forward(request, response);

    }
}
