package controller;

import exception.ConnectionPoolException;
import exception.DAOException;
import org.apache.log4j.Logger;
import service.Service;
import service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class TeashopController extends HttpServlet {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static Logger logger = Logger.getLogger(TeashopController.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI().toLowerCase();

        Service currentService = serviceFactory.getService(requestUri);

        try {
            currentService.execute(req, resp);
        } catch (NullPointerException | SQLException | ParseException e) {
            logger.error("Error while executing service. Message: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error while executing service. ", e);
        } catch (DAOException | ConnectionPoolException e) {
            e.printStackTrace();
            throw new ServletException("Error while executing service. ", e);
        }
    }
}
