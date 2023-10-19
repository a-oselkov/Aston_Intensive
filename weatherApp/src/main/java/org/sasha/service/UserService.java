package org.sasha.service;

import org.sasha.Model.User;
import org.sasha.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    Optional<String> findLastCheck(Long id);
    void save(UserDto dto);
    public List<User> findAll();
}
