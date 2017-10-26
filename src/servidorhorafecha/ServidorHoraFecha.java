/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorhorafecha;

/**
 *
 * @author User
 */
public class ServidorHoraFecha {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SocketListener socketListener= new SocketListener(30000);
        socketListener.run();
    }
    
}
