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
import com.zona.recreativacr.com.zona.data.MealPlan;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MealPlansAddActivity extends AppCompatActivity {
    EditText almuerzo, cena, desayuno, meriendaAlmuerzo, meriendaCena, meriendaDesayuno, nombre;
    ProgressBar mpAddPB;
    MealPlan mealPlan = null;

    FirebaseFirestore mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plans_add);

        setTitle("Agregar un nuevo plan alimenticio");

        nombre = findViewById(R.id.nombre_mp_editText);
        almuerzo = findViewById(R.id.almuerzo_mp_editText);
        cena = findViewById(R.id.cena_mp_editText);
        desayuno = findViewById(R.id.desayuno_mp_editText);
        meriendaAlmuerzo = findViewById(R.id.meriendaAlm_mp_editText);
        meriendaCena = findViewById(R.id.meriendaCena_mp_editText);
        meriendaDesayuno = findViewById(R.id.meriendaDes_mp_editText);
        mpAddPB = findViewById(R.id.mpAdd_progressBar);


        if(getIntent().hasExtra("mealplan")) {
            mealPlan = getIntent().getParcelableExtra("mealplan");
            nombre.setText(mealPlan.nombre);
            almuerzo.setText(mealPlan.almuerzo);
            cena.setText(mealPlan.cena);
            desayuno.setText(mealPlan.desayuno);
            meriendaAlmuerzo.setText(mealPlan.meriendaAlmuerzo);
            meriendaCena.setText(mealPlan.meriendaCena);
            meriendaDesayuno.setText(mealPlan.meriendaDesayuno);
        }
    }

    public void addMealPlan(final View view){
        //TODO: agregar validaciones
        mpAddPB.setVisibility(View.VISIBLE);
        mDatabase = FirebaseFirestore.getInstance();
        String name = nombre.getText().toString();
        String alm = almuerzo.getText().toString();
        String cen = cena.getText().toString();
        String des = desayuno.getText().toString();
        String meriendaAlm = meriendaAlmuerzo.getText().toString();
        String meriendaCen = meriendaCena.getText().toString();
        String meriendaDes = meriendaDesayuno.getText().toString();
        if(mealPlan == null) {
            String idDoc = UUID.randomUUID().toString();

            Map<String, Object> docData = new HashMap<>();
            docData.put("almuerzo", alm);
            docData.put("cena", cen);
            docData.put("desayuno", des);
            docData.put("meriendaAlmuerzo", meriendaAlm);
            docData.put("meriendaCena", meriendaCen);
            docData.put("meriendaDesayuno", meriendaDes);
            docData.put("id", idDoc);
            docData.put("nombre", name);

            mDatabase.collection("Comidas").document(idDoc).set(docData)
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
                            almuerzo.setText("");
                            cena.setText("");
                            desayuno.setText("");
                            meriendaAlmuerzo.setText("");
                            meriendaCena.setText("");
                            meriendaDesayuno.setText("");
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
                    //Log.d("addMealPlan", e.toString());
                }
            });
        } else {

            Map<String, Object> docData = new HashMap<>();
            docData.put("almuerzo", alm);
            docData.put("cena", cen);
            docData.put("desayuno", des);
            docData.put("meriendaAlmuerzo", meriendaAlm);
            docData.put("meriendaCena", meriendaCen);
            docData.put("meriendaDesayuno", meriendaDes);
            docData.put("id", mealPlan.id);
            docData.put("nombre", name);

            mDatabase.collection("Comidas").document(mealPlan.id)
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
        mpAddPB.setVisibility(View.GONE);
    }
}
