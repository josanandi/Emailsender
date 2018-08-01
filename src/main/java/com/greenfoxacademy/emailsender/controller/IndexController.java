package com.greenfoxacademy.emailsender.controller;

import com.greenfoxacademy.emailsender.model.EmailPojo;
import com.greenfoxacademy.emailsender.services.SendGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Autowired
    SendGridService sendGridService;

    @RequestMapping(value = "/email/", method = RequestMethod.POST)
    public String index(@RequestBody EmailPojo emailPojo) {
        String response = sendGridService.sendMail(emailPojo);
        return response;
    }

}
