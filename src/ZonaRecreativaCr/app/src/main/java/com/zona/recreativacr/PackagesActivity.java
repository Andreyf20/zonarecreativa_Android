package com.zona.recreativacr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PackagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);
        this.setTitle(R.string.adminPackages);
    }
}
