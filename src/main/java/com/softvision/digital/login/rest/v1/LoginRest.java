package com.softvision.digital.login.rest.v1;

import com.softvision.digital.common.model.ResultDto;
import com.softvision.digital.common.util.ResultUtil;
import com.softvision.digital.login.rest.v1.model.CredentialDto;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        } else if (loginId.equals("200")) {
            responseEntity = new ResponseEntity(ResultUtil.getSuccess("Login Successful"), HttpStatus.OK);
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

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
