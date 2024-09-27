package ru.laurent.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MailParams {
    @Value("${env_id}")
    private String id;
    @Value("${env_username}")
    private String emailTo;
    @Value("${env_path}")
    private File file;
}
