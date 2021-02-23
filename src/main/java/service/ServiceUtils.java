package service;

import entity.Language;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceUtils {

    private final static Logger logger = Logger.getLogger(ServiceUtils.class);

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("(?:[a-z0-9!#$%&'*+\\=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\=?^_`{|}~-]+)*|\"" +
                    "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\" +
                    "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
                    "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.)" +
                    "{3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:" +
                    "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\" +
                    "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("^\\+?77([0124567][0-8]\\d{7})$");

    private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z]{6,}$");

    static boolean validateUser(String email, String phoneNumber, String userPassword, HttpServletRequest request, HttpServletResponse response, String pathIfNotValid) throws ServletException, IOException {
        Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        Matcher phoneNumberMatcher = VALID_PHONE_NUMBER_REGEX.matcher(phoneNumber);
        Matcher passwordMatcher = VALID_PASSWORD_REGEX.matcher(userPassword);
        HttpSession session = request.getSession();
        boolean isValid = true;

        if (!emailMatcher.find()) {
            String invalidEmailMessageEn = "Email should be like this: example@mail.com";
            String invalidEmailMessageRu = "Эл. адрес должен быть как: example@mail.com";
            String attribute = "invalidEmailMessage";

            setIfInvalidMessage(invalidEmailMessageEn, invalidEmailMessageRu, attribute, request, session);

            RequestDispatcher dispatcher = request.getRequestDispatcher(pathIfNotValid);
            dispatcher.forward(request, response);

            isValid = false;

        } else if (!passwordMatcher.find()) {
            String invalidPasswordMessageEn = "Password must be English, contain at least one letter, one number, and be longer than six characters";
            String invalidPasswordMessageRu = "Пароль должен быть на анлгийском, содержать минимум шесть символов, из которых хотя бы одна буква и одна цифра";
            String attribute = "invalidPasswordMessage";

            setIfInvalidMessage(invalidPasswordMessageEn, invalidPasswordMessageRu, attribute, request, session);

            RequestDispatcher dispatcher = request.getRequestDispatcher(pathIfNotValid);
            dispatcher.forward(request, response);

            isValid = false;
        } else if (!phoneNumberMatcher.find()) {
            String invalidPhoneNumberMessageEn = "Phone number should be like this: +77XX-XXX-XX-XX or 77XX-XXX-XX-XX ";
            String invalidPhoneNumberMessageRu = "Тел. номер должен быть как: +77XX-XXX-XX-XX or 77XX-XXX-XX-XX";
            String attribute = "invalidPhoneNumberMessage";

            setIfInvalidMessage(invalidPhoneNumberMessageEn, invalidPhoneNumberMessageRu, attribute, request, session);

            RequestDispatcher dispatcher = request.getRequestDispatcher(pathIfNotValid);
            dispatcher.forward(request, response);

            isValid = false;
        }
        return isValid;
    }

    static boolean validateUser(String email, String phoneNumber, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        Matcher phoneNumberMatcher = VALID_PHONE_NUMBER_REGEX.matcher(phoneNumber);
        HttpSession session = request.getSession();
        boolean isValid = true;

        if (!emailMatcher.find()) {
            String invalidEmailMessageEn = "Email should be like this: example@mail.com";
            String invalidEmailMessageRu = "Эл. адрес должен быть как: example@mail.com";
            String attribute = "invalidEmailMessage";

            setIfInvalidMessage(invalidEmailMessageEn, invalidEmailMessageRu, attribute, request, session);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/userForm.jsp");
            dispatcher.forward(request, response);

            isValid = false;

        } else if (!phoneNumberMatcher.find()) {
            String invalidPhoneNumberMessageEn = "Phone number should be like this: +77XX-XXX-XX-XX or 77XX-XXX-XX-XX ";
            String invalidPhoneNumberMessageRu = "Тел. номер должен быть как: +77XX-XXX-XX-XX or 77XX-XXX-XX-XX";
            String attribute = "invalidPhoneNumberMessage";

            setIfInvalidMessage(invalidPhoneNumberMessageEn, invalidPhoneNumberMessageRu, attribute, request, session);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/userForm.jsp");
            dispatcher.forward(request, response);

            isValid = false;
        }
        return isValid;
    }

    static String hashPassword(String password) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error while hashing password. Message: " + e.getMessage());
            e.printStackTrace();
        }

        byte[] messageBytes = new byte[0];

        if (messageDigest != null) {
            messageBytes = messageDigest.digest(password.getBytes());
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : messageBytes) {
            stringBuilder.append(String.format("%02X", b));
        }
        return stringBuilder.toString();
    }

    static void setIfInvalidMessage(String messageEn, String messageRu, String attribute, HttpServletRequest request, HttpSession session) {
        Language language = (Language) session.getAttribute("language");
        String message = null;
        String languageName = null;

        if (language != null)
            languageName = language.getName();

        if (languageName == null || languageName.equals("en"))
            message = messageEn;

        else if (languageName.equals("ru"))
            message = messageRu;

        request.setAttribute(attribute, message);
    }
}
