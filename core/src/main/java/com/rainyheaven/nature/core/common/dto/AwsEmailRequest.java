package com.rainyheaven.nature.core.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwsEmailRequest {

    private String from;
    private String replyTo;
    private String to;
    private String subject;
    private String textBody;
    private String textHtml;

}
