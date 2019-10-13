package com.zona.recreativacr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.zona.recreativacr.com.zona.data.MealPlan;
import com.zona.recreativacr.com.zona.data.MedicalStaff;
import com.zona.recreativacr.com.zona.recyclerview.RecyclerViewConfig;

import java.util.ArrayList;
import java.util.List;

public class MedicalStaffsActivity extends AppCompatActivity {

    RecyclerView medicalStaffRV;
    FirebaseFirestore mDatabase;
    Task<QuerySnapshot> medicalStaffQuerySnapshot;
    List<MedicalStaff> medicalStaffs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_staffs);
        medicalStaffRV = findViewById(R.id.medicalstaff_recyclerView);

        this.setTitle("Personal Médico");

        readMedicalStaffs(new DataStatus<MedicalStaff>() {
            @Override
            public void DataIsLoaded(List<MedicalStaff> list) {
                findViewById(R.id.medicalstaff_progressBar).setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigMedicalStaff(medicalStaffRV, getBaseContext(),
                        medicalStaffs);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    public void readMedicalStaffs (final DataStatus dataStatus) {
        mDatabase = FirebaseFirestore.getInstance();
        medicalStaffQuerySnapshot = mDatabase.collection("PersonalMedico")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                medicalStaffs.add(document.toObject(MedicalStaff.class));
                            }
                            //Log.d("Empleado", employees.get(0).nombre);
                            dataStatus.DataIsLoaded(medicalStaffs);
                        } else {
                            Log.d("Comida", "Task not successful");
                        }
                    }
                });
    }

    public void goToAddMedicalStaff(View view){
        Intent i = new Intent(getBaseContext(), MedicalStaffsAddActivity.class);
        startActivity(i);
    }
}
