package com.orxtradev.swithuser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.model.CodesModel;
import com.orxtradev.swithuser.model.InviteModel;
import com.orxtradev.swithuser.model.User;
import com.orxtradev.swithuser.utils.SharedPrefDueDate;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CodeActivity extends AppCompatActivity {


    //init the views
    @BindView(R.id.codeTV)
    TextView codeTV;

    @BindView(R.id.loading)
    ProgressBar loading;


    SharedPrefDueDate pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);

        pref = new SharedPrefDueDate(this);


        getcode();

    }


    private void getcode() {
        loading.setVisibility(View.VISIBLE);
        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("InvitationCodes").child(pref.getUserId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                loading.setVisibility(View.GONE);
                if (!dataSnapshot.exists()) {


                    CodesModel m = new CodesModel();
                    m.setId(pref.getUserId());
                    m.setCode(new Random().nextInt(1000000) + "");
                    m.setUserId(pref.getUserId());

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("InvitationCodes")
                            .child(m.getId()).setValue(m);


                    return;
                }

                CodesModel model = dataSnapshot.getValue(CodesModel.class);

                if (model == null) {

                    CodesModel m = new CodesModel();
                    m.setId(pref.getUserId());
                    m.setCode(new Random().nextInt(1000000) + "");
                    m.setUserId(pref.getUserId());

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("InvitationCodes")
                            .child(m.getId()).setValue(m);

                    return;
                }
                codeTV.setText(model.getCode());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        };
        df.addValueEventListener(postListener);
    }

    @OnClick(R.id.shareBtn)
    void shareAction() {
        String shareBody = "نزل تطبيق سويتش من اللينك ده ودخل الكود بعد التسجيل لتحصل علي الخصم الكود : " + codeTV.getText().toString() + "       https://play.google.com/store/apps/details?id=com.orxtradev.swithuser";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "تطبيق سويتش");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "مشاركة الكود"));
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


