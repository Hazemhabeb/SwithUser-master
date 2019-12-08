package com.orxtradev.swithuser.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.adapters.PiecesAdapter;
import com.orxtradev.swithuser.model.CoponModel;
import com.orxtradev.swithuser.model.DayModel;
import com.orxtradev.swithuser.model.DiscountModel;
import com.orxtradev.swithuser.model.GovernmentModel;
import com.orxtradev.swithuser.model.InviteModel;
import com.orxtradev.swithuser.model.NotificationModel;
import com.orxtradev.swithuser.model.PiecesOrderModel;
import com.orxtradev.swithuser.model.PreviewOrder;
import com.orxtradev.swithuser.model.User;
import com.orxtradev.swithuser.model.equiment;
import com.orxtradev.swithuser.model.equimentPieces;
import com.orxtradev.swithuser.model.maintenanceOrder;
import com.orxtradev.swithuser.notification.DownstreamMessage;
import com.orxtradev.swithuser.utils.SharedPrefDueDate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaintanceActivity extends AppCompatActivity {


    //init the views
    @BindView(R.id.goverSP)
    Spinner goverSP;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.equimentSP)
    Spinner equimentSP;
    @BindView(R.id.piecesRV)
    RecyclerView piecesRV;
    //    @BindView(R.id.quantityTV)
//    TextView quantityTV;
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
    @BindView(R.id.fromET)
    EditText fromET;
    @BindView(R.id.toET)
    EditText toET;
    @BindView(R.id.coponET)
    EditText coponET;
    @BindView(R.id.noteET)
    EditText noteET;
    @BindView(R.id.shippingTV)
    TextView shippingTV;
    @BindView(R.id.discountTV)
    TextView discountTV;
    @BindView(R.id.totalPriceTV)
    TextView totalPriceTV;
    @BindView(R.id.daySP)
    Spinner daySp;
    @BindView(R.id.fromSp)
    Spinner fromSp;
    @BindView(R.id.toSP)
    Spinner toSP;


    private ArrayList<GovernmentModel> govers = new ArrayList<>();
    private ArrayList<equiment> equiments = new ArrayList<>();
    private ArrayList<equimentPieces> pieces = new ArrayList<>();


    SharedPrefDueDate pref;

    PiecesAdapter adapter;

    Double discountValue;
    Double discountPersent;

    String dataPiece = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintance);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        pref = new SharedPrefDueDate(this);
        //the piece intent
        dataPiece = getIntent().getStringExtra("piece");


        discountValue = 0.0;
        discountPersent = 0.0;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MaintanceActivity.this, RecyclerView.HORIZONTAL, false);

        adapter = new PiecesAdapter(MaintanceActivity.this, pieces, dataPiece);

        piecesRV.setLayoutManager(layoutManager);
        piecesRV.setAdapter(adapter);


        getGover();
        getequiment();
        getPieces();

        checkPreview();
        getDiscount();

