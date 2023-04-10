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

//import java.util.Objects;

public class SinginAct extends AppCompatActivity {
    TextView btn;
    private EditText inputUserN,inputPass,inputmail,inputConfPass,inputPhoneNbr;
    Button btnRegister;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);
        btn=findViewById(R.id.alreadyHaveAcc);
        inputUserN=findViewById(R.id.inputUser);
        inputmail=findViewById(R.id.inputEmail);
        inputPhoneNbr=findViewById(R.id.inputPhone);
        inputPass=findViewById(R.id.inputLPassword);
        inputConfPass=findViewById(R.id.inputCPassword);
        mAuth=FirebaseAuth.getInstance();
        mLoadingBar=new ProgressDialog(SinginAct.this);


        btnRegister=findViewById(R.id.btnSingin);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              checkCrededentials();

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SinginAct.this,LoginAct.class));
            }
        });
    }

    private void checkCrededentials() {
        String username=inputUserN.getText().toString();
        String email=inputmail.getText().toString();
        String password=inputPass.getText().toString();
        String phoneNbr=inputPhoneNbr.getText().toString();
        String condPhoneNbr = "^[+]\\d{10,13}$"; //^[+]?[0-9]{10,13}$ // \\d=[0-9]
        String confrmPassword=inputConfPass.getText().toString();

  if(username.isEmpty() || username.length()<7){
      showError(inputUserN,"votre nom d'utilisateur n'est pas valide! (doit contenir au moins 7 caractères) ");
  }
  else if (email.isEmpty() || !email.contains("@")) {
      showError(inputmail,"votre Email adress n'est pas valide!");
  }
  else if (phoneNbr.isEmpty() || !phoneNbr.matches(condPhoneNbr)) {
      showError(inputPhoneNbr,"votre numéro de téléphone n'est pas valide");
  }
  else if (password.isEmpty() || password.length()<8  ) {
      showError(inputPass,"votre mot de passe n'est pas valide!(doit contenir au moins 8 caractères)");
  }
  else if (confrmPassword.isEmpty() || !confrmPassword.equals(password)) {
      showError(inputConfPass,"le mot de passe ne correspond pas");
  }
  else {
   mLoadingBar.setTitle("Registration");
   mLoadingBar.setMessage("veuillez patienter pendant que vous vérifiez vos informations d'identification.");
   mLoadingBar.setCanceledOnTouchOutside(false);
   mLoadingBar.show();

   mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
       @Override
       public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){
               Toast.makeText(SinginAct.this, "inscription réussie", Toast.LENGTH_LONG).show();
               Intent intent=new Intent(SinginAct.this,MainActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
               mLoadingBar.dismiss();

           }
           else {
               Toast.makeText(SinginAct.this, task.getException().toString(),Toast.LENGTH_SHORT).show();
           }
           //bToast.makeText(SinginAct.this, Objects.requireNonNull(task.getException()).toString(),Toast.LENGTH_SHORT).show();


       }
   });

  }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}