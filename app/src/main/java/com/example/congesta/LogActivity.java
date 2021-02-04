package com.example.congesta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.congesta.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class LogActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public TextView regLink;
    public EditText EmailText,NameText,PassText;
    public Button regButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        mAuth = FirebaseAuth.getInstance();
        regLink=(TextView)findViewById(R.id.regLink);
        EmailText=(EditText)findViewById(R.id.emailFeild);
        NameText=(EditText)findViewById(R.id.nameFeild);
        PassText=(EditText)findViewById(R.id.passFeild);
        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regLinkClick();
            }
        });
        regButton=(Button)findViewById(R.id.Regbutton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authFun();
            }
        });


    }

    public void regLinkClick(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void authFun(){
        final String Email=EmailText.getText().toString();
        final String Name=NameText.getText().toString();
        final String Password=PassText.getText().toString();

        if(Email.isEmpty()){
            EmailText.setError("Enter Email");
            EmailText.requestFocus();
            return;
        }
        if(Name.isEmpty()){
            NameText.setError("Enter Email");
            NameText.requestFocus();
            return;
        }
        if(Password.isEmpty()){
            PassText.setError("Enter Email");
            PassText.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user=new User(Name,Email,Password);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LogActivity.this,"Signup Complete",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(LogActivity.this,"Signup Failed",Toast.LENGTH_LONG).show();
                            }
                        }

                    });
                }
                else {
                    Toast.makeText(LogActivity.this,"Signup Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}