package com.stock.controller.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CountDownLatchTest {

    static double threadCount = 100.0; //使用 20个线程处理
    static final CountDownLatch latch = new CountDownLatch((int) threadCount);

    public static void getWycjSituationAll() throws Exception {

//        Thread[] threads = new Thread[(int)threadCount];
//        final CountDownLatch latch = new CountDownLatch((int)threadCount);
//        for (int i = 0; i < threadCount; i++) {
//            final int num = i;
//            threads[i] = new Thread(new Runnable() {
//                public void run() {
//                    try {
//                        //具体执行的功能
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    System.out.print(num + " add.\t");
//                    latch.countDown();
//                }
//            });
//            threads[i].start();
//        }
//        try {
//            System.out.println("开始执行");
//            latch.await();
//            System.out.println("全部执行完毕");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}

