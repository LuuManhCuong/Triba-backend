package com.backend.triba.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.triba.dto.EmailDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody EmailDTO emailDTO) {
        try {
        	System.out.print("sendemail: "+ emailDTO);
            // Tạo một SimpleMailMessage để cấu hình thông tin email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailDTO.getTo());
            message.setSubject(emailDTO.getSubject());
            message.setText(emailDTO.getText());

            // Gửi email
            emailSender.send(message);
            
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email";
        }
    }
}
