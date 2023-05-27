package com.example.toxicapplication.exception;

public class NoValidEmailException extends Exception{
    public NoValidEmailException(String message){
        super(message);
    }
}
