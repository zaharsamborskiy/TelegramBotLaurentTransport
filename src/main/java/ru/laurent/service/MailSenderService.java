package ru.laurent.service;


import ru.laurent.dto.MailParams;


public interface MailSenderService {
    void send(MailParams mailParams);
}
