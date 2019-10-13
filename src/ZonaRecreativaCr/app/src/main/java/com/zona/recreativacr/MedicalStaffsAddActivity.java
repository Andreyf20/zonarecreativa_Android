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
import com.google.firebase.firestore.FirebaseFirestore;
import com.zona.recreativacr.com.zona.data.MedicalStaff;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MedicalStaffsAddActivity extends AppCompatActivity {
    EditText nombre, numeroTelefono, descripcion;
    ProgressBar msAddPB;
    MedicalStaff medicalStaff = null;

    FirebaseFirestore mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_staffs_add);

        setTitle("Agregar nuevo personal médico");

        nombre = findViewById(R.id.nombre_ms_editText);
        numeroTelefono = findViewById(R.id.numeroTelefono_ms_editText);
        descripcion = findViewById(R.id.descripcion_ms_editText);
        msAddPB = findViewById(R.id.msAdd_progressBar);

        if(getIntent().hasExtra("medicalstaff")) {
            medicalStaff = getIntent().getParcelableExtra("medicalstaff");
            nombre.setText(medicalStaff.nombre);
            numeroTelefono.setText(medicalStaff.numeroTelefono);
            descripcion.setText(medicalStaff.descripcion);
        }
    }

    public void addMedicalStaff(final View view){
        //TODO: agregar validaciones
        msAddPB.setVisibility(View.VISIBLE);
        mDatabase = FirebaseFirestore.getInstance();
        String name = nombre.getText().toString();
        String phoneNumber = numeroTelefono.getText().toString();
        String descripction = descripcion.getText().toString();

        if(medicalStaff == null) {
            String idDoc = UUID.randomUUID().toString();

            Map<String, Object> docData = new HashMap<>();
            docData.put("descripcion", descripction);
            docData.put("id", idDoc);
            docData.put("nombre", name);
            docData.put("numeroTelefono", phoneNumber);

            mDatabase.collection("PersonalMedico").document(idDoc).set(docData)
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
                }
            });
        } else{
            Map<String, Object> docData = new HashMap<>();
            docData.put("descripcion", descripction);
            docData.put("id", medicalStaff.id);
            docData.put("nombre", name);
            docData.put("numeroTelefono", phoneNumber);

            mDatabase.collection("PersonalMedico").document(medicalStaff.id)
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
        msAddPB.setVisibility(View.GONE);
    }
}
