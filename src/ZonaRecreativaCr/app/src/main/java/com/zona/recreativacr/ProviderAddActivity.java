package com.zona.recreativacr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zona.recreativacr.com.zona.data.Package;
import com.zona.recreativacr.com.zona.data.Provider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProviderAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText nombreET, emailET, phoneET, dirET, descET;
    Spinner serviceSP;
    String service = "";
    ProgressBar providersAddPB;
    Provider provider = null;
    FirebaseFirestore mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_add);
        setTitle("Agregar un nuevo Proveedor");

        nombreET = findViewById(R.id.provider_name_editText);
        emailET = findViewById(R.id.provider_email_editText);
        phoneET = findViewById(R.id.provider_phone_editText);
        dirET = findViewById(R.id.provider_direccion_editText);
        descET = findViewById(R.id.provider_desc_editText);
        providersAddPB = findViewById(R.id.providerAdd_progressBar);
        serviceSP = findViewById(R.id.service_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.service, android.R.layout.simple_spinner_item);
        serviceSP.setAdapter(adapter);
        serviceSP.setOnItemSelectedListener(this);

        if(getIntent().hasExtra("provider")) {
            provider = getIntent().getParcelableExtra("provider");
            nombreET.setText(provider.name);
            emailET.setText(provider.email);
            phoneET.setText(provider.numeroTelefono);
            dirET.setText(provider.direccion);
            descET.setText(provider.descripcion);
            setSelectionSpinner(provider.tipoDeServicio);
        }
    }

    public void addProvider(final View view){
        //TODO: agregar validaciones
        providersAddPB.setVisibility(View.VISIBLE);
        mDatabase = FirebaseFirestore.getInstance();
        String name = nombreET.getText().toString();
        String email = emailET.getText().toString();
        String phone = phoneET.getText().toString();
        String dir = dirET.getText().toString();
        String desc = descET.getText().toString();

        if(provider == null) {
            String idDoc = UUID.randomUUID().toString();

            Map<String, Object> docData = new HashMap<>();
            docData.put("descripcion", desc);
            docData.put("direccion", dir);
            docData.put("email", email);
            docData.put("id", idDoc);
            docData.put("name", name);
            docData.put("numeroTelefono", phone);
            docData.put("tipoDeServicio", service);

            mDatabase.collection("Proveedores").document(idDoc).set(docData)
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
                            emailET.setText("");
                            phoneET.setText("");
                            serviceSP.setSelection(0);
                            dirET.setText("");
                            descET.setText("");
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
            docData.put("descripcion", desc);
            docData.put("direccion", dir);
            docData.put("email", email);
            docData.put("id", provider.id);
            docData.put("name", name);
            docData.put("numeroTelefono", phone);
            docData.put("tipoDeServicio", service);

            mDatabase.collection("Proveedores").document(provider.id)
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
        providersAddPB.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        service = (String) parent.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parent.setSelection(0);
    }

    private void setSelectionSpinner(String type) {
        switch (type) {
            case "Transporte":
                serviceSP.setSelection(1);
                break;
            case "Recreación y Entretenimiento":
                serviceSP.setSelection(2);
                break;
            case "Eventos":
                serviceSP.setSelection(3);
                break;
            case "Organización de evento":
                serviceSP.setSelection(4);
                break;
            case "Limpieza":
                serviceSP.setSelection(5);
                break;
            case "Maintenance":
                serviceSP.setSelection(6);
                break;
            case "Repairs":
                serviceSP.setSelection(7);
                break;
            case "Public Safety":
                serviceSP.setSelection(8);
                break;
            case "Special Projects":
                serviceSP.setSelection(9);
                break;
            case "Event Set up":
                serviceSP.setSelection(10);
                break;
            case "Housekeeping":
                serviceSP.setSelection(11);
                break;
            default:
                serviceSP.setSelection(0);
                break;
        }
    }
}
