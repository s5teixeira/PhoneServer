package com.custom.clientserver;
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

// server class //
public class PhoneServer
{
    private static int port = 2014;
    private static ServerSocket listener = null;
    private static Map<String, ArrayList<String>> data=new HashMap<String, ArrayList<String>>();
    private static ArrayList<String> reqMethod= new ArrayList<>(Arrays.asList("STORE","GET","REMOVE"));

    public static void main(String[] args) throws IOException {
        //server socket //
        listener = new ServerSocket(port);
        while (true)
        {
            Socket clientSocket = null;
            try
            {
                clientSocket = listener.accept(); //accepting requested connection //
                //input/output that receives data from client//
                DataInputStream x = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream y = new DataOutputStream(clientSocket.getOutputStream());
                //client request thread //
                Thread t = new ClientThread(clientSocket, x, y,data,reqMethod);
                t.start();
            }
            catch (Exception e){
                clientSocket.close();
                e.printStackTrace();
            }
        }
    }
}