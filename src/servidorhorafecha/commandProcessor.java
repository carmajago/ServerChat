/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorhorafecha;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javafx.scene.chart.PieChart;

/**
 *
 * @author User
 */
public class commandProcessor {

    public List<User> clients = new LinkedList<>();
    private int intentos;
    
    
    

    public void add1(User socketController) {
        clients.add(socketController);
    }

    public boolean remove(SocketController socketController)
    {
        return clients.remove(socketController);
    }
    
    public boolean writeText(String username,String text) {

        for (User client:clients) {
            if(username.trim().equals(client.getUsername().trim()))
            {
                client.getSocketcontroller().writeText(text);
                return true;
            }
        }
        return false;
    }
    
    public boolean SendAll(String text,User sender){
        if(clients.size()>1){
         for (User client : clients) {
             if ( sender!= client) {
            client.getSocketcontroller().writeText(text);
             }
             }
         return true;
        }else
            return false;
    }

   

    public int getUsersCount() {
        return clients.size();
    }
    
    public boolean Registrar(String username){
              
        return username.matches("^[a-zA-Z]+[\\w]{7,14}$");
    }
    
    public boolean ExistUsername(String username){
 
        for (User client : clients) {
            if(client.getUsername().trim().equals(username.trim()))
                return true;
                }
    return false;
    }

    public String responseCommand(User sender, String aCommand) {
        
        String response = "200";
        
        if(sender.permisos){
        aCommand = aCommand.trim().toUpperCase();
            
        if(aCommand.startsWith("REGISTER")){
                intentos++;
                if(intentos<4){
                    if(aCommand.length()==8){
                        response="Ingrese un nombre de usuario";
                    }else{                        
                        String nombre=aCommand.substring(9).trim();
             if(Registrar(nombre)){
                 
                      if(ExistUsername(nombre)){
                        response="EL nombre de usuario ya esta registrado";
                        intentos--;
                    }else{
                    response="100 cliente registrado";
                    sender.setUsername(nombre);
                    }
             
                  
             }else
             response="200 ingrese minimo 8 caracteres";
                    }
                }else{
                    sender.setPermisos(false);
                    response="Limite de intentos excedido";
                }
                
        }
        
        if (aCommand.startsWith("SENDALL")) {
            if (SendAll(aCommand.substring(8),sender)) {

                response = "100";
            }
        }else if(aCommand.startsWith("SEND")){
            String arreglo[]=aCommand.split(" ");
            
            int total=arreglo[1].length();
            if(writeText(arreglo[1].trim(),aCommand.substring(total+5))){
             response="100";   
            }else
                response="200";
            
        }
        
        else if (aCommand.equals("NUMOFUSERS")) {
            response = "100 ....." + this.getUsersCount();
        }

        }else
           response="Limite de intentos excedidos";
        return response;
    }
    
}
