package org.JamesWeb.service.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    @Autowired
    private JavaMailSender emailConfig;
    public void sendApprovalEmail(
            String email,
            String ownerName) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setFrom("hari120320041203@gmail.com");
        message.setTo(email);
        message.setSubject("Merchant Account Approved");

        message.setText(
                "Hello " + ownerName + ",\n\n" +
                        "Your merchant account has been approved successfully.\n\n" +
                        "You can now use our payment gateway services.\n\n" +
                        "Regards,\n" +
                        "Payment Gateway Team"
        );

        emailConfig.send(message);
    }
}
