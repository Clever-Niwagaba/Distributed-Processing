package Distributed.Matrix;

import java.io.IOException;
import java.util.Arrays;

public class ClientEnd {
    static int [][] Result;
    static int [][] Matrix1;
    static int [][] Matrix2;
    public static void main(String[] args) throws InterruptedException {

        Matrix1 = new int[][]{{5,65,16},{5,12,56},{12,36,56},{20,35,10}};
        Matrix2 = new int[][]{{8,23,52},{6,23,11},{12,36,6},{5,2,36}};
        System.out.println("Establishing Connections....");

        // Send out to multiple different servers to multiply matrix
        MessagePassClient client1 = new MessagePassClient("localhost",2004, Matrix1[0], Matrix2, 0, Result);
        MessagePassClient client2 = new MessagePassClient("",5001, Matrix1[1], Matrix2, 1, Result);
        MessagePassClient client3 = new MessagePassClient("",5002, Matrix1[2], Matrix2, 2, Result);
        MessagePassClient client4 = new MessagePassClient("",5003, Matrix1[3], Matrix2, 3, Result);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client1.Socket_connection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3);
                    client2.Socket_connection();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5);
                    client3.Socket_connection();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(7);
                    client4.Socket_connection();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        while(true){
            if (t1.isAlive() == true){
                System.out.println("Results got: "+ Arrays.toString(Result));
                break;
            }

        }



    }
}
