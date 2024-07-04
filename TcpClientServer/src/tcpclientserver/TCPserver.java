package tcpclientserver;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */




import java.io.*;
import java.net.*;

public class TCPserver {
    public static void server_fun(){
        try {
        ServerSocket serverSocket = new ServerSocket(1254);
        System.out.println("Waiting for client on port: " + serverSocket.getLocalPort() + ".......");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Just Connected to " + clientSocket.getRemoteSocketAddress());

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            boolean loggedIn = false;

            while (!loggedIn) {
                try {
                    // Prompt the client to enter username
                    out.writeUTF("Enter username: ");
                    String username = in.readUTF();
                    System.out.println("Received username: " + username);

                    // Prompt the client to enter password
                    out.writeUTF("Enter password: ");
                    String password = in.readUTF();
                    System.out.println("Received password: " + password);

                    // Check if the username and password are correct
                    if (username.equals("rehabmohamedahmed") && password.equals("sec2_33243029")) {
                        loggedIn =  true;
                        out.writeUTF("success");
                    } else {
                        loggedIn =  false; 
                        out.writeUTF("failed");
                    }

                    System.out.println("Authentication result: " + (loggedIn ? "success" : "failed"));
                } catch (EOFException e) {
                    System.out.println("EOFException occurred. Continuing authentication process.");
                } catch (SocketException se) {
                    System.out.println("SocketException occurred: " + se.getMessage());
                    break; // Break out of inner loop and continue waiting for a new connection
                }
            }

            // Close streams but not the client socket
            in.close();
            out.close();
            clientSocket.close(); // Don't close client socket on failed authentication
        }
    } catch (IOException e) {
        System.out.println(e.toString());
    }
    }
    
}
