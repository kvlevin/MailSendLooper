package com.levin;

import com.levin.dao.SenderDao;
import com.levin.dao.TargetEmailDao;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class TargetEmail {

    String email;
    int isValid;
    int refuseCount;
    long lastSendTime;

    public static void init() {
        SqlSession session = null;
        try {


            File senderFile = new File("target.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(senderFile)));
            String s = bufferedReader.readLine();
            do {
                try {
                    TargetEmail targetEmail = new TargetEmail();
                    targetEmail.setEmail(s.trim());
                    session = SessionFactonry.getSession();
                    TargetEmailDao mapper = session.getMapper(TargetEmailDao.class);
                    mapper.saveTargetEmail(targetEmail);

                } catch (Exception e) {
                 //   e.printStackTrace();

                }finally {
                    session.commit();
                    session.close();
                }
                s = bufferedReader.readLine();
            } while (s != null);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取目标邮箱失败，原有用邮箱");
        } finally {


        }

    }

    public static synchronized TargetEmail getTargetEmail(long i) {

        SqlSession session = SessionFactonry.getSession();
        TargetEmailDao mapper = session.getMapper(TargetEmailDao.class);
        try {
            TargetEmail lastEmail = mapper.getLastEmail(i);
            if (lastEmail != null) {
                lastEmail.setLastSendTime(System.currentTimeMillis());
                mapper.updateTargetEmail(lastEmail);
            }
            return lastEmail;
        }catch (Exception e){

        }finally {
            session.commit();
            session.close();
        }
        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public int getRefuseCount() {
        return refuseCount;
    }

    public void setRefuseCount(int refuseCount) {
        this.refuseCount = refuseCount;
    }

    public long getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(long lastSendTime) {
        this.lastSendTime = lastSendTime;
    }
}
