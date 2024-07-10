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
  public void sendNewInstallationMail(String to, String customerName)
      throws MessagingException {

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    String htmlContent = "<!DOCTYPE html>"
        + "<html lang=\"en\">"
        + "<head>"
        + "<meta charset=\"UTF-8\">"
        + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
        + "<title>Installation completed</title>"
        + "</head>"
        + "<body style=\"font-family: Arial, sans-serif; "
        + "background-color: #f0f0f0; padding: 20px;\">"
        + "<div style=\"background-color: #ffffff; padding: 30px; "
        + "border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
        + "<h2 style=\"color: #333; margin-bottom: 20px;\">Hello " + customerName + ",</h2>"
        + "<p style=\"font-size: 16px; color: #666; line-height: 1.6;\">"
        + "Your installation was completed successfully!</p>"
        + "<p style=\"font-size: 14px; color: #888;\">Thank you for choosing our service!</p>"
        + "</div>"
        + "</body>"
        + "</html>";

    helper.setTo(to);
    helper.setSubject("Installation completed!");
    helper.setText(htmlContent, true);

    mailSender.send(message);
  }
}