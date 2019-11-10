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
import com.zona.recreativacr.com.zona.data.Transport;
import com.zona.recreativacr.com.zona.recyclerview.IClickListener;
import com.zona.recreativacr.com.zona.recyclerview.RecyclerViewConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransportsActivity extends AppCompatActivity {

    RecyclerView transportRV;
    ProgressBar transportPB;
    FirebaseFirestore mDatabase;
    Task<QuerySnapshot> transportQuerySnapshot;
    List<Transport> transports = new ArrayList<>();
    View contentView;

    IClickListener listener = new IClickListener() {
        @Override
        public void OnClickObject(int position) {
            TransportsActivity.this.clickObject(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transports);
        contentView = findViewById(android.R.id.content);
        transportRV = findViewById(R.id.transport_recyclerView);
        transportPB = findViewById(R.id.transport_progressBar);
        this.setTitle("Transporte");

        readTransports(new DataStatus<Transport>() {
            @Override
            public void DataIsLoaded(List<Transport> list) {
                transportPB.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigTransport(transportRV, getBaseContext(),
                        transports, listener);
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

    private void deleteTransport(final int position){
        transportPB.setVisibility(View.VISIBLE);
        mDatabase.collection("Transportes")
                .document(transports.get(position).id)
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
                        transports.remove(position);
                        Objects.requireNonNull(transportRV.getAdapter()).notifyItemRemoved(position);
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
        transportPB.setVisibility(View.GONE);
    }

    public void clickObject(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] options = {"Actualizar", "Eliminar"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    Intent intent = new Intent(getBaseContext(), TransportsAddActivity.class);
                    intent.putExtra("transport", transports.get(position));
                    startActivity(intent);
                }
                else if(which == 1) {
                    deleteTransport(position);
                }
            }
        }).create().show();
    }

    public void updateTransports(View view) {
        transportPB.setVisibility(View.VISIBLE);
        transports.clear();
        transportRV.invalidate();
        Objects.requireNonNull(transportRV.getAdapter()).notifyDataSetChanged();
        readTransports(new DataStatus<Transport>() {
            @Override
            public void DataIsLoaded(List<Transport> list) {
                transportPB.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigTransport(transportRV, getBaseContext(),
                        transports, listener);
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
