package com.custom.clientserver;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

//client thread //
public class ClientThread extends Thread {
    Socket socket;
    DataInputStream x;
    DataOutputStream y;
    Map<String, ArrayList<String>> data;
    ArrayList<String> reqMethod;

    //constructor //
    public ClientThread(Socket socket, DataInputStream x, DataOutputStream y, Map<String, ArrayList<String>> data, ArrayList<String> reqMethod) {
        this.socket = socket;
        this.x = x;
        this.y = y;
        this.data = data;
        this.reqMethod = reqMethod;
    }

    @Override //run method //
    public void run() {
        String message;
        String response;
        //Client sending 3 types of messages //
        while (true) {
            try {  //message //
                message = x.readUTF();
                System.out.println("Message Received: " + message);
                if (message.contains(" ")) {
                    String[] info = message.split(" ", 2);
                    if (info.length < 3) ;
                    {
                        String request = info[0];
                        for (String string : info) {
                            System.out.println(string);
                        }
                        // storing the messages //
                        switch (request) {
                            case "STORE":
                                if (data.containsKey(info[1])) {
                                    ArrayList<String> list = data.get(info[1]);
                                    list.add(info[2]);
                                    data.replace(info[2], list);
                                    y.writeUTF("200");
                                } else {
                                    data.put(info[1], new ArrayList<String>(Arrays.asList(info[2])));
                                    y.writeUTF("200");
                                }
                                break;
                            // getting messages //
                            case "GET":
                                if (data.containsKey(info[1])) {
                                    y.writeUTF(data.get(info[1]).get(0));
                                } else {
                                    data.put(info[1], new ArrayList<String>(Arrays.asList(info[2])));
                                    y.writeUTF("300");
                                }
                                break;
                            //removing messages
                            case "REMOVE":
                                if (data.containsKey(info[1])) {
                                    data.remove(info[1]);
                                    y.writeUTF("100");
                                } else {
                                    data.put(info[1], new ArrayList<String>(Arrays.asList(info[2])));
                                    y.writeUTF("300");
                                }
                                break;
                            default:
                                y.writeUTF("400");
                                break;
                        }
                    }
                }
                // closing stream if want to exit //
                else if (message.equals("exit")) {
                    this.socket.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.x.close();
            this.y.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
