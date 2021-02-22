package service;

import dao.CartDao;
import dao.CartItemDao;
import dao.ProductDao;
import dao.factory.DaoFactory;
import entity.Cart;
import entity.CartItem;
import entity.Product;
import exception.ConnectionPoolException;
import exception.DAOException;
import org.apache.log4j.Logger;

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


public class CartService implements Service {

    private final static Logger logger = Logger.getLogger(CartService.class);

    CartItemDao cartItemDao = DaoFactory.getCartItemDao();
    ProductDao productDao = DaoFactory.getProductDao();
    CartDao cartDao = DaoFactory.getCartDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/cart/delete":
                    deleteCartItem(request, response);
                    break;
                case "/cart/edit":
                    editCartItem(request, response);
                    break;
                case "/cart/deleteCart":
                    deleteCart(request, response);
                    break;
                case "/cart/order":
                    order(request, response);
                    break;
                default:
                    listCartItems(request, response);
                    break;
            }
        } catch (SQLException | ConnectionPoolException | DAOException ex) {
            logger.error("Error in executing CartService. Message: " + ex.getMessage());
            throw new ServletException(ex);
        }
    }

    public void listCartItems(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ConnectionPoolException, DAOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            String noCartMessageEn = "Buy something first!";
            String noCartMessageRu = "Купите что-нибудь!";
            String attribute = "noCartMessage";
            ServiceUtils.setIfInvalidMessage(noCartMessageEn, noCartMessageRu, attribute, request, session);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        } else {
            List<CartItem> cartItems = cartItemDao.getByCartId(cart.getId());
            Product product;

            for (CartItem cartItem : cartItems) {
                product = productDao.get(cartItem.getProductId());
                cartItem.setProductName(product.getName());
                cartItem.setPrice(product.getPrice());
            }

            request.setAttribute("cartItems", cartItems);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/cart.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void deleteCart(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ConnectionPoolException, DAOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        long cartId = cart.getId();
        cartItemDao.deleteByCartId(cartId);
        cartDao.delete(cart);
        session.removeAttribute("cart");

        response.sendRedirect("/teashop/categoryList");
    }

    public void order(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        request.setAttribute("createdAt", cart.getCreatedAt());
        request.setAttribute("totalPrice", cart.getTotalPrice());
        session.removeAttribute("cart");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/order.jsp");
        dispatcher.forward(request, response);
    }

    public void editCartItem(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        // will be added soon
    }

    public void deleteCartItem(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ConnectionPoolException, DAOException {

        long id = Long.parseLong(request.getParameter("id"));
        CartItem cartItem = cartItemDao.get(id);

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        Product product = productDao.get(cartItem.getProductId());
        BigDecimal productPrice = product.getPrice();
        BigDecimal quantity = new BigDecimal(cartItem.getQuantity());
        BigDecimal totalPriceOfDeletingProducts = productPrice.multiply(quantity);
        BigDecimal previousTotalPrice = cart.getTotalPrice();
        BigDecimal currentTotalPrice = previousTotalPrice.subtract(totalPriceOfDeletingProducts);

        if (currentTotalPrice.equals(new BigDecimal(0)))
            deleteCart(request, response);
        else {
            cart.setTotalPrice(currentTotalPrice);
            cartItemDao.delete(cartItem);
            response.sendRedirect("/teashop/cart");
        }
    }
}
