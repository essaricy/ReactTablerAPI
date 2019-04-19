package com.softvision.digital.login.rest.v1.model;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private String loginId;

    private String firstName;

    private String lastName;

    private String displayName;

    private String designation;

    private String imageUrl;

    private Set<String> roles;

    private String authToken;

}
