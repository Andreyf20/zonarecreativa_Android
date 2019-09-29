package com.zona.recreativacr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {
    Button packagesBtn, insuranceBtn, mealBtn, medicalBtn, transportBtn, logoutBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        packagesBtn = findViewById(R.id.packages_button);
        insuranceBtn = findViewById(R.id.insurance_button);
        mealBtn = findViewById(R.id.meals_button);
        medicalBtn = findViewById(R.id.medical_staff_button);
        transportBtn = findViewById(R.id.transport_button);
        logoutBtn = findViewById(R.id.logout_button);
    }

    public void logout(View view){
        this.finish();
    }
}
