package com.music.puzzle.service;


public interface MailService {

    void sendEmail(String to, String subject, String content);
}
