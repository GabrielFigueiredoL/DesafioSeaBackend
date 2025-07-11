package com.gabrielfigueiredol.seaApi.infra;

import com.gabrielfigueiredol.seaApi.model.User;
import com.gabrielfigueiredol.seaApi.model.UserRole;
import com.gabrielfigueiredol.seaApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RolesInitializer implements CommandLineRunner {

    @Value("${admin.login}")
    private String adminLogin;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${user.login}")
    private String userLogin;

    @Value("${user.password}")
    private String userPassword;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.findByLogin(adminLogin) == null) {
            String encryptedAdminPassword = new BCryptPasswordEncoder().encode(adminPassword);
            User admin = new User(adminLogin, encryptedAdminPassword, UserRole.ADMIN);
            userRepository.save(admin);
        }

        if(userRepository.findByLogin(userLogin) == null) {
            String encryptedUserPassword = new BCryptPasswordEncoder().encode(userPassword);
            User user = new User(userLogin, encryptedUserPassword, UserRole.USER);
            userRepository.save(user);
        }
    }
}