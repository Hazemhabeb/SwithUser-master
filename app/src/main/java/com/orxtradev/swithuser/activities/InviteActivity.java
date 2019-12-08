package com.orxtradev.swithuser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.model.CityModel;
import com.orxtradev.swithuser.model.CodesModel;
import com.orxtradev.swithuser.model.DiscountModel;
import com.orxtradev.swithuser.model.GovernmentModel;
import com.orxtradev.swithuser.model.InviteModel;
import com.orxtradev.swithuser.model.MandoubModel;
import com.orxtradev.swithuser.model.User;
import com.orxtradev.swithuser.utils.SharedPrefDueDate;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteActivity extends AppCompatActivity {


    //init the views
    @BindView(R.id.codeET)
    EditText codeET;
    @BindView(R.id.loading)
    ProgressBar loading;


    SharedPrefDueDate pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);

        pref = new SharedPrefDueDate(this);


    }


    @OnClick(R.id.sendBtn)
    void sendAction() {

        if (codeET.getText().toString().isEmpty()) {
            Toast.makeText(InviteActivity.this, "من فضلك ادخل الكود", Toast.LENGTH_LONG).show();
            return;
        }


        loading.setVisibility(View.VISIBLE);

        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        Query q = df.child("InvitationCodes").orderByChild("code").equalTo(codeET.getText().toString());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(InviteActivity.this, "برجاء التأكد من الكود ", Toast.LENGTH_LONG).show();
                    loading.setVisibility(View.GONE);
                    return;
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CodesModel model = snapshot.getValue(CodesModel.class);

                    loading.setVisibility(View.GONE);

                    if (model == null) {
                        Toast.makeText(InviteActivity.this, "برجاء التأكد من الكود ", Toast.LENGTH_LONG).show();
                    } else {

                        InviteModel m = new InviteModel();
                        m.setId(random());
                        m.setInvitationId(model.getId());
                        m.setCode(model.getCode());
                        m.setTime(System.currentTimeMillis()+"");
                        m.setUserId(pref.getUserId());

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        ref.child("InvitationUsers")
                                .child(m.getId()).setValue(m);

//                         here to get the discount and added it the the user

                        getDiscountFirst();



                        break;


                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        q.addValueEventListener(postListener);


    }


    private void getDiscountFirst (){


        loading.setVisibility(View.VISIBLE);
        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        Query q = df.child("Discount").child("First");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Log.d("google","error discount code1");
//                    Toast.makeText(InviteActivity.this, "برجاء التأكد من الكود ", Toast.LENGTH_LONG).show();
                    loading.setVisibility(View.GONE);
                    return;
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DiscountModel model = snapshot.getValue(DiscountModel.class);

                    loading.setVisibility(View.GONE);

                    if (model == null) {
                        Log.d("google","error discount code");
//                        Toast.makeText(InviteActivity.this, "برجاء التأكد من الكود ", Toast.LENGTH_LONG).show();
                    } else {




                        Log.d("google","this is the discount        "+model.getDiscount());

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        ref.child("Users")
                                .child(pref.getUserId()).child("discount").setValue(model.getDiscount());

                        Intent i = new Intent(InviteActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                        break;


                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        q.addValueEventListener(postListener);


    }

    @OnClick(R.id.skipBtn)
    void skipAction() {
        Intent i = new Intent(InviteActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
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


