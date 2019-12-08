package com.orxtradev.swithuser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.adapters.OrderPreviewAdapter;
import com.orxtradev.swithuser.model.maintenanceOrder;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends AppCompatActivity {

    //init the view
    @BindView(R.id.numberTV)
    TextView numberTV;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.phone2TV)
    TextView phone2TV;
    @BindView(R.id.phoneTV)
    TextView phoneTV;
    @BindView(R.id.goverTV)
    TextView goverTV;
    @BindView(R.id.addressTV)
    TextView addressTV;
    @BindView(R.id.buldingTV)
    TextView buldingTV;

    @BindView(R.id.flatTV)
    TextView flatTV;
    @BindView(R.id.nearTV)
    TextView nearTV;
    @BindView(R.id.equimentTV)
    TextView equimentTV;
    @BindView(R.id.dayTV)
    TextView dayTV;
    @BindView(R.id.clockTV)
    TextView clockTV;
    @BindView(R.id.noteTV)
    TextView noteTV;
    @BindView(R.id.totalPriceTV)
    TextView totalPriceTV;
    @BindView(R.id.statusTV)
    TextView statusTV;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.recieveBtn)
    Button recieveBtn;

    @BindView(R.id.rateBtn)
    Button rateBtn;


    maintenanceOrder data ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        data = (maintenanceOrder) getIntent().getSerializableExtra("data");

        numberTV.setText(" طلب رقم "+data.getOrderNumber());
        nameTV.setText(data.getName());
        phone2TV.setText(data.getPhone2());
        phoneTV.setText(data.getPhone());
        goverTV.setText(data.getGovernment());
        addressTV.setText(data.getAddress());
        buldingTV.setText(data.getBuildingNumber());
        flatTV.setText(data.getFlatNumber());
        nearTV.setText(data.getLandmark());
        equimentTV.setText(data.getEquimentId());
        dayTV.setText(data.getDay());
        clockTV.setText(" من "+data.getFromClock()+"  الي  "+data.getToClock());
        noteTV.setText(data.getPieceId()+"   -   "+data.getNote());
        totalPriceTV.setText(data.getTotalPrice()+" جنيه ");

        String note  = "";

        for (int i = 0 ;i<data.getPiecesOrder().size();i++){
            note += data.getPiecesOrder().get(i).getName() + "  - العدد :  "+data.getPiecesOrder().get(i).getQuantity() +" \n";
        }
        noteTV.setText(note);


        String status = "";

        if (data.getStatus().equals("0")){
            status = "في الانتظار";
            statusTV.setTextColor(getResources().getColor(R.color.color_hotel3));

            rateBtn.setVisibility(View.GONE);
        }else  if (data.getStatus().equals("1")){
            status = "تم القبول";
            statusTV.setTextColor(getResources().getColor(R.color.color_hotel2));
            rateBtn.setVisibility(View.GONE);
        }else  if (data.getStatus().equals("2")){
            status = "قيد التنفيذ";
            statusTV.setTextColor(getResources().getColor(R.color.colorPrimary));
            rateBtn.setVisibility(View.GONE);
        }else  if (data.getStatus().equals("3")){
            status = "انتهت";
            statusTV.setTextColor(getResources().getColor(R.color.gray_text));
            rateBtn.setVisibility(View.VISIBLE);
            recieveBtn.setVisibility(View.GONE);
        }else  if (data.getStatus().equals("-1")){
            status = "تم الرفض";
            statusTV.setTextColor(getResources().getColor(R.color.colorAccent));
            recieveBtn.setVisibility(View.GONE);
            rateBtn.setVisibility(View.GONE);
        }

        statusTV.setText(status);




    }



    @OnClick(R.id.recieveBtn)void recieveAction(){

        loading.setVisibility(View.VISIBLE);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("MaintenanceOrders")
                .child(data.getId()).child("status").setValue("3").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!
                // ...
                loading.setVisibility(View.GONE);
                Toast.makeText(OrderDetailActivity.this, "تم الاستلام بنجاح ", Toast.LENGTH_LONG).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Write failed
                // ...
                loading.setVisibility(View.GONE);
                Toast.makeText(OrderDetailActivity.this, "حدث خطأ برجاء المحاولة مره اخري ", Toast.LENGTH_LONG).show();
                finish();

            }
        });


    }

    @OnClick(R.id.reportBtn)void reportAction(){

        Intent i = new Intent(OrderDetailActivity.this,ContactUsActivity.class);
        i.putExtra("data"," طلب صيانة رقم "+data.getOrderNumber());
        startActivity(i);


    }

    @OnClick(R.id.rateBtn)void rateAction(){
        Intent i = new Intent(OrderDetailActivity.this, RateActivity.class);
        i.putExtra("data2",data);
        startActivity(i);
    }


}
