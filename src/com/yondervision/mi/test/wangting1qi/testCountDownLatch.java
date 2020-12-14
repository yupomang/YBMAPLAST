package com.yondervision.mi.test.wangting1qi;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.yondervision.mi.test.wangting1qi.HttpSend6010appapi00212.httpURLConnectionPOST6010;

public class testCountDownLatch {
    //并发运行httpsend
    CountDownLatch countDownLatch = new CountDownLatch(1);
    private  void runThread(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0;i<500 ;i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        //System.out.println("Thread:"+Thread.currentThread().getName()+",time: "+System.currentTimeMillis());
                        long starTime=System.currentTimeMillis();
                        //网厅
                        //httpURLConnectionPOST5021("0076458492", "1", "", "");
                        //省平台
                       /* httpURLConnectionPOST5975("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
                                "李其康","330211195810274011");*/
                        //市平台
                        httpURLConnectionPOST6010("王琰慧","330682199408031287");

                        long endTime=System.currentTimeMillis();
                        long Time=endTime-starTime;
                        System.out.println("请求大数据平台耗时"+Time+"毫秒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        countDownLatch.countDown();
    }

    public static void main(String[] args) {
        testCountDownLatch test = new testCountDownLatch();
        test.runThread();
    }
}