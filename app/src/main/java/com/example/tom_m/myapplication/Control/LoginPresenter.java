package com.example.tom_m.myapplication.Control;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView view;
    private LoginInteractor interactor;

    public LoginPresenter(ILoginView loginView){
        this.view = loginView;
        this.interactor = new LoginInteractor();
    }

    public void attemptLogin(String host, String user, String password, int port){
        if (interactor.validateHostname(host))
            if (interactor.validatePort(port))
                if (interactor.validateLogin(host, user, password, port)){
                    view.saveData(host, user, password, port);
                    view.navigateToMainActivity();
                }
    }
}
