package com.orxtradev.swithuser.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.orxtradev.swithuser.utils.SharedPrefDueDate;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsActivity extends AppCompatActivity {


    //init the views
    @BindView(R.id.nameET)
    EditText nameET;
    @BindView(R.id.phoneET)
    EditText phoneET;
    @BindView(R.id.messageET)
    EditText messageET;
    @BindView(R.id.goverSP)
    Spinner goverSP;
    @BindView(R.id.loading)
    ProgressBar loading;


    private ArrayList<GovernmentModel> govers = new ArrayList<>();

    SharedPrefDueDate pref;


    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        ButterKnife.bind(this);


        pref = new SharedPrefDueDate(this);
        getGover();

        data = getIntent().getStringExtra("data");

        if (data != null)
            messageET.setText( "استفسار بخصوص   " + data +"    ");
    }


    @OnClick(R.id.sendBtn)
    void sendAction() {

        
        if (!validate())
            return;

        ContactUs model = new ContactUs();
        model.setId(random());
        model.setTime(System.currentTimeMillis() + "");
        model.setGovernment(govers.get(goverSP.getSelectedItemPosition()).getName());


        String message = "";
        if (data != null)
            message = "استفسار بخصوص   " + data + messageET.getText().toString();
        else
            message = messageET.getText().toString();

        model.setMessage(message);
        model.setUserId(pref.getUserId());
        model.setName(nameET.getText().toString());
        model.setPhone(phoneET.getText().toString());


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("ContactUs")
                .child(model.getId()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!
                // ...
                loading.setVisibility(View.GONE);
                Toast.makeText(ContactUsActivity.this, "تم ارسال الرسالة بنجاح ", Toast.LENGTH_LONG).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Write failed
                // ...
                loading.setVisibility(View.GONE);
                Toast.makeText(ContactUsActivity.this, "حدث خطأ برجاء المحاولة مره اخري ", Toast.LENGTH_LONG).show();
                finish();

            }
        });

    }


    private boolean validate() {


        if (nameET.getText().toString().isEmpty() || phoneET.getText().toString().isEmpty() ||
                messageET.getText().toString().isEmpty()) {

            Toast.makeText(ContactUsActivity.this, "من فضلك قم بملئ جميع البيانات ", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    // to get the governoment

    private void getGover() {

        loading.setVisibility(View.VISIBLE);

        govers.clear();
        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        df.child("Government").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            GovernmentModel model = snapshot.getValue(GovernmentModel.class);
                            govers.add(model);
                        }
                    }
                    loading.setVisibility(View.GONE);


                    String d[] = new String[govers.size()];

                    for (int i = 0; i < govers.size(); i++) {
                        d[i] = govers.get(i).getName();
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(ContactUsActivity.this, R.layout.spinner_center_item,
                            d);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_center_item); // The drop down view
                    goverSP.setAdapter(spinnerArrayAdapter);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
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
