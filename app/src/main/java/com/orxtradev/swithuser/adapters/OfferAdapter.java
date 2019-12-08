package com.orxtradev.swithuser.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.model.Offers;
import com.orxtradev.swithuser.model.equiment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {


    private Context context;
    ArrayList<Offers> data;

    public OfferAdapter(Context context, ArrayList<Offers> data) {
        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_product, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.detailTV.setText(data.get(position).getDesc());
        holder.titleTV.setText(data.get(position).getName());

        holder.priceParent.setVisibility(View.GONE);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.startActivity(new Intent(context, OrderDetailActivity.class));
//            }
//        });
    }

    @Override
    public int getItemCount() {
//        if (list.size() != 0) {
//            return list.size();
//        } else
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.titleTV)
        TextView titleTV;
        @BindView(R.id.detailTV)
        TextView detailTV;
        @BindView(R.id.priceParent)
        LinearLayout priceParent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}