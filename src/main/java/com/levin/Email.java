package com.levin;

import com.levin.dao.MailDao;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Email {
    long id;
    String contentFile;
    long lastModify;


    private static Email ourInstance =null;

    public String getContentFile() {
        return contentFile;
    }

    public void setContentFile(String contentFile) {
        this.contentFile = contentFile;
    }

    public long getLastModify() {
        return lastModify;
    }

    public void setLastModify(long lastModify) {
        this.lastModify = lastModify;
    }

    static {

        SqlSession session = SessionFactonry.getSession();
        MailDao mapper = session.getMapper(MailDao.class);
        Email email = mapper.selectLastOne();
        try {
            File mail = new File(".");

            Email newEmail = new Email();
            for (String item : mail.list()) {
                if (item.endsWith(".eml")) {
                    newEmail.setContentFile(item);
                    newEmail.setLastModify(new File(item).lastModified());
                    break;
                }
            }
            if (email != null && (email.getLastModify() >= newEmail.getLastModify())) {
                ourInstance = email;
            } else {
                ourInstance = newEmail;
                mapper.saveEmail(newEmail);
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);

        } finally {
            session.commit();
            session.close();
        }


    }


    public static Email getInstance() {
        return ourInstance;
    }

    private Email() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
