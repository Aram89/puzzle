package com.music.puzzle.repository;

import com.music.puzzle.domain.Mail;
import org.springframework.data.repository.CrudRepository;

public interface MailRepo extends CrudRepository<Mail, Long> {
}
