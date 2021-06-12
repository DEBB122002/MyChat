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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registor extends AppCompatActivity {
EditText userET,passET,emailET;
Button reg;
 FirebaseAuth mAuth;
    private DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registor );
        userET =findViewById( R.id.username );
        passET =findViewById(R.id.password);
        emailET=findViewById( R.id.email );
        reg= findViewById( R.id.submit );
mAuth =FirebaseAuth.getInstance();
reg.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String username=userET.getText().toString();
        String email= emailET.getText().toString();
        String pass=passET.getText().toString();
        if(TextUtils.isEmpty( username )||TextUtils.isEmpty( email )||TextUtils.isEmpty( pass )){
            Toast.makeText( Registor.this,"Three filled are required" ,Toast.LENGTH_SHORT ).show();
        }else if(pass.length()<10)
        {Toast.makeText( Registor.this,"PASSWORD LENGHTH SHOULD BE MORE THAN ",Toast.LENGTH_SHORT );}
        else{
            RegistorNow( username,email,pass);
        }
    }
} );
    }
    private void RegistorNow(final String username,String email,String Password){
        mAuth.createUserWithEmailAndPassword(email, Password)
                .addOnCompleteListener(Registor.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Registor.this, "Authentication done",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId=user.getUid();
                            myref= FirebaseDatabase.getInstance().getReference("MyUsers").child( userId);

                            Intent i= new Intent(Registor.this,MainActivity.class);
                            i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK );
                            startActivity( i );
                            //
                            HashMap<String,String> hashmap=new HashMap<>();
                            hashmap.put( "id",userId );
                            hashmap.put("username",username);
                            hashmap.put("imageURL","default");
                          myref.setValue( hashmap ).addOnCompleteListener( new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {

                              }
                          } );
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registor.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });


    }
}