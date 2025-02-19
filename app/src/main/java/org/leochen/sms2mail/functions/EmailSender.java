package org.leochen.sms2mail.functions;

import android.util.Log;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    private static final String TAG = EmailSender.class.getSimpleName() ;

    public static void sendEmail(String to, String subject, String body) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", SMTPInfo.getSMTP_HOST());
        props.put("mail.smtp.port", SMTPInfo.getSMTP_PORT());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTPInfo.getSMTP_USERNAME(), SMTPInfo.getSMTP_PASSWORD());
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTPInfo.getSMTP_FROM()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    public static boolean send(String to, String subject, String msgBody){
        if(msgBody == null){
            Log.d(TAG, "No Message Body, Do Not Send Mail.");
            return false;
        }
        try {
            EmailSender.sendEmail(to, subject, msgBody);
            Log.d(TAG, "Email sent successfully.");
            return true;
        } catch (MessagingException e) {
            Log.d(TAG, "Failed to send email.");
            e.printStackTrace();
            return false;
        }

    }
}
