package com.orxtradev.swithuser.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.model.ContactUs;
import com.orxtradev.swithuser.model.GovernmentModel;
import com.orxtradev.swithuser.model.PreviewOrder;
import com.orxtradev.swithuser.model.RateModel;
import com.orxtradev.swithuser.model.User;
import com.orxtradev.swithuser.model.maintenanceOrder;
import com.orxtradev.swithuser.utils.SharedPrefDueDate;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RateActivity extends AppCompatActivity {


    //init the views

    @BindView(R.id.rateRB)
    RatingBar rateRB;
    @BindView(R.id.messageET)
    EditText messageET;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.numberTV)
    TextView numberTV;
    @BindView(R.id.checkTV)
    TextView checkTV;
    @BindView(R.id.sendBtn)
    Button sendBtn;

    SharedPrefDueDate pref;


    PreviewOrder data1;
    maintenanceOrder data2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        ButterKnife.bind(this);

        pref = new SharedPrefDueDate(this);

        data1 = (PreviewOrder) getIntent().getSerializableExtra("data1");
        data2 = (maintenanceOrder) getIntent().getSerializableExtra("data2");

        if (data1 != null) {
            numberTV.setText("طلب معانية رقم "  +data1.getOrderNumber());
            checkRate(data1.getId());
        } else if (data2 != null) {
            numberTV.setText("طلب صيانة رقم "  +data2.getOrderNumber());
            checkRate(data2.getId());
        }






    }


    private void checkRate(String id){
        loading.setVisibility(View.VISIBLE);
        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Rating").child(id);

        ValueEventListener postListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    RateModel model = dataSnapshot.getValue(RateModel.class);

                    loading.setVisibility(View.GONE);
                    if (model == null)
                        return;


                    checkTV.setVisibility(View.VISIBLE);

                    rateRB.setClickable(false);
                    rateRB.setFocusable(false);
                    rateRB.setFocusable(View.NOT_FOCUSABLE);
                    rateRB.setRating(Float.parseFloat(model.getRate()));
                    messageET.setClickable(false);
                    messageET.setFocusable(false);
                    messageET.setText(model.getMessage());

                    sendBtn.setVisibility(View.GONE);


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        };
        df.addValueEventListener(postListener);
    }
    @OnClick(R.id.sendBtn)
    void sendAction() {

        RateModel model = new RateModel();

        model.setTime(System.currentTimeMillis() + "");
        model.setUserId(pref.getUserId());
        model.setMessage(messageET.getText().toString());
        model.setRate(rateRB.getRating() + "");

        if (data1 != null) {
            model.setId(data1.getId());
            model.setMandobId(data1.getMandoubId());
            model.setOrderId(data1.getId());
            model.setOrderType("1");
        } else if (data2 != null) {
            model.setId(data2.getId());
            model.setMandobId(data2.getMandoubId());
            model.setOrderId(data2.getId());
            model.setOrderType("2");
        }


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Rating")
                .child(model.getId()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!
                // ...
                loading.setVisibility(View.GONE);
                Toast.makeText(RateActivity.this, "تم ارسال التقييم بنجاح ", Toast.LENGTH_LONG).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Write failed
                // ...
                loading.setVisibility(View.GONE);
                Toast.makeText(RateActivity.this, "حدث خطأ برجاء المحاولة مره اخري ", Toast.LENGTH_LONG).show();
                finish();

            }
        });

    }


    /**
     * to get ids for the firebase
     *
     * @return random string
     */
    protected String random() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }


}
