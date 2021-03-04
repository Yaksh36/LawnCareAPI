package patel.yaksh.lawn_care_consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RabbitMQ {

    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = "forgot-queue")
    public void sendEmail(Map<String,String> incomingMessage){

        System.out.println("Message Received: " + incomingMessage);

        String email = incomingMessage.get("email");
        String body = incomingMessage.get("body");

        // Create the email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        //mailMessage.setFrom("help@lawncare.com");
        mailMessage.setSubject("Password reset");
        mailMessage.setText(body);

        // Send the email
        mailSender.send(mailMessage);
    }

    @RabbitListener(queues = "request-service-queue")
    public void sendServiceEmail(Map<String,String> incomingMessage){

        System.out.println("Message Received: " + incomingMessage);

        if (incomingMessage.containsKey("requesterEmail") && incomingMessage.containsKey("providerEmail")){

            String requesterEmail = incomingMessage.get("requesterEmail");
            String providerEmail = incomingMessage.get("providerEmail");
            String requesterBody = incomingMessage.get("requesterBody");
            String subject = incomingMessage.get("subject");

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(requesterEmail, providerEmail);
            mailMessage.setFrom("help@lawncare.com");
            mailMessage.setSubject(subject);
            mailMessage.setText(requesterBody);
            mailSender.send(mailMessage);

//            String providerBody = incomingMessage.get("providerBody");
//            SimpleMailMessage mailMessage2 = new SimpleMailMessage();
//            mailMessage.setTo(providerEmail);
//            mailMessage.setFrom("help@lawncare.com");
//            mailMessage.setSubject(subject);
//            mailMessage.setText(providerBody);
//            mailSender.send(mailMessage2);

        }else{
            String email = incomingMessage.get("email");
            String body = incomingMessage.get("body");
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setFrom("help@lawncare.com");
            mailMessage.setSubject("Service created");
            mailMessage.setText(body);
            mailSender.send(mailMessage);
        }
    }

}
