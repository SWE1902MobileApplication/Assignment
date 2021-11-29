package com.example.assignment;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

public class Global extends Application {
    private FirebaseAuth auth;
    private CalendarUtils util;

    public FirebaseAuth getLoginUser() {
        return auth;
    }
    public CalendarUtils getUtil() { return util; }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }
    public void setUtil(CalendarUtils util){ this.util = util;}

}
