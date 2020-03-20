package com.example.loginapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.R;
import com.example.loginapp.modal.User;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<User> users;
    OnClickRecyclerView onClickRecyclerView;
    public RecyclerViewAdapter(List<User> users ,OnClickRecyclerView onClickRecyclerView) {
        this.onClickRecyclerView=onClickRecyclerView;
        this.users =users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewlayout,parent,false);
        return new ViewHolder(view, onClickRecyclerView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User currentUser = users.get(position);
        holder.Name.setText(currentUser.getName());
        holder.Email.setText(currentUser.getEmail());


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Name;
        TextView Email;
        TextView Phone;
        TextView Gender;
        TextView Hobies;
        ImageView delete;
        ImageView Edit;
        OnClickRecyclerView onClickRecyclerView;
        public ViewHolder(@NonNull View itemView , final OnClickRecyclerView onClickRecyclerView) {
            super(itemView);
            this.onClickRecyclerView=onClickRecyclerView;
            Name =itemView.findViewById(R.id.name);
            Email =itemView.findViewById(R.id.email);
            delete =itemView.findViewById(R.id.delete);
            Edit =itemView.findViewById(R.id.edit);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRecyclerView.onClick(getAdapterPosition());
                }
            });
            delete.setOnClickListener(this);
            Edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
         switch (v.getId()){
             case R.id.delete:
                 onClickRecyclerView.onDelete(getAdapterPosition());
                 break;
             case R.id.edit:
                 onClickRecyclerView.onEdit(getAdapterPosition());
                 break;
         }
        }
    }

    public interface OnClickRecyclerView{
        void onDelete(int position );
        void onEdit(int position);
        void onClick(int position);

    }
}
