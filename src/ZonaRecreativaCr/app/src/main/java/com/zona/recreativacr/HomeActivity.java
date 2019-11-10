package com.zona.recreativacr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

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

    public void goToPackages(View view){
        Intent i = new Intent(getBaseContext(), PackagesActivity.class);
        startActivity(i);
    }

    public void goToEmployee(View view){
        Intent i = new Intent(getBaseContext(), EmployeesActivity.class);
        startActivity(i);
    }

    public void goToTransport(View view){
        Intent i = new Intent(getBaseContext(), TransportsActivity.class);
        startActivity(i);
    }

    public void goToMealPlan(View view){
        Intent i = new Intent(getBaseContext(), MealPlansActivity.class);
        startActivity(i);
    }

    public void goToMedicalStaff(View view){
        Intent i = new Intent(getBaseContext(), MedicalStaffsActivity.class);
        startActivity(i);
    }

    public void goToNewAdmin(View view){
        Intent i = new Intent(getBaseContext(), NewAdminActivity.class);
        startActivity(i);
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        this.finish();
    }

    public void callMs(View view){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "86236931", null)));
    }
}
