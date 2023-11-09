package org.sasha.controller;

import org.sasha.model.CurrentWeatherCheck;
import org.sasha.model.User;
import org.sasha.service.CurrentWeatherCheckService;
import org.sasha.service.UserService;
import org.sasha.service.impl.CurrentWeatherCheckServiceImpl;
import org.sasha.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginServlet extends HttpServlet {
    public static long ID = 1;
    private final UserService userService = new UserServiceImpl();
    private final CurrentWeatherCheckService currentWeatherCheckService = new CurrentWeatherCheckServiceImpl();

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        if (request.getRequestURI().equals("/login")) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        switch (request.getRequestURI()) {
            case "/login" -> login(request, response);
            case "/logout" -> logout(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void login(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<User> userOpt = userService.findByEmail(email);
        User user = null;
        if (userOpt.isPresent()) {
            user = userOpt.get();
            ID = user.getId();
            if (user.getPass().equals(password)) {
                String region;
//                currentWeatherCheckService.findLastCheckByUser(user).get();
//                if (currentWeatherCheckService.findLastCheckByUser(user).isPresent()) {
//                    CurrentWeatherCheck check = currentWeatherCheckService.findLastCheckByUser(user)
//                            .get();
//                    region = check.getLocation().getRegion();
//                } else {
//                    region = user.getRegion();
//                }
                CurrentWeatherCheck check = user.getLastCheck();
                if (check == null) {
                    region = user.getRegion();
                } else {
                    region = check.getLocation().getRegion();
                }

                session.setAttribute("userId", user.getId());
                session.setAttribute("flash", "Вы успешно вошли");

                String path = request.getContextPath() + "/?town=" + region;
                response.sendRedirect(path);
            }
        } else {
            session.setAttribute("flash", "Неверные логин или пароль");
            response.setStatus(422);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private void logout(HttpServletRequest request,
                        HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        session.setAttribute("flash", "Вы успешно вышли");
        response.sendRedirect("/");
    }
}
