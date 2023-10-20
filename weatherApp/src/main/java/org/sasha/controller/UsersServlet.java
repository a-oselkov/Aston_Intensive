package org.sasha.controller;

import org.apache.commons.lang3.ArrayUtils;
import org.sasha.Model.CurrentWeather;
import org.sasha.Model.User;
import org.sasha.dto.UserDto;
import org.sasha.service.UserService;
import org.sasha.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UsersServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "list":
                showUsers(request, response);
                break;
            case "show":
                showUser(request, response);
                break;
            case "new":
                newUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "new":
                createUser(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return "list";
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 1, "");
    }

    private String getId(HttpServletRequest request) {
        return request.getParameter("id");
    }

    private void showUsers(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException, ServletException {

        request.setAttribute("users", userService.findAll());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/users.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showUser(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        Long id = Long.valueOf(getId(request));

        User user = userService.findById(id).get();
        List<CurrentWeather> checks = userService.findAllCheckById(id);

        request.setAttribute("user", user);
        request.setAttribute("checks", checks);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/show.jsp");
        requestDispatcher.forward(request, response);
    }

    private void newUser(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/new.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createUser(HttpServletRequest request,
                            HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();

        String name = request.getParameter("name");
        String region = request.getParameter("region");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        UserDto dto = new UserDto(name, region, email, pass);

        if (name.isEmpty() || region.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/new.jsp");
            request.setAttribute("user", dto);
            session.setAttribute("flash", "Поля не должны быть пустыми");
            response.setStatus(422);
            requestDispatcher.forward(request, response);
            return;
        }

        userService.save(dto);
        session.setAttribute("flash", "Пользователь успешно создан");
        response.sendRedirect("/users");
    }

    private void deleteUser(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();

        Long id = Long.valueOf(getId(request));

        User user = userService.findById(id).get();

        userService.deleteById(id);
        session.setAttribute("flash", "Пользователь успешно удален");
        response.sendRedirect("/users");
    }
}
