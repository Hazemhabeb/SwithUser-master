package com.orxtradev.swithuser.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.model.CityModel;
import com.orxtradev.swithuser.model.GovernmentModel;
import com.orxtradev.swithuser.model.NotificationModel;
import com.orxtradev.swithuser.model.PreviewMModel;
import com.orxtradev.swithuser.model.PreviewOrder;
import com.orxtradev.swithuser.model.equiment;
import com.orxtradev.swithuser.notification.DownstreamMessage;
import com.orxtradev.swithuser.utils.SharedPrefDueDate;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewActivity extends AppCompatActivity {

    //init the views
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.goverSP)
    Spinner goverSP;
    @BindView(R.id.equimentSP)
    Spinner equimentSP;
    @BindView(R.id.nameET)
    EditText nameET;

    @BindView(R.id.phoneET)
    EditText phoneET;

    @BindView(R.id.phone2ET)
    EditText phone2ET;
    @BindView(R.id.addressET)
    EditText addressET;
    @BindView(R.id.buldingET)
    EditText buldingET;
    @BindView(R.id.appartementET)
    EditText appartementET;

    @BindView(R.id.nearET)
    EditText nearET;


    private ArrayList<GovernmentModel> govers  = new ArrayList<>();
    private ArrayList<equiment> equiments  = new ArrayList<>();

    SharedPrefDueDate pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);

        pref = new SharedPrefDueDate(this);


        getGover();
        getequiment();
    }

    @OnClick(R.id.previewBtn)void previewAction(){


        if (!validate())
            return;
        PreviewOrder model = new PreviewOrder();


        model.setId(random());
        model.setName(nameET.getText().toString());
        model.setAddress(addressET.getText().toString());
        model.setBuildingNumber(buldingET.getText().toString());
        model.setFlatNumber(appartementET.getText().toString());
        model.setLandmark(nearET.getText().toString());
        model.setPhone(phoneET.getText().toString());
        model.setPhone2(phone2ET.getText().toString());
        model.setStatus("0");
        model.setTime(System.currentTimeMillis()+"");
        model.setUserId(pref.getUserId());
        model.setEquimentTypeId(equiments.get(equimentSP.getSelectedItemPosition()).getName());
        model.setGovernment(govers.get(goverSP.getSelectedItemPosition()).getName());

        model.setOrderNumber(new Random().nextInt(1000000)+"");


        loading.setVisibility(View.VISIBLE);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("PreviewOrders")
                .child(model.getId()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!
                // ...



//                NotificationModel notificationModel = new NotificationModel();
//                notificationModel.setId(random());
//                notificationModel.setDataId(model.getId());
//                notificationModel.setName("تطبيق سويتش");
//                notificationModel.setDesc("طلب معاينة جديد");
//                notificationModel.setTime(System.currentTimeMillis()+"");
//                notificationModel.setType("1");
//                notificationModel.setStatus("0");

//                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
//                ref1.child("Notification")
//                        .child(notificationModel.getId()).setValue(notificationModel);
//
//                DownstreamMessage downstreamMessage = new DownstreamMessage();
//
//
//                String [] data = new String[4];
//                data[0] = govers.get(goverSP.getSelectedItemPosition()).getId();
//                data[1] = model.getId();
//                data[2] = "1";
//                data[3] = notificationModel.getId();
//
//                downstreamMessage.execute(data);

                loading.setVisibility(View.GONE);
                Toast.makeText(PreviewActivity.this, "تم ارسال الطلب بنجاح ", Toast.LENGTH_LONG).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Write failed
                // ...
                loading.setVisibility(View.GONE);
                Toast.makeText(PreviewActivity.this, "حدث خطأ برجاء المحاولة مره اخري ", Toast.LENGTH_LONG).show();
                finish();

            }
        });


    }



    private boolean validate(){
        if (nameET.getText().toString().isEmpty()||phoneET.getText().toString().isEmpty()||
        buldingET.getText().toString().isEmpty()||appartementET.getText().toString().isEmpty()||
        addressET.getText().toString().isEmpty()|| nearET.getText().toString().isEmpty()){
            Toast.makeText(PreviewActivity.this, "من فضلك قم بملئ جميع البيانات ", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @OnClick(R.id.contactusBtn)void contactAction(){

        startActivity(new Intent(PreviewActivity.this,ContactUsActivity.class));

    }


    // to get the governoment

    private void getGover(){

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

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(PreviewActivity.this, R.layout.spinner_center_item,
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


    private void getequiment(){

        loading.setVisibility(View.VISIBLE);

        equiments.clear();
        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        df.child("Equipment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            equiment model = snapshot.getValue(equiment.class);
                            equiments.add(model);
                        }
                    }
                    loading.setVisibility(View.GONE);


                    String d[] = new String[equiments.size()];

                    for (int i = 0; i < equiments.size(); i++) {
                        d[i] = equiments.get(i).getName();
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(PreviewActivity.this, R.layout.spinner_center_item,
                            d);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_center_item); // The drop down view
                    equimentSP.setAdapter(spinnerArrayAdapter);

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
