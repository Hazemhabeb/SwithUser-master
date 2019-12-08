package com.orxtradev.swithuser.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.orxtradev.swithuser.R;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sendBtn)void sendBtn(){



    }
}
