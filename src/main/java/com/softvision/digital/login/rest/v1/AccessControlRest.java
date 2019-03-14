package com.softvision.digital.login.rest.v1;

import com.softvision.digital.common.model.ResultDto;
import com.softvision.digital.common.util.ResultUtil;
import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(value = "api/access/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "All operations pertaining to ACL")
public class AccessControlRest {

    @GetMapping("/{role}")
    public ResponseEntity<String> getAccessList(@PathVariable String role) throws IOException {
        ResponseEntity responseEntity;
        if (role.equalsIgnoreCase("ADMIN")) {
            File file = ResourceUtils.getFile("classpath:menu/" + role + ".json");
            String menuContent = FileUtils.readFileToString(file, "UTF-8");
            responseEntity = new ResponseEntity(menuContent, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    /*@GetMapping("/{role}")
    public ResponseEntity<ResultDto> getAccessList(@PathVariable String role) throws IOException {

        ResponseEntity responseEntity;
        if (role.equalsIgnoreCase("ADMIN")) {
            File file = ResourceUtils.getFile("classpath:menu/" + role + ".json");
            String menuContent = FileUtils.readFileToString(file, "UTF-8");
            ResultDto resultDto = ResultUtil.getSuccess("Successful", menuContent);
            responseEntity = new ResponseEntity(resultDto, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity(ResultUtil.getFailure("Invalid role"), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }*/

}
