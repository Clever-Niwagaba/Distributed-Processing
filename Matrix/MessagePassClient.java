package Distributed.Matrix;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class MessagePassClient implements Serializable{

    String host;
    int port;
    int[] row;
    int[][] matrix;
    int row_number;
    int results[][];
    public MessagePassClient(String host, int port, int[] row, int [][]second_matrix, int row_number, int [][]results){
        this.host = host;
        this.port = port;
        this.row = row;
        this.matrix = second_matrix;
        this.row_number = row_number;
        this.results = results;

    }

    void Socket_connection() throws IOException, InterruptedException, ClassNotFoundException {
        Socket socket = new Socket(this.host,this.port);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        ObjectOutputStream output = new ObjectOutputStream(out);
        output.flush();
        ObjectInputStream input = new ObjectInputStream(in);

        Thread Sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    get_Computation();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            void get_Computation() throws IOException {
                //send row x to a certain server with the whole second matrix
                InfoBlock send_compute = new InfoBlock(row, matrix, row_number);
                //Send the querry;
                output.writeObject(send_compute);
                output.flush();
                System.out.println("Query Sent to:\nhost: "+ host+"\nrow number: "+row_number);


            }
        });

        Thread recv = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (input.readObject() != null) {
                        System.out.println("Got Response from Server....");
                        InfoBlock values = (InfoBlock) input.readObject();
                        results[(int) values.row_number] = (int[]) values.results;

                        System.out.println("Answer received from:\n host: " + host + "\nrow number: " + row_number + "\n" +
                                Arrays.toString((int[]) values.results));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        Sender.start();
        recv.start();

        Sender.join();
        recv.join();

        InfoBlock send_compute = new InfoBlock(row, matrix, row_number);
        //Send the querry;
        output.writeObject(send_compute);
        System.out.println("Query Sent to:\nhost: "+ host+"\nrow number: "+row_number);


        InfoBlock values = (InfoBlock) input.readObject();
        System.out.println("Got Response from Server....");
        results[(int) values.row_number] = (int[]) values.results;

        System.out.println("Answer received from:\n host: " + host + "\nrow number: " + row_number + "\n" +
                Arrays.toString((int[]) values.results));






    }

}
