package Distributed.Matrix;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MessagePassServer implements Serializable {
    private ObjectInputStream input;
    private ObjectOutputStream output;
    Socket socket;
    private int port;

    public MessagePassServer(int port){
        this.port = port;

    }
    public void serverpoint() throws IOException, InterruptedException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(port,10);
        System.out.println("Server Started...");
        while (true) {
            socket = serverSocket.accept();

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            input = new ObjectInputStream(in);

            output = new ObjectOutputStream(out);
            output.flush();

            if (input.readObject() != null){

                System.out.println("Query is Received ...");

                InfoBlock values = (InfoBlock)input.readObject();

                System.out.println("the row: "+((InfoBlock) input.readObject()).row_number);

                System.out.println("Starting computing...");
                int [] ans = thread_matrix((int []) values.row, (int [][])values.matrix);

                System.out.println("Computation finished...");
                System.out.println("Send Results...");

                InfoBlock send = new InfoBlock(ans, values.row_number);
                output.writeObject(send);
                output.flush();
                System.out.println("Results Sent!...");

                input.close();
                output.close();
                break;

            }

        }

    }

    public static int[] thread_matrix(int[] mat1, int[][] mat2){
        int[] Result = new int[mat2.length];
        for (int j = 0; j<=mat2[0].length-1;j++) {
            int x = 0;
            int temp = 0;
            for (int k = 0; k<=mat2[0].length-1;k++) {
                try {
                    temp += mat1[k] * mat2[x][j];
                    x++;
                }catch (ArrayIndexOutOfBoundsException xt){
                    Result[j] = 0;
                    continue;
                }
            }
            Result[j] = temp;
        }
        System.out.println("Row number: "+ Arrays.toString(Result));
        return Result;
    }
}
