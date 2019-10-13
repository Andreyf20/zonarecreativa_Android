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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.zona.recreativacr.com.zona.data.Employee;
import com.zona.recreativacr.com.zona.recyclerview.IClickListener;
import com.zona.recreativacr.com.zona.recyclerview.RecyclerViewConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeesActivity extends AppCompatActivity {

    RecyclerView employeeRV;
    ProgressBar employeePB;
    FirebaseFirestore mDatabase;
    Task<QuerySnapshot> employeeQuerySnapshot;
    List<Employee> employees = new ArrayList<>();
    View contentView;

    IClickListener listener = new IClickListener() {
        @Override
        public void OnClickObject(int position) {
            EmployeesActivity.this.clickObject(position);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);
        contentView = findViewById(android.R.id.content);
        employeeRV = findViewById(R.id.employee_recyclerView);
        employeePB = findViewById(R.id.employee_progressBar);

        this.setTitle("Seguros laborales");


        readEmployees(new DataStatus<Employee>() {
            @Override
            public void DataIsLoaded(List<Employee> employees) {
                employeePB.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigEmployee(employeeRV, getBaseContext(),
                        employees, listener);
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

    public void readEmployees (final DataStatus dataStatus) {
        mDatabase = FirebaseFirestore.getInstance();
        employeeQuerySnapshot = mDatabase.collection("Empleados")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                employees.add(document.toObject(Employee.class));
                            }
                            //Log.d("Empleado", employees.get(0).nombre);
                            dataStatus.DataIsLoaded(employees);
                        } else {
                            Log.d("Empleado", "Task not successful");
                        }
                    }
                });
    }

    public void goToAddEmployee(View view){
        Intent i = new Intent(getBaseContext(), EmployeesAddActivity.class);
        startActivity(i);
    }

    public void deleteEmployee(int position){
        final Snackbar[] snackbar = new Snackbar[1];
        mDatabase.collection("Empleados")
                .document(employees.get(position).id)
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
    }

    public void clickObject(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] options = {"Actualizar", "Eliminar"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    Intent intent = new Intent(getBaseContext(), EmployeesAddActivity.class);
                    intent.putExtra("employee", employees.get(position));
                    startActivity(intent);
                }
                else if(which == 1) {
                    deleteEmployee(position);
                }
            }
        }).create().show();
    }
}
