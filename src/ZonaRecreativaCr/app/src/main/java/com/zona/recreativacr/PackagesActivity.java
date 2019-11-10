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
import com.zona.recreativacr.com.zona.data.Employee;
import com.zona.recreativacr.com.zona.data.Package;
import com.zona.recreativacr.com.zona.recyclerview.IClickListener;
import com.zona.recreativacr.com.zona.recyclerview.RecyclerViewConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PackagesActivity extends AppCompatActivity {
    RecyclerView packagesRV;
    ProgressBar packagesPB;
    FirebaseFirestore mDatabase;
    Task<QuerySnapshot> packageQuerySnapshot;
    List<Package> packages = new ArrayList<>();
    View contentView;

    IClickListener listener = new IClickListener() {
        @Override
        public void OnClickObject(int position) {
            PackagesActivity.this.clickObject(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);
        this.setTitle(R.string.adminPackages);

        contentView = findViewById(android.R.id.content);
        packagesRV = findViewById(R.id.package_recyclerView);
        packagesPB = findViewById(R.id.package_progressBar);

        readPackages(new DataStatus<Package>() {
            @Override
            public void DataIsLoaded(List<Package> packages) {
                packagesPB.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigPackages(packagesRV, getBaseContext(),
                        packages, listener);
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

    public void readPackages (final DataStatus dataStatus) {
        mDatabase = FirebaseFirestore.getInstance();
        packageQuerySnapshot = mDatabase.collection("Paquetes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                packages.add(document.toObject(Package.class));
                            }
                            //Log.d("Empleado", employees.get(0).nombre);
                            dataStatus.DataIsLoaded(packages);
                        } else {
                            Log.d("Empleado", "Task not successful");
                        }
                    }
                });
    }

    public void goToAddPackage(View view){
        Intent i = new Intent(getBaseContext(), PackagesAddActivity.class);
        startActivity(i);
    }

    private void deletePackage(final int position){
        packagesPB.setVisibility(View.VISIBLE);
        mDatabase.collection("Paquetes")
                .document(packages.get(position).id)
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
                        packages.remove(position);
                        Objects.requireNonNull(packagesRV.getAdapter()).notifyItemRemoved(position);
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
        packagesPB.setVisibility(View.GONE);
    }

    public void clickObject(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] options = {"Actualizar", "Eliminar"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    Intent intent = new Intent(getBaseContext(), PackagesAddActivity.class);
                    intent.putExtra("package", packages.get(position));
                    startActivity(intent);
                }
                else if(which == 1) {
                    deletePackage(position);
                }
            }
        }).create().show();
    }

    public void updatePackages(View view) {
        packagesPB.setVisibility(View.VISIBLE);
        packages.clear();
        packagesRV.invalidate();
        Objects.requireNonNull(packagesRV.getAdapter()).notifyDataSetChanged();
        readPackages(new DataStatus<Package>() {
            @Override
            public void DataIsLoaded(List<Package> packages) {
                packagesPB.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigPackages(packagesRV, getBaseContext(),
                        packages, listener);
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
