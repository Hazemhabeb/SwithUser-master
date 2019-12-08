package com.orxtradev.swithuser.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.orxtradev.swithuser.model.GovernmentModel;
import com.orxtradev.swithuser.model.User;
import com.orxtradev.swithuser.utils.SharedPrefDueDate;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.nameET)
    EditText nameET;
    @BindView(R.id.goverSP)
    Spinner goverSP;
    @BindView(R.id.citySP)
    Spinner citySP;

    @BindView(R.id.phoneET)
    EditText phoneET;
    @BindView(R.id.maleRB)
    RadioButton maleRB;
    @BindView(R.id.femaleRB)
    RadioButton femaleRB;
    @BindView(R.id.genderRG)
    RadioGroup genderRG;
    @BindView(R.id.loading)
    ProgressBar loading;

    SharedPrefDueDate pref;

    private ArrayList<GovernmentModel> govers  = new ArrayList<>();
    private ArrayList<CityModel> cities  = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        pref = new SharedPrefDueDate(this);

        getGover();
//        getCities();

    }


    @OnClick(R.id.registerBtn)
    void register() {


        if (!validate())
            return;


        // here to send the data to server and to go to the main activity
        User model = new User();

        model.setId(random());
        model.setName(nameET.getText().toString());
        model.setActive("1");
        model.setTime(System.currentTimeMillis()+"");

        model.setCity(cities.get(citySP.getSelectedItemPosition()).getName());
        model.setGovernment(govers.get(goverSP.getSelectedItemPosition()).getName());

        model.setPhone(phoneET.getText().toString());
        model.setActive("1");


        String gender = "";
        if (maleRB.isChecked())
            gender = "male";
        else
            gender = "female";

        model.setGender(gender);


        loading.setVisibility(View.VISIBLE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Users")
                .child(model.getId()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!
                // ...



                loading.setVisibility(View.GONE);
                pref.setUserId(model.getId());

                DatabaseReference ref =   FirebaseDatabase.getInstance().getReference().child("Tokens");



                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("google", "getInstanceId failed", task.getException());
                                    return;
                                }

                                // Get new Instance ID token
                                String token = task.getResult().getToken();


                                ref.child(pref.getUserId()).child("token").setValue(token);
                                ref.child(pref.getUserId()).child("id").setValue(pref.getUserId());

                                // Log and toast
//                                String msg = getString(R.string.msg_token_fmt, token);
//                                Log.d(TAG, msg);
//                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });


                Intent i = new Intent(RegisterActivity.this, InviteActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Write failed
                // ...
                loading.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this, "حدث خطأ برجاء المحاولة مره اخري ", Toast.LENGTH_LONG).show();

            }
        });

//        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
    }


    private boolean validate() {

        if (nameET.getText().toString().isEmpty()  || phoneET.getText().toString().isEmpty()) {

            Toast.makeText(RegisterActivity.this, "من فضلك قم بملئ جميع البيانات", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }



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

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(RegisterActivity.this, R.layout.spinner_center_item,
                            d);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_center_item); // The drop down view
                    goverSP.setAdapter(spinnerArrayAdapter);


                    goverSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            getCities(govers.get(goverSP.getSelectedItemPosition()).getId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        });
    }


    private void getCities(String goverId){

        loading.setVisibility(View.VISIBLE);

        cities.clear();
        DatabaseReference df = FirebaseDatabase.getInstance().getReference();

        Query q = df.child("City").orderByChild("goverId").equalTo(goverId);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            CityModel model = snapshot.getValue(CityModel.class);
                            cities.add(model);
                        }
                    }
                    loading.setVisibility(View.GONE);


                    String d[] = new String[cities.size()];

                    for (int i = 0; i < cities.size(); i++) {
                        d[i] = cities.get(i).getName();
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(RegisterActivity.this, R.layout.spinner_center_item,
                            d);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_center_item); // The drop down view
                    citySP.setAdapter(spinnerArrayAdapter);




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


