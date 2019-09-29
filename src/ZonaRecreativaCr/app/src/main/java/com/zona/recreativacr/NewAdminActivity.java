package com.zona.recreativacr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class NewAdminActivity extends AppCompatActivity {

    EditText emailET, passwordET;
    Button createBtn;
    ProgressBar createPB;
    Snackbar snackbar;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin);
        this.setTitle(R.string.adminNewAdmin);

        emailET = findViewById(R.id.admin_email_text);
        passwordET = findViewById(R.id.admin_password_text);
        createBtn = findViewById(R.id.create_admin_button);
        createPB = findViewById(R.id.create_admin_progressBar);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void createNewAdmin(final View view){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(passwordET.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if(email.isEmpty()) {
            emailET.setError(getString(R.string.validEmail));
            emailET.requestFocus();
        }

        if(password.isEmpty()) {
            passwordET.setError(getString(R.string.validPassword));
        }

        if ((!(email.isEmpty()) && !(password.isEmpty()))){
            createBtn.setEnabled(false);
            createPB.setVisibility(View.VISIBLE);

            mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(NewAdminActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                snackbar = Snackbar.make(view, R.string.newAdminCreated,
                                        Snackbar.LENGTH_LONG);
                                emailET.setText("");
                                passwordET.setText("");
                            }else{
                                snackbar = Snackbar.make(view, R.string.invalidCredentials,
                                        Snackbar.LENGTH_LONG);
                                snackbar.setAction(R.string.retry, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        createNewAdmin(view);
                                    }
                                });
                                snackbar.setActionTextColor(ContextCompat.getColor(getBaseContext(),
                                        R.color.Pink));
                            }
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(),
                                    R.color.LightBlueDark));
                            snackbar.show();
                            createPB.setVisibility(View.GONE);
                            createBtn.setEnabled(true);
                        }
                    });
        }
    }
}
