package br.com.java.exception;

public class UpdateFailException extends IllegalArgumentException{
    public UpdateFailException(String msg) {
        super(msg);
    }
}
