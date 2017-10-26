/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorhorafecha;

import servidorhorafecha.SocketController;

/**
 *
 * @author Carlos
 */
public class User {
    
    private int id;
    private String username;
    private SocketController socketcontroller;
    
    boolean permisos;

    public User(int id, SocketController socketcontroller,boolean permisos) {
        this.id = id;
        this.socketcontroller = socketcontroller;
        this.permisos=permisos;
        this.username="";
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the socketcontroller
     */
    public SocketController getSocketcontroller() {
        return socketcontroller;
    }

    /**
     * @param socketcontroller the socketcontroller to set
     */
    public void setSocketcontroller(SocketController socketcontroller) {
        this.socketcontroller = socketcontroller;
    }

    public boolean isPermisos() {
        return permisos;
    }

    public void setPermisos(boolean permisos) {
        this.permisos = permisos;
    }
    
    
    
    
    
}
