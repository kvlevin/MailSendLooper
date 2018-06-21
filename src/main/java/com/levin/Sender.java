package com.levin;


import com.levin.dao.SenderDao;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Sender {

    String email;
    String password;
    String send_server;
    String receiveServer;
    long last_send_time;
    static Queue<Sender> concurrentLinkedQueue = new ConcurrentLinkedQueue();

    static {
        SqlSession session = null;
        try {

            session = SessionFactonry.getSession();
            SenderDao mapper = session.getMapper(SenderDao.class);

            File senderFile = new File("sender.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(senderFile)));
            String s = bufferedReader.readLine();
            do {
                try {

                    Sender sender = new Sender();
                    String[] split = s.split("\\s+");
                    sender.setEmail(split[0]);
                    sender.setPassword(split[1]);
                    sender.setSend_server(split[2]);
                    mapper.saveSender(sender);
                } catch (Exception e) {

                }
                s = bufferedReader.readLine();
            } while (s != null);

            List<Sender> senderList = mapper.getSenderList();

            for (Sender sender : senderList) {
                concurrentLinkedQueue.add(sender);
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取发送邮箱失败，原有用邮箱");
        } finally {
            session.commit();
            session.close();

        }

    }


    public static synchronized Sender getSender() {
        Sender sender = concurrentLinkedQueue.peek();
        if (sender.getLast_send_time() > System.currentTimeMillis() - 100 * 1000) {
            return null;
        } else {
            sender = concurrentLinkedQueue.poll();
            concurrentLinkedQueue.add(sender);
            SqlSession session = SessionFactonry.getSession();
            SenderDao mapper = session.getMapper(SenderDao.class);
            sender.setLast_send_time(System.currentTimeMillis());
            mapper.updateLastTime(sender.getEmail(), sender.getLast_send_time());
            session.commit();
            session.close();
            return sender;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSend_server() {
        return send_server;
    }

    public void setSend_server(String send_server) {
        this.send_server = send_server;
    }

    public String getReceiveServer() {
        return receiveServer;
    }

    public void setReceiveServer(String receiveServer) {
        this.receiveServer = receiveServer;
    }

    public long getLast_send_time() {
        return last_send_time;
    }

    public void setLast_send_time(long last_send_time) {
        this.last_send_time = last_send_time;
    }
}
