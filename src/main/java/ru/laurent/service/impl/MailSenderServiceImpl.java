package ru.laurent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.laurent.dto.MailParams;
import ru.laurent.service.MailSenderService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableAutoConfiguration
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Value("${ACTIVATION_URI}")
    private String activationServiceUri;
    @Value("${MAIL_URI}")
    private String serviceMailUri;
    @Autowired
    private MailParams mailParams;

    public MailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;

    }


    @Override
    public void send(MailParams mailParams) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
        var subject = "Данные от водителя " + format.format(new Date());
        try {
            sendMimeMessageWithAttachments(
                    subject,
                    emailFrom,
                    mailParams.getEmailTo(),
                    new File(("wb.xlsx")));
        } catch (MailException | MessagingException e) {
            System.out.println(e);
        }
    }


    private void sendMimeMessageWithAttachments(String subject, String from, String to, File source) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(subject);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setReplyTo(from);
        helper.setText("", false);
        helper.addAttachment("wb.xlsx", source);
        javaMailSender.send(message);
    }
}
