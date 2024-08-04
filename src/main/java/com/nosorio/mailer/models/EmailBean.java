package com.nosorio.mailer.models;

import lombok.Data;
import org.springframework.core.io.FileSystemResource;

@Data
public class EmailBean {
    private String subject;

    private String body;

    private FileSystemResource file;

    private String attachmentFilename;

    private String destinationAddrs;
}
