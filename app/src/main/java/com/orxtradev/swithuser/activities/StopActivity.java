package com.orxtradev.swithuser.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.model.ContactUs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StopActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);
        ButterKnife.bind(this);

    }



    @OnClick(R.id.shareBtn)void contactAction(){
        startActivity(new Intent(StopActivity.this, ContactUsActivity.class));

    }
}
