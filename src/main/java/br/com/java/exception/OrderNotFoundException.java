package br.com.java.exception;

public class OrderNotFoundException extends IllegalArgumentException{
    
    public OrderNotFoundException(String msg){
        super(msg);
    }
}
