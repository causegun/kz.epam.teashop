package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminAuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("adminUser") != null);

        String loginURI = httpRequest.getContextPath() + "/admin/login";

        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);

        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");

        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/adminHome.jsp");
            dispatcher.forward(servletRequest, servletResponse);

        } else if (isLoggedIn || isLoginRequest) {
            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/adminLogin.jsp");
            dispatcher.forward(servletRequest, servletResponse);

        }
    }

    @Override
    public void destroy() {

    }
}
