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
import com.zona.recreativacr.com.zona.data.Employee;
import com.zona.recreativacr.com.zona.data.Transport;
import com.zona.recreativacr.com.zona.recyclerview.RecyclerViewConfig;

import java.util.ArrayList;
import java.util.List;

public class TransportsActivity extends AppCompatActivity {

    RecyclerView transportRV;
    FirebaseFirestore mDatabase;
    Task<QuerySnapshot> transportQuerySnapshot;
    List<Transport> transports = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transports);

        transportRV = findViewById(R.id.transport_recyclerView);

        this.setTitle("Transporte");

        readTransports(new DataStatus<Transport>() {
            @Override
            public void DataIsLoaded(List<Transport> list) {
                findViewById(R.id.transport_progressBar).setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigTransport(transportRV, getBaseContext(),
                        transports);
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

    public void readTransports (final DataStatus dataStatus) {
        mDatabase = FirebaseFirestore.getInstance();
        transportQuerySnapshot = mDatabase.collection("Transportes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                transports.add(document.toObject(Transport.class));
                            }
                            //Log.d("Empleado", employees.get(0).nombre);
                            dataStatus.DataIsLoaded(transports);
                        } else {
                            Log.d("Transporte", "Task not successful");
                        }
                    }
                });
    }

    public void goToAddTransport(View view){
        Intent i = new Intent(getBaseContext(), TransportsAddActivity.class);
        startActivity(i);
    }
}
