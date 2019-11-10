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
import com.zona.recreativacr.com.zona.data.Package;
import com.zona.recreativacr.com.zona.data.Provider;
import com.zona.recreativacr.com.zona.recyclerview.IClickListener;
import com.zona.recreativacr.com.zona.recyclerview.RecyclerViewConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProviderActivity extends AppCompatActivity {
    RecyclerView providersRV;
    ProgressBar providersPB;
    FirebaseFirestore mDatabase;
    Task<QuerySnapshot> providersQuerySnapshot;
    List<Provider> providers = new ArrayList<>();
    View contentView;

    IClickListener listener = new IClickListener() {
        @Override
        public void OnClickObject(int position) {
            ProviderActivity.this.clickObject(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        setTitle("Proveedores");

        contentView = findViewById(android.R.id.content);
        providersRV = findViewById(R.id.provider_recyclerView);
        providersPB = findViewById(R.id.provider_progressBar);

        readProviders(new DataStatus<Provider>() {
            @Override
            public void DataIsLoaded(List<Provider> provider) {
                providersPB.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigProviders(providersRV, getBaseContext(),
                        providers, listener);
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

    public void readProviders (final DataStatus dataStatus) {
        mDatabase = FirebaseFirestore.getInstance();
        providersQuerySnapshot = mDatabase.collection("Proveedores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                providers.add(document.toObject(Provider.class));
                            }
                            //Log.d("Empleado", employees.get(0).nombre);
                            dataStatus.DataIsLoaded(providers);
                        }
                    }
                });
    }


    public void goToAddProvider(View view){
        Intent i = new Intent(getBaseContext(), ProviderAddActivity.class);
        startActivity(i);
    }

    private void deletePackage(final int position){
        providersPB.setVisibility(View.VISIBLE);
        mDatabase.collection("Proveedores")
                .document(providers.get(position).id)
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
                        providers.remove(position);
                        Objects.requireNonNull(providersRV.getAdapter()).notifyItemRemoved(position);
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
        providersPB.setVisibility(View.GONE);
    }

    public void clickObject(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] options = {"Actualizar", "Eliminar"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    Intent intent = new Intent(getBaseContext(), ProviderAddActivity.class);
                    intent.putExtra("provider", providers.get(position));
                    startActivity(intent);
                }
                else if(which == 1) {
                    deletePackage(position);
                }
            }
        }).create().show();
    }

    public void updateProviders(View view) {
        providersPB.setVisibility(View.VISIBLE);
        providers.clear();
        providersRV.invalidate();
        Objects.requireNonNull(providersRV.getAdapter()).notifyDataSetChanged();
        readProviders(new DataStatus<Provider>() {
            @Override
            public void DataIsLoaded(List<Provider> provider) {
                providersPB.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigProviders(providersRV, getBaseContext(),
                        providers, listener);
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
