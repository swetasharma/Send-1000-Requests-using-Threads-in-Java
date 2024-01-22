package com.test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Random;

public class Main extends Thread{
    static int threadCount = 0;
    static int activeThreadCount = 0;

    public static void main(String[] args) throws UnirestException {
        Thread t = new Thread();
        int i = 1;
        while (threadCount < 250){
            t =  new Thread(
                    new Main()
            );
            t.start();
            threadCount++;
            t.setName("Thread-"+i++);
            System.out.println("Total number of Started thread = " + threadCount);
        }
    }
    public void run (){
        activeThreadCount++;
        System.out.println("Active Threads" + activeThreadCount);
        int num = new Random().nextInt(9000000) + 1000000;
        String orderId = "O-SLJ" + num;
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = null;
        try {
            response = Unirest.post("http://localhost:8074/api/v1/cart/creditcard")
                    .header("Content-Type", "application/json")
                    .body("{\r\n  \"environment_url\": \"https://my.salesforce.com\",\r\n  \"environment\": \"my.site.com\",\r\n  \"cartSource\": null,\r\n  \"cartId\": \"00881757\",\r\n  \"orderId\": \""+orderId+"\",\r\n    \"phone\": \"000\",\r\n    \"address\": {\r\n      \"addressLine1\": \"5800 Granite Parkway Suite 600\",\r\n   \"addressLine2\": null,\r\n}")
                    .asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.getBody());

        System.out.println("Ended thread = " + this.getName());
        activeThreadCount--;
    }
}