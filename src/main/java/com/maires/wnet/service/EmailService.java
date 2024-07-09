package com.maires.wnet.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * The type Email service.
 */
@Service
public class EmailService {

  private final JavaMailSender mailSender;

  /**
   * Instantiates a new Email service.
   *
   * @param mailSender the mail sender
   */
  @Autowired
  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  /**
   * Send new installation completed.
   *
   * @param to           the to
   * @param customerName the customer name
   * @throws MessagingException the messaging exception
   */
  public void sendNewInstallationCompleted(String to, String customerName)
      throws MessagingException {

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo(to);
    helper.setSubject("Installation completed!");
    helper.setText("Hello " + customerName + ", your installation was completed!");

    mailSender.send(message);
  }
}