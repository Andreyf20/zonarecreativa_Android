package com.zona.recreativacr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.zona.recreativacr.com.zona.data.MedicalStaff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements LocationListener{
    Button packagesBtn, insuranceBtn, mealBtn, medicalBtn, transportBtn, logoutBtn;
    ProgressBar callMsPB;
    LocationManager mLocationManager;
    double lat, lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        packagesBtn = findViewById(R.id.packages_button);
        insuranceBtn = findViewById(R.id.insurance_button);
        mealBtn = findViewById(R.id.meals_button);
        medicalBtn = findViewById(R.id.medical_staff_button);
        transportBtn = findViewById(R.id.transport_button);
        logoutBtn = findViewById(R.id.provider_button);
        callMsPB = findViewById(R.id.home_progressBar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            } else {
                mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                        0, this);
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                        0, this);
            }
        }
    }

        public void goToPackages(View view) {
        Intent i = new Intent(getBaseContext(), PackagesActivity.class);
        startActivity(i);
    }

    public void goToEmployee(View view) {
        Intent i = new Intent(getBaseContext(), EmployeesActivity.class);
        startActivity(i);
    }

    public void goToTransport(View view) {
        Intent i = new Intent(getBaseContext(), TransportsActivity.class);
        startActivity(i);
    }

    public void goToMealPlan(View view) {
        Intent i = new Intent(getBaseContext(), MealPlansActivity.class);
        startActivity(i);
    }

    public void goToMedicalStaff(View view) {
        Intent i = new Intent(getBaseContext(), MedicalStaffsActivity.class);
        startActivity(i);
    }

    public void goToNewAdmin(View view) {
        Intent i = new Intent(getBaseContext(), NewAdminActivity.class);
        startActivity(i);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        this.finish();
    }

    public void goToProvider(View view) {
        Intent i = new Intent(getBaseContext(), ProviderActivity.class);
        startActivity(i);
    }

    public void callMs(View view) {
        callMsPB.setVisibility(View.VISIBLE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
                List<Address> addresses;

                String state = "";

                try {
                    addresses = geocoder.getFromLocation(lat, lon, 10);
                    if (addresses.size() > 0) {
                        for (Address address : addresses) {
                            if (address.getLocality() != null && address.getLocality().length() > 0) {
                                state = address.getAdminArea();
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
                final String finalState = state;
                //Log.d("Ciudad", "State: " + state + " lat "+lat+" lon "+lon);
                Task<QuerySnapshot> medicalStaffQuerySnapshot = mDatabase.collection("PersonalMedico")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<MedicalStaff> medicalStaffs = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                medicalStaffs.add(document.toObject(MedicalStaff.class));
                            }
                            findNearestMS(medicalStaffs, finalState);
                        }
                    }
                });
            }
        });

    }

    private void findNearestMS(List<MedicalStaff> medicalStaffs, String state){
        String phoneNumber = "911";
        state = getCleanState(state);
        if(!state.equals("")) {
            for (MedicalStaff medicalStaff : medicalStaffs) {
                if (medicalStaff.provincia.contains(state)) {
                    phoneNumber = medicalStaff.numeroTelefono;
                    break;
                }
            }
        }
        //Log.d("Ciudad", "State: " + state + " lat "+lat+" lon "+lon+" phoneNumber "+phoneNumber);
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
        callMsPB.setVisibility(View.GONE);
    }

    private String getCleanState(String state){
        switch (state){
            case "San José Province":
                return "San José";
            case "Heredia":
                return "Heredia";
            case "Provincia de Cartago":
                return "Cartago";
            case "Provincia de Alajuela":
                return "Alajuela";
            default:
                return "default";
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
