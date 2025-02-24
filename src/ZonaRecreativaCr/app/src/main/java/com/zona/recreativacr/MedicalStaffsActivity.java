package com.zona.recreativacr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.zona.recreativacr.com.zona.data.MedicalStaff;
import com.zona.recreativacr.com.zona.recyclerview.IClickListener;
import com.zona.recreativacr.com.zona.recyclerview.RecyclerViewConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MedicalStaffsActivity extends AppCompatActivity {

    RecyclerView medicalStaffRV;
    ProgressBar medicalStaffPB;
    FirebaseFirestore mDatabase;
    Task<QuerySnapshot> medicalStaffQuerySnapshot;
    List<MedicalStaff> medicalStaffs = new ArrayList<>();
    View contentView;

    IClickListener listener = new IClickListener() {
        @Override
        public void OnClickObject(int position) {
            MedicalStaffsActivity.this.clickObject(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_staffs);
        contentView = findViewById(android.R.id.content);
        medicalStaffRV = findViewById(R.id.medicalStaff_recyclerView);
        medicalStaffPB = findViewById(R.id.medicalstaff_progressBar);
        this.setTitle("Personal Médico");

        readMedicalStaffs(new DataStatus<MedicalStaff>() {
            @Override
            public void DataIsLoaded(List<MedicalStaff> list) {
                medicalStaffPB.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigMedicalStaff(medicalStaffRV, getBaseContext(),
                        medicalStaffs, listener);
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

    private void deleteMedicalStaff(final int position){
        medicalStaffPB.setVisibility(View.VISIBLE);
        mDatabase.collection("PersonalMedico")
                .document(medicalStaffs.get(position).id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar snackbar;
                        snackbar = Snackbar.make(contentView, R.string.okDeleted,
                                Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(),
                                R.color.LightBlueDark));
                        snackbar.show();
                        medicalStaffs.remove(position);
                        Objects.requireNonNull(medicalStaffRV.getAdapter()).notifyItemRemoved(position);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar snackbar;
                snackbar = Snackbar.make(contentView, R.string.errorDeleted,
                        Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(),
                        R.color.LightBlueDark));
                snackbar.show();
            }
        });
        medicalStaffPB.setVisibility(View.GONE);
    }

    public void clickObject(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] options = {"Actualizar", "Eliminar"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    Intent intent = new Intent(getBaseContext(), MedicalStaffsAddActivity.class);
                    intent.putExtra("medicalstaff", medicalStaffs.get(position));
                    startActivity(intent);
                }
                else if(which == 1) {
                    deleteMedicalStaff(position);
                }
            }
        }).create().show();
    }

    public void updateMedicalStaff(View view){
        medicalStaffPB.setVisibility(View.VISIBLE);
        medicalStaffs.clear();
        medicalStaffRV.invalidate();
        Objects.requireNonNull(medicalStaffRV.getAdapter()).notifyDataSetChanged();
        readMedicalStaffs(new DataStatus<MedicalStaff>() {
            @Override
            public void DataIsLoaded(List<MedicalStaff> list) {
                medicalStaffPB.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigMedicalStaff(medicalStaffRV, getBaseContext(),
                        medicalStaffs, listener);
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
}
