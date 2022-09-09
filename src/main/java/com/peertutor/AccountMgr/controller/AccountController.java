package com.peertutor.AccountMgr.controller;

import com.peertutor.AccountMgr.controller.errors.BadRequestAlertException;
import com.peertutor.AccountMgr.model.enumeration.UserType;
import com.peertutor.AccountMgr.model.viewmodel.request.AccountRegistrationReq;
import com.peertutor.AccountMgr.service.AccountService;
import com.peertutor.AccountMgr.service.dto.AccountDTO;
import com.peertutor.AccountMgr.util.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@Controller
@RequestMapping(path="/account-mgr")
@RequiredArgsConstructor
public class AccountController {
    @Autowired
    AppConfig appConfig;

    @Autowired
    private AccountService accountService;

    private static final String ENTITY_NAME = "AccountController";
    private String applicationName = "AccountMgr";

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path="/public-api")
    public @ResponseBody String callPublicApi() {
        String endpoint = "https://api.publicapis.org/entries"; //url+":"+port;
        System.out.println("endpoint" + endpoint);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

        return response.toString();
    }

    @GetMapping(path="/call-app-student-mgr")
    public @ResponseBody String callAppTwo() {
        String url = appConfig.getStudentMgr().get("url");
        String port = appConfig.getStudentMgr().get("port");

        String endpoint = url+":"+port + "/";
        System.out.println("endpoint" + endpoint);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

        return response.toString();
    }

    @PostMapping(path = "/account")
    public @ResponseBody ResponseEntity<AccountDTO> addNewUser(@RequestBody @Valid AccountRegistrationReq req) {

        AccountDTO customer = new AccountDTO();

        try {
            UserType userType = UserType.valueOf(req.usertype);
            customer.setName(req.name);
            customer.setPassword(req.password);
        } catch (IllegalArgumentException e) {
            throw new BadRequestAlertException("invalid user type", ENTITY_NAME, "invalidUserType");
        }

        AccountDTO account = accountService.saveAccount(customer);

        return ResponseEntity.ok().body(account);
    }
}
