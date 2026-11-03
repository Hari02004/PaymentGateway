package org.JamesWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MerchantEmailService {
    @Value("${admin.email}")
    String adminEmail;
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendApprovalEmail(
            String ownerName) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setFrom("hari120320041203@gmail.com");
        message.setTo(adminEmail);
        message.setSubject("Merchant Account is waiting for Approval");

        message.setText(
                "Hello " + ownerName + ",\n\n" +
                        "Dear admin Merchant account has been created.\n\n" +
                        "Admin need to update the status to \"Approved\".\n\n" +
                        "Regards,\n" +
                        "Payment Gateway Team"
        );
        javaMailSender.send(message);
    }
}
