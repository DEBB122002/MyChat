package com.example.dchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.dchat.Model.Users;
import com.example.dchat.fragments.ChatFragment;
import com.example.dchat.fragments.UserFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//
    FirebaseUser firebaseUser;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef= FirebaseDatabase.getInstance().getReference("Users").child( firebaseUser.getUid() );

   myRef.addValueEventListener( new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot snapshot) {
         Users users =snapshot.getValue(Users.class);

       }

       @Override
       public void onCancelled(@NonNull DatabaseError error) {

       }
   } );
   //tab layout
        TabLayout tableLayout =findViewById( R.id.tabLayout );
        ViewPager viewPager=findViewById( R.id.viewPager);
        ViewPagerAdpater viewPagerAdpater=new ViewPagerAdpater( getSupportFragmentManager() );
viewPagerAdpater.addFragment( new ChatFragment(),"chats" );
viewPagerAdpater.addFragment(new UserFragment(),"users"  );
        viewPager.setAdapter(viewPagerAdpater);
        tableLayout.setupWithViewPager(viewPager);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(MainActivity.this,Login.class)
                );
                finish();
                return true;
        }
        return false;

    }


    class ViewPagerAdpater extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;
ViewPagerAdpater(FragmentManager fm)
{super(fm);
this.fragments=new ArrayList<>();
this.titles=new ArrayList<>();


}

        @NonNull
        @Override
        public Fragment getItem(int position) {
             return  fragments.get( position );
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

public void addFragment(Fragment fragment,String title)
{
    fragments.add(fragment);
    titles.add(title);
}
public CharSequence getPageTitle(int position)
{
    return titles.get( position );
}



    }













}