//        ArrayAdapter<CharSequence> hotelAdapter = ArrayAdapter.createFromResource(MaintanceActivity.this,
//                R.array.gavor_array, R.layout.spinner_center_item);
//
//        hotelAdapter.setDropDownViewResource(R.layout.spinner_center_item);
//        daySp.setAdapter(hotelAdapter);


        // here to get the date for next 7 days





    }

    @OnClick(R.id.coponBtn)
    void coponAction() {

        if (coponET.getText().toString().isEmpty()) {
            Toast.makeText(MaintanceActivity.this, "قم بادخال الكوبون", Toast.LENGTH_LONG).show();
            return;
        }


        //check if the copon is correct or not
        loading.setVisibility(View.VISIBLE);

        DatabaseReference df = FirebaseDatabase.getInstance().getReference();

        Query q = df.child("Copon").orderByChild("code").equalTo(coponET.getText().toString());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(MaintanceActivity.this, "الكوبون غير صحيح من فضلك قم بادخاله مره اخري", Toast.LENGTH_LONG).show();
                    coponET.setText("");

                    loading.setVisibility(View.GONE);
                    return;
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CoponModel model = snapshot.getValue(CoponModel.class);


                    loading.setVisibility(View.GONE);

                    if (model == null) {
                        Toast.makeText(MaintanceActivity.this, "الكوبون غير صحيح من فضلك قم بادخاله مره اخري", Toast.LENGTH_LONG).show();
                        coponET.setText("");
                    } else {


                        // here to add the user can use the copon only one
                        checkCoponOne(model);


                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        q.addListenerForSingleValueEvent(postListener);

    }

    private void checkCoponOne(CoponModel model) {


        ArrayList<maintenanceOrder> orders = new ArrayList<>();


        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        df.child("MaintenanceOrders").orderByChild("userId").equalTo(pref.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            maintenanceOrder model = snapshot.getValue(maintenanceOrder.class);

                            for (int i = 0; i < orders.size(); i++) {
                                if (orders.get(i).getId().equals(model.getId())) {
                                    orders.remove(i);
                                }
                            }
                            orders.add(model);
                        }
                    }

                }

                for (int i = 0; i < orders.size(); i++) {
                    if (orders.get(i).getCoponId().equals(model.getCode())) {

                        Toast.makeText(MaintanceActivity.this, "تم استخدام الكوبون من قبل  ", Toast.LENGTH_LONG).show();
                        coponET.setText("");
                        return;
                    }
                }


                discountPersent = Double.parseDouble(model.getValue());

                coponET.setTextColor(getResources().getColor(R.color.colorPrimary));
                Toast.makeText(MaintanceActivity.this, "الكوبون  صحيح ", Toast.LENGTH_LONG).show();


                if (govers.size() == 0)
                    return;

                double price = Double.parseDouble(govers.get(goverSP.getSelectedItemPosition()).getShippingPrice());
                for (int j = 0; j < piecesOrder.size(); j++) {
                    price += Double.parseDouble(piecesOrder.get(j).getPrice()) *
                            Integer.parseInt(piecesOrder.get(j).getQuantity());
                }

                price = price - (((price * discountPersent) / 100) - discountValue);

                if (price < 0)
                    price = 0;

                totalPriceTV.setText(" سعر الخدمة الاجمالي  " + price + " جنيه فقط لا غير ");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        });

    }

    private void checkPreview() {
        loading.setVisibility(View.VISIBLE);

        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        Query q = df.child("PreviewOrders").orderByChild("userId")
                .equalTo(pref.getUserId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                loading.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            PreviewOrder data = snapshot.getValue(PreviewOrder.class);
                            if (data == null)
                                return;

                            nameET.setText(data.getName());
                            phone2ET.setText(data.getPhone2());
                            phoneET.setText(data.getPhone());
//                goverSP.setText(data.getGovernment());
                            addressET.setText(data.getAddress());
                            buldingET.setText(data.getBuildingNumber());
                            appartementET.setText(data.getFlatNumber());
                            nearET.setText(data.getLandmark());

                            for (int i = 0; i < govers.size(); i++) {
                                if (govers.get(i).getName().equals(data.getGovernment())) {
                                    goverSP.setSelection(i);
                                }
                            }

                            for (int i = 0; i < equiments.size(); i++) {
                                if (equiments.get(i).getName().equals(data.getEquimentTypeId())) {
                                    equimentSP.setSelection(i);
                                }
                            }
                        }
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        };
        q.addValueEventListener(postListener);
    }


    // here to add the arraylist to the model

    ArrayList<PiecesOrderModel> piecesOrder = new ArrayList<>();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPiecesAddedEvent(PiecesOrderModel event) {

        if (piecesOrder.size() == 0) {
            if (!event.getQuantity().equals("0")) {
                piecesOrder.add(event);
            }
        }
        for (int i = 0; i < piecesOrder.size(); i++) {
            if (piecesOrder.get(i).getId().equals(event.getId())) {

                piecesOrder.remove(i);
                if (!event.getQuantity().equals("0")) {
                    piecesOrder.add(event);
                }
                break;
            }
            if (i == piecesOrder.size() - 1) {
                if (!event.getQuantity().equals("0")) {
                    piecesOrder.add(event);
                }
            }
        }

        if (govers.size() == 0)
            return;

        double price = Double.parseDouble(govers.get(goverSP.getSelectedItemPosition()).getShippingPrice());
        for (int j = 0; j < piecesOrder.size(); j++) {
            price += Double.parseDouble(piecesOrder.get(j).getPrice()) *
                    Integer.parseInt(piecesOrder.get(j).getQuantity());
        }

        price = price - (((price * discountPersent) / 100) - discountValue);

        if (price < 0)
            price = 0;

        totalPriceTV.setText(" سعر الخدمة الاجمالي  " + price + " جنيه فقط لا غير ");
    }

    @OnClick(R.id.contactusBtn)
    void contactAction() {

        startActivity(new Intent(MaintanceActivity.this, ContactUsActivity.class));

    }

    maintenanceOrder model = null;

    @OnClick(R.id.maintanceBtn)
    void maintanceAction() {


        if (!validate())
            return;


        model = new maintenanceOrder();
        model.setId(random());
        model.setTime(System.currentTimeMillis() + "");
        model.setUserId(pref.getUserId());
        model.setName(nameET.getText().toString());
        model.setPhone(phoneET.getText().toString());
        model.setPhone2(phone2ET.getText().toString());
        model.setGovernment(govers.get(goverSP.getSelectedItemPosition()).getName());
        model.setOrderNumber(new Random().nextInt(1000000) + "");
        model.setAddress(addressET.getText().toString());
        model.setBuildingNumber(buldingET.getText().toString());
        model.setFlatNumber(appartementET.getText().toString());
        model.setLandmark(nearET.getText().toString());
        model.setDay(daySp.getSelectedItem().toString());
        model.setFromClock(fromSp.getSelectedItem().toString());
        model.setToClock(toSP.getSelectedItem().toString());
        model.setEquimentId(equiments.get(equimentSP.getSelectedItemPosition()).getName());

        // here to add the pieces
        model.setPiecesOrder(piecesOrder);

//        model.setPieceId(pieces.get(piecesSP.getSelectedItemPosition()).getName());
//        model.setPieceNumber(quantityTV.getText().toString());
        model.setCoponId(coponET.getText().toString());
        model.setNote(noteET.getText().toString());
        model.setStatus("0");
        model.setShippingPrice(govers.get(goverSP.getSelectedItemPosition()).getShippingPrice());


        // HERE THE PRICE OF THE MAINTENANCE


        double price = Double.parseDouble(govers.get(goverSP.getSelectedItemPosition()).getShippingPrice());


        for (int i = 0; i < piecesOrder.size(); i++) {
            price += Double.parseDouble(piecesOrder.get(i).getPrice()) *
                    Integer.parseInt(piecesOrder.get(i).getQuantity());
        }


        model.setTotalPrice(price + "");
        model.setDiscount(discountPersent + "");
        model.setShippingPrice(govers.get(goverSP.getSelectedItemPosition()).getShippingPrice());


        loading.setVisibility(View.VISIBLE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("MaintenanceOrders")
                .child(model.getId()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!


                NotificationModel notificationModel = new NotificationModel();
                notificationModel.setId(random());
                notificationModel.setDataId(model.getId());
                notificationModel.setName("تطبيق سويتش");
                notificationModel.setDesc("طلب صيانة جديد");
                notificationModel.setTime(System.currentTimeMillis() + "");
                notificationModel.setType("2");
                notificationModel.setStatus("0");


                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                ref1.child("Notification")
                        .child(notificationModel.getId()).setValue(notificationModel);

                DownstreamMessage downstreamMessage = new DownstreamMessage();

                String[] data = new String[4];
                data[0] = govers.get(goverSP.getSelectedItemPosition()).getId();
                data[1] = model.getId();
                data[2] = "2";
                data[3] = notificationModel.getId();

                downstreamMessage.execute(data);


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.child("Users")
                        .child(pref.getUserId()).child("discount").setValue("0");

                Log.d("google", "user send the notification");

//                loading.setVisibility(View.VISIBLE);

                sendDiscount();

                // ...

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Write failed
                // ...
                loading.setVisibility(View.GONE);
                Toast.makeText(MaintanceActivity.this, "حدث خطأ برجاء المحاولة مره اخري ", Toast.LENGTH_LONG).show();
                finish();

            }
        });


    }

    // set the discount to the other user
    private void sendDiscount() {
//        loading.setVisibility(View.GONE);


        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        Query q = df.child("InvitationUsers").orderByChild("userId").equalTo(pref.getUserId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Log.d("google", "error discount code 333");
//                    Toast.makeText(InviteActivity.this, "برجاء التأكد من الكود ", Toast.LENGTH_LONG).show();
                    loading.setVisibility(View.GONE);
                    Toast.makeText(MaintanceActivity.this, "تم ارسال الطلب بنجاح ", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    InviteModel m = snapshot.getValue(InviteModel.class);

                    loading.setVisibility(View.GONE);

                    if (m == null) {
                        Log.d("google", "error discount code 333");
//                        Toast.makeText(InviteActivity.this, "برجاء التأكد من الكود ", Toast.LENGTH_LONG).show();
                        Toast.makeText(MaintanceActivity.this, "تم ارسال الطلب بنجاح ", Toast.LENGTH_LONG).show();
                        finish();
                    } else {

//                        Log.d("google","this is the discount        "+model.getDiscount());

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


                        ref.child("Users")
                                .child(m.getInvitationId()).child("discount").setValue(((Double.parseDouble(model.getTotalPrice())) / 10) + "");


                        Toast.makeText(MaintanceActivity.this, "تم ارسال الطلب بنجاح ", Toast.LENGTH_LONG).show();
                        finish();

                        return;


                    }

                }

                Toast.makeText(MaintanceActivity.this, "تم ارسال الطلب بنجاح ", Toast.LENGTH_LONG).show();
                finish();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        q.addValueEventListener(postListener);
    }


    private boolean validate() {

        if (nameET.getText().toString().isEmpty() || phoneET.getText().toString().isEmpty() ||
                addressET.getText().toString().isEmpty() || buldingET.getText().toString().isEmpty() ||
                appartementET.getText().toString().isEmpty() || nearET.getText().toString().isEmpty()) {
            Toast.makeText(MaintanceActivity.this, "من فضلك قم بملئ جميع البيانات ", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @OnClick(R.id.fromET)
    void fromAction() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MaintanceActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                fromET.setText(selectedHour + ":" + selectedMinute);
            }

        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");

        mTimePicker.show();


    }

    @OnClick(R.id.toET)
    void toAction() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MaintanceActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                toET.setText(selectedHour + ":" + selectedMinute);
            }

        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();


    }

    // to get the governoment

    private void getGover() {

        loading.setVisibility(View.VISIBLE);

        govers.clear();
        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        df.child("Government").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                govers.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            GovernmentModel model = snapshot.getValue(GovernmentModel.class);
                            govers.add(model);
                        }
                    }
                    loading.setVisibility(View.GONE);


                    String[] d = new String[govers.size()];

                    for (int i = 0; i < govers.size(); i++) {
                        d[i] = govers.get(i).getName();
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(MaintanceActivity.this, R.layout.spinner_center_item,
                            d);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_center_item); // The drop down view
                    goverSP.setAdapter(spinnerArrayAdapter);


                    goverSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if (pieces.size() != 0) {
                                shippingTV.setText(" رسوم الخدمة لمحافظة " + govers.get(i).getName() + "  " + govers.get(i).getShippingPrice() + " جنيه ");

                                double price = Double.parseDouble(govers.get(i).getShippingPrice());
                                for (int j = 0; j < piecesOrder.size(); j++) {

                                    price += Double.parseDouble(piecesOrder.get(j).getPrice()) *
                                            Integer.parseInt(piecesOrder.get(j).getQuantity());
                                }


                                price = price - (((price * discountPersent) / 100) - discountValue);

                                if (price < 0)
                                    price = 0;

//                                        * Double.parseDouble(pieces.get(piecesSP.getSelectedItemPosition()).getPrice()));
                                totalPriceTV.setText(" سعر الخدمة الاجمالي  " + price + " جنيه فقط لا غير ");
                            }


                            getAvaliableDates(govers.get(i));
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


    private void getAvaliableDates(GovernmentModel model) {

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MM-yyyy", new Locale("ar"));

        ArrayList<String> ddd = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = sdf.format(calendar.getTime());
            Log.i("google", day);

            if (i == 0)
                continue;


//            if (model.getTimes().get(""))
//            d[i - 1] = day;
            ddd.add(day);
        }

        for (int i=0;i<model.getTimes().size();i++) {
            if (i==0) {
                if (model.getTimes().get(i).getAvailable().equals("0")) {
                    for (int j = 0; j < ddd.size(); j++) {
                        if (ddd.get(j).contains("السبت")) {
                            ddd.remove(j);
                            break;
                        }
                    }

                }
            }else if (i==1){
                if (model.getTimes().get(i).getAvailable().equals("0")) {
                    for (int j = 0; j < ddd.size(); j++) {
                        if (ddd.get(j).contains("الأحد")) {
                            ddd.remove(j);
                            break;
                        }
                    }

                }
            }else if (i==2){
                if (model.getTimes().get(i).getAvailable().equals("0")) {
                    for (int j = 0; j < ddd.size(); j++) {
                        if (ddd.get(j).contains("الاثنين")) {
                            ddd.remove(j);
                            break;
                        }
                    }

                }
            }else if (i==3){
                if (model.getTimes().get(i).getAvailable().equals("0")) {
                    for (int j = 0; j < ddd.size(); j++) {
                        if (ddd.get(j).contains("الثلاثاء")) {
                            ddd.remove(j);
                            break;
                        }
                    }

                }
            }else if (i==4){
                if (model.getTimes().get(i).getAvailable().equals("0")) {
                    for (int j = 0; j < ddd.size(); j++) {
                        if (ddd.get(j).contains("الأربعاء")) {
                            ddd.remove(j);
                            break;
                        }
                    }

                }
            }else if (i==5){
                if (model.getTimes().get(i).getAvailable().equals("0")) {
                    for (int j = 0; j < ddd.size(); j++) {
                        if (ddd.get(j).contains("الخميس")) {
                            ddd.remove(j);
                            break;
                        }
                    }

                }
            }else if (i==6){
                if (model.getTimes().get(i).getAvailable().equals("0")) {
                    for (int j = 0; j < ddd.size(); j++) {
                        if (ddd.get(j).contains("الجمعة")) {
                            ddd.remove(j);
                            break;
                        }
                    }

                }
            }
        }



        String d[] = new String[ddd.size()];
        for (int i=0;i<ddd.size();i++){
            d[i] = ddd.get(i);
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(MaintanceActivity.this, R.layout.spinner_center_item,
                d);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_center_item); // The drop down view
        daySp.setAdapter(spinnerArrayAdapter);


        //todo here the available time
        daySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                availableTime(model,d[i]);
                availableTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void availableTime(){
        // here the time

        ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, R.layout.spinner_center_item);

        fromAdapter.setDropDownViewResource(R.layout.spinner_center_item); // The drop down view

        fromSp.setAdapter(fromAdapter);

        List<String> times = Arrays.asList(getResources().getStringArray(R.array.time_array));

