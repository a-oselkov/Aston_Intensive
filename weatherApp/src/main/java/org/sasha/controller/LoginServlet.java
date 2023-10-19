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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static org.sasha.utils.Request.getApiUrl;

public class LoginServlet extends HttpServlet {
    public static long ID;
    private final UserService userService = new UserServiceImpl();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (ID == 0) {
            HttpSession session = request.getSession();
            request.getRequestDispatcher("/login.html").forward(request, response);
        }
        String email = request.getParameter("login");
        String pass = request.getParameter("password");
        String passs = request.getContextPath();

//        String path = request.getContextPath() + "/login?login=" + email + "&password=" + pass;
//        response.sendRedirect(path);

        Optional<User> userOpt = userService.findByEmail(email);
        User user;
        if (userOpt.isPresent()) {
            user = userService.findByEmail(email).get();
            ID = user.getId();
            if (user.getPass().equals(pass)) {
                String region = userService.findLastCheck(user.getId()).orElse(user.getRegion());
                String path1 = request.getContextPath() + "/?town=" + region;
                response.sendRedirect(path1);
                //request.getRequestDispatcher("/index.html").forward(request, response);
            }
        } else {
            PrintWriter out = response.getWriter();
            out.write("LOGIN/PASSWORD ERROR");

        }


//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/view.jsp");
//        requestDispatcher.forward(request, response);
//


//        session.setAttribute("w", weatherData);
//        request.getRequestDispatcher("/index.html").forward(request, response);
    }

}
