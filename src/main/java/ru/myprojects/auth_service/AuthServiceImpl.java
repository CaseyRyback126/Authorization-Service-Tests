package ru.myprojects.auth_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;


    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            Session session = new Session();
            session.setSessionId(UUID.randomUUID().toString());
            session.setUser(user);
            sessionRepository.save(session);
            return user;
        }
        return null;
    }

    @Override
    public User register(User user) {
        if (userRepository.findByUsername(user.getName()) != null) {
            throw new RuntimeException("Username already exist");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userRepository.save(user);
    }

    @Override
    public void logout(Long userId) {
        User user = new User();
        user.setId(userId);
        List<Session> sessions = sessionRepository.findByUser(user);
        for (Session session : sessions){
            sessionRepository.delete(session);
        }
    }
}
