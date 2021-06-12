package com.example.dchat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dchat.MessageActivity;
import com.example.dchat.Model.Users;
import com.example.dchat.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter <UserAdapter.ViewHolder>{
   private Context context;
   private List<Users>musers;
    public UserAdapter(Context context, List<Users> musers
    )
    {this.context=context;
        this.musers=musers;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from( context ).inflate( R.layout.users_item,parent,false );

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
        //return new UserAdapter.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
final Users users= musers.get( position ) ;
holder.username.setText( users.getUsername() );
 holder.itemView.setOnClickListener( new View.OnClickListener() {
     @Override
     public void onClick(View v) {
Intent i= new Intent(context, MessageActivity.class);
i.putExtra( "userid",users.getId());
   context.startActivity( i ); }
 } );
{
  //  holder.imageView.setImageResource( R.mipmap.ic_launcher );

}
    }

    @Override
    public int getItemCount() {
        return musers.size();
    }
    ///
public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
       // public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            username =itemView.findViewById(R.id.uk1);
            //imageView =itemView.findViewById( R.id.imageView1);

        }
    }


}
