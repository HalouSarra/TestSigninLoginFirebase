package com.example.testsigninloginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAct extends AppCompatActivity {
    TextView btn;
    EditText inputEmail,inputPassword;
    Button btnLogin;
    private FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn=findViewById(R.id.textViewSignup);
        inputEmail=findViewById(R.id.inputLmail);
        inputPassword=findViewById(R.id.inputLPassword);
        btnLogin=findViewById(R.id.loginbtn);
        mAuth=FirebaseAuth.getInstance();
        mLoadingBar=new ProgressDialog(LoginAct.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCrededentials();
            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginAct.this,SinginAct.class));
            }
        });
   }

    private void checkCrededentials() {

            String email=inputEmail.getText().toString();
            String password=inputPassword.getText().toString();


            if(email.isEmpty() || !email.contains("@")){
                showError(inputEmail,"votre Email adress n'est pas valide!");
            }
            else if (password.isEmpty() || password.length()<8 ) {
                showError(inputPassword,"votre mot de passe n'est pas valide!(doit contenir au moins 8 caractères)");
            }
            else {
                mLoadingBar.setTitle("Login");
                mLoadingBar.setMessage("veuillez patienter pendant que vous vérifiez vos informations d'identification.");
                mLoadingBar.setCanceledOnTouchOutside(false);
                mLoadingBar.show();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Intent intent=new Intent(LoginAct.this,MainActivity.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                         startActivity(intent);
                         mLoadingBar.dismiss();
                     }
                    }
                });
            }
        }

        private void showError(EditText input, String s) {
            input.setError(s);
            input.requestFocus();
        }

    }


