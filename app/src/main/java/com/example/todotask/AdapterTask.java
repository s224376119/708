package com.example.todotask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.ViewHolder> {
    List<TaskModel> list;
    Context context;
    OnClickEditData onClickEditData;
    LayoutInflater inflater;

    public AdapterTask(List<TaskModel> list, Context context, OnClickEditData onClickEditData) {
        this.list = list;
        this.context = context;
        this.onClickEditData = onClickEditData;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TaskModel taskModel = list.get(position);
        holder.tvName.setText(taskModel.getName());
        holder.date.setText(formatDate(taskModel.getCurrentTimeStamp()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailPage.class);
            intent.putExtra("data", taskModel);
            context.startActivity(intent);
        });

        holder.imvEdit.setOnClickListener(v -> {
            onClickEditData.onclick(taskModel);
        });
        holder.imvDelete.setOnClickListener(v -> {
            deleteTask(taskModel, holder.getAdapterPosition(), v);
        });

    }

    public String formatDate(long timeStamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(timeStamp);
        return sdf.format(date);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvEdit, imvDelete;
        TextView tvName, date;
        TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imvEdit = itemView.findViewById(R.id.imvEdit);
            imvDelete = itemView.findViewById(R.id.imvDelete);
            date = itemView.findViewById(R.id.date);

        }
    }

    private void deleteTask(TaskModel taskModel, int index, View view) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getRootView().getContext())
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this item?")
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        boolean deleteStatus = databaseHelper.deletePost(String.valueOf(taskModel.getTaskKey()));
                        if (deleteStatus) {
                            Toast.makeText(context.getApplicationContext(), "Task deleted successfully", Toast.LENGTH_LONG).show();
                            list.remove(index);
                            notifyItemRemoved(index);
                        } else {
                            Toast.makeText(context.getApplicationContext(), "Failed to delete task", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        alertDialog.show();
    }


}
