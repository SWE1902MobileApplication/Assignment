package com.example.assignment;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Global extends Application {
    private FirebaseUser user;
    private CalendarUtils util;

    public FirebaseUser getLoginUser() {
        return user;
    }
    public CalendarUtils getUtil() { return util; }

    public void setAuth(FirebaseUser user) {
        this.user = user;
    }
    public void setUtil(CalendarUtils util){ this.util = util;}

}
