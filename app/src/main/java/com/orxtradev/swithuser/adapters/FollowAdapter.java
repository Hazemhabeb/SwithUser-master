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
import com.orxtradev.swithuser.activities.MaintanceActivity;
import com.orxtradev.swithuser.activities.OrderDetailActivity;
import com.orxtradev.swithuser.model.PiecesFollowModel;
import com.orxtradev.swithuser.model.maintenanceOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {


    private Context context;

    private ArrayList<PiecesFollowModel> data;

    public FollowAdapter(Context context, ArrayList<PiecesFollowModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_follow, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.nameTV.setText(data.get(position).getName());

        //to get the data


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(data.get(position).getChangeTime());


            if (System.currentTimeMillis() >= strDate.getTime()) {
                holder.changeTV.setVisibility(View.VISIBLE);
                holder.changeTimeTV.setText("قم بتغير القطعه الان");
                holder.changeTimeTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, MaintanceActivity.class));
                    }
                });
                
            } else {
                holder.changeTV.setVisibility(View.GONE);

                Date c = Calendar.getInstance().getTime();
//                System.out.println("Current time => " + c);

//                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = sdf.format(c);
                String endDate = sdf.format(strDate);


                int days = getCountOfDays(formattedDate, endDate);

                holder.changeTimeTV.setText(" باقي  " + days + " يوم علي تغيرها  ");
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.changeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,MaintanceActivity.class);
                intent.putExtra("piece",data.get(position).getName());

                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.nameTV)
        TextView nameTV;
        @BindView(R.id.changeTimeTV)
        TextView changeTimeTV;
        @BindView(R.id.changeTV)
        TextView changeTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

    public int getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


    /*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    */

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return (int) dayCount;
    }


}
