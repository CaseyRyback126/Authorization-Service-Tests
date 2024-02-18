package ru.myprojects.auth_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private WebApplicationContext wac;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private SessionRepository sessionRepository;
    private MockHttpSession session;
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        this.session = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
        this.authService = new AuthServiceImpl();
    }

    @Test
    public void testRegisterSuccess() {
        User user = new User();
        user.setName("testUser");
        user.setPassword("testPass");

        when(userRepository.save(user)).thenReturn(user);

        authService.register(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setName("testUser");
        user.setPassword("testPass");

        when(userRepository.findByUsername(user.getName()))
                .thenReturn(user);

        Session session = new Session();
        session.setUser(user);

        when(sessionRepository.save(any()))
                .thenReturn(session);

        authService.login(user.getName(), user.getPassword());

        verify(sessionRepository, times(1)).save(any());
    }

    @Test
    public void testLogoutSuccess() {
        User user = new User();
        user.setId(1L);

        when(sessionRepository.findBySessionId(session.getId()))
                .thenReturn(new Session());

        authService.logout(user.getId());

        verify(sessionRepository, times(1)).delete(any());
    }

}
