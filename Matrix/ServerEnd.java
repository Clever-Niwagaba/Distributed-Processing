package Distributed.Matrix;

import java.io.IOException;

public class ServerEnd {
    public static void main(String[] args) throws InterruptedException {
        MessagePassServer s1 = new MessagePassServer(2004);
        MessagePassServer s2 = new MessagePassServer(5001);
        MessagePassServer s3 = new MessagePassServer(5002);
        MessagePassServer s4 = new MessagePassServer(5003);

        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    s1.serverpoint();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    s2.serverpoint();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread th3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    s3.serverpoint();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread th4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    s4.serverpoint();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        th1.start();
//        th2.start();
//        th3.start();
//        th4.start();

        th1.join();
//        th2.join();
//        th3.join();
//        th4.join();



    }
}
