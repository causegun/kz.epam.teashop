package service;

import dao.CartDao;
import dao.CartItemDao;
import dao.ProductDao;
import dao.UserDao;
import dao.factory.DaoFactory;
import entity.*;
import exception.ConnectionPoolException;
import exception.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AddToCartService implements Service {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException, DAOException, ConnectionPoolException {

        long productId = Long.parseLong(request.getParameter("id"));
        HttpSession session = request.getSession();
        ProductDao productDao = daoFactory.getProductDao();
        UserDao userDao = daoFactory.getUserDao();
        CartDao cartDao = daoFactory.getCartDao();
        CartItemDao cartItemDao = daoFactory.getCartItemDao();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String createdAt = dtf.format(now);

        String quantityParameter = request.getParameter("quantity" + productId);
        int quantity = Integer.parseInt(quantityParameter);

        BigDecimal totalPrice;
        Product product = productDao.get(productId);
        BigDecimal productPrice = product.getPrice();
        BigDecimal quantityDecimal = new BigDecimal(quantity);
        totalPrice = quantityDecimal.multiply(productPrice);

        User user = (User) session.getAttribute("customerUser");
        User userWithId = userDao.getByEmail(user.getEmail());
        long userId = userWithId.getId();

        long cartId;
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setCreatedAt(createdAt);
            cart.setTotalPrice(totalPrice);
            cartDao.insert(cart);
            cartId = cartDao.getId(userId, createdAt);
            cart.setId(cartId);
            session.setAttribute("cart", cart);
        } else {
            cart.setTotalPrice(cart.getTotalPrice().add(totalPrice));
            cartDao.update(cart);
            cartId = cart.getId();
        }

        CartItem cartItem = new CartItem(productId, cartId, createdAt, quantity);
        cartItemDao.insert(cartItem);

        Language language = (Language) session.getAttribute("language");
        String addMessage = "";
        String languageName = null;

        if (language != null)
            languageName = language.getName();

        if (languageName == null || languageName.equals("en"))
            addMessage = "Added " + quantity + " " + product.getName() + " to cart";

        else if (languageName.equals("ru"))
            addMessage = "Добавлено " + quantity + " " + product.getName() + " в корзину";

        session.setAttribute("addMessage", addMessage);

        response.sendRedirect("/teashop/productList/category?id=" + product.getCategoryId());
    }
}
