package filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    public static final String CODE_ENCODING = "UTF-8";
    public static final String CONFIG_CONTEXT_TYPE = "text/html; charset=UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        servletResponse.setContentType(CONFIG_CONTEXT_TYPE);
        servletRequest.setCharacterEncoding(CODE_ENCODING);
        servletResponse.setCharacterEncoding(CODE_ENCODING);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
