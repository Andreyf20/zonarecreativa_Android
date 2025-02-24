package com.zona.recreativacr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailET, passwordET;
    Button loginBtn;
    ProgressBar loginPB;
    Snackbar snackbar;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailET = findViewById(R.id.admin_email_text);
        passwordET = findViewById(R.id.admin_password_text);
        loginBtn = findViewById(R.id.create_admin_button);
        loginPB = findViewById(R.id.login_progressBar);

        passwordET.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginBtn.performClick();
                    return true;
                }
                return false;
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(final View view) {
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
            loginBtn.setEnabled(false);
            loginPB.setVisibility(View.VISIBLE);

            mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Intent i = new Intent(getBaseContext(), HomeActivity.class);
                                startActivity(i);
                                emailET.setText("");
                                passwordET.setText("");
                                emailET.requestFocus();
                            }else{
                                snackbar = Snackbar.make(view, R.string.invalidCredentials,
                                        Snackbar.LENGTH_LONG);
                                View snackbarView = snackbar.getView();
                                snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(),
                                        R.color.LightBlueDark));
                                snackbar.show();
                            }
                            loginPB.setVisibility(View.GONE);
                            loginBtn.setEnabled(true);
                        }
                    });
        }
        /*Intent i = new Intent(getBaseContext(), HomeActivity.class);
        startActivity(i);*/
    }
}
