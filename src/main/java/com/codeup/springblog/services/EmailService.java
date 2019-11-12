package com.codeup.springblog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired //same as dependency injection without added it to controller
    public JavaMailSender emailSender;


}
