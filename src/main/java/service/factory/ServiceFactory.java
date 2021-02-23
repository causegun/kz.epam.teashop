package service.factory;

import service.*;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    private static final Map<String, Service> SERVICE_MAP = new HashMap<>();
    private static final ServiceFactory SERVICE_FACTORY = new ServiceFactory();

    private ServiceFactory() {
    }

    static {
        SERVICE_MAP.put("/TEASHOP/LOGIN", new LoginService());
        SERVICE_MAP.put("/TEASHOP/USER/HOME", new RegisterService());
        SERVICE_MAP.put("/TEASHOP/LOGOUT", new LogoutService());
        SERVICE_MAP.put("/TEASHOP/REGISTER", new RegisterPageService());
        SERVICE_MAP.put("/TEASHOP/LANGUAGE", new LanguageService());
        SERVICE_MAP.put("/TEASHOP/CATEGORYLIST", new CategoryListService());
        SERVICE_MAP.put("/TEASHOP/PRODUCTLIST/CATEGORY", new ProductListService());
        SERVICE_MAP.put("/TEASHOP/PRODUCTLIST/ADDTOCART/PRODUCT", new AddToCartService());
        SERVICE_MAP.put("/TEASHOP/CART", new CartService());
        SERVICE_MAP.put("/TEASHOP/CART/DELETE", new CartService());
        SERVICE_MAP.put("/TEASHOP/CART/DELETECART", new CartService());
        SERVICE_MAP.put("/TEASHOP/CART/ORDER", new CartService());
        SERVICE_MAP.put("/TEASHOP/CART/ORDER/ORDERINFO", new CartService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/LOGIN", new AdminLoginService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/LOGOUT", new AdminLogoutService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/USERS", new ShowUsersService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/USERS/NEW", new ShowUsersService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/USERS/INSERT", new ShowUsersService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/USERS/DELETE", new ShowUsersService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/USERS/EDIT", new ShowUsersService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/USERS/UPDATE", new ShowUsersService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/PRODUCTS", new ShowProductsService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/PRODUCTS/NEW", new ShowProductsService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/PRODUCTS/INSERT", new ShowProductsService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/PRODUCTS/DELETE", new ShowProductsService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/PRODUCTS/EDIT", new ShowProductsService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/PRODUCTS/UPDATE", new ShowProductsService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/PRODUCTS/RU", new ShowProductsService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/CATEGORIES", new ShowCategoriesService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/CATEGORIES/NEW", new ShowCategoriesService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/CATEGORIES/INSERT", new ShowCategoriesService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/CATEGORIES/DELETE", new ShowCategoriesService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/CATEGORIES/EDIT", new ShowCategoriesService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/CATEGORIES/UPDATE", new ShowCategoriesService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/CATEGORIES/RU", new ShowCategoriesService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/ORDERS", new ShowOrdersService());
        SERVICE_MAP.put("/TEASHOP/ADMIN/ORDERS/DELETE", new ShowOrdersService());

    }
    public Service getService(String request) {
        Service service;
        service  = SERVICE_MAP.get(request.toUpperCase());
        return service;
    }

    public static ServiceFactory getInstance() {
        return SERVICE_FACTORY;
    }
}
