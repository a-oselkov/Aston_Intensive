package org.sasha.controller;

import org.sasha.Model.User;
import org.sasha.dto.TownWeatherDto;
import org.sasha.dto.WeatherDto.CurrentDto;
import org.sasha.dto.WeatherDto.LocationDto;
import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.service.CurrentWeatherService;
import org.sasha.service.UserService;
import org.sasha.service.impl.CurrentWeatherServiceImpl;
import org.sasha.service.impl.UserServiceImpl;
import org.sasha.utils.Formater;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;

import static org.sasha.utils.Request.getApiUrl;

public class LoginServlet extends HttpServlet {
    public static long ID;
    private final UserService userService = new UserServiceImpl();

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
            user = userService.findByEmail(email).get();
            ID = user.getId();
            if (user.getPass().equals(password)) {
                String region = userService.findLastCheck(user.getId()).orElse(user.getRegion());

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
