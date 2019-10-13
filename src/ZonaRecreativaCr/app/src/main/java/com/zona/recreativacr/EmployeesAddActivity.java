package com.zona.recreativacr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zona.recreativacr.com.zona.data.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EmployeesAddActivity extends AppCompatActivity {
    EditText nombre, numeroSeguro, vence, vige, cedula;
    ProgressBar employeeAddPB;
    FirebaseFirestore mDatabase;
    Date ven;
    Date vig;
    Employee employee = null;

    final int DATE_PICKER_VEN = 0;
    final int DATE_PICKER_VIG = 1;

    DatePickerDialog.OnDateSetListener ven_dateListener,vig_dateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_add);

        setTitle("Agregar un nuevo empleado");

        nombre = findViewById(R.id.nombre_editText);
        numeroSeguro = findViewById(R.id.numeroSeguro_editText);
        vence = findViewById(R.id.vence_editText);
        vige = findViewById(R.id.vige_editText);
        cedula = findViewById(R.id.cedula_editText);
        employeeAddPB = findViewById(R.id.employeeAdd_progressBar);

        ven_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
                try {
                    ven = dateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                vence.setText(date);
            }
        };

        vig_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
                try {
                    vig = dateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                vige.setText(date);
            }
        };

        vence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog datePicker = createDatePickerDialog(DATE_PICKER_VEN);
                if(datePicker != null)
                    datePicker.show();
            }
        });

        vige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog datePicker = createDatePickerDialog(DATE_PICKER_VIG);
                if(datePicker != null)
                    datePicker.show();
            }
        });

        if(getIntent().hasExtra("employee")) {
            employee = getIntent().getParcelableExtra("employee");
            nombre.setText(employee.nombre);
            numeroSeguro.setText(employee.numeroSeguro);
            vence.setText(employee.vence.toString());
            vige.setText(employee.vige.toString());
            cedula.setText(employee.cedula);
        }
    }

    protected Dialog createDatePickerDialog(int id) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            switch (id) {
                case DATE_PICKER_VEN:
                    return new DatePickerDialog(this, ven_dateListener,
                            Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                case DATE_PICKER_VIG:
                    return new DatePickerDialog(this, vig_dateListener,
                            Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            }
            return null;
        }
        return null;
    }

    public void addEmployee(final View view) {
        //TODO: agregar validaciones
        mDatabase = FirebaseFirestore.getInstance();
        employeeAddPB.setVisibility(View.VISIBLE);
        String name = nombre.getText().toString();
        String insurance = numeroSeguro.getText().toString();
        Timestamp expire = new Timestamp(ven);
        Timestamp vi = new Timestamp(vig);
        String id = cedula.getText().toString();
        // Si es nulo entonces es uno nuevo
        if(employee == null) {
            String idDoc = UUID.randomUUID().toString();

            Map<String, Object> docData = new HashMap<>();
            docData.put("cedula", id);
            docData.put("id", idDoc);
            docData.put("nombre", name);
            docData.put("numeroSeguro", insurance);
            docData.put("vence", expire);
            docData.put("vige", vi);

            mDatabase.collection("Empleados").document(idDoc).set(docData)
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
                            numeroSeguro.setText("");
                            vence.setText("");
                            vige.setText("");
                            cedula.setText("");
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
                    //Log.d("addEmployee", e.toString());
                }
            });

        } // Si no es nulo entonces se esta modificando uno existente
        else {

            Map<String, Object> docData = new HashMap<>();
            docData.put("cedula", id);
            docData.put("id", employee.id);
            docData.put("nombre", name);
            docData.put("numeroSeguro", insurance);
            docData.put("vence", expire);
            docData.put("vige", vi);

            mDatabase.collection("Empleados").document(employee.id)
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
                    //Log.d("addEmployee", e.toString());
                }
            });
        }
        employeeAddPB.setVisibility(View.GONE);
    }
}
