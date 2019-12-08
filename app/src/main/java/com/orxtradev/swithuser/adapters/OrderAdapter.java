package com.orxtradev.swithuser.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.activities.OrderDetailActivity;
import com.orxtradev.swithuser.model.maintenanceOrder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {


    private Context context;

    private ArrayList<maintenanceOrder> data;
    public OrderAdapter(Context context, ArrayList<maintenanceOrder> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_order, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.numberTV.setText(data.get(position).getOrderNumber());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
        String dateString = formatter.format(new Date(Long.parseLong(data.get(position).getTime())));
        holder.timeTV.setText(dateString);

        //0 for pending 1 for accpeted 2 for workinng 3 for finishedd -1 for refused

        String status = "";

        if (data.get(position).getStatus().equals("0")){
            status = "في الانتظار";
            holder.stausTV.setTextColor(context.getResources().getColor(R.color.color_hotel3));
        }else  if (data.get(position).getStatus().equals("1")){
            status = "تم القبول";
            holder.stausTV.setTextColor(context.getResources().getColor(R.color.color_hotel2));
        }else  if (data.get(position).getStatus().equals("2")){
            status = "قيد التنفيذ";
            holder.stausTV.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else  if (data.get(position).getStatus().equals("3")){
            status = "انتهت";
            holder.stausTV.setTextColor(context.getResources().getColor(R.color.gray_text));
        }else  if (data.get(position).getStatus().equals("-1")){
            status = "تم الرفض";
            holder.stausTV.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }

        holder.stausTV.setText(status);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,OrderDetailActivity.class);
                intent.putExtra("data",data.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {



        @BindView(R.id.numberTV)
        TextView numberTV;
        @BindView(R.id.statusTV)TextView stausTV;
        @BindView(R.id.timeTV)TextView timeTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}
