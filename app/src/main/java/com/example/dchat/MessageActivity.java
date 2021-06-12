package com.example.dchat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dchat.Adapter.MessageAdapter;
import com.example.dchat.Model.Chat;
import com.example.dchat.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
TextView username;
FirebaseUser fuser;
RecyclerView recyclerView;
DatabaseReference reference;
EditText msg_edit;
Button send;
MessageAdapter messageAdapter;
List<Chat> mchat;
Intent intent;
   String userid;

//RecyclerView recyclerView;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_message );
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
   username=findViewById( R.id.us1 );
        send=findViewById( R.id.sendbtn );
        msg_edit=findViewById( R.id.send_text );
       recyclerView=findViewById( R.id.rc2 );
       recyclerView.setHasFixedSize( true );

LinearLayoutManager linearLayoutManager=new LinearLayoutManager( getApplicationContext() );
linearLayoutManager.setStackFromEnd( true );
recyclerView.setLayoutManager( linearLayoutManager );



intent =getIntent();
   userid=intent.getStringExtra( "userid");
fuser= FirebaseAuth.getInstance().getCurrentUser();
reference= FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);
    reference.addValueEventListener( new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Users user=snapshot.getValue(Users.class);
            username.setText(  user.getUsername());
readMessage( fuser.getUid(),userid );
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    } );
send.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String msg=msg_edit.getText().toString();
        if(!msg.equals( "" ))
        {sendMessage(fuser.getUid(),userid,msg);
        

        }else{
            Toast.makeText( MessageActivity.this, "Please send non empty", Toast.LENGTH_SHORT ).show();
        }msg_edit.setText("");
    }
} );
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void sendMessage(String sender ,String receiver, String message)
    {DatabaseReference reference =FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashmap= new HashMap<>();
hashmap.put("sender",sender);
hashmap.put("receiver",receiver);
hashmap.put( "message",message );
reference.child("Chats").push().setValue( hashmap );

//
        final DatabaseReference chatRef=FirebaseDatabase.getInstance().getReference("ChatList").child(fuser.getUid()).child( userid );


chatRef.addListenerForSingleValueEvent( new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(!snapshot.exists())
        {
            chatRef.child( "id" ).setValue( userid );
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
} );



    }
private void readMessage(final String myid,
                         final String userid){



        mchat= new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener( new ValueEventListener() {
            @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
                mchat.clear();
                String f;

                for(DataSnapshot snapshot1:snapshot.getChildren())
                {Chat chat=snapshot1.getValue(Chat.class);
if(chat.getReceiver()!=null&&chat.getSender()!=null)
{if((chat.getReceiver().equals(myid)&&chat.getSender().equals( userid ))||(chat.getReceiver().equals( userid )&&chat.getSender().equals( myid )))
                    {mchat.add( chat );

                    }}
                  messageAdapter=new MessageAdapter( MessageActivity.this,mchat );
                    recyclerView.setAdapter( messageAdapter );
                } }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );


    }
}