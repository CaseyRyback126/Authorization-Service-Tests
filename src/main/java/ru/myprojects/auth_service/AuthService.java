package ru.myprojects.auth_service;

public interface AuthService {
    User login (String username, String password);
    User register(User user);
    void logout(Long userId);
}
