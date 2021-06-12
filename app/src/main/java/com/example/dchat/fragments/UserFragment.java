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


public class UserFragment extends Fragment {
private RecyclerView recyclerView;
private UserAdapter userAdapter;
private List<Users>musers;

public UserFragment(){

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view=inflater.inflate( R.layout.fragment_users,container,false);
      recyclerView=view.findViewById( R.id.recylerview );
      recyclerView.setHasFixedSize( true );
      recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
musers =new ArrayList<>();
ReadUsers();
return  view;
}

private void ReadUsers(){

    final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("MyUsers");
   reference.addValueEventListener( new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot snapshot) {
           musers.clear()
           ;
           for (DataSnapshot snapshot1 : snapshot.getChildren()) {
               Users user = snapshot1.getValue( Users.class );
               assert user != null;
               if (!user.getId().equals( firebaseUser.getUid() )) {
                   musers.add( user );


               }
               userAdapter = new UserAdapter( getActivity(), musers );
               recyclerView.setAdapter( userAdapter );
           }

       }


       @Override
       public void onCancelled(@NonNull DatabaseError error) {

       }
   } );




 }}