package com.zona.recreativacr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zona.recreativacr.com.zona.data.Transport;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransportsAddActivity extends AppCompatActivity {
    EditText nombre, numeroTelefono, precio, descripcion;
    ProgressBar transportAddPB;
    Transport transport = null;

    FirebaseFirestore mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transports_add);

        setTitle("Agregar un nuevo transporte");

        nombre = findViewById(R.id.nombre_transporte_editText);
        numeroTelefono = findViewById(R.id.numeroTelefono_transporte_editText);
        precio = findViewById(R.id.precio_transporte_editText);
        descripcion = findViewById(R.id.descripcion_transporte_editText);
        transportAddPB = findViewById(R.id.transportAdd_progressBar);


        if(getIntent().hasExtra("transport")) {
            transport = getIntent().getParcelableExtra("transport");
            nombre.setText(transport.nombre);
            numeroTelefono.setText(transport.numeroTelefono);
            precio.setText(String.valueOf(transport.precio));
            descripcion.setText(transport.descripcion);
        }
    }

    public void addTransport(final View view){
        //TODO: agregar validaciones
        transportAddPB.setVisibility(View.VISIBLE);
        mDatabase = FirebaseFirestore.getInstance();
        String name = nombre.getText().toString();
        String phoneNumber = numeroTelefono.getText().toString();
        int price = Integer.parseInt(precio.getText().toString());
        String descripction = descripcion.getText().toString();
        if(transport == null) {
            String idDoc = UUID.randomUUID().toString();

            Map<String, Object> docData = new HashMap<>();
            docData.put("descripcion", descripction);
            docData.put("id", idDoc);
            docData.put("nombre", name);
            docData.put("numeroTelefono", phoneNumber);
            docData.put("precio", price);

            mDatabase.collection("Transportes").document(idDoc).set(docData)
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
                            nombre.setText("");
                            numeroTelefono.setText("");
                            precio.setText("");
                            descripcion.setText("");
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
                    //Log.d("addTransport", e.toString());
                }
            });
        } else {
            Map<String, Object> docData = new HashMap<>();
            docData.put("descripcion", descripction);
            docData.put("id", transport.id);
            docData.put("nombre", name);
            docData.put("numeroTelefono", phoneNumber);
            docData.put("precio", price);

            mDatabase.collection("Transportes").document(transport.id)
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
        transportAddPB.setVisibility(View.GONE);
    }
}
