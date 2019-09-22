package com.example.tom_m.myapplication.Control;

public interface ILoginInteractor {
    public boolean validateHostname(String hostname);
    public boolean validateLogin(String hostname, String username, String password, int port );
    public boolean validatePort(int port);
}
