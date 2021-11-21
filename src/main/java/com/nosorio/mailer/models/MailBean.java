package com.nosorio.mailer.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailBean {
    //Asunto del email
    private String subject;

    //contenido del correo electrónico
    private String text;

    /*Accesorios
    //private FileSystemResource file;

    //nombre del accesorio
    private String attachmentFilename;*/

    private String destinationAddrs;
}
