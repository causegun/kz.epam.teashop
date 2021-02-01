package service;

import dao.factory.DaoFactory;
import entity.*;

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


public class AddToCartService implements Service{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        long productId =  Long.parseLong(request.getParameter("id"));
        HttpSession session = request.getSession();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String createdAt = dtf.format(now);
        String quantityParameter = request.getParameter("quantity" + productId);
        int quantity = Integer.parseInt(quantityParameter);
        BigDecimal totalPrice;
        Cart cart = (Cart) session.getAttribute("cart");
        Product product = DaoFactory.getProductDao().get(productId);
        BigDecimal productPrice = product.getPrice();
        totalPrice = new BigDecimal(quantity).multiply(productPrice);
        User user = (User) session.getAttribute("customerUser");
        User userWithId = DaoFactory.getUserDao().getByEmail(user.getEmail());
        long userId = userWithId.getId();
        long cartId;
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setCreatedAt(createdAt);
            cart.setTotalPrice(totalPrice);
            DaoFactory.getCartDao().insert(cart);
            cartId = DaoFactory.getCartDao().getId(userId, createdAt);
            cart.setId(cartId);
            session.setAttribute("cart", cart);
        } else {
            cart.setTotalPrice(cart.getTotalPrice().add(totalPrice));
            DaoFactory.getCartDao().update(cart);
            cartId = cart.getId();
        }

        CartItem cartItem = new CartItem(productId, cartId, createdAt, quantity);
        DaoFactory.getCartItemDao().insert(cartItem);

        Language language = (Language) session.getAttribute("language");
        String addMessage = "Added " + quantity + " " + product.getName() + " to cart";
        if (language == null || language.getName().equals("en"))
            addMessage = "Added " + quantity + " " + product.getName() + " to cart";
        else if (language.getName().equals("ru"))
            addMessage = "Добавлено " + quantity + " " + product.getName() + " в корзину";
        session.setAttribute("addMessage", addMessage);
        response.sendRedirect("/teashop/productList/category?id="+product.getCategoryId());
    }
}
