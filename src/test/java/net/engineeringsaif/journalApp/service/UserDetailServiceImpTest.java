package net.engineeringsaif.journalApp.service;

import net.engineeringsaif.journalApp.entity.User;
import net.engineeringsaif.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailServiceImpTest {

    @InjectMocks
    private UserDetailServiceImp userDetailService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUserNameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram").password("inricksj").roles(new ArrayList<>()).build());
        UserDetails user = userDetailService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }
}
