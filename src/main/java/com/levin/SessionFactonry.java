package com.levin;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SessionFactonry {

    private static SqlSessionFactory sqlSessionFactory = null;

    static {

        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(resource);
            sqlSessionFactory =
                    new SqlSessionFactoryBuilder().build(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    public static SqlSession getSession() {
        return sqlSessionFactory.openSession();
    }
}





