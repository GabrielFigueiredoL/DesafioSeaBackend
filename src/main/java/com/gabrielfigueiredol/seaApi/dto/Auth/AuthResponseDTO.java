package com.gabrielfigueiredol.seaApi.dto.Auth;

import com.gabrielfigueiredol.seaApi.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    String role;
}
