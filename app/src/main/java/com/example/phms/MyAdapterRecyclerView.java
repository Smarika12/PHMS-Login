package com.example.phms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterRecyclerView extends RecyclerView.Adapter<MyViewHolder>
{
    private Context context;
    private List <DataClass> dataList;

    public MyAdapterRecyclerView(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {

        holder.recFoodName.setText(dataList.get(position).getFoodName());
        holder.recFoodCal.setText(dataList.get(position).getCalorieCount());
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder
{
    TextView recFoodName, recFoodCal;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView)
    {
        super(itemView);

        recFoodName = itemView.findViewById(R.id.recTitle);
        recFoodCal = itemView.findViewById(R.id.recCalorie);
    }

}
