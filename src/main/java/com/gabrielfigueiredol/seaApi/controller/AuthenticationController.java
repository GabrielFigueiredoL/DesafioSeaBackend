package com.gabrielfigueiredol.seaApi.controller;

import com.gabrielfigueiredol.seaApi.dto.auth.AuthResponseDTO;
import com.gabrielfigueiredol.seaApi.dto.auth.AuthenticationDTO;
import com.gabrielfigueiredol.seaApi.infra.security.TokenService;
import com.gabrielfigueiredol.seaApi.model.User;
import com.gabrielfigueiredol.seaApi.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO loginData, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(loginData.getLogin(), loginData.getPassword());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateAuthToken((User) auth.getPrincipal());
        UserRole userRole = ((User) auth.getPrincipal()).getRole();

        String cookieValue = String.format("token=%s; Max-Age=%d; Path=/; HttpOnly; SameSite=None%s",
                token, 18000, "; Secure");

        response.setHeader("Set-Cookie", cookieValue);

        return ResponseEntity.ok(new AuthResponseDTO(userRole.getRole()));
    }
}
