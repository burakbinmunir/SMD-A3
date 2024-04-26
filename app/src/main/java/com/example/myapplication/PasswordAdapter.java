package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.ViewHolder>{

    ArrayList<Password> passwordsArrayList;

    public PasswordAdapter(ArrayList<Password> passwordsArrayList){
        this.passwordsArrayList = passwordsArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.activity_single_password_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordAdapter.ViewHolder holder, int position) {
        holder.tvAppName.setText(passwordsArrayList.get(position).getAppName());
        holder.tvAppUserName.setText(passwordsArrayList.get(position).getAppUserName());
        holder.tvAppPassword.setText(passwordsArrayList.get(position).getAppPassword());
    }

    @Override
    public int getItemCount() {
        return passwordsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvAppName, tvAppUserName, tvAppPassword;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAppName = itemView.findViewById(R.id.tvAppName);
            tvAppUserName = itemView.findViewById(R.id.tvAppUserName);
            tvAppPassword = itemView.findViewById(R.id.tvAppPassword);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), tvAppName.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
