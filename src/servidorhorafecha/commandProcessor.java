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
    
    public String getUsers(){
        String users="";
        
        for (User item:clients) {
            if(!item.getUsername().equals(""))
            users+=item.getId()+" "+item.getUsername()+";";
        }
        users=users.substring(0, users.length()-1);
        
        return users;
    }

    public String responseCommand(User sender, String aCommand) {
        
        String response = "200";
        
        if(sender.permisos){
        aCommand = aCommand.trim();
            
        if(aCommand.toUpperCase().startsWith("REGISTER")){
                sender.setIntentos();
                if(sender.getIntentos()<4){
                    if(aCommand.length()==8){
                        response="200 Ingrese un nombre de usuario";
                    }else{                        
                        String nombre=aCommand.substring(9).trim();
             if(Registrar(nombre)){
                 
                      if(ExistUsername(nombre)){
                        response="201 EL nombre de usuario ya esta registrado";
                        
                    }else{
                    response="101 cliente registrado";
                    sender.setUsername(nombre);
                    }
             
                  
             }else
             response="204 ingrese minimo 8 caracteres";
                    }
                }else{
                    sender.setPermisos(false);
                    response="206 Limite de intentos excedido";
                }
                
        }
        
        if (aCommand.toUpperCase().startsWith("SENDALL")) {
            if (SendAll(aCommand.substring(8),sender)) {

                response = "103";
            }
        }else if(aCommand.toUpperCase().startsWith("SEND")){
            String arreglo[]=aCommand.split(" ");
            
            int total=arreglo[1].length();
            String salida=aCommand.substring(total+5);
            if(writeText(arreglo[1].trim(),salida)){
             response="102";   
            }else
                response="200";
            
        }
        
        else if (aCommand.toUpperCase().equals("NUMOFUSERS")) {
            response = "105  " + this.getUsersCount();
        }else if (aCommand.toUpperCase().equals("GETUSERS")) {
            response = "106 " + this.getUsers();
        }   

        }else
           response="206 Limite de intentos excedidos";
        return response;
    }
    
}
