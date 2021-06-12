package com.example.dchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
EditText userlog,passlog;
Button log,Regs;
FirebaseAuth mAuth;
FirebaseUser firebaseUser;


    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {Intent i =new Intent(Login.this,MainActivity.class);
            startActivity( i );
            finish();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );


        userlog=findViewById( R.id.user1 );
        passlog=findViewById( R.id.p1 );
log =findViewById( R.id.logbtn);
Regs=findViewById( R.id.Cac );

mAuth=FirebaseAuth.getInstance();
// new user checking


        Regs.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Login.this,Registor.class);
                startActivity( i );
            }
        } );


log.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        enter();
    }
} );

    }
    public void enter(){

            String email_text =userlog.getText().toString();
            String pass_text=passlog.getText().toString();
            if(TextUtils.isEmpty( email_text )||TextUtils.isEmpty( pass_text )){
                Toast.makeText(Login.this,"Please fill", Toast.LENGTH_SHORT);

            }else{mAuth.signInWithEmailAndPassword(email_text, pass_text)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Login.this, "Authentication done.",
                                        Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(Login.this,MainActivity.class);
                                startActivity( i );
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });


                    }
                    }              }

