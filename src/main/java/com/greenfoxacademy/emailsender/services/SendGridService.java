package com.greenfoxacademy.emailsender.services;

import com.greenfoxacademy.emailsender.model.EmailPojo;

public interface SendGridService {
    public String sendMail(EmailPojo emailPojo);
}
