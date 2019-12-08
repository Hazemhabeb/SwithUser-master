package com.orxtradev.swithuser.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.model.Offers;
import com.orxtradev.swithuser.model.PiecesOrderModel;
import com.orxtradev.swithuser.model.equimentPieces;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PiecesAdapter extends RecyclerView.Adapter<PiecesAdapter.ViewHolder> {


    private Context context;
    ArrayList<equimentPieces> data;
    String selectedPiece;

    public PiecesAdapter(Context context, ArrayList<equimentPieces> data,
                         String selectedPiece) {
        this.context = context;
        this.data = data;
        this.selectedPiece = selectedPiece;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_pieces, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.nameTV.setText(data.get(position).getName());


//        Log.d("google","this is the data comming "+selectedPiece+"  " +
//                " this is the data"+data.get(position).getName());

        if (selectedPiece != null)
            if (selectedPiece.equals(data.get(position).getName())) {
                holder.piecesParent.setBackground(context.getResources().getDrawable(R.drawable.back_corner3));
                holder.selectedTV.setText("true");
                holder.quantityTV.setText(1 + "");


                PiecesOrderModel model = new PiecesOrderModel();
                model.setId(data.get(position).getId());
                model.setName(data.get(position).getName());
                model.setPrice(data.get(position).getSellingPrice());
                model.setQuantity(1 + "");

                EventBus.getDefault().post(model);
            }

        if (!data.get(position).getImage().isEmpty()) {
            Glide.with(context).load(data.get(position).getImage()).into(holder.pieceIV);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.selectedTV.getText().equals("false")) {
                    holder.piecesParent.setBackground(context.getResources().getDrawable(R.drawable.back_corner3));
                    holder.selectedTV.setText("true");
                    holder.quantityTV.setText(1 + "");


                    PiecesOrderModel model = new PiecesOrderModel();
                    model.setId(data.get(position).getId());
                    model.setName(data.get(position).getName());
                    model.setPrice(data.get(position).getSellingPrice());
                    model.setQuantity(1 + "");

                    EventBus.getDefault().post(model);

                } else {
                    holder.piecesParent.setBackground(context.getResources().getDrawable(R.drawable.back_corner4));
                    holder.selectedTV.setText("false");
                    holder.quantityTV.setText(0 + "");


                    PiecesOrderModel model = new PiecesOrderModel();
                    model.setId(data.get(position).getId());
                    model.setName(data.get(position).getName());
                    model.setPrice(data.get(position).getSellingPrice());
                    model.setQuantity(0 + "");

                    EventBus.getDefault().post(model);
                }
            }
        });
        //todo here to change the cart data
        holder.plusIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(holder.quantityTV.getText().toString());

                q = q + 1;
                holder.quantityTV.setText(q + "");
                holder.piecesParent.setBackground(context.getResources().getDrawable(R.drawable.back_corner3));
                holder.selectedTV.setText("true");

                PiecesOrderModel model = new PiecesOrderModel();
                model.setId(data.get(position).getId());
                model.setName(data.get(position).getName());
                model.setPrice(data.get(position).getSellingPrice());
                model.setQuantity(q + "");

                EventBus.getDefault().post(model);

            }
        });

        holder.minceIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(holder.quantityTV.getText().toString());
                if (q == 0) {

                    holder.piecesParent.setBackground(context.getResources().getDrawable(R.drawable.back_corner4));
                    holder.selectedTV.setText("false");
                    holder.quantityTV.setText(0 + "");


                    PiecesOrderModel model = new PiecesOrderModel();
                    model.setId(data.get(position).getId());
                    model.setName(data.get(position).getName());
                    model.setPrice(data.get(position).getSellingPrice());
                    model.setQuantity(q + "");

                    EventBus.getDefault().post(model);

                    return;
                }

                q = q - 1;
                holder.quantityTV.setText(q + "");

                PiecesOrderModel model = new PiecesOrderModel();
                model.setId(data.get(position).getId());
                model.setName(data.get(position).getName());
                model.setPrice(data.get(position).getSellingPrice());
                model.setQuantity(q + "");

                EventBus.getDefault().post(model);


                if (q == 0) {

                    holder.piecesParent.setBackground(context.getResources().getDrawable(R.drawable.back_corner4));
                    holder.selectedTV.setText("false");
                    holder.quantityTV.setText(0 + "");


                }
            }
        });
    }


    @Override
    public int getItemCount() {
//        if (list.size() != 0) {
//            return list.size();
//        } else
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.nameTV)
        TextView nameTV;
        @BindView(R.id.plusIV)
        ImageView plusIV;
        @BindView(R.id.minceIV)
        ImageView minceIV;
        @BindView(R.id.quantityTV)
        TextView quantityTV;
        @BindView(R.id.piecesParent)
        LinearLayout piecesParent;
        @BindView(R.id.selectedTV)
        TextView selectedTV;
        @BindView(R.id.pieceIV)
        ImageView pieceIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}
