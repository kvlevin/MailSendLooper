package com.levin;

import com.levin.dao.MailDao;
import org.apache.ibatis.session.SqlSession;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class Worker implements Runnable {
    @Override
    public void run() {
        do {
            Sender sender = Sender.getSender();
            if (sender == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Email instance = Email.getInstance();
                TargetEmail targetEmail = TargetEmail.getTargetEmail(instance.getId());
                if (targetEmail == null) {
                    System.out.println("邮件已发送完");
                    break;
                }


                // Assuming you are sending email from localhost


                // Get system properties
                Properties properties = System.getProperties();

                // Setup mail server
                properties.put("mail.transport.protocol", "smtp");
                properties.setProperty("mail.smtp.host", sender.getSend_server());
                properties.put("mail.smtp.auth", "true");
                properties.setProperty("mail.user", sender.getEmail());
                properties.setProperty("mail.password", sender.getPassword());

                Authenticator auth = new SMTPAuthenticator(sender.getEmail(), sender.getPassword());

                // Get the default Session object.
                Session session = Session.getDefaultInstance(properties, auth);

                Exception ex = null;
                try {
                    InputStream inputStream = new FileInputStream(instance.getContentFile());
                    // Create a default MimeMessage object.
                    MimeMessage message = new MimeMessage(session, inputStream);
                    // Set From: header field of the header.
                    message.setFrom(new InternetAddress(sender.getEmail()));
                    // Set To: header field of the header.

                    message.setRecipient(Message.RecipientType.TO, new InternetAddress(targetEmail.getEmail()));
                    // Send message
                    Transport.send(message,sender.getEmail(),sender.getPassword());
                    System.out.println("Sent message successfully....");
                } catch (MessagingException mex) {
                    ex = mex;
                    mex.printStackTrace();
                } catch (FileNotFoundException e) {
                    ex = e;
                    e.printStackTrace();
                    System.out.println("读取邮件失败,请核查");
                }
                SqlSession sqlSession = SessionFactonry.getSession();
                MailDao mapper = sqlSession.getMapper(MailDao.class);
                try {
                    mapper.insertLog(new Log((int) instance.getId(), sender.getEmail(), targetEmail
                            .getEmail(), (ex == null ? "成功" : ex.getMessage()), System.currentTimeMillis()));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    sqlSession.commit();
                    sqlSession.close();
                }


            }


        } while (true);
    }


    private class SMTPAuthenticator extends javax.mail.Authenticator {

        private String SMTP_AUTH_USER;
        private String SMTP_AUTH_PWD;

        public SMTPAuthenticator(String SMTP_AUTH_USER, String SMTP_AUTH_PWD) {
            this.SMTP_AUTH_USER = SMTP_AUTH_USER;
            this.SMTP_AUTH_PWD = SMTP_AUTH_PWD;
        }

        public PasswordAuthentication getPasswordAuthentication() {
             return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
        }
    }


}
