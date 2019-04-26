package com.softvision.digital.login.rest.v1;

import com.softvision.digital.common.model.ResultDto;
import com.softvision.digital.common.util.ResultUtil;
import com.softvision.digital.login.rest.v1.model.CredentialDto;
import com.softvision.digital.login.rest.v1.model.UserDto;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "api/login/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "All operations pertaining to sample")
public class LoginRest {

    @PostMapping("/")
    public ResponseEntity<ResultDto> login(@RequestBody CredentialDto credential) {
        String loginId = credential.getLoginId();
        String password = credential.getPassword();
        sleep();
        ResponseEntity responseEntity;
        if (!password.equalsIgnoreCase("#password")) {
            responseEntity = new ResponseEntity(ResultUtil.getFailure("Please provide login Id and password"), HttpStatus.BAD_REQUEST);
        } else if (loginId.equals("someother_role") || loginId.equals("invoice_viewer") || loginId.equals("invoice_editor")) {
            String roleName = loginId.toUpperCase();
            setAuthentication(roleName);
            responseEntity = new ResponseEntity(ResultUtil.getSuccess("Login Successful", getUserDto(roleName)), HttpStatus.OK);
        } else if (loginId.equals("400")) {
            responseEntity = new ResponseEntity(ResultUtil.getFailure("Please provide valid credentials"), HttpStatus.BAD_REQUEST);
        } else if (loginId.equals("401")) {
            responseEntity = new ResponseEntity(ResultUtil.getFailure("Invalid Credentials"), HttpStatus.UNAUTHORIZED);
        } else if (loginId.equals("404")) {
            responseEntity = new ResponseEntity(ResultUtil.getFailure("Requested user could not be found"), HttpStatus.NOT_FOUND);
        } else if (loginId.equals("500")) {
            responseEntity = new ResponseEntity(ResultUtil.getFailure("Technical Error. Something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            responseEntity = new ResponseEntity(ResultUtil.getFailure("Unknown Error. Something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    private void setAuthentication(String roleName) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(roleName));
        Authentication auth = new UsernamePasswordAuthenticationToken(roleName, "#password", authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private UserDto getUserDto() {
        return getUserDto(null);
    }

    private UserDto getUserDto(String roleName) {
        UserDto user = new UserDto();
        user.setDesignation("Technical Manager");
        user.setFirstName("Srikanth");
        user.setLastName("Ragi");
        user.setLoginId("srikanth.ragi");
        user.setImageUrl("/images/users/1136.jpg");
        //user.setRoles(new HashSet(Arrays.asList("PROJECT_MANAGER", "APPRAISING_MANAGER")));
        user.setRoles(new HashSet(Arrays.asList(roleName)));
        user.setDisplayName(user.getFirstName() + " " + user.getLastName());
        user.setAuthToken("VALID_TOKEN");
        return user;
    }

    @GetMapping("/{jwtToken}")
    public ResponseEntity<ResultDto> validate(@PathVariable("jwtToken") String jwtToken) {
        ResponseEntity responseEntity;
        if (jwtToken.equals("VALID_TOKEN")) {
            responseEntity = new ResponseEntity(ResultUtil.getSuccess("Valid Token"), HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity(ResultUtil.getSuccess("INVALID_TOKEN"), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
