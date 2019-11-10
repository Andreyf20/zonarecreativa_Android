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
import com.zona.recreativacr.com.zona.data.MealPlan;
import com.zona.recreativacr.com.zona.recyclerview.IClickListener;
import com.zona.recreativacr.com.zona.recyclerview.RecyclerViewConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MealPlansActivity extends AppCompatActivity {

    RecyclerView mealplanRV;
    ProgressBar mealplanPB;
    FirebaseFirestore mDatabase;
    Task<QuerySnapshot> mealplanQuerySnapshot;
    List<MealPlan> mealplans = new ArrayList<>();
    View contentView;

    IClickListener listener = new IClickListener() {
        @Override
        public void OnClickObject(int position) {
            MealPlansActivity.this.clickObject(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plans);
        contentView = findViewById(android.R.id.content);
        mealplanRV = findViewById(R.id.mealplan_recyclerView);
        mealplanPB = findViewById(R.id.mealplan_progressBar);
        this.setTitle("Planes alimenticios");

        readMealPlans(new DataStatus<MealPlan>() {
            @Override
            public void DataIsLoaded(List<MealPlan> list) {
                mealplanPB.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigMealPlan(mealplanRV, getBaseContext(),
                        mealplans, listener);
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

    public void readMealPlans (final DataStatus dataStatus) {
        mDatabase = FirebaseFirestore.getInstance();
        mealplanQuerySnapshot = mDatabase.collection("Comidas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                mealplans.add(document.toObject(MealPlan.class));
                            }
                            //Log.d("Empleado", employees.get(0).nombre);
                            dataStatus.DataIsLoaded(mealplans);
                        } else {
                            Log.d("Comida", "Task not successful");
                        }
                    }
                });
    }

    public void goToAddMealPlan(View view){
        Intent i = new Intent(getBaseContext(), MealPlansAddActivity.class);
        startActivity(i);
    }

    private void deleteMealPlan(final int position){
        mealplanPB.setVisibility(View.VISIBLE);
        mDatabase.collection("Comidas")
                .document(mealplans.get(position).id)
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
                        mealplans.remove(position);
                        Objects.requireNonNull(mealplanRV.getAdapter()).notifyItemRemoved(position);
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
        mealplanPB.setVisibility(View.GONE);
    }

    public void clickObject(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] options = {"Actualizar", "Eliminar"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    Intent intent = new Intent(getBaseContext(), MealPlansAddActivity.class);
                    intent.putExtra("mealplan", mealplans.get(position));
                    startActivity(intent);
                }
                else if(which == 1) {
                    deleteMealPlan(position);
                }
            }
        }).create().show();
    }

    public void updateMealPlans(View view){
        mealplanPB.setVisibility(View.VISIBLE);
        mealplans.clear();
        mealplanRV.invalidate();
        Objects.requireNonNull(mealplanRV.getAdapter()).notifyDataSetChanged();
        readMealPlans(new DataStatus<MealPlan>() {
            @Override
            public void DataIsLoaded(List<MealPlan> list) {
                mealplanPB.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigMealPlan(mealplanRV, getBaseContext(),
                        mealplans, listener);
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
