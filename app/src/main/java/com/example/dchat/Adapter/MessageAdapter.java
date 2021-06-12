package com.example.dchat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dchat.Model.Chat;
import com.example.dchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter <MessageAdapter.ViewHolder>{
    private Context context;
    private List<Chat>mChat;
    public static final int msg_left=0;
    public static final int msg_right=1;


    public MessageAdapter(Context context, List<Chat> mChat
    )
    {this.context=context;
        this.mChat=mChat;


    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType ==msg_right) {


            View view = LayoutInflater.from( context ).inflate( R.layout.chat_item_right, parent, false );



            return new ViewHolder(view);
        }else {

            View view = LayoutInflater.from( context ).inflate( R.layout.chat_item_left, parent, false );


            return new ViewHolder( view );


        } }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
Chat chat=mChat.get(position);
holder.Show_msg.setText(  chat.getMessage());

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }
    ///
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Show_msg;
        // public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            Show_msg =itemView.findViewById(R.id.show_message);
            //imageView =itemView.findViewById( R.id.imageView1);

        }
    }  @Override
    public int getItemViewType(int position){
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
      if(mChat.get(position).getSender().equals( fuser.getUid() ))
      {
          return msg_right;
      }
else{
    return msg_left;
      }

    }

}
