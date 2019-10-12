package com.zona.recreativacr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);
        employeeRV = findViewById(R.id.employee_recyclerView);
        employeePB = findViewById(R.id.employee_progressBar);

        this.setTitle("Seguros laborales");


        readEmployees(new DataStatus<Employee>() {
            @Override
            public void DataIsLoaded(List<Employee> employees) {
                employeePB.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigEmployee(employeeRV, getBaseContext(),
                        employees);
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

}
