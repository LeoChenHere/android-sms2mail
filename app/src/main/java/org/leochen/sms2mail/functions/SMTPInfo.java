package org.leochen.sms2mail.functions;

import lombok.Getter;

public class SMTPInfo {
    @Getter
    private static final String SMTP_HOST = "smtp.163.com";
    @Getter
    private static final int SMTP_PORT = 994;
    @Getter
    private static final String SMTP_USERNAME = "xxx@163.com";
    @Getter
    private static final String SMTP_PASSWORD = "xxx";
    @Getter
    private static final String SMTP_FROM = "xxx@163.com";
}
