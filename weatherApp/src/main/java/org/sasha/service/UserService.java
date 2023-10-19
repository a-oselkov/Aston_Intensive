package org.sasha.service;

import org.sasha.Model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    Optional<String> findLastCheck(Long id);
}
