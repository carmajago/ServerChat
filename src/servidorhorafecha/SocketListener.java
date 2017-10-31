/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorhorafecha;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */

public class SocketListener {

    private int thePort = 0;
    private final commandProcessor commProc= new commandProcessor();

    public SocketListener(int newPort) {
        thePort = newPort;
    }

    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        boolean quit = false;
        SocketController socketController= null;

        try {
            serverSocket = new ServerSocket(thePort);
        } catch (IOException ex) {
            Logger.getLogger(SocketListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (serverSocket != null) {
            while (!quit) {
                try {
                    socket = serverSocket.accept();
                    socketController= new SocketController(socket,commProc);
                   // commProc.add1(socketController);
                } catch (IOException ex) {
                    Logger.getLogger(SocketListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
