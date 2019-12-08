package com.orxtradev.swithuser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.adapters.FollowAdapter;
import com.orxtradev.swithuser.model.FollowModel;
import com.orxtradev.swithuser.model.PiecesFollowModel;
import com.orxtradev.swithuser.model.User;
import com.orxtradev.swithuser.model.maintenanceOrder;
import com.orxtradev.swithuser.utils.SharedPrefDueDate;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrackActivity extends AppCompatActivity {



    @BindView(R.id.emptyParent)
    LinearLayout emptyParent;
    @BindView(R.id.followParent)
    LinearLayout followParent;
    @BindView(R.id.orderParent)
    LinearLayout orderParent;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.equimentTV)
    TextView equimentTV;
    @BindView(R.id.followRV)
    RecyclerView followRV;

    @BindView(R.id.loading)
    ProgressBar loading;

    FollowAdapter adapter ;


    ArrayList<PiecesFollowModel> pieces = new ArrayList<>();


    SharedPrefDueDate pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        ButterKnife.bind(this);

        pref = new SharedPrefDueDate(this);
        adapter = new FollowAdapter(TrackActivity.this,pieces);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TrackActivity.this,RecyclerView.VERTICAL,false);

        followRV.setLayoutManager(layoutManager);

        followRV.setAdapter(adapter);

        getData();
    }

    @OnClick(R.id.previewBtn)void previewAction(){
        startActivity(new Intent(TrackActivity.this,PreviewActivity.class));
    }




    //get the data of the user

    private void getData (){


        loading.setVisibility(View.VISIBLE);

        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("FollowEquipment");

        Query q = df.orderByChild("userId").equalTo(pref.getUserId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                loading.setVisibility(View.GONE);


                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            FollowModel model = snapshot.getValue(FollowModel.class);

                            if (model == null) {
                                emptyParent.setVisibility(View.VISIBLE);
                                followParent.setVisibility(View.GONE);
                                orderParent.setVisibility(View.VISIBLE);
                                return;
                            }


                            emptyParent.setVisibility(View.GONE);
                            followParent.setVisibility(View.VISIBLE);
                            orderParent.setVisibility(View.GONE);


                            nameTV.setText(model.getName());
                            equimentTV.setText(model.getEquipment());


                            pieces.addAll(model.getPiecesFollow());
                            adapter.notifyDataSetChanged();



                        }
                    }

                } else {

                    emptyParent.setVisibility(View.VISIBLE);
                    followParent.setVisibility(View.GONE);
                    orderParent.setVisibility(View.VISIBLE);

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        };
        q.addValueEventListener(postListener);
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
