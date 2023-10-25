package org.sasha.controller;

import org.sasha.model.User;
import org.sasha.service.UserService;
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
                String region = "tver";
                        //userService.findLastCheck(user.getId()).orElse(user.getRegion());

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
