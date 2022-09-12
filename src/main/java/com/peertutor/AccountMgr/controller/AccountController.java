package com.peertutor.AccountMgr.controller;

import com.peertutor.AccountMgr.controller.errors.BadRequestAlertException;
import com.peertutor.AccountMgr.model.enumeration.UserType;
import com.peertutor.AccountMgr.model.viewmodel.request.AccountRegistrationReq;
import com.peertutor.AccountMgr.model.viewmodel.request.AuthenticationReq;
import com.peertutor.AccountMgr.model.viewmodel.response.AccountRegistrationRes;
import com.peertutor.AccountMgr.service.AccountService;
import com.peertutor.AccountMgr.service.dto.AccountDTO;
import com.peertutor.AccountMgr.util.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/account-mgr")
@RequiredArgsConstructor
public class AccountController {
    private static final String ENTITY_NAME = "AccountController";
    @Autowired
    AppConfig appConfig;
    @Autowired
    private AccountService accountService;
    private String applicationName = "AccountMgr";


    // do not remove, for health check...
    @GetMapping(path = "/health")
    public @ResponseBody String healthCheck() {
        return "Ok";
    }

    @GetMapping(path = "/public-api")
    public @ResponseBody String callPublicApi() {
        String endpoint = "https://api.publicapis.org/entries"; //url+":"+port;
        System.out.println("endpoint" + endpoint);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

        return response.toString();
    }

    @GetMapping(path = "/call-app-student-mgr")
    public @ResponseBody String callAppTwo() {
        String url = appConfig.getStudentMgr().get("url");
        String port = appConfig.getStudentMgr().get("port");


        String endpoint = url + "/"; //":"+port + "/";
        System.out.println("endpoint" + endpoint);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

        return response.toString();
    }

    @PostMapping(path = "/account")
    public @ResponseBody ResponseEntity<AccountRegistrationRes> userRegistration(@RequestBody @Valid AccountRegistrationReq req) {

        AccountDTO newUser = new AccountDTO();
        AccountDTO savedUser;

        try {
            UserType userType = UserType.valueOf(req.usertype);
            newUser.setName(req.name);
            newUser.setPassword(req.password);
            newUser.setUserType(userType);
        } catch (IllegalArgumentException e) {
            throw new BadRequestAlertException("invalid user type", ENTITY_NAME, "invalidUserType");
        } finally {
            savedUser = accountService.registerNewUserAccount(newUser);
        }

        AccountRegistrationRes res = new AccountRegistrationRes();
        res.name = savedUser.getName();
        res.sessionToken = savedUser.getSessionToken();
        res.usertype = savedUser.getUserType().toString();

        return savedUser != null ?
                ResponseEntity.ok().body(res) :
                ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @GetMapping(path = "/account")
    public @ResponseBody ResponseEntity<AccountRegistrationRes> userLogin(@RequestBody @Valid AccountRegistrationReq req) {

        AccountDTO newUser = new AccountDTO();
        AccountDTO savedUser;

        try {
            UserType userType = UserType.valueOf(req.usertype);
            newUser.setName(req.name);
            newUser.setPassword(req.password);
            newUser.setUserType(userType);
        } catch (IllegalArgumentException e) {
            throw new BadRequestAlertException("invalid user type", ENTITY_NAME, "invalidUserType");
        } finally {
            savedUser = accountService.loginExistingUserAccount(newUser);
        }

        AccountRegistrationRes res = new AccountRegistrationRes();
        res.name = savedUser.getName();
        res.sessionToken = savedUser.getSessionToken();
        res.usertype = savedUser.getUserType().toString();

        return savedUser != null ?
                ResponseEntity.ok().body(res) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping(path = "/auth")
    public @ResponseBody ResponseEntity<Boolean> userTokenAuthentication(@RequestBody @Valid AuthenticationReq req) {

        boolean result = accountService.hasValidTokenAuthentication(req.name, req.sessionToken);

        return ResponseEntity.ok().body(result);
    }
}
