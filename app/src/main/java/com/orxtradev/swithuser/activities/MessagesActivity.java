package com.orxtradev.swithuser.activities;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.adapters.MessageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.loading)
    ProgressBar loading;

    private MessageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        ButterKnife.bind(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new MessageAdapter(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
    }
}
