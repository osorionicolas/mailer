package com.nosorio.mailer.models;

import lombok.Data;
import org.springframework.core.io.FileSystemResource;

@Data
public class EmailBean {
    //Asunto del email
    private String subject;

    //contenido del correo electrónico
    private String text;

    private FileSystemResource file;

    private String attachmentFilename;

    private String destinationAddrs;
}
