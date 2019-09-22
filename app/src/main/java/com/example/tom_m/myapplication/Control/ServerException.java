package com.example.tom_m.myapplication.Control;

public class ServerException extends Throwable {

    private static final long serialVersionUID = 1L;

    public ServerException(){
        super();
    }

    public ServerException( final String message){
        super (message);
    }

    public ServerException( final String message, final Throwable cause){
        super (message, cause);
    }

}
