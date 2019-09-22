package com.example.tom_m.myapplication.Control;

public interface ILoginView {
    public void navigateToMainActivity();
    public void saveData(String host, String user, String password, int port);
    public void loginFailed();
}
