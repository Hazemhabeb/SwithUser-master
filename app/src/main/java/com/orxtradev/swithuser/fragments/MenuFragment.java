package com.orxtradev.swithuser.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.activities.CodeActivity;
import com.orxtradev.swithuser.activities.ContactUsActivity;
import com.orxtradev.swithuser.activities.DiscountActivity;
import com.orxtradev.swithuser.activities.IntroActivity;
import com.orxtradev.swithuser.activities.MainActivity;
import com.orxtradev.swithuser.model.DiscountModel;
import com.orxtradev.swithuser.model.FollowModel;
import com.orxtradev.swithuser.model.GovernmentModel;
import com.orxtradev.swithuser.model.PiecesFollowModel;
import com.orxtradev.swithuser.model.PiecesOrderModel;
import com.orxtradev.swithuser.model.User;
import com.orxtradev.swithuser.notification.DownstreamMessage;
import com.orxtradev.swithuser.utils.SharedPrefDueDate;


import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MenuFragment extends Fragment {

    public MenuFragment() {
        // Required empty public constructor
    }


    //init the views
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.locationTV)
    TextView locationTV;
    @BindView(R.id.phoneTV)
    TextView phoneTV;
    @BindView(R.id.loading)
    ProgressBar loading;


    SharedPrefDueDate pref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu___, container, false);
        ButterKnife.bind(this,view);

        pref = new SharedPrefDueDate(getContext());
        getProfileData();

        return view;
    }



    private void getProfileData (){

        loading.setVisibility(View.VISIBLE);
        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Users").child(pref.getUserId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User model = dataSnapshot.getValue(User.class);

                loading.setVisibility(View.GONE);
                if (model==null)
                    return;

                nameTV.setText(model.getName());
                locationTV.setText(model.getCity()+" - "+model.getGovernment());
                phoneTV.setText(model.getPhone());



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        };
        df.addValueEventListener(postListener);
    }



    @OnClick(R.id.logoutParent)void logoutAction(){

        pref.setUserId("");
        Intent i = new Intent(getContext(), IntroActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

        // here to add the send time to server
//        GovernmentModel model =  new GovernmentModel("");
//        model.setId("-Lq8ODrxgTqzlt5pH1ns");
//        model.setName("محافظة القاهرة");
//        model.setShippingPrice("0");
//
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//        ref.child("Government")
//                .child(model.getId()).setValue(model);

    }

    @OnClick(R.id.contactusBtn)void contactAction(){
        startActivity(new Intent(getContext(), ContactUsActivity.class));
    }
    @OnClick(R.id.discountParent)void discountAction(){
        startActivity(new Intent(getContext(), DiscountActivity.class));
    }
    @OnClick(R.id.codeParent)void codeAction(){
        startActivity(new Intent(getContext(), CodeActivity.class));
    }

    @OnClick(R.id.rateParent)void rateAction(){
        Uri uri = Uri.parse("market://details?id=com.orxtradev.swithuser");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=com.orxtradev.swithuser")));
        }


    }

    @OnClick(R.id.ordersParent)void orderAction(){
        Intent i = new Intent(getContext(), MainActivity.class);
        i.putExtra("data",1);
        startActivity(i);
        getActivity().finish();
    }
    @OnClick(R.id.notifParent)void notificationAction(){
        Intent i = new Intent(getContext(), MainActivity.class);
        i.putExtra("data",2);
        startActivity(i);
        getActivity().finish();
    }
    @OnClick(R.id.addnotificatio)void notification(){


//        FollowModel model = new FollowModel();
//
//        model.setId(random());
//        model.setUserId(pref.getUserId());
//        model.setEquipment("جهاز خمسه شمعه");
//        model.setName("محمد احمد حسن ابرراهيم ");
//        model.setTime(System.currentTimeMillis()+"");
//        ArrayList<PiecesFollowModel> pieces = new ArrayList<>();
//
//
//        PiecesFollowModel follow = new PiecesFollowModel();
//        follow.setId("-LnqotvfWdWCfSVgXVU7");
//        follow.setChangeTime("12/11/2019");
//        follow.setName("شمعه اولي مبدأي");
//
//
//        PiecesFollowModel follow1 = new PiecesFollowModel();
//        follow1.setId("-LnqotvfWdWCfSVgXVU7");
//        follow1.setChangeTime("12/12/2019");
//        follow1.setName("شمعه ثانية");
//
//        pieces.add(follow);
//        pieces.add(follow1);
//
//        model.setPiecesFollow(pieces);
//
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//        ref.child("FollowEquipment")
//                .child(model.getUserId()).setValue(model);




//        startActivity(new Intent(getContext(), IntroActivity.class));


//        DownstreamMessage downstreamMessage = new DownstreamMessage();
//
//        downstreamMessage.execute("here");


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
