package service;

import dao.factory.DaoFactory;

import entity.Language;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Locale;

public class LanguageService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        long id = Integer.parseInt(request.getParameter("id"));
        Language language = DaoFactory.getLanguageDao().get(id);
        Locale.setDefault(new Locale(language.getName()));
        HttpSession session = request.getSession();
        session.setAttribute("language", language);
        session.removeAttribute("noCartMessage");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
