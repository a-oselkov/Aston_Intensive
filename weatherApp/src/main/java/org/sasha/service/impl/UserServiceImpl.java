package org.sasha.service.impl;

import org.sasha.model.CurrentWeather;
import org.sasha.model.User;
import org.sasha.dao.UserDao;
import org.sasha.dto.UserDto;
import org.sasha.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDao();

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
//
//    @Override
//    public Optional<String> findLastCheck(Long id) {
//        return userDao.findLastCheck(id);
//    }

    @Override
    public void save(UserDto dto) {
        userDao.save(dto);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
//
//    @Override
//    public List<CurrentWeather> findAllCheckById(Long id) {
//        return userDao.findAllCheckById(id);
//    }
}
