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
import com.orxtradev.swithuser.activities.OrderPreviewDetailActivity;
import com.orxtradev.swithuser.model.NotificationModel;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    private Context context;

    ArrayList<NotificationModel> data;
    public NotificationAdapter(Context context,ArrayList<NotificationModel> data) {
        this.context = context;
        this.data = data;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_notification, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.titleTV.setText(data.get(position).getName());
        holder.detailTV.setText(data.get(position).getDesc());

        if (data.get(position).getType().equals("1")) {
            Intent i = new Intent(context, OrderPreviewDetailActivity.class);
            i.putExtra("data", data.get(position));
            context.startActivity(i);
        }else  if (data.get(position).getType().equals("2")) {
            Intent i = new Intent(context, OrderDetailActivity.class);
            i.putExtra("data", data.get(position));
            context.startActivity(i);
        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {



        @BindView(R.id.titleTV)
        TextView titleTV;
        @BindView(R.id.detailTV)
        TextView detailTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}
