/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.DTO;

/**
 *
 * @author asjad
 */

// Data Transfer Object (DTO) class for Users

public class UserDTO {

    private String  username, password, role ;
  public String getUsername(){
      return username;
  }
  
  public void setUsername(String username){
      this.username = username ;
  }
    public String getPassword(){
      return password;
  }
  
   public void setPassword(String password){
      this.password = password ;
  }
    public String getRole(){
      return role;
  }
   public void setRole(String role){
      this.role = role ;
  }
   
}
