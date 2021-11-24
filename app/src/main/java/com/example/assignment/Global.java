package com.example.assignment;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

public class Global extends Application {
    private FirebaseAuth auth;

    public FirebaseAuth getLoginUser() {
        return auth;
    }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }

}
