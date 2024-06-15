package com.email.test;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class EmailTestApplication {

  private final JavaMailSender emailSender;

  public static void main(String[] args) {
    SpringApplication.run(EmailTestApplication.class, args);
  }

  boolean sendEmail(String recipient) {
    try {
      MimeMessage mimeMessage = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
      helper.setFrom("email.from@gmail.com");
      helper.setTo(recipient);
      helper.setSubject("Welcome to mailpit email server");
      helper.setText("You are seeing this message means it's working");
      emailSender.send(mimeMessage);
      return true;
    } catch (Exception e) {
      log.error("Error while sending email", e);
    }
    return false;
  }

  @GetMapping("/sendEmail")
  public String wish(@RequestParam String email) {
    if (!sendEmail(email)) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return "check your email here http://localhost:8025";
  }
}
