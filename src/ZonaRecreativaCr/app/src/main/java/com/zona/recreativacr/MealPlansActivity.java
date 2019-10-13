package com.zona.recreativacr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.zona.recreativacr.com.zona.data.Employee;
import com.zona.recreativacr.com.zona.data.MealPlan;
import com.zona.recreativacr.com.zona.recyclerview.RecyclerViewConfig;

import java.util.ArrayList;
import java.util.List;

public class MealPlansActivity extends AppCompatActivity {

    RecyclerView mealplanRV;
    FirebaseFirestore mDatabase;
    Task<QuerySnapshot> mealplanQuerySnapshot;
    List<MealPlan> mealplans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plans);
        mealplanRV = findViewById(R.id.mealplan_recyclerView);

        this.setTitle("Planes alimenticios");

        readMealPlans(new DataStatus<MealPlan>() {
            @Override
            public void DataIsLoaded(List<MealPlan> list) {
                findViewById(R.id.mealplan_progressBar).setVisibility(View.GONE);
                new RecyclerViewConfig().setConfigMealPlan(mealplanRV, getBaseContext(),
                        mealplans);
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
}
