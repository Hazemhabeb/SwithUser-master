package com.orxtradev.swithuser.activities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
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
import com.orxtradev.swithuser.adapters.ViewPagerAdapter;
import com.orxtradev.swithuser.fragments.HomeFragment;
import com.orxtradev.swithuser.fragments.MenuFragment;
import com.orxtradev.swithuser.fragments.MyOrderFragment;
import com.orxtradev.swithuser.fragments.NotificationFragment;
import com.orxtradev.swithuser.fragments.OrderFragment;
import com.google.android.material.tabs.TabLayout;
import com.orxtradev.swithuser.model.FollowModel;
import com.orxtradev.swithuser.model.User;
import com.orxtradev.swithuser.services.MyNotificationPublisher;
import com.orxtradev.swithuser.utils.SharedPrefDueDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //init the views
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;


    //the icons of tablayout  icon white  don't selected
    private int[] tabIcons = {
            R.drawable.ic_home_gray,
            R.drawable.ic_order_gray,
            R.drawable.ic_notifications_gray,
            R.drawable.ic_menu_gray
    };
    // icon of tab layout selected blue icons
    private int[] tabIconsSelected = {
            R.drawable.ic_home,
            R.drawable.ic_order,
            R.drawable.ic_notifications,
            R.drawable.ic_menu
    };

    private String[] page_titles;


    SharedPrefDueDate pref;

    String notification_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pref = new SharedPrefDueDate(this);

        if (pref.getUserId() == null || pref.getUserId().isEmpty()) {

            Intent i = new Intent(MainActivity.this, IntroActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            return;
        }


        getData();
        page_titles = new String[]{"الرئيسية", "طلباتك", "الأشعارات", "القائمة"};
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


        int dataIntent = getIntent().getIntExtra("data", 0);

        notification_ = getIntent().getStringExtra("notification_");


        if (dataIntent == 1) {
            viewPager.setCurrentItem(1);
        } else if (dataIntent == 2) {
            viewPager.setCurrentItem(2);
        }


        if (notification_ != null) {
            if (notification_.equals("true")) {
                viewPager.setCurrentItem(2);
            }
        }

        getProfileData();

        FirebaseDatabase.getInstance().getReference();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Tokens");


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

    }


    private void getProfileData() {

//        loading.setVisibility(View.VISIBLE);
        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Users").child(pref.getUserId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User model = dataSnapshot.getValue(User.class);

//                loading.setVisibility(View.GONE);
                if (model == null)
                    return;

//
//                nameTV.setText(model.getName());
//                locationTV.setText(model.getCity()+" - "+model.getGovernment());
//                phoneTV.setText(model.getPhone());


                if (model.getActive().equals("0")) {
                    Intent i = new Intent(MainActivity.this, StopActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                loading.setVisibility(View.GONE);
            }
        };
        df.addValueEventListener(postListener);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "");
        adapter.addFragment(new MyOrderFragment(), "");
        adapter.addFragment(new NotificationFragment(), "");
        adapter.addFragment(new MenuFragment(), "");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

    }

    /**
     * set up the tab icons to the tab layout and inti the custom view to it
     */
    private void setupTabIcons() {
        final View view0 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ((ImageView) view0.findViewById(R.id.image_tab)).setImageResource(R.drawable.ic_home);
        ((TextView) view0.findViewById(R.id.title)).setText(page_titles[0]);
        tabLayout.getTabAt(0).setCustomView(view0);


        View view1 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ((ImageView) view1.findViewById(R.id.image_tab)).setImageResource(tabIcons[1]);
        ((TextView) view1.findViewById(R.id.title)).setText(page_titles[1]);
        tabLayout.getTabAt(1).setCustomView(view1);

        View view2 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ((ImageView) view2.findViewById(R.id.image_tab)).setImageResource(tabIcons[2]);
        ((TextView) view2.findViewById(R.id.title)).setText(page_titles[2]);
        tabLayout.getTabAt(2).setCustomView(view2);


        View view3 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ((ImageView) view3.findViewById(R.id.image_tab)).setImageResource(tabIcons[3]);
        ((TextView) view3.findViewById(R.id.title)).setText(page_titles[3]);
        tabLayout.getTabAt(3).setCustomView(view3);


        final View[] selectedImageResources = {view0, view1, view2, view3};

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((ImageView) selectedImageResources[tab.getPosition()].findViewById(R.id.image_tab))
                        .setImageResource(tabIconsSelected[tab.getPosition()]);
                ((TextView) selectedImageResources[tab.getPosition()].findViewById(R.id.title))
                        .setTextColor(getResources().getColor(R.color.colorPrimary));

                tab.setCustomView(selectedImageResources[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((ImageView) selectedImageResources[tab.getPosition()].findViewById(R.id.image_tab))
                        .setImageResource(tabIcons[tab.getPosition()]);

                ((TextView) selectedImageResources[tab.getPosition()].findViewById(R.id.title))
                        .setTextColor(getResources().getColor(R.color.gray5));

                tab.setCustomView(selectedImageResources[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    //todo the track
    //get the data of the user

    private void getData() {


//        loading.setVisibility(View.VISIBLE);

        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("FollowEquipment");

        Query q = df.orderByChild("userId").equalTo(pref.getUserId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            FollowModel model = snapshot.getValue(FollowModel.class);

                            if (model == null) {
                                return;
                            }



                            for (int i = 0; i < model.getPiecesFollow().size(); i++) {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                Date strDate = null;
                                try {
                                    strDate = sdf.parse(model.getPiecesFollow().get(i).getChangeTime());


                                    if (System.currentTimeMillis() >= strDate.getTime()) {
//                                        holder.changeTV.setVisibility(View.VISIBLE);
//                                        holder.changeTimeTV.setText("قم بتغير القطعه الان");
                                        scheduleNotification(MainActivity.this,3000,10);
                                    } else {
//                                        holder.changeTV.setVisibility(View.GONE);

                                        Date c = Calendar.getInstance().getTime();
                                        String formattedDate = sdf.format(c);
                                        String endDate = sdf.format(strDate);

                                        int days = getCountOfDays(formattedDate, endDate);

                                        long milisecond = days *60  * 60  * 24  * 1000;

                                        Log.d("google","this is the milli second " +milisecond);
                                        scheduleNotification(MainActivity.this,milisecond,30);


//                                        holder.changeTimeTV.setText(" باقي  " + days + " يوم علي تغيرها  ");
                                    }


                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            }

                        }
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        q.addValueEventListener(postListener);
    }



    public void scheduleNotification(Context context, long delay, int notificationId) {//delay is after how much time(in millis) from current time you want to schedule the notification

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("سويتش ابلكيشن")
                .setContentText("يوجد شمعة يجب تغيرها الان")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.drawable.logggoo)).getBitmap())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, TrackActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId, "سويتش ابلكيشن",
                    NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription("يوجد شمعة يجب تغيرها الان");
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
            Log.d("google","here in the above 0000");
//            notificationManager.notify(1, notificationBuilder.build());
        } else {
//            notificationManager.notify(0, notificationBuilder.build());
            Log.d("google","here in the below  0000");
        }



        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
    public int getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


    /*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    */

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return (int) dayCount;
    }


}
