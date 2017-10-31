/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorhorafecha;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.ir.CatchNode;

/**
 *
 * @author User
 */
public class SocketController implements Runnable {

    private Thread theThread = null;
    private Socket theSocket = null;
    private PrintWriter theOut = null;
    private BufferedReader theIn = null;
    private commandProcessor theCommandProcessor=null;
    
    
    public SocketController(Socket newSocket,commandProcessor newCommandProcessor ) {
        theSocket = newSocket;
        theCommandProcessor= newCommandProcessor;
        try {

            theOut = new PrintWriter(theSocket.getOutputStream(), true);
            theIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream(), "UTF-8"));

        } catch (IOException ex) {
            Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
        }

        theThread = new Thread(this);
        theThread.start();
    }

    public void close() {
        try {
            theIn.close();
            theOut.close();
            theSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeText(String text) {
        theOut.println(text);
    }

    public String readText() {

        try {
            return theIn.readLine();
        } catch (IOException ex) {
            Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override

    public void run() {
        String command = null;
        boolean quit = false;
        commandProcessor comProc = new commandProcessor();
        writeText("100 BroadCast Server");
        
        User user=new User(theCommandProcessor.clients.size()+1,this,true);
        theCommandProcessor.add1(user);
        
        
        while (!quit) {

            command = readText();
            if (command != null) {
                //writeText(command);
                if (command.trim().toUpperCase().equals("QUIT")) {
                     close();
                     theCommandProcessor.remove(this);
                    quit = true;
                } else {
                    writeText(theCommandProcessor.responseCommand(user,command));
                }
            }
            else{
            theCommandProcessor.remove(this);
            quit=true;
            }
        }
       
    }

}
