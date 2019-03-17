package com.music.puzzle.controller;

import com.music.puzzle.domain.Mail;
import com.music.puzzle.exception.AppException;
import com.music.puzzle.service.MailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api
@Controller
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping
    public ResponseEntity send(@RequestBody Mail mail) throws AppException {
        mailService.saveMail(mail);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
