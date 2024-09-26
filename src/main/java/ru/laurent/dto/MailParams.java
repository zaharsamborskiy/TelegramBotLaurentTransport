package ru.laurent.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.File;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MailParams {
    private String id = "";
    private String emailTo = "";
    private File file = new File("");
}
