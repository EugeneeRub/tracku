package com.erproject.busgo.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.erproject.busgo.services.authManager.Authenticator;

public class AuthenticatorService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        Authenticator authenticator = new Authenticator(this);
        return authenticator.getIBinder();
    }
}
