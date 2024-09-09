package com.springanything.mail;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MailHelper {

  private final JavaMailSender javaMailSender;
  private final CustomMailProperties customMailProperties;
  private final SpringTemplateEngine springTemplateEngine;

  public void send(
    String title,
    String to,
    String templateName,
    Map<String, String> values
  ) throws MessagingException, UnsupportedEncodingException {

    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setFrom(customMailProperties.getAddress(), customMailProperties.getPersonal());
    helper.setTo(to);
    helper.setSubject(title);

    Context context = new Context();
    values.forEach(context::setVariable);
    helper.setText(springTemplateEngine.process(templateName, context), true);

    javaMailSender.send(message);
  }
}