//        List<String> tttt = new ArrayList<>();
//
//
//        DayModel dayModel = null;
//        for (int i = 0;i<model.getTimes().size();i++){
//            if (model.getTimes().get(i).getDay().contains("Sat")){
//                if (day.contains("السبت")){
//
//                }
//            }
//        }
//        for (int i =0;i<times.size();i++){
//
//
//            if (times.get(i).contains(startHour)){
//
//                for (int j = i ; j < times.size();j++){
//                    tttt.add(times.get(j));
//                }
//                break;
//
//            }
//        }


//        String ds[] = new String[tttt.size()];
//        for (int k = 0; k < tttt.size(); k++) {
//            ds[k] = tttt.get(k);
//        }
//
//        ArrayAdapter<String> toAdapter = new ArrayAdapter<>(MaintanceActivity.this, R.layout.spinner_center_item,
//                ds);
//        toAdapter.setDropDownViewResource(R.layout.spinner_center_item); // The drop down view
//        fromSp.setAdapter(toAdapter);





//        List<String> times = Arrays.asList(getResources().getStringArray(R.array.time_array));



        Log.d("google", "this is the time   " + times.size());
        fromSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (i > times.size() - 1)
                    return;

                ArrayList<String> dd = new ArrayList<>();


                for (int j = i + 2; j < times.size(); j++) {
                    dd.add(times.get(j));
                }

                String d[] = new String[dd.size()];
                for (int k = 0; k < dd.size(); k++) {
                    d[k] = dd.get(k);
                }

                ArrayAdapter<String> toAdapter = new ArrayAdapter<>(MaintanceActivity.this, R.layout.spinner_center_item,
                        d);
                toAdapter.setDropDownViewResource(R.layout.spinner_center_item); // The drop down view
                toSP.setAdapter(toAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getequiment() {

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


                    String[] d = new String[equiments.size()];

                    for (int i = 0; i < equiments.size(); i++) {
                        d[i] = equiments.get(i).getName();
                    }


                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(MaintanceActivity.this, R.layout.spinner_center_item,
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


    private void getPieces() {

        loading.setVisibility(View.VISIBLE);

        pieces.clear();
        adapter.notifyDataSetChanged();
        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        df.child("EquipmentPieces").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            equimentPieces model = snapshot.getValue(equimentPieces.class);
                            pieces.add(model);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    loading.setVisibility(View.GONE);

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


    private void getDiscount() {


        loading.setVisibility(View.VISIBLE);
        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Users").child(pref.getUserId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User model = dataSnapshot.getValue(User.class);

                loading.setVisibility(View.GONE);
                if (model == null)
                    return;


                if (model.getDiscount().equals("0")) {
                    discountTV.setText("ليس لديك رصيد خصومات الان");
                    discountValue = 0.0;
                } else {
                    discountTV.setText(" لديك  " + model.getDiscount() + " من الخصومات سيتم استخدامها عند الطلب ");
                    discountValue = Double.parseDouble(model.getDiscount());

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        };
        df.addValueEventListener(postListener);
    }


}
