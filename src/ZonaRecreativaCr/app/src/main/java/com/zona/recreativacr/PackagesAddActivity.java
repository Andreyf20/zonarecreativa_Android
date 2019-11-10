package com.zona.recreativacr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zona.recreativacr.com.zona.data.Package;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PackagesAddActivity extends AppCompatActivity {
    EditText nombreET, capacidadET, precioET, tipoET, descripcionET;
    Switch breakfastSW, coffeeSW, lunchSW, activeSW;
    ProgressBar packagesAddPB;
    Package packag = null;
    FirebaseFirestore mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages_add);
        setTitle("Agregar un nuevo paquete");

        nombreET = findViewById(R.id.name_pq_editText);
        capacidadET = findViewById(R.id.capacity_pq_editText);
        precioET = findViewById(R.id.price_pq_editText);
        tipoET = findViewById(R.id.type_pq_editText);
        descripcionET = findViewById(R.id.descripcion_pq_editText);
        breakfastSW = findViewById(R.id.breakfast_switch);
        coffeeSW = findViewById(R.id.coffee_switch);
        lunchSW = findViewById(R.id.lunch_switch);
        activeSW = findViewById(R.id.active_switch);
        packagesAddPB = findViewById(R.id.packageAdd_progressBar);

        if(getIntent().hasExtra("package")) {
            packag = getIntent().getParcelableExtra("package");
            nombreET.setText(packag.name);
            capacidadET.setText(packag.capacity);
            precioET.setText(packag.price);
            tipoET.setText(packag.type);
            descripcionET.setText(packag.descrip);
            if(packag.breakfast)
                breakfastSW.setChecked(true);
            if(packag.coffe)
                coffeeSW.setChecked(true);
            if(packag.lunch)
                lunchSW.setChecked(true);
            if(packag.active)
                activeSW.setChecked(true);
        }
    }

    public void addPackage(final View view){
        //TODO: agregar validaciones
        packagesAddPB.setVisibility(View.VISIBLE);
        mDatabase = FirebaseFirestore.getInstance();
        String name = nombreET.getText().toString();
        String capacity = capacidadET.getText().toString();
        String price = precioET.getText().toString();
        String type = tipoET.getText().toString();
        String descripction = descripcionET.getText().toString();
        boolean breakfast = breakfastSW.isChecked();
        boolean coffee = coffeeSW.isChecked();
        boolean lunch = lunchSW.isChecked();
        boolean active = activeSW.isChecked();

        if(packag == null) {
            String idDoc = UUID.randomUUID().toString();

            Map<String, Object> docData = new HashMap<>();
            docData.put("active", active);
            docData.put("breakfast", breakfast);
            docData.put("capacity", capacity);
            docData.put("coffe", coffee);
            docData.put("descrip", descripction);
            docData.put("id", idDoc);
            docData.put("imgURL", "");
            docData.put("lunch", lunch);
            docData.put("name", name);
            docData.put("price", price);
            docData.put("refImage", "");
            docData.put("refThumbnail", "");
            docData.put("thumbnailURL", "");
            docData.put("type", type);

            mDatabase.collection("Paquetes").document(idDoc).set(docData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar snackbar;
                            snackbar = Snackbar.make(view, R.string.okCreated,
                                    Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(),
                                    R.color.LightBlueDark));
                            snackbar.show();
                            nombreET.setText("");
                            capacidadET.setText("");
                            precioET.setText("");
                            tipoET.setText("");
                            descripcionET.setText("");
                            breakfastSW.setChecked(false);
                            coffeeSW.setChecked(false);
                            lunchSW.setChecked(false);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar snackbar;
                    snackbar = Snackbar.make(view, R.string.errorCreated,
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(),
                            R.color.LightBlueDark));
                    snackbar.show();
                }
            });
        } else {
            Map<String, Object> docData = new HashMap<>();
            docData.put("active", active);
            docData.put("breakfast", breakfast);
            docData.put("capacity", capacity);
            docData.put("coffe", coffee);
            docData.put("descrip", descripction);
            docData.put("id", packag.id);
            docData.put("imgURL", packag.imgURL);
            docData.put("lunch", lunch);
            docData.put("name", name);
            docData.put("price", price);
            docData.put("refImage", packag.refImage);
            docData.put("refThumbnail", packag.refThumbnail);
            docData.put("thumbnailURL", packag.thumbnailURL);
            docData.put("type", type);

            mDatabase.collection("Paquetes").document(packag.id)
                    .set(docData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar snackbar;
                            snackbar = Snackbar.make(view, R.string.okUpdated,
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
                    snackbar = Snackbar.make(view, R.string.errorUpdated,
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(),
                            R.color.LightBlueDark));
                    snackbar.show();
                }
            });
        }
        packagesAddPB.setVisibility(View.GONE);
    }
}
