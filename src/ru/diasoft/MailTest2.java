package ru.diasoft;

import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailTest2 {

    public void test1(String to, String from, String host, String msgText, 
            String subject, String login, String password) {
        // create some properties and get the default Session
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.port", "465");
//        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        
        props.put("mail.debug", "true");

        Session session = Session.getInstance(props, null);
//        session.setDebug(debug);

        try {
            // create a message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            // If the desired charset is known, you can use
            // setText(text, charset)
            msg.setText(msgText, "utf-8");

//            Transport.send(msg);
            SMTPTransport t
                    = (SMTPTransport) session.getTransport("smtp");
            try {
                t.connect(host, login, password);
//		    t.connect();
                t.sendMessage(msg, msg.getAllRecipients());
            } finally {
                System.out.println("Response: "
                        + t.getLastServerResponse());
                t.close();
            }
        } catch (MessagingException mex) {
            System.out.println("\n--Exception handling in msgsendsample.java");

            mex.printStackTrace();
            System.out.println();
            Exception ex = mex;
            do {
                if (ex instanceof SendFailedException) {
                    SendFailedException sfex = (SendFailedException) ex;
                    Address[] invalid = sfex.getInvalidAddresses();
                    if (invalid != null) {
                        System.out.println("    ** Invalid Addresses");
                        for (int i = 0; i < invalid.length; i++) {
                            System.out.println("         " + invalid[i]);
                        }
                    }
                    Address[] validUnsent = sfex.getValidUnsentAddresses();
                    if (validUnsent != null) {
                        System.out.println("    ** ValidUnsent Addresses");
                        for (int i = 0; i < validUnsent.length; i++) {
                            System.out.println("         " + validUnsent[i]);
                        }
                    }
                    Address[] validSent = sfex.getValidSentAddresses();
                    if (validSent != null) {
                        System.out.println("    ** ValidSent Addresses");
                        for (int i = 0; i < validSent.length; i++) {
                            System.out.println("         " + validSent[i]);
                        }
                    }
                }
                System.out.println();
                if (ex instanceof MessagingException) {
                    ex = ((MessagingException) ex).getNextException();
                } else {
                    ex = null;
                }
            } while (ex != null);
        }
    }

    public static void main(String[] args) {

        new MailTest2().test1("mironovichsa@ya.ru",
                "mironovichsa@ya.ru",
                "smtp.yandex.ru",
                "Проверочное сообщение "  + new Date(),
                "Сообщение от xmlBlaster",
                "mironovichsa", "");
/*
        new MailTest2().test1("mironovichs@mail.ru",
                "mironovichs@mail.ru",
                "smtp.mail.ru",
                "Проверочное сообщение "  + new Date(),
                "Сообщение от xmlBlaster",
                "mironovichs", "");
        */
    }

}
