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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zona.recreativacr.com.zona.data.MedicalStaff;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MedicalStaffsAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText nombre, numeroTelefono, descripcion;
    Spinner provinceSP;
    String province = "San José";
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
        provinceSP = findViewById(R.id.province_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.province, android.R.layout.simple_spinner_item);
        provinceSP.setAdapter(adapter);
        provinceSP.setOnItemSelectedListener(this);

        if(getIntent().hasExtra("medicalstaff")) {
            medicalStaff = getIntent().getParcelableExtra("medicalstaff");
            nombre.setText(medicalStaff.nombre);
            numeroTelefono.setText(medicalStaff.numeroTelefono);
            descripcion.setText(medicalStaff.descripcion);
            setSelectionSpinner(medicalStaff.provincia);
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
            docData.put("provincia", province);

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
            docData.put("provincia", province);

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

    private void setSelectionSpinner(String type) {
        switch (type) {
            case "Alajuela":
                provinceSP.setSelection(1);
                break;
            case "Cartago":
                provinceSP.setSelection(2);
                break;
            case "Limón":
                provinceSP.setSelection(3);
                break;
            case "Puntarenas":
                provinceSP.setSelection(4);
                break;
            case "Guanacaste":
                provinceSP.setSelection(5);
                break;
            case "Heredia":
                provinceSP.setSelection(6);
                break;
            default:
                provinceSP.setSelection(0);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        province = (String) provinceSP.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parent.setSelection(0);
    }
}
