package com.softvision.digital.login.rest.v1.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CredentialDto {

    @NotBlank(message = "Login Id is required")
    private String loginId;

    @NotBlank(message = "Password is required")
    private String password;

}
