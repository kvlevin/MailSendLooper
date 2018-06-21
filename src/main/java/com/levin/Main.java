package com.levin;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String args[]) throws IOException {

        //read from email
        //read  sender
        //read receiver
        //read sender to memory
        //send from last rencent  sender to last recent receiver

        ExecutorService executor = Executors.newFixedThreadPool(5);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                TargetEmail.init();

            }
        });

        try {
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i < 1; i++) {
            executor.execute(new Worker());
        }

        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");


    }


}
