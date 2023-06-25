package com.example.linemaster;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MySignal.init(this);
    }
}
