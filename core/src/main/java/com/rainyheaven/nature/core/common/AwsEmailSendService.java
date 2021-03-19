package com.rainyheaven.nature.core.common;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.rainyheaven.nature.core.common.dto.AwsEmailRequest;
import com.rainyheaven.nature.core.common.dto.EmailVerifyNumSendRequestDto;
import com.rainyheaven.nature.core.common.dto.PasswordChangeLinkRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "prod"})
@Slf4j
@RequiredArgsConstructor
public class AwsEmailSendService implements EmailSender {

    private static final String FROM = "GamePush <ljs@rainy-heaven.com>";
    private static final String REPLY_TO = "sonaky47@gmail.com";

    @Override
    public void sendVerifyNum(EmailVerifyNumSendRequestDto emailVerifyNumSendRequestDto) {

        String verifyNum = String.valueOf(emailVerifyNumSendRequestDto.getVerifyNum());
        String textBody = "안녕하세요. 네이처 리퍼블릭입니다. 이메일 인증번호는 " + verifyNum + " 입니다.";
        String textHtml = "<html><body>"
                + "<p style=\"font-size:13px;\">회원님의 이메일 인증번호는 <span style=\"color:#20b4da; font-size:15px; font-weight:700;\">" + verifyNum + "</span> 입니다. <br> 인증번호는 10분 동안만 유효합니다. <p>"
                + "<div style=\"margin-top: 20px; font-size:11px; text-align:left; color:#555\">"
                + "<span>상호명: 네이처 리퍼블릭</span> | <span>주소: 서울특별시 도봉구 김봉박네</span> | <span>대표자: 김덕배</span> <br><span>사업자등록번호: 123-456-78910</span> | <span>통신판매업신고번호: 제2017-서울강동-12345호</span>"
                + "</div>"
                + "</body></html>";

        String subject = "안녕하세요. 네이처 리퍼블릭 인증번호입니다.";
        String to = emailVerifyNumSendRequestDto.getEmail();

        AwsEmailRequest awsEmailRequest = new AwsEmailRequest();
        awsEmailRequest.setFrom(FROM);
        awsEmailRequest.setReplyTo(REPLY_TO);
        awsEmailRequest.setSubject(subject);
        awsEmailRequest.setTo(to);
        awsEmailRequest.setTextBody(textBody);
        awsEmailRequest.setTextHtml(textHtml);

        try {
            send(awsEmailRequest);
        } catch (Exception ex) {
            log.error("[ERROR] EMAIL_SEND_FAILED - " + ex.getMessage());
            throw new RuntimeException("이메일 전송에 실패하였습니다.");
        }



    }

    @Override
    public void sendPasswordChangeLink(PasswordChangeLinkRequestDto passwordChangeLinkRequestDto) {

    }

    private void send(AwsEmailRequest awsEmailRequest) {
        String to = awsEmailRequest.getTo();
        String textHtml = awsEmailRequest.getTextHtml();
        String textBody = awsEmailRequest.getTextBody();
        String subject = awsEmailRequest.getSubject();
        String replyTo = awsEmailRequest.getReplyTo();
        String from = awsEmailRequest.getFrom();

        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).build();
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message().withBody(new Body()
                                .withHtml(new Content().withCharset("UTF-8").withData(textHtml))
                                .withText(new Content().withCharset("UTF-8").withData(textBody))
                        )
                                .withSubject(new Content().withCharset("UTF-8").withData(subject))
                )
                .withReplyToAddresses(replyTo)
                .withSource(from);
        client.sendEmail(request);
    }
}
