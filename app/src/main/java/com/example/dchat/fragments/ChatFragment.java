 package com.example.dchat.fragments;

 import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

 import androidx.annotation.NonNull;
 import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dchat.Adapter.UserAdapter;
import com.example.dchat.Model.Chatlist;
import com.example.dchat.Model.Users;
import com.example.dchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.database.ValueEventListener;

 import java.util.ArrayList;
import java.util.List;

 public class ChatFragment extends Fragment {

     private UserAdapter userAdapter;
     private List<Users> mUsers;
     FirebaseUser fuser;
     DatabaseReference reference;

     private List<Chatlist> userlist;

     RecyclerView recyclerView;

     public ChatFragment() {

     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         View view = inflater.inflate( R.layout.fragment_chat, container, false );
         recyclerView = view.findViewById( R.id.rc3 );
         recyclerView.setHasFixedSize( true );
         recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
         fuser = FirebaseAuth.getInstance().getCurrentUser();
         userlist = new ArrayList<>();
         reference = FirebaseDatabase.getInstance().getReference();

         userlist = new ArrayList<>();
         reference = FirebaseDatabase.getInstance().getReference( "ChatList" ).child( fuser.getUid() );
     reference.addValueEventListener( new ValueEventListener() {

         @Override

         public void onDataChange(@NonNull DataSnapshot snapshot) { userlist.clear();
             for(DataSnapshot snapshot1:snapshot.getChildren())
             {Chatlist chatlist=snapshot1.getValue(
                     Chatlist.class
             );
                 userlist.add( chatlist );

             }
           chatlist();


         }


         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     } );





     return view;
     }

     private void chatlist() {  mUsers=new ArrayList<>();
         reference=FirebaseDatabase.getInstance().getReference("MyUsers");
         reference.addValueEventListener( new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 mUsers.clear();
                 for(DataSnapshot snapshot1:snapshot.getChildren())
                 {


                     Users user=snapshot1.getValue(Users.class);
                     assert user != null;
                     for(Chatlist chatlist:userlist)

                     {if(user.getId()!=null)
                     {  if(user.getId().equals(chatlist.getId()))
                     {                         mUsers.add( user );

                     }}

                     }
                 }System.out.println( mUsers );
                 userAdapter=new UserAdapter( getContext(),mUsers);
                 recyclerView.setAdapter( userAdapter ) ;
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         } );
     }
     }

