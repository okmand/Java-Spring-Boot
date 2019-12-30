package com.netcracker.controller;

import com.netcracker.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SimpleEmailExampleController {

    @Autowired
    public JavaMailSender emailSender;

    @PostMapping("/sendSimpleEmail")
    public String sendSimpleEmail(@ModelAttribute Greeting greeting, Model model) {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        String emailName = greeting.getEmail();
        message.setTo(emailName);
        message.setSubject("IT'S FINALLY WORKING");
        message.setText("Hello from my server. It is testing Simple Email");

        // Send Message!
        this.emailSender.send(message);
        model.addAttribute("emailName", emailName);
        return "sent";
    }
}