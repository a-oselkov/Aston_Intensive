package org.sasha.service.impl;

import org.sasha.Model.User;
import org.sasha.dao.CurrentWeatherDao;
import org.sasha.dao.UserDao;
import org.sasha.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDao();

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<String> findLastCheck(Long id) {
        return userDao.findLastCheck(id);
    }
}
