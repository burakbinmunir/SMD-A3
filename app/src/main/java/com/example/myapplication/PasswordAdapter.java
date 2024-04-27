package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.ViewHolder>{

    ArrayList<Password> passwordsArrayList;
    Context context;
    public PasswordAdapter(Context context, ArrayList<Password> passwordsArrayList){
        this.passwordsArrayList = passwordsArrayList;
        this.context = context;
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

        holder.imgBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditPassword.class);
                intent.putExtra("appId", passwordsArrayList.get(position).getId());
                intent.putExtra("appUsername", passwordsArrayList.get(position).getAppUserName());
                intent.putExtra("appPassword", passwordsArrayList.get(position).getAppPassword());
                intent.putExtra("appName", passwordsArrayList.get(position).getAppName());

                context.startActivity(intent);
            }
        });

        holder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler databaseHandler = new DatabaseHandler(context);
                databaseHandler.open();
                databaseHandler.deletePassword(passwordsArrayList.get(position).getId());

                databaseHandler.close();
                passwordsArrayList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Do you really want to delete it?");
                deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete code
                        DatabaseHandler database = new DatabaseHandler(context);
                        database.open();
                        database.deletePassword(passwordsArrayList.get(holder.getAdapterPosition()).getId());
                        database.close();

                        passwordsArrayList.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        ///parentActivity.deleteContactFromList(holder.getAdapterPosition());
                    }
                });
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Canceled");
                    }
                });

                deleteDialog.show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return passwordsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvAppName, tvAppUserName, tvAppPassword;
        ImageButton imgBtnEdit, imgBtnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAppName = itemView.findViewById(R.id.tvAppName);
            tvAppUserName = itemView.findViewById(R.id.tvAppUserName);
            tvAppPassword = itemView.findViewById(R.id.tvAppPassword);
            imgBtnEdit = itemView.findViewById(R.id.imgBtnEdit);
            imgBtnDelete = itemView.findViewById(R.id.imgBtnDelete);

        }
    }
}
