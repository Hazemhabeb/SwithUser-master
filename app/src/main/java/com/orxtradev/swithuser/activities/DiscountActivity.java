package com.orxtradev.swithuser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.model.CodesModel;
import com.orxtradev.swithuser.model.User;
import com.orxtradev.swithuser.utils.SharedPrefDueDate;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiscountActivity extends AppCompatActivity {

    SharedPrefDueDate pref;



    @BindView(R.id.discountTV)TextView discountTV;
    @BindView(R.id.loading)ProgressBar loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        ButterKnife.bind(this);
        pref = new SharedPrefDueDate(this);


//        calculateDiscount();

        getDiscount();
    }







    private void getDiscount(){

        loading.setVisibility(View.VISIBLE);
        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Users").child(pref.getUserId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User model = dataSnapshot.getValue(User.class);

                loading.setVisibility(View.GONE);
                if (model==null)
                    return;

                discountTV.setText(model.getDiscount());

//                calculateDiscount(model.getDiscount());





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        };
        df.addValueEventListener(postListener);
    }


//    //calculate the discount
//    private void calculateDiscount(String discount){
//
//    }


}


