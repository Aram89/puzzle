package com.music.puzzle.service;

import com.music.puzzle.domain.Mail;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.repository.MailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender emailSender;
    private final MailRepo repo;

    @Autowired
    public MailService(JavaMailSender emailSender, MailRepo repo) {
        this.emailSender = emailSender;
        this.repo = repo;
    }

    public void sendEmail(String to, String subject, String content) throws AppException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            emailSender.send(message);
        } catch (MailException e) {
            throw new AppException(e.getCause().getMessage());
        }

    }

    public void saveMail(Mail mail) {
        repo.save(mail);
    }

}
