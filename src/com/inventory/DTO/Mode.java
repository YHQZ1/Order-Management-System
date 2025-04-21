/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventory.DTO;

/**
 *
 * @author vanshikadhawan
 */
public class Mode {
    private String paymentMode; 
    
    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
    
     public void setPaymentMode() {
        this.paymentMode = " Cash ";
    }
}
  
