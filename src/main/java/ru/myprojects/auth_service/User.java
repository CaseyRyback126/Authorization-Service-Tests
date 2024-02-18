package ru.myprojects.auth_service;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String name;
    @Column(nullable = false, name = "password")
    private String password;

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String hashpw) {
        this.password = hashpw;
    }

    public void setId(Long userId) {
        this.id = userId;
    }
}
