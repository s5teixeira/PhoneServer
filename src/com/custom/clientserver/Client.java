package com.custom.clientserver;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

// client class //
public class Client {

    public static void main(String[] args)  { 
        try
        {
            Scanner scan = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("localhost");
            Socket socket = new Socket(ip, 2014);
            // input/output that receives data from socket //
            DataInputStream x = new DataInputStream(socket.getInputStream());
            DataOutputStream y = new DataOutputStream(socket.getOutputStream());

            //switching info between client & client handler //
            while (true)
            {
                System.out.println(x.readUTF());
                String send = scan.nextLine();
                y.writeUTF(send);
                if(send.equals("exit"))
                {
                    socket.close();
                    break;
                }
                String message = x.readUTF();
                System.out.println(message);
            }
            scan.close();
            x.close();
            y.close();
        }
        catch(Exception e) {
        e.printStackTrace();
        }
    }
}