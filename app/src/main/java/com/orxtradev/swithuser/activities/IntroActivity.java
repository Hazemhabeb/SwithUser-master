package com.orxtradev.swithuser.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.adapters.ViewPagerAdapter;
import com.orxtradev.swithuser.fragments.HomeFragment;
import com.orxtradev.swithuser.fragments.Intro1Fragment;
import com.orxtradev.swithuser.fragments.Intro2Fragment;
import com.orxtradev.swithuser.fragments.Intro3Fragment;
import com.orxtradev.swithuser.fragments.MenuFragment;
import com.orxtradev.swithuser.fragments.MyOrderFragment;
import com.orxtradev.swithuser.fragments.NotificationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends AppCompatActivity {


    //init the views
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.previousBtn)
    Button previousBtn;
    @BindView(R.id.nextBtn)
    Button nextBtn;
    @BindView(R.id.skipBtn)
    Button skipBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

        setupViewPager(viewpager);

    }


    @OnClick(R.id.nextBtn)
    void nextAction() {

        if (viewpager.getCurrentItem() < 2)
            viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);

        if (viewpager.getCurrentItem()==2){
            skipBtn.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.GONE);

        }else{
            skipBtn.setVisibility(View.GONE);
            nextBtn.setVisibility(View.VISIBLE);
        }



    }

    @OnClick(R.id.previousBtn)
    void previousAction() {

        if (viewpager.getCurrentItem() > 0)
            viewpager.setCurrentItem(viewpager.getCurrentItem() - 1);

        if (viewpager.getCurrentItem()==2){
            skipBtn.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.GONE);

        }else{
            skipBtn.setVisibility(View.GONE);
            nextBtn.setVisibility(View.VISIBLE);
        }

    }


    @OnClick(R.id.skipBtn)void skipAction(){
        startActivity(new Intent(IntroActivity.this,RegisterActivity.class));
        finish();
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Intro1Fragment(), "");
        adapter.addFragment(new Intro2Fragment(), "");
        adapter.addFragment(new Intro3Fragment(), "");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

    }


}
