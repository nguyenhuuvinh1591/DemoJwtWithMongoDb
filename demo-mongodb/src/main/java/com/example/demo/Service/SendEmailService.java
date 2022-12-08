package com.example.demo.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.Contact;
import com.example.demo.constant.CommonConstant;

@Service
@Transactional(rollbackFor = Exception.class)
public class SendEmailService {
    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private ContactService contactService;

    public void sendEmail(String receiver, String content, String subject) {
        try {
            // Create a Simple MailMessage.
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(CommonConstant.MY_EMAIL);
            message.setTo(receiver);
            message.setSubject(subject);
            message.setText(content);
            // Send Message!
            this.emailSender.send(message);
        } catch (Exception e) {
        }
    }

    public static boolean validateEmailIsValid(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public void sendAllEmailInContact(Integer number, String content, String subject) {
        List<Contact> listContact = contactService.getAllContacts();
        if (CollectionUtils.isNotEmpty(listContact)) {
            listContact.forEach(item -> {
                // validate email is valid
                if (validateEmailIsValid(item.getEmail())) {
                    for (int i = 0; i < number; i++) {
                        sendEmail(item.getEmail(), content, subject);
                    }
                }
            });
        }
    }
}
