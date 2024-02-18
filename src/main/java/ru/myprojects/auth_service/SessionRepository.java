package ru.myprojects.auth_service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session,Long> {
    Session findBySessionId(String sessionId);
    List<Session> findByUser(User user);
}